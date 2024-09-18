package ru.pas_zhukov.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigurationProperties {
    public String getProperty(String key) {
        return System.getProperty(key);
    }
}
