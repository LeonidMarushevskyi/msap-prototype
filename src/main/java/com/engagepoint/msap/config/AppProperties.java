package com.engagepoint.msap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Oleg.Korneychuk
 * @version 6/4/2016
 */
@ConfigurationProperties(prefix = "msap", ignoreUnknownFields = false)
public class AppProperties {
    private String defaultAddress;

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
