package com.io.loopit.paysheet.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("spring.datasource.hibernate")
public class DatasourceProperties {
    private String ddlAuto;
    private String showSql;
}
