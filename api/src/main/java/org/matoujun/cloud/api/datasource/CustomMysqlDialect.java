package org.matoujun.cloud.api.datasource;

import org.hibernate.dialect.MySQL57InnoDBDialect;

/**
 * @author matoujun

 */
public class CustomMysqlDialect extends MySQL57InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
