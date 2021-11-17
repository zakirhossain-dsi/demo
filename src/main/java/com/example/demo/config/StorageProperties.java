package com.example.demo.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

    private final String filesDir = "files";
    private final String profileImageDir = "profile-images";
}
