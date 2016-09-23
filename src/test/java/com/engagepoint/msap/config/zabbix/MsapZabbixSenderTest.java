package com.engagepoint.msap.config.zabbix;

import com.alibaba.fastjson.JSONException;
import org.junit.Test;

import static com.engagepoint.msap.config.zabbix.MsapZabbixResponse.*;
import static com.engagepoint.msap.config.zabbix.MsapZabbixSender.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by dmytro.palczewski on 6/6/2016.
 */
public class MsapZabbixSenderTest {

    @Test
    public void testCreateResponseEmptyInput(){
        assertEquals(EMPTY_RESPONSE, createResponse(null));
        assertEquals(EMPTY_RESPONSE, createResponse(new byte[0]));
    }

    @Test
    public void testCreateResponseTooShortInput(){
        assertEquals(EMPTY_RESPONSE, createResponse(new byte[12]));
        assertEquals(EMPTY_RESPONSE, createResponse(new byte[13]));
    }

    @Test(expected = JSONException.class)
    public void testCreateResponseInvalidJsonInput(){
        assertEquals(EMPTY_RESPONSE, createResponse(new byte[14]));
    }

    @Test
    public void testCreateResponseFromJsonStringSuccess(){
        String jsonString = "{\"response\":\"failed\",\"info\":\"host [msap-prototype-192.168.24.44-8080] not found\"}";
        MsapZabbixResponse response = createResponseFromJsonString(jsonString);
        assertEquals(ZabbixResponseType.FAILED, response.getType());
        assertEquals(jsonString, response.getStringValue());
        assertNotNull(response.getJsonValue());
    }

    @Test
    public void testCreateResponseFromJsonStringFailed(){
        String jsonString = "{\"response\":\"success\",\"data\":[]}";
        MsapZabbixResponse response = createResponseFromJsonString(jsonString);
        assertEquals(ZabbixResponseType.SUCCESS, response.getType());
        assertEquals(jsonString, response.getStringValue());
        assertNotNull(response.getJsonValue());
    }

    @Test
    public void testCreateResponseFromJsonStringUnknown(){
        String jsonString = "{\"response\":\"foo\",\"data\":[]}";
        MsapZabbixResponse response = createResponseFromJsonString(jsonString);
        assertEquals(ZabbixResponseType.UNKNOWN, response.getType());
        assertEquals(jsonString, response.getStringValue());
        assertNotNull(response.getJsonValue());
    }
}
