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
    @Scenario("integration_tests/authentication/case_login_ok.json")
    public void loginOk() throws Exception {
    }

    @Test
    @Scenario("integration_tests/authentication/case_register_ok.json")
    public void registerOk() throws Exception {
    }


}
