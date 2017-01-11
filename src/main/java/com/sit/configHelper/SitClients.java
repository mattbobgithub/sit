package com.sit.configHelper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by colem on 1/10/2017.
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "sitprops")
public class SitClients {
    private HashMap<String, String> clients = new HashMap<String, String>();

    public HashMap<String, String> getClients(){
        return clients;
    }
}
