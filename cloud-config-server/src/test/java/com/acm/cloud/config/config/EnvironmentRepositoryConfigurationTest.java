package com.acm.cloud.config.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.acm.cloud.config.server.EnvironmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EnvironmentRepositoryConfiguration.class)
// @IntegrationTest({ "server.port:0",
// "spring.cloud.bootstrap.name:enable-bootstrap", "info.foo:bar" })
@WebAppConfiguration
@ActiveProfiles("database")
public class EnvironmentRepositoryConfigurationTest {

    @Autowired
    private EnvironmentRepository repository;

    @Test
    public void test() {
        String profile = "test";
        String label = "";
        String application = "";
        repository.findOne(application, profile, label);
    }

}
