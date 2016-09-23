package com.engagepoint.msap.config.zabbix;
/**
 * Created by dmytro.palczewski on 6/3/2016.
 */
public class ActiveChecksRequest extends MsapZabbixRequest {

    public static final String ACTIVE_CHECKS_REQUEST_NAME = "active checks";

    private String host;

    private String host_metadata;

    public ActiveChecksRequest(){
        super(ACTIVE_CHECKS_REQUEST_NAME);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost_metadata() {
        return host_metadata;
    }

    public void setHost_metadata(String hostMetadata) {
        this.host_metadata = hostMetadata;
    }
}
