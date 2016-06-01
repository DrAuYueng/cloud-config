package com.acm.cloud.config.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DBConfigServiceImpl implements ConfigService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Config> searchList(String configName, String profile, String version) {
        String sql = "select name,profile,version,key,value,default_value,create_time,update_time,remark from config where name= ? and profile= ? and version= ?";
        return (List<Config>) jdbcTemplate.query(sql, new String[] { configName, profile, version }, new RowMapper<Config>() {

            @Override
            public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
                Config config = new Config();
                config.setId(rs.getLong("id"));
                config.setCreateTime(rs.getDate("create_time"));
                config.setDefaultValue(rs.getString("default_value"));
                config.setKey(rs.getString("key"));
                config.setName(rs.getString("name"));
                config.setProfile(rs.getString("profile"));
                config.setRemark(rs.getString("remark"));
                config.setType(rs.getString("type"));
                config.setUpdateTime(rs.getDate("update_time"));
                config.setValue(rs.getString("value"));
                config.setVersion(rs.getString("version"));
                return config;
            }

        });
    }

}
