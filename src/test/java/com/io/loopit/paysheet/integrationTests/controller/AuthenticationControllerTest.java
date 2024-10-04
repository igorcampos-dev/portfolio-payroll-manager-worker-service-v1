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
    public void loginInvalidPassword() {

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_invalid_cpf.json")
    public void loginInvalidCpf() {

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_employee_not_exists.json")
    public void loginEmployeeNotExists(){

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_fields_empty.json")
    public void fieldsLoginEmpty(){

    }

    @Test
    @Scenario("integration_tests/authentication/login/case_login_fields_null.json")
    public void fieldsLoginNull(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_ok.json")
    public void registerOk(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_employee_not_found.json")
    public void employeeNotFound(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_fields_null.json")
    public void fieldsRegisterNull(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_fields_empty.json")
    public void fieldsRegisterEmpty(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_invalid_password.json")
    public void registerInvalidPassword(){

    }

    @Test
    @Scenario("integration_tests/authentication/register/case_register_invalid_cpf.json")
    public void registerInvalidCpf(){

    }

}
