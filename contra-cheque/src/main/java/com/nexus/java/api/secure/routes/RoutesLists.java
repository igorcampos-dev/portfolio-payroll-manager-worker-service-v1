package com.nexus.java.api.secure.routes;

import com.nexus.security.dto.Routes;
import org.springframework.http.HttpMethod;
import java.util.List;

public class RoutesLists {

    public static List<Routes> getHasRoleAdminRoutes(){
        return List.of(
                new Routes("/nexus/admin/employees", HttpMethod.GET),
                new Routes("/nexus/admin/{userId}/{paycheckDate}", HttpMethod.DELETE),
                new Routes("/nexus/admin/{userId}/{paycheckDate}", HttpMethod.POST),
                new Routes("/nexus/admin/{userId}/{paycheckDate}", HttpMethod.PUT)
        );
    }

    public static List<Routes> getPermitAllRoutes(){
        return List.of(
                new Routes("/v3/api-docs/**", HttpMethod.GET),
                new Routes("/v3/api-docs.yaml", HttpMethod.GET),
                new Routes("/swagger-ui/index.html", HttpMethod.GET),
                new Routes("/swagger-ui/**", HttpMethod.GET),
                new Routes("/swagger-ui.html", HttpMethod.GET),
                new Routes("/nexus/employees/login", HttpMethod.POST),
                new Routes("/nexus/employees/validate", HttpMethod.POST),
                new Routes("/nexus/admin/login", HttpMethod.POST)
                );
    }

    public static List<Routes> getHasRoleUserRoutes(){
        return List.of(
                new Routes("/nexus/employees/paycheck/{userId}", HttpMethod.GET),
                new Routes("/nexus/employees/paycheck/{userId}/{paycheckDate}", HttpMethod.GET)
        );
    }
}