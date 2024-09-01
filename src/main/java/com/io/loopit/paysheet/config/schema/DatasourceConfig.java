package com.io.loopit.paysheet.config.schema;

import com.io.loopit.paysheet.properties.DatasourceProperties;
import com.io.loopit.paysheet.model.payroll.EmployeeEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "payrollFactory",
        transactionManagerRef = "payrollTransactionManager",
        basePackages = "com.io.loopit.paysheet.repository.payroll"
)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DatasourceConfig {

    private final DatasourceProperties datasourceProperties;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties payrollProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource payroll(
            @Qualifier("payrollProperties") DataSourceProperties payrollProperties
    ){
        return payrollProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean payrollFactory(
            @Qualifier("payroll") DataSource payroll,
            EntityManagerFactoryBuilder builder
    ){

        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", datasourceProperties.getDdlAuto());
        properties.put("hibernate.show_sql", datasourceProperties.getShowSql());

        return builder.dataSource(payroll)
                      .properties(properties)
                      .packages(EmployeeEntity.class)
                      .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager payrollTransactionManager(
            @Qualifier("payrollFactory") EntityManagerFactory payrollFactory
    ){
        return new JpaTransactionManager(payrollFactory);
    }

}