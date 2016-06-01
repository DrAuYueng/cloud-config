package com.acm.cloud.config.service;

import java.util.List;

public interface ConfigService {
    List<Config> searchList(String application, String profile, String version);
}
