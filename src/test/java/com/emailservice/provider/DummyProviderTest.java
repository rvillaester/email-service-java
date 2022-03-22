package com.emailservice.provider;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyProviderTest {

    private DummyProvider provider = new DummyProvider();

    @Test
    public void shouldAlwaysReturnSuccess() {
        EmailResponse response = provider.process(new EmailRequest());
        assertTrue(response.isSuccess());
    }
}
