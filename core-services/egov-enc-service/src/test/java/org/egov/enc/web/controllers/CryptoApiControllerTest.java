package org.egov.enc.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.egov.enc.services.EncryptionService;
import org.egov.enc.services.KeyManagementService;
import org.egov.enc.services.SignatureService;
import org.egov.enc.web.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CryptoApiController.class})
@ExtendWith(SpringExtension.class)
class CryptoApiControllerTest {
    @Autowired
    private CryptoApiController cryptoApiController;

    @MockBean
    private EncryptionService encryptionService;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @MockBean
    private KeyManagementService keyManagementService;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private SignatureService signatureService;

    @Test
    void testCryptoEncryptPost() throws Exception {
        when(encryptionService.encrypt((EncryptionRequest) any())).thenReturn("Encrypt");

        EncryptionRequest encryptionRequest = new EncryptionRequest();
        encryptionRequest.setEncryptionRequests(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(encryptionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crypto/v1/_encrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(cryptoApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Encrypt"));
    }

    @Test
    void testCryptoRotateAllKeys() throws Exception {
        when(keyManagementService.rotateAllKeys()).thenReturn(new RotateKeyResponse(true));

        RotateKeyRequest rotateKeyRequest = new RotateKeyRequest();
        rotateKeyRequest.setTenantId("42");
        String content = (new ObjectMapper()).writeValueAsString(rotateKeyRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crypto/v1/_rotateallkeys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(cryptoApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"acknowledged\":true}"));
    }

    @Test
    void testCryptoRotateKeys() throws Exception {
        when(keyManagementService.rotateKey((RotateKeyRequest) any())).thenReturn(new RotateKeyResponse(true));

        RotateKeyRequest rotateKeyRequest = new RotateKeyRequest();
        rotateKeyRequest.setTenantId("42");
        String content = (new ObjectMapper()).writeValueAsString(rotateKeyRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crypto/v1/_rotatekey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(cryptoApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"acknowledged\":true}"));
    }

    @Test
    void testCryptoSignPost() throws Exception {
        when(signatureService.hashAndSign((SignRequest) any())).thenReturn(new SignResponse("42", "Signature"));

        SignRequest signRequest = new SignRequest();
        signRequest.setTenantId("42");
        signRequest.setValue("42");
        String content = (new ObjectMapper()).writeValueAsString(signRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crypto/v1/_sign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(cryptoApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"value\":\"42\",\"signature\":\"Signature\"}"));
    }

    @Test
    void testCryptoVerifyPost() throws Exception {
        when(signatureService.hashAndVerify((VerifyRequest) any())).thenReturn(new VerifyResponse(true));

        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setSignature(null);
        verifyRequest.setValue("42");
        String content = (new ObjectMapper()).writeValueAsString(verifyRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/crypto/v1/_verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(cryptoApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"verified\":true}"));
    }
}

