package com.nexus.java.api.application.util;

import com.nexus.security.dto.Routes;
import org.springframework.http.HttpMethod;

import java.util.List;

public class Path {
    public static final String DOMAIN = "/nexus";
    public static final String GET_EMPLOYEES = "/admin/employees";
    public static final String DELETE_ADMIN = "/admin/{userId}/{paycheckDate}";
    public static final String POST_ADMIN = "/admin/{userId}/{paycheckDate}";
    public static final String PUT_ADMIN = "/admin/{userId}/{paycheckDate}";
    public static final String POST_LOGIN_EMPLOYEE = "/employees/login";
    public static final String POST_VALIDATE_EMPLOYEE = "/employees/validate";
    public static final String POST_VALIDATE_ADMIN = "/admin/login";
    public static final String GET_EMPLOYEE_PAYCHECK_ID = "/employees/paycheck/{userId}";
    public static final String GET_EMPLOYEE_PAYCHECK_DATE = "/employees/paycheck/{userId}";


    private static final String SWAGGER_API_DOC = "/v3/api-docs/**";
    private static final String SWAGGER_API_DOC_YML = "/v3/api-docs.yaml";
    private static final String SWAGGER_UI_HTML ="/swagger-ui/index.html";
    private static final String SWAGGER_UI ="/swagger-ui/**";
    private static final String SWAGGER_HTML_UI = "/swagger-ui.html";


    private static final String FINAL_GET_EMPLOYEES = DOMAIN.concat(GET_EMPLOYEES);
    private static final String FINAL_DELETE_ADMIN = DOMAIN.concat(DELETE_ADMIN);
    private static final String FINAL_POST_ADMIN = DOMAIN.concat(POST_ADMIN);
    private static final String FINAL_PUT_ADMIN = DOMAIN.concat(PUT_ADMIN);
    private static final String FINAL_POST_LOGIN_EMPLOYEE = DOMAIN.concat(POST_LOGIN_EMPLOYEE);
    private static final String FINAL_POST_VALIDATE_EMPLOYEE = DOMAIN.concat(POST_VALIDATE_EMPLOYEE);
    private static final String FINAL_POST_VALIDATE_ADMIN = DOMAIN.concat(POST_VALIDATE_ADMIN);
    private static final String FINAL_GET_EMPLOYEE_PAYCHECK = DOMAIN.concat(GET_EMPLOYEE_PAYCHECK_ID);
    private static final String FINAL_GET_EMPLOYEE_PAYCHECK_DATE = DOMAIN.concat(GET_EMPLOYEE_PAYCHECK_DATE);

    public static List<Routes> getHasRoleAdminRoutes(){
        return List.of(
                new Routes(FINAL_GET_EMPLOYEES, HttpMethod.GET),
                new Routes(FINAL_DELETE_ADMIN, HttpMethod.DELETE),
                new Routes(FINAL_POST_ADMIN, HttpMethod.POST),
                new Routes(FINAL_PUT_ADMIN, HttpMethod.PUT)
        );
    }

    public static List<Routes> getPermitAllRoutes(){
        return List.of(
                new Routes(SWAGGER_API_DOC, HttpMethod.GET),
                new Routes(SWAGGER_API_DOC_YML, HttpMethod.GET),
                new Routes(SWAGGER_UI_HTML, HttpMethod.GET),
                new Routes(SWAGGER_UI, HttpMethod.GET),
                new Routes(SWAGGER_HTML_UI, HttpMethod.GET),
                new Routes(FINAL_POST_LOGIN_EMPLOYEE, HttpMethod.POST),
                new Routes(FINAL_POST_VALIDATE_EMPLOYEE, HttpMethod.POST),
                new Routes(FINAL_POST_VALIDATE_ADMIN, HttpMethod.POST)
        );
    }

    public static List<Routes> getHasRoleUserRoutes(){
        return List.of(
                new Routes(FINAL_GET_EMPLOYEE_PAYCHECK, HttpMethod.GET),
                new Routes(FINAL_GET_EMPLOYEE_PAYCHECK_DATE, HttpMethod.GET)
        );
    }
}
