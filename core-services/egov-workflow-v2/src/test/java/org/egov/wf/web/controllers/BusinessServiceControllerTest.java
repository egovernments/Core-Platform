package org.egov.wf.web.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.wf.service.V1.BusinessMasterServiceV1;
import org.egov.wf.util.ResponseInfoFactory;
import org.egov.wf.web.models.BusinessServiceRequest;
import org.egov.wf.web.models.BusinessServiceSearchCriteria;
import org.egov.wf.web.models.RequestInfoWrapper;
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

@ContextConfiguration(classes = {BusinessServiceController.class})
@ExtendWith(SpringExtension.class)
class BusinessServiceControllerTest {
    @MockBean
    private BusinessMasterServiceV1 businessMasterServiceV1;

    @Autowired
    private BusinessServiceController businessServiceController;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private ResponseInfoFactory responseInfoFactory;

    /**
     * Method under test: {@link BusinessServiceController#create(BusinessServiceRequest)}
     */
    @Test
    void testCreate() throws Exception {
        when(businessMasterServiceV1.create((BusinessServiceRequest) any())).thenReturn(new ArrayList<>());
        when(responseInfoFactory.createResponseInfoFromRequestInfo((RequestInfo) any(), (Boolean) any()))
                .thenReturn(new ResponseInfo());

        BusinessServiceRequest businessServiceRequest = new BusinessServiceRequest();
        businessServiceRequest.setBusinessServices(new ArrayList<>());
        businessServiceRequest.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(businessServiceRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/egov-wf/businessservice/_create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(businessServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":null,\"msgId\":null,\"status\":null},"
                                        + "\"BusinessServices\":[]}"));
    }

    /**
     * Method under test: {@link BusinessServiceController#search(BusinessServiceSearchCriteria, RequestInfoWrapper)}
     */
    @Test
    void testSearch() throws Exception {
        when(businessMasterServiceV1.search((BusinessServiceSearchCriteria) any())).thenReturn(new ArrayList<>());
        when(responseInfoFactory.createResponseInfoFromRequestInfo((RequestInfo) any(), (Boolean) any()))
                .thenReturn(new ResponseInfo());

        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(requestInfoWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/egov-wf/businessservice/_search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(businessServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":null,\"msgId\":null,\"status\":null},"
                                        + "\"BusinessServices\":[]}"));
    }

    /**
     * Method under test: {@link BusinessServiceController#update(BusinessServiceRequest)}
     */
    @Test
    void testUpdate() throws Exception {
        when(businessMasterServiceV1.update((BusinessServiceRequest) any())).thenReturn(new ArrayList<>());
        when(responseInfoFactory.createResponseInfoFromRequestInfo((RequestInfo) any(), (Boolean) any()))
                .thenReturn(new ResponseInfo());

        BusinessServiceRequest businessServiceRequest = new BusinessServiceRequest();
        businessServiceRequest.setBusinessServices(new ArrayList<>());
        businessServiceRequest.setRequestInfo(new RequestInfo());
        String content = (new ObjectMapper()).writeValueAsString(businessServiceRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/egov-wf/businessservice/_update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(businessServiceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"ResponseInfo\":{\"apiId\":null,\"ver\":null,\"ts\":null,\"resMsgId\":null,\"msgId\":null,\"status\":null},"
                                        + "\"BusinessServices\":[]}"));
    }
}

