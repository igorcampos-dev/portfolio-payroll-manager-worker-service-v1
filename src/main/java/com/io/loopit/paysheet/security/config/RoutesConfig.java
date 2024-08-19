package com.io.loopit.paysheet.security.config;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RoutesConfig {

    public static Map<String, HttpMethod> getAdminRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/admin/paycheck", HttpMethod.POST);
        routes.put("/v1/admin/paycheck/employees", HttpMethod.GET);
        routes.put("/v1/admin/paycheck", HttpMethod.PUT);
        routes.put("/v1/admin/paycheck", HttpMethod.DELETE);
        return routes;
    }

    public static Map<String, HttpMethod> getUserRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/employee/paycheck/info-basics", HttpMethod.GET);
        routes.put("/v1/employee/paycheck", HttpMethod.GET);
        return routes;
    }

    public static Map<String, HttpMethod> getDefaultPublicRoutes() {
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v3/api-docs/**", HttpMethod.GET);
        routes.put("/v3/api-docs.yaml", HttpMethod.GET);
        routes.put("/swagger-ui/index.html", HttpMethod.GET);
        routes.put("/swagger-ui/**", HttpMethod.GET);
        routes.put("/swagger-ui.html", HttpMethod.GET);
        return routes;
    }

    public static Map<String, HttpMethod> getPublicRoutes(){
        Map<String, HttpMethod> routes = new HashMap<>();
        routes.put("/v1/auth/login", HttpMethod.POST);
        routes.put("/v1/auth/register", HttpMethod.POST);
        return routes;
    }

}