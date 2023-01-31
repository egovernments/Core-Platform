package org.egov.collection.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.node.MissingNode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

import org.egov.collection.model.Instrument;
import org.egov.collection.model.InstrumentType;
import org.egov.collection.model.InstrumentVoucher;
import org.egov.collection.model.SurrenderReason;
import org.egov.collection.model.TransactionType;
import org.egov.collection.model.enums.InstrumentStatusEnum;
import org.egov.collection.model.v1.AuditDetails_v1;
import org.egov.collection.web.contract.BankAccountContract;
import org.egov.collection.web.contract.BankContract;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InstrumentRepositoryTest {

    @Test
    void testCreateInstrument6() {

        InstrumentRepository instrumentRepository = new InstrumentRepository();
        RequestInfo requestinfo = new RequestInfo();
        Instrument instrument = mock(Instrument.class);
        when(instrument.getTenantId()).thenThrow(new CustomException("Code", "An error occurred"));
        when(instrument.getBank()).thenReturn(new BankContract("42"));
        assertThrows(CustomException.class, () -> instrumentRepository.createInstrument(requestinfo, instrument));
        verify(instrument).getTenantId();
        verify(instrument, atLeast(1)).getBank();
    }

    @Test
    void testCreateInstrument7() {

        InstrumentRepository instrumentRepository = new InstrumentRepository();
        RequestInfo requestinfo = new RequestInfo();
        BankContract bankContract = mock(BankContract.class);
        doThrow(new CustomException("Code", "An error occurred")).when(bankContract).setTenantId((String) any());
        Instrument instrument = mock(Instrument.class);
        when(instrument.getTenantId()).thenReturn("42");
        when(instrument.getBank()).thenReturn(bankContract);
        assertThrows(CustomException.class, () -> instrumentRepository.createInstrument(requestinfo, instrument));
        verify(instrument).getTenantId();
        verify(instrument, atLeast(1)).getBank();
        verify(bankContract).setTenantId((String) any());
    }
}

