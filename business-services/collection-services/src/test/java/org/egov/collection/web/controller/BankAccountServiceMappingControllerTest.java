package org.egov.collection.web.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.egov.collection.model.BankAccountServiceMappingSearchCriteria;

import org.egov.collection.service.BankAccountMappingService;
import org.egov.collection.util.CollectionMastersRequestValidator;
import org.egov.collection.web.contract.BankAccountServiceMapping;
import org.egov.collection.web.contract.BankAccountServiceMappingReq;
import org.egov.collection.web.contract.BankAccountServiceMappingSearchReq;
import org.egov.collection.web.contract.factory.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.Error;
import org.egov.common.contract.response.ErrorResponse;
import org.egov.common.contract.response.ResponseInfo;
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
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {BankAccountServiceMappingController.class})
@ExtendWith(SpringExtension.class)
class BankAccountServiceMappingControllerTest {
    @MockBean
    private BankAccountMappingService bankAccountMappingService;

    @Autowired
    private BankAccountServiceMappingController bankAccountServiceMappingController;

    @MockBean
    private CollectionMastersRequestValidator collectionMastersRequestValidator;

    @Test
    void testCreate() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(new ErrorResponse());

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"responseInfo\":null,\"error\":null}"));
    }

    @Test
    void testCreate2() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(null);

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[],\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":\"uief87324"
                                        + "\",\"msgId\":null,\"status\":\"200 OK\"}}"));
    }

    @Test
    void testCreate3() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        ResponseInfo responseInfo = new ResponseInfo();
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(new ErrorResponse(responseInfo, new Error()));

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":null,\"msgId\":null,\"status\":null},\"error"
                                        + "\":{\"code\":0,\"message\":null,\"description\":null,\"fields\":null}}"));
    }

    @Test
    void testCreate4() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(new ErrorResponse());

        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new BankAccountServiceMapping());

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(bankAccountServiceMappingList);
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testCreate5() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(new ErrorResponse());

        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new BankAccountServiceMapping());
        bankAccountServiceMappingList.add(new BankAccountServiceMapping());

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(bankAccountServiceMappingList);
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testCreate6() throws Exception {
        ArrayList<BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new BankAccountServiceMapping());

        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(bankAccountServiceMappingList);
        bankAccountServiceMappingReq.setRequestInfo(new RequestInfo());
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(null);

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[{\"businessDetails\":null,\"bankAccount\":null,\"bank\":null,\"bankBranch\":null"
                                        + ",\"tenantId\":null}],\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":\"uief87324\",\"msgId\""
                                        + ":null,\"status\":\"200 OK\"}}"));
    }

    @Test
    void testCreate7() throws Exception {
        BankAccountServiceMappingReq bankAccountServiceMappingReq = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq.setRequestInfo(null);
        when(bankAccountMappingService.createBankAccountServiceMappingAsync((BankAccountServiceMappingReq) any()))
                .thenReturn(bankAccountServiceMappingReq);
        when(collectionMastersRequestValidator.validateBankAccountServiceRequest((BankAccountServiceMappingReq) any()))
                .thenReturn(null);

        BankAccountServiceMappingReq bankAccountServiceMappingReq1 = new BankAccountServiceMappingReq();
        bankAccountServiceMappingReq1.setBankAccountServiceMapping(new ArrayList<>());
        bankAccountServiceMappingReq1.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(bankAccountServiceMappingReq1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[],\"ResponseInfo\":{\"apiId\":\"\",\"ver\":\"\",\"ts\":null,\"resMsgId\":\"uief87324\""
                                        + ",\"msgId\":\"\",\"status\":\"200 OK\"}}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch() throws Exception {
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ArrayList<>());
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ErrorResponse());

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"responseInfo\":null,\"error\":null}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch2() throws Exception {
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ArrayList<>());
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any())).thenReturn(null);

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[],\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":\"uief87324"
                                        + "\",\"msgId\":null,\"status\":\"200 OK\"}}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch3() throws Exception {
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ArrayList<>());
        ResponseInfo responseInfo = new ResponseInfo();
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ErrorResponse(responseInfo, new Error()));

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"responseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":null,\"msgId\":null,\"status\":null},\"error"
                                        + "\":{\"code\":0,\"message\":null,\"description\":null,\"fields\":null}}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch4() throws Exception {
        ArrayList<org.egov.collection.model.BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new org.egov.collection.model.BankAccountServiceMapping());
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(bankAccountServiceMappingList);
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any())).thenReturn(null);

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[{\"businessDetails\":null,\"bankAccount\":null,\"bank\":null,\"bankBranch\":null"
                                        + ",\"tenantId\":null}],\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":\"uief87324\",\"msgId\""
                                        + ":null,\"status\":\"200 OK\"}}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch5() throws Exception {
        ArrayList<org.egov.collection.model.BankAccountServiceMapping> bankAccountServiceMappingList = new ArrayList<>();
        bankAccountServiceMappingList.add(new org.egov.collection.model.BankAccountServiceMapping());
        bankAccountServiceMappingList.add(new org.egov.collection.model.BankAccountServiceMapping());
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(bankAccountServiceMappingList);
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any())).thenReturn(null);

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[{\"businessDetails\":null,\"bankAccount\":null,\"bank\":null,\"bankBranch\":null"
                                        + ",\"tenantId\":null},{\"businessDetails\":null,\"bankAccount\":null,\"bank\":null,\"bankBranch\":null,\"tenantId"
                                        + "\":null}],\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":\"uief87324\",\"msgId\":null,\"status\":\"200"
                                        + " OK\"}}"));
    }

    /**
     * Method under test: {@link BankAccountServiceMappingController#search(BankAccountServiceMappingSearchReq, BindingResult, RequestInfoWrapper)}
     */
    @Test
    void testSearch6() throws Exception {
        when(bankAccountMappingService.searchBankAccountService((BankAccountServiceMappingSearchCriteria) any()))
                .thenReturn(new ArrayList<>());
        when(collectionMastersRequestValidator
                .validateBankAccountSearchRequest((BankAccountServiceMappingSearchCriteria) any())).thenReturn(null);

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(null);
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/bankAccountServiceMapping/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bankAccountServiceMappingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"BankAccountServiceMapping\":[],\"ResponseInfo\":{\"apiId\":\"\",\"ver\":\"\",\"ts\":null,\"resMsgId\":\"uief87324\""
                                        + ",\"msgId\":\"\",\"status\":\"200 OK\"}}"));
    }
}

