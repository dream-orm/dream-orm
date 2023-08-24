package com.dream.boot.autoconfigure;

import com.dream.drive.config.DriveProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dream")
public class DreamProperties extends DriveProperties {
}
