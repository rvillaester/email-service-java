package com.emailservice.controller;

import com.emailservice.model.EmailRequest;
import com.emailservice.model.EmailResponse;
import com.emailservice.model.ValidationResponse;
import com.emailservice.provider.EmailProvider;
import com.emailservice.validator.EmailRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {

    @Mock
    private EmailRequestValidator emailRequestValidator;
    @Mock
    private EmailProvider provider1;
    @Mock
    private EmailProvider provider2;
    @InjectMocks
    private EmailController emailController;

    private EmailRequest request = new EmailRequest();

    @BeforeEach
    public void setup() {
        when(emailRequestValidator.validate(request)).thenReturn(new ValidationResponse());
        List<EmailProvider> providers = new ArrayList<>();
        providers.add(provider1);
        providers.add(provider2);
        emailController.setProviders(providers);
    }

    @Test
    public void canValidateRequestAndReturnValidationError() {
        ValidationResponse validationResponse = new ValidationResponse();
        String validationErrorMessage = "Subject is required";
        validationResponse.addValidationMessage(validationErrorMessage);
        when(emailRequestValidator.validate(request)).thenReturn(validationResponse);
        EmailResponse emailResponse = emailController.send(request);
        assertAll("Request validation error scenario",
                () -> assertEquals(emailResponse.getValidationMessages().size(), validationResponse.getValidationMessages().size()),
                () -> assertEquals(emailResponse.getValidationMessages().get(0), validationErrorMessage),
                () -> assertFalse(emailResponse.isSuccess()));
    }

    @Test
    public void canOrchestrateToMultipleProviders() {
        when(provider1.process(request)).thenReturn(EmailResponse.sendFailed());
        when(provider2.process(request)).thenReturn(EmailResponse.internalError());
        EmailResponse emailResponse = emailController.send(request);
        verify(provider1).process(request);
        verify(provider2).process(request);
        assertFalse(emailResponse.isSuccess());
    }

    @Test
    public void skipRemainingProvidersWhenTheRequestIsAlreadySuccessful() {
        when(provider1.process(request)).thenReturn(EmailResponse.success());
        EmailResponse emailResponse = emailController.send(request);
        verify(provider1).process(request);
        verify(provider2, never()).process(request);
        assertTrue(emailResponse.isSuccess());
    }
}
