package com.emailservice.util;

import com.emailservice.model.EmailRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationUtilTest {

    @Test
    public void canConvertObjectToJson() {
        EmailRecord mail = new EmailRecord("Rey", "rey@test.com");
        String json = ApplicationUtil.toJsonString(mail);
        String expected = "{\"name\":\"Rey\",\"email\":\"rey@test.com\"}";
        assertEquals(expected, json);
    }

    @Test
    public void canCheckIfEmpty() {
        assertTrue(ApplicationUtil.isEmpty(""));
        String nullStr = null;
        assertTrue(ApplicationUtil.isEmpty(nullStr));
        assertTrue(ApplicationUtil.isEmpty(new String[]{}));

        assertFalse(ApplicationUtil.isEmpty("not empty"));
        assertFalse(ApplicationUtil.isEmpty(new String[]{"1"}));
    }
}
