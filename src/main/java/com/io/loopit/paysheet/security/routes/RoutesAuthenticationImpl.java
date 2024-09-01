package com.io.loopit.paysheet.security.routes;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public class RoutesAuthenticationImpl implements RoutesAuthentication {

    @Override
    public void configureRoutes(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry){
        this.getPublicRoutes().forEach((key, value) -> authorizationManagerRequestMatcherRegistry.requestMatchers(value, key).permitAll());
        this.getDefaultPublicRoutes().forEach((key, value) -> authorizationManagerRequestMatcherRegistry.requestMatchers(value, key).permitAll());
        this.getUserRoutes().forEach((key, value) -> authorizationManagerRequestMatcherRegistry.requestMatchers(value, key).hasRole("USER"));
        this.getAdminRoutes().forEach((key, value) -> authorizationManagerRequestMatcherRegistry.requestMatchers(value, key).hasRole("ADMIN"));
        authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();
    }

    private Map<String, HttpMethod> getAdminRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/admin/paycheck", HttpMethod.POST);
        routes.put("/v1/admin/paycheck/employees", HttpMethod.GET);
        routes.put("/v1/admin/paycheck", HttpMethod.PUT);
        routes.put("/v1/admin/paycheck", HttpMethod.DELETE);
        return routes;
    }

    private Map<String, HttpMethod> getUserRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/employee/paycheck/info-basics", HttpMethod.GET);
        routes.put("/v1/employee/paycheck", HttpMethod.GET);
        return routes;
    }

    private Map<String, HttpMethod> getDefaultPublicRoutes() {
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/swagger-ui/swagger-initializer.js", HttpMethod.GET);
        routes.put("/swagger-ui/swagger-ui-standalone-preset.js", HttpMethod.GET);
        routes.put("/swagger-ui/swagger-ui.css", HttpMethod.GET);
        routes.put("/swagger-ui/swagger-ui-bundle.js", HttpMethod.GET);
        routes.put("/swagger-ui/favicon-32x32.png", HttpMethod.GET);
        routes.put("/v3/api-docs", HttpMethod.GET);
        routes.put("/v3/api-docs/swagger-config", HttpMethod.GET);
        routes.put("/swagger-ui/favicon-16x16.png", HttpMethod.GET);
        routes.put("/swagger-ui/index.css", HttpMethod.GET);
        routes.put("/swagger-ui/index.html", HttpMethod.GET);
        return routes;
    }

    private Map<String, HttpMethod> getPublicRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/auth/login", HttpMethod.POST);
        routes.put("/v1/auth/register", HttpMethod.POST);
        return routes;
    }

}
