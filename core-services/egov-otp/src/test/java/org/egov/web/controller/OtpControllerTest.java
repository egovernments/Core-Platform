package org.egov.web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.egov.domain.model.Token;
import org.egov.domain.model.TokenRequest;
import org.egov.domain.model.TokenSearchCriteria;
import org.egov.domain.model.ValidateRequest;
import org.egov.domain.service.TokenService;
import org.egov.web.contract.OtpRequest;
import org.egov.web.contract.OtpValidateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OtpController.class})
@ExtendWith(SpringExtension.class)
class OtpControllerTest {
    @Autowired
    private OtpController otpController;

    @MockBean
    private TokenService tokenService;

    @Test
    void testCreateOtp() throws Exception {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenService.create((TokenRequest) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                        true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/_create")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OtpRequest()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(otpController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseInfo\":null,\"otp\":{\"otp\":\"42\",\"identity\":\"Identity\",\"tenantId\":\"42\",\"UUID\":\"01234567-89AB"
                                        + "-CDEF-FEDC-BA9876543210\",\"isValidationSuccessful\":true}}"));
    }

    @Test
    void testSearch() throws Exception {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenService.search((TokenSearchCriteria) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                        true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/_search")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OtpRequest()));
        MockMvcBuilders.standaloneSetup(otpController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseInfo\":null,\"otp\":{\"otp\":\"42\",\"identity\":\"Identity\",\"tenantId\":\"42\",\"UUID\":\"01234567-89AB"
                                        + "-CDEF-FEDC-BA9876543210\",\"isValidationSuccessful\":true}}"));
    }

    @Test
    void testSearch2() throws Exception {
        when(tokenService.search((TokenSearchCriteria) any())).thenReturn(null);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/_search")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OtpRequest()));
        MockMvcBuilders.standaloneSetup(otpController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"responseInfo\":null,\"otp\":null}"));
    }

    @Test
    void testCreateOtp2() throws Exception {
        when(tokenService.create((TokenRequest) any())).thenReturn(null);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/_create")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OtpRequest()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(otpController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"responseInfo\":null,\"otp\":null}"));
    }

    @Test
    void testValidateOtp() throws Exception {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenService.validate((ValidateRequest) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                        true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/_validate")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new OtpValidateRequest()));
        MockMvcBuilders.standaloneSetup(otpController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseInfo\":null,\"otp\":{\"otp\":null,\"identity\":\"Identity\",\"tenantId\":\"42\",\"UUID\":\"01234567-89AB"
                                        + "-CDEF-FEDC-BA9876543210\",\"isValidationSuccessful\":true}}"));
    }
}

