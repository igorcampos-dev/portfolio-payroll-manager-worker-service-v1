package com.io.loopit.paysheet.config.schema;

import com.io.loopit.paysheet.properties.SecondaryDatasourceProperties;
import com.io.loopit.paysheet.model.rh.EmployeeRhEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "rhFactory",
        transactionManagerRef = "rhTransactionManager",
        basePackages = "com.io.loopit.paysheet.repository.rh"
)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SecondaryDatasourceConfig {

    private final SecondaryDatasourceProperties rhConfiguration;

    @Bean
    @ConfigurationProperties("spring.datasource-secondary")
    public DataSourceProperties rhProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource rh(
            @Qualifier("rhProperties") DataSourceProperties rhProperties
    ){
        return rhProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean rhFactory(
            @Qualifier("rh") DataSource rh,
            EntityManagerFactoryBuilder builder)
    {

        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", rhConfiguration.getDdlAuto());
        properties.put("hibernate.show_sql", rhConfiguration.getShowSql());

        return builder.dataSource(rh)
                      .properties(properties)
                      .packages(EmployeeRhEntity.class)
                      .build();
    }

    @Bean
    public PlatformTransactionManager rhTransactionManager(
            @Qualifier("rhFactory") EntityManagerFactory rhFactory
    ){
        return new JpaTransactionManager(rhFactory);
    }

}