package com.creditcloud.jpa.unit_test.configuration;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * 解决JPA与MySQL 表名大小写转换问题
 * @author steven
 */
public class PhysicalNamingStrategyImpl extends SpringPhysicalNamingStrategy {
    @Override
    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return false;
    }
}
