package com.fyl.boot.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Deprecated
//@Order(10)
//@Configuration
//@MapperScan(basePackages = "com.fyl.boot.dao2", sqlSessionTemplateRef = "sqlSessionTemplate" + MybatisConfig2.SUFFIX)
public class MybatisConfig2 {
    //bean后缀
    static final String SUFFIX = "_2";
    //Mapper.xml路径
    static final String MAPPER_LOCATIONS = "classpath*:com.fyl.boot.dao2.mapper/*Mapper.xml";

    @Bean(name = "dataSource" + SUFFIX)
    @ConfigurationProperties(prefix = "spring.datasource2")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactory" + SUFFIX)
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate" + SUFFIX)
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean(name = "transactionManager" + SUFFIX)
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
