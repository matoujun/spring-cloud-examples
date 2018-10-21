package org.matoujun.cloud.api.dao.impl;

import org.matoujun.cloud.api.dao.NativeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author matoujun

 */
@Service
@Slf4j
public class NativeDaoImpl implements NativeDao {
    @Autowired
    @Qualifier("cloudJdbcTemplate")
    public JdbcTemplate jdbcTemplate;


    @Override
    public <T> T loadById(Class<T> clz, long id) {
        String table = clz.getSimpleName();
        String selectSQL = "SELECT * FROM " + table + " WHERE id = ? and state >= 0 ";
        List<T> dbs = jdbcTemplate.query(selectSQL, new Object[]{id}, new
                BeanPropertyRowMapper(clz));
        if (dbs != null && dbs.size() > 0) {
            return dbs.get(0);
        }
        return null;
    }

}
