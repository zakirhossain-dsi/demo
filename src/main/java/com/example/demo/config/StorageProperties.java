package com.example.demo.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

    public final String filesDir = "files";
    public final String profileImageDir = "profile-images";
}
