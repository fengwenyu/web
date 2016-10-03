package com.shangpin.config;

import com.github.pagehelper.PageHelper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by 文宇 on 2016/9/27.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan
@MapperScan(basePackages = "com.shangpin.service.mapper")
public class MybatisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new HikariDataSource();
    }

    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource){
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);
        dstm.setDefaultTimeout(10);
        return dstm;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource){
        SqlSessionFactoryBean session = new	SqlSessionFactoryBean();
        session.setDataSource(dataSource);
        session.setTypeAliasesPackage("com.shangpin.pojo");
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        session.setPlugins(new Interceptor[]{pageHelper});
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //将加载多个绝对匹配的所有Resource
        //然后进行遍历模式匹配
        Resource[] resources=null;
        try {
            resources = resolver.getResources("classpath*:/mybatis/*.xml");
            session.setMapperLocations(resources);
            return session.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 如果走自动扫描mapper接口，上面两个sqlsession,sqltemplate的在scaner中可不指定<br/>
     * scaner会自动创建MapperFactoryBean然后自动装配上面两个(单一数据源的情况)<br/>
     * 两个以上的数据源时，需要指定，如设置：sqlSessionFactoryBeanName
     * @return
     */
    /*@Bean
    @ConditionalOnBean(value={SqlSessionFactory.class,SqlSessionTemplate.class})
    public MapperScannerConfigurer mapScaner(){
        MapperScannerConfigurer scaner = new MapperScannerConfigurer();
        scaner.setBasePackage("com.shangpin.service.,apper");
        //自动扫描继承该dao的接口
        scaner.setAnnotationClass(Repository.class);

        //若是有多个数据源需要指定session 的bean name
        //scaner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return scaner;
    }*/
}
