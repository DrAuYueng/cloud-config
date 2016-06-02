package com.acm.cloud.config.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.acm.cloud.config.environment.Environment;
import com.acm.cloud.config.server.ConfigServerApplication;
import com.acm.cloud.config.server.EnvironmentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConfigServerApplication.class)
@IntegrationTest({ "spring.config.name:configserver", "local.server.port:8888" })
@WebAppConfiguration
@ActiveProfiles("database")
public class EnvironmentRepositoryConfigurationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private EnvironmentRepository repository;

    @Test
    public void contextLoad() {

        Environment environment = new TestRestTemplate().getForObject("http://localhost:" + port + "/member/test",
                Environment.class);
        assertFalse(environment.getPropertySources().isEmpty());
        assertEquals("1", environment.getPropertySources().get(0).getSource().get("bubugao.member.register.captcha.easy.time"));
    }

    @Test
    public void findEnv() {
        String profile = "test";
        String label = "0.0.1";
        String application = "member";
        Environment environment = repository.findOne(application, profile, label);
        assertFalse(environment.getPropertySources().isEmpty());
        assertEquals("1", environment.getPropertySources().get(0).getSource().get("bubugao.member.register.captcha.easy.time"));
    }

}
