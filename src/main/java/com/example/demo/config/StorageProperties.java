package com.example.demo.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("storage")
public class StorageProperties {

    private StorageProperties(){

    }

    private static final String FILES_DIR = "files";
    private static final String PROFILE_IMAGE_DIR = "profile-images";
}
