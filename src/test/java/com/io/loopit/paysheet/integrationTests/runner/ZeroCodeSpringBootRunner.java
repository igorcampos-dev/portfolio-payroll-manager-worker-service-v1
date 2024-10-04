package com.io.loopit.paysheet.integrationTests.runner;

import com.io.loopit.paysheet.Application;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;
import org.springframework.context.ConfigurableApplicationContext;

@SuppressWarnings("unused")
public class ZeroCodeSpringBootRunner extends ZeroCodeUnitRunner {

    public static boolean appRunning = false;
    private static ConfigurableApplicationContext context;

    public ZeroCodeSpringBootRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        if(!appRunning){
            System.setProperty("spring.profiles.active", "test");
            Application.run();
            appRunning = true;
        }
    }

}