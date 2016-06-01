package com.acm.cloud.config.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.acm.cloud.config.environment.Environment;
import com.acm.cloud.config.server.ConfigServerApplication;
import com.acm.cloud.config.server.EnvironmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConfigServerApplication.class)
@IntegrationTest({ "spring.config.name:configserver" })
@WebAppConfiguration
@ActiveProfiles("database")
public class EnvironmentRepositoryConfigurationTest {

    @Autowired
    private EnvironmentRepository repository;

    @Test
    public void test() {
        String profile = "test";
        String label = "0.0.1";
        String application = "member";
        Environment env = repository.findOne(application, profile, label);
        System.out.println(env);
    }

}
