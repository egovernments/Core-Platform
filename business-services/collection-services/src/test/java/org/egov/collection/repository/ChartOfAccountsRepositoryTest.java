package org.egov.collection.repository;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.collection.web.contract.ChartOfAccount;
import org.egov.collection.web.contract.ChartOfAccountsResponse;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ChartOfAccountsRepository.class, String.class})
@ExtendWith(SpringExtension.class)
class ChartOfAccountsRepositoryTest {
    @Autowired
    private ChartOfAccountsRepository chartOfAccountsRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testGetChartOfAccounts() throws RestClientException {
        ChartOfAccountsResponse chartOfAccountsResponse = new ChartOfAccountsResponse();
        ArrayList<ChartOfAccount> chartOfAccountList = new ArrayList<>();
        chartOfAccountsResponse.setChartOfAccounts(chartOfAccountList);
        chartOfAccountsResponse.setResponseInfo(new ResponseInfo());
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<ChartOfAccountsResponse>) any(),
                (Object[]) any())).thenReturn(chartOfAccountsResponse);
        ArrayList<String> accountCodes = new ArrayList<>();
        List<ChartOfAccount> actualChartOfAccounts = chartOfAccountsRepository.getChartOfAccounts(accountCodes, "42",
                new RequestInfo());
        assertSame(chartOfAccountList, actualChartOfAccounts);
        assertTrue(actualChartOfAccounts.isEmpty());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<ChartOfAccountsResponse>) any(),
                (Object[]) any());
    }

    @Test
    void testGetChartOfAccounts2() throws RestClientException {
        ChartOfAccountsResponse chartOfAccountsResponse = mock(ChartOfAccountsResponse.class);
        ArrayList<ChartOfAccount> chartOfAccountList = new ArrayList<>();
        when(chartOfAccountsResponse.getChartOfAccounts()).thenReturn(chartOfAccountList);
        doNothing().when(chartOfAccountsResponse).setChartOfAccounts((List<ChartOfAccount>) any());
        doNothing().when(chartOfAccountsResponse).setResponseInfo((ResponseInfo) any());
        chartOfAccountsResponse.setChartOfAccounts(new ArrayList<>());
        chartOfAccountsResponse.setResponseInfo(new ResponseInfo());
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<ChartOfAccountsResponse>) any(),
                (Object[]) any())).thenReturn(chartOfAccountsResponse);
        ArrayList<String> accountCodes = new ArrayList<>();
        List<ChartOfAccount> actualChartOfAccounts = chartOfAccountsRepository.getChartOfAccounts(accountCodes, "42",
                new RequestInfo());
        assertSame(chartOfAccountList, actualChartOfAccounts);
        assertTrue(actualChartOfAccounts.isEmpty());
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<ChartOfAccountsResponse>) any(),
                (Object[]) any());
        verify(chartOfAccountsResponse).getChartOfAccounts();
        verify(chartOfAccountsResponse).setChartOfAccounts((List<ChartOfAccount>) any());
        verify(chartOfAccountsResponse).setResponseInfo((ResponseInfo) any());
    }
}

