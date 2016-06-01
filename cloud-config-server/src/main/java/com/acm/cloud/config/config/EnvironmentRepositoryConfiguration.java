/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acm.cloud.config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

import com.acm.cloud.config.server.ConfigServerProperties;
import com.acm.cloud.config.server.DBEnvironmentRepository;
import com.acm.cloud.config.server.EnvironmentRepository;
import com.acm.cloud.config.service.ConfigService;
import com.acm.cloud.config.service.DBConfigServiceImpl;

@Configuration
@ConditionalOnMissingBean(EnvironmentRepository.class)
@EnableConfigurationProperties(ConfigServerProperties.class)
public class EnvironmentRepositoryConfiguration {

    @Configuration
    @Profile("database")
    protected static class DatabaseConfiguration {
        @Autowired
        private ConfigurableEnvironment environment;

        @Bean
        public EnvironmentRepository environmentRepository() {
            return new DBEnvironmentRepository(environment);
        }

        @Configuration
        protected static class JDBCConfiguration {
            @Bean
            public ConfigService configService() {
                return new DBConfigServiceImpl();
            }
        }
    }

}