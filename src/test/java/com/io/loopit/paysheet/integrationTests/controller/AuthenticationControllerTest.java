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
    @Scenario("integration_tests/authentication/case_without_bearer_token_login.json")
    public void withoutBearerTokenLogin() throws Exception {
    }

    @Test
    @Scenario("integration_tests/authentication/case_without_bearer_token_register.json")
    public void withoutBearerTokenRegister() throws Exception {
    }


}
