package org.matoujun.cloud.common;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author matoujun
 */
public class BeanUtil {

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * copy the special properties from src to target
     *
     * @param src
     * @param target
     * @param copyProperties
     * @throws IntrospectionException
     */
    public static void copyProperties(Object src, Object target, String... copyProperties) throws
            IntrospectionException {
        if (Objects.nonNull(copyProperties) && copyProperties.length > 0) {
            List<PropertyDescriptor> targetPds = new ArrayList<>();
            for (String property : copyProperties) {
                PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
                targetPds.add(descriptor);
            }
            targetPds.stream().parallel().forEach(targetPd -> {
                Method writeMethod = targetPd.getWriteMethod();
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(src.getClass(),
                        targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
                                    readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(src);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()
                            )) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from " +
                                            "source to target", ex);
                        }
                    }
                }
            });
        }
    }

    public static <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleTypes = new ArrayList<>();
        for (Object field : tuple) {
            tupleTypes.add(field.getClass());
        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple
                    .length]));
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> map(Class<T> type, List<Object[]> records) {
        List<T> result = new LinkedList<>();
        for (Object[] record : records) {
            result.add(map(type, record));
        }
        return result;
    }

}