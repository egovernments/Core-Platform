package org.egov.boundary.domain.service;

import org.egov.boundary.persistence.repository.MdmsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MDMSServiceTest {

    @Mock
    private MdmsRepository mdmsRepository;

    private MdmsService mdmsService;

    @BeforeEach
    public void setup() {
        this.mdmsService = new MdmsService(mdmsRepository);
    }

    //TODO Test cases for service, repository
    @Test
    public void testFetchGeography() {


    }

}
