package vn.com.ezmobi.ezhealth.ezhuserservice.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ezmobivietnam on 2021-01-10.
 */
public class BaseControllerTest {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
