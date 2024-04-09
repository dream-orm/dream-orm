package com.dream.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dream")
public class DreamProperties extends com.dream.drive.config.DreamProperties {
}
