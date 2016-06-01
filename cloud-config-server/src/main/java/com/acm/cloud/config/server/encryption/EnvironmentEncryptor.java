package com.acm.cloud.config.server.encryption;

import com.acm.cloud.config.environment.Environment;

public interface EnvironmentEncryptor {
    Environment decrypt(Environment environment);
}