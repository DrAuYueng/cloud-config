/*
 * Copyright 2013-2015 the original author or authors.
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

package com.acm.cloud.config.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import com.acm.cloud.config.environment.Environment;
import com.acm.cloud.config.environment.PropertySource;
import com.acm.cloud.config.service.Config;
import com.acm.cloud.config.service.ConfigService;

@ConfigurationProperties(prefix = "spring.cloud.config.server.database")
public class DBEnvironmentRepository implements EnvironmentRepository {

    private static Log logger = LogFactory.getLog(DBEnvironmentRepository.class);

    private static final String DEFAULT_LABEL = "0.0.1";

    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigService configService;

    public DBEnvironmentRepository(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public String getDefaultLabel() {
        return DEFAULT_LABEL;
    }

    @Override
    public Environment findOne(String config, String profile, String label) {
        Environment env = new Environment(config, StringUtils.commaDelimitedListToStringArray(profile), label);
        List<Config> configList = configService.searchList(config, profile, label);
        return processConfig(env, configList);
    }

    private Environment processConfig(Environment env, List<Config> configList) {
        if (configList == null) {
            return env;
        }

        Map<String, Object> source = new HashMap<>();

        for (Config config : configList) {
            if (config == null) {
                continue;
            }
            source.put(config.getKey(), config.getValue() == null ? config.getDefaultValue() : config.getValue());
        }

        PropertySource propertySource = new PropertySource(env.getName(), source);
        env.add(propertySource);
        return env;
    }
}
