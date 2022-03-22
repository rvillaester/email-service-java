package com.emailservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.util.Calendar;

public class ApplicationUtil {

    public static String toJsonString(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println(String.format("Error in converting object to JSON. %s", e.getMessage()));
        }
        return null;
    }

    public static long timeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isEmpty(String[] strArr) {
        return strArr == null || strArr.length == 0;
    }
}
