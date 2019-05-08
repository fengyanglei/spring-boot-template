package com.fyl.boot.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * MyBatis基础配置
 */
@Deprecated
//@Configuration
//@MapperScan(basePackages = "com.fyl.boot.dao", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig implements TransactionManagementConfigurer {
    //Mapper.xml路径
    static final String MAPPER_LOCATIONS = "classpath*:com.fyl.boot.dao.mapper/*Mapper.xml";

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource setDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(setDataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(setSqlSessionFactoryBean());
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(setDataSource());
    }
}
