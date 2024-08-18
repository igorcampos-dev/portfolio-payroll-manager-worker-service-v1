package com.io.loopit.paysheet.config.schema.prop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("spring.datasource-secondary.hibernate")
public class RhProp {
    private String ddlAuto;
    private String showSql;
}