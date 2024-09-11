package com.io.loopit.paysheet.integrationTests.controller;

import com.io.loopit.paysheet.integrationTests.runner.ZeroCodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.Scenario;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ZeroCodeSpringBootRunner.class)
@TargetEnv("application-test.properties")
public class AuthenticationControllerTest {

    @Test
    @Scenario("integration_tests/authentication/login/case_login_ok.json")
    public void loginOk() {
    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_invalid_password.json")
    public void invalidPassword() {

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_employee_not_exists.json")
    public void employeeNotExists(){

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_fields_empty.json")
    public void fieldsEmpty(){

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_fields_null.json")
    public void fieldsNull(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_ok.json")
    public void registerOk(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_employee_not_found.json")
    public void employeeNotFound(){

    }

}
