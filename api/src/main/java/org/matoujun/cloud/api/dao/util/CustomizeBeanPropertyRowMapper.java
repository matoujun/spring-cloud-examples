package org.matoujun.cloud.api.dao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author matoujun

 */
public class CustomizeBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {

    /**
     * Logger available to subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * The class we are mapping to
     */
    @Nullable
    private Class<T> mappedClass;

    /**
     * Whether we're strictly validating
     */
    private boolean checkFullyPopulated = false;

    /**
     * Whether we're defaulting primitives when mapping a null value
     */
    private boolean primitivesDefaultedForNullValue = false;

    /**
     * ConversionService for binding JDBC values to bean properties
     */
    @Nullable
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();

    /**
     * Map of the fields we provide mapping for
     */
    @Nullable
    private Map<String, PropertyDescriptor> mappedFields;

    /**
     * Set of bean properties we provide mapping for
     */
    @Nullable
    private Set<String> mappedProperties;


    /**
     * Create a new {@code BeanPropertyRowMapper} for bean-style configuration.
     *
     * @see #setMappedClass
     * @see #setCheckFullyPopulated
     */
    public CustomizeBeanPropertyRowMapper() {
        super();
    }

    /**
     * Create a new {@code BeanPropertyRowMapper}, accepting unpopulated
     * properties in the target bean.
     * <p>Consider using the {@link #newInstance} factory method instead,
     * which allows for specifying the mapped type once only.
     *
     * @param mappedClass the class that each row should be mapped to
     */
    public CustomizeBeanPropertyRowMapper(Class<T> mappedClass) {
        super(mappedClass);
        initialize(mappedClass);
    }

    /**
     * Initialize the mapping meta-data for the given class.
     *
     * @param mappedClass the mapped class
     */
    @Override
    protected void initialize(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.mappedFields = new HashMap<>();
        this.mappedProperties = new HashSet<>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(lowerCaseName(pd.getName()), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!lowerCaseName(pd.getName()).equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                this.mappedProperties.add(pd.getName());
            }
        }
    }

    /**
     * Extract the values for all columns in the current row.
     * <p>Utilizes public setters and result set meta-data.
     *
     * @see java.sql.ResultSetMetaData
     */
    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        T mappedObject = BeanUtils.instantiateClass(this.mappedClass);

        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);

        initBeanWrapper(bw);

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Set<String> populatedProperties = (isCheckFullyPopulated() ? new HashSet<>() : null);

        for (int index = 1; index <= columnCount; index++) {
            String column = JdbcUtils.lookupColumnName(rsmd, index);
            String field = lowerCaseName(column.replaceAll(" ", ""));
            PropertyDescriptor pd = (this.mappedFields != null ? this.mappedFields.get(field) :
                    null);
            if (pd != null) {
                try {
                    Object value = getColumnValue(rs, index, pd);
                    if (rowNumber == 0 && logger.isDebugEnabled()) {
                        logger.debug("Mapping column '" + column + "' to property '" + pd.getName
                                () +
                                "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType())
                                + "'");
                    }

                    try {
                        if ("List".equalsIgnoreCase(pd.getPropertyType().getSimpleName())) {

                            if(StringUtils.isEmpty((String) value)){
                                continue;
                            }
                            Class clz = Class.forName("org.matoujun.cloud.api.model");
                            Type listType = new TypeReference<List<T>>(clz) {}.getType();
                            List list = JSON.parseObject((String) value, listType);
                            bw.setPropertyValue(pd.getName(), list);
                        } else {
                            bw.setPropertyValue(pd.getName(), value);
                        }
                    } catch (TypeMismatchException ex) {
                        if (value == null && this.primitivesDefaultedForNullValue) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("Intercepted TypeMismatchException for row " +
                                        rowNumber +
                                        " and column '" + column + "' with null value when " +
                                        "setting property '" +
                                        pd.getName() + "' of type '" +
                                        ClassUtils.getQualifiedName(pd.getPropertyType()) +
                                        "' on object: " + mappedObject, ex);
                            }
                        } else {
                            throw ex;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (populatedProperties != null) {
                        populatedProperties.add(pd.getName());
                    }
                } catch (NotWritablePropertyException ex) {
                    throw new DataRetrievalFailureException(
                            "Unable to map column '" + column + "' to property '" + pd.getName()
                                    + "'", ex);
                }
            } else {
                // No PropertyDescriptor found
                if (rowNumber == 0 && logger.isDebugEnabled()) {
                    logger.debug("No property found for column '" + column + "' mapped to field " +
                            "'" + field + "'");
                }
            }
        }

        if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
            throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all " +
                    "fields " +
                    "necessary to populate object of class [" + this.mappedClass.getName() + "]: " +
                    this.mappedProperties);
        }

        return mappedObject;
    }
}
