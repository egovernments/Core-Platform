package org.egov.wf.repository.V1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.egov.wf.config.WorkflowConfig;
import org.egov.wf.repository.querybuilder.BusinessServiceQueryBuilder;
import org.egov.wf.repository.rowmapper.BusinessServiceRowMapper;
import org.egov.wf.service.MDMSService;
import org.egov.wf.web.models.BusinessServiceSearchCriteria;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BusinessServiceRepositoryV1.class})
@ExtendWith(SpringExtension.class)
class BusinessServiceRepositoryV1Test {
    @MockBean
    private BusinessServiceQueryBuilder businessServiceQueryBuilder;

    @Autowired
    private BusinessServiceRepositoryV1 businessServiceRepositoryV1;

    @MockBean
    private BusinessServiceRowMapper businessServiceRowMapper;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private MDMSService mDMSService;

    @MockBean
    private WorkflowConfig workflowConfig;

    /**
     * Method under test: {@link BusinessServiceRepositoryV1#getBusinessServices(BusinessServiceSearchCriteria)}
     */
    @Test
    void testGetBusinessServices() {
        when(mDMSService.getStateLevelMapping()).thenReturn(new HashMap<>());
        assertTrue(businessServiceRepositoryV1.getBusinessServices(new BusinessServiceSearchCriteria()).isEmpty());
        verify(mDMSService).getStateLevelMapping();
    }

    @Test
    void testGetBusinessServicesWithString() throws DataAccessException {
        when(businessServiceQueryBuilder.getBusinessServices((BusinessServiceSearchCriteria) any(), (List<Object>) any()))
                .thenReturn("Business Services");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());
        when(mDMSService.getStateLevelMapping()).thenReturn(new HashMap<>());

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("foo");
        ArrayList<String> stateUuids = new ArrayList<>();
        assertTrue(businessServiceRepositoryV1
                .getBusinessServices(new BusinessServiceSearchCriteria("42", stringList, stateUuids, new ArrayList<>()))
                .isEmpty());
        verify(businessServiceQueryBuilder).getBusinessServices((BusinessServiceSearchCriteria) any(),
                (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(mDMSService).getStateLevelMapping();
    }

    @Test
    void testGetRoleTenantAndStatusMapping() throws DataAccessException {
        when(businessServiceQueryBuilder.getBusinessServices((BusinessServiceSearchCriteria) any(), (List<Object>) any()))
                .thenReturn("Business Services");
        when(jdbcTemplate.query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any()))
                .thenReturn(new ArrayList<>());
        when(mDMSService.getStateLevelMapping()).thenReturn(new HashMap<>());
        assertTrue(businessServiceRepositoryV1.getRoleTenantAndStatusMapping().isEmpty());
        verify(businessServiceQueryBuilder).getBusinessServices((BusinessServiceSearchCriteria) any(),
                (List<Object>) any());
        verify(jdbcTemplate).query((String) any(), (Object[]) any(), (ResultSetExtractor<Object>) any());
        verify(mDMSService).getStateLevelMapping();
    }

}

