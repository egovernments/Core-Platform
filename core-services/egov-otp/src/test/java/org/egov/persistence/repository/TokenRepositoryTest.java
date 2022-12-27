package org.egov.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.egov.domain.exception.TokenUpdateException;
import org.egov.domain.model.Token;

import org.egov.domain.model.TokenSearchCriteria;
import org.egov.domain.model.ValidateRequest;
import org.egov.web.util.OtpConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TokenRepository.class})
@ExtendWith(SpringExtension.class)
class TokenRepositoryTest {
    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @MockBean
    private OtpConfiguration otpConfiguration;

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    void testSave() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Token token = new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));

        assertSame(token, tokenRepository.save(token));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
    }

    @Test
    void testSave3() throws DataAccessException {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any()))
                .thenThrow(new TokenUpdateException(new Token("42", "id", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime, 1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        LocalDateTime expiryDateTime1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(TokenUpdateException.class,
                () -> tokenRepository.save(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime1, 1L, 10L, true, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()))));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
    }

    @Test
    void testMarkAsValidated() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Token token = new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));

        Token actualMarkAsValidatedResult = tokenRepository.markAsValidated(token);
        assertSame(token, actualMarkAsValidatedResult);
        assertTrue(actualMarkAsValidatedResult.isValidated());
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
    }

    @Test
    void testMarkAsValidated2() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(0);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(TokenUpdateException.class,
                () -> tokenRepository
                        .markAsValidated(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime,
                                1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
    }

    @Test
    void testMarkAsValidated4() throws DataAccessException {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any()))
                .thenThrow(new TokenUpdateException(new Token("42", "id", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime, 1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        LocalDateTime expiryDateTime1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(TokenUpdateException.class,
                () -> tokenRepository.markAsValidated(
                        new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime1, 1L, 10L, true,
                                Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()))));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
    }

    @Test
    void testFindByIdentityAndTenantId() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(tokenRepository.findByIdentityAndTenantId(new ValidateRequest("42", "Otp", "Identity"))
                .getTokens()
                .isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindByIdentityAndTenantId3() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(tokenRepository
                .findByIdentityAndTenantId(new ValidateRequest("org.egov.domain.model.ValidateRequest", "Otp", "Identity"))
                .getTokens()
                .isEmpty());
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testFindByIdentityAndTenantId5() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        ValidateRequest validateRequest = mock(ValidateRequest.class);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(validateRequest.getIdentity())
                .thenThrow(new TokenUpdateException(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime, 1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        when(validateRequest.getTenantId()).thenReturn("42");
        assertThrows(TokenUpdateException.class, () -> tokenRepository.findByIdentityAndTenantId(validateRequest));
        verify(validateRequest).getIdentity();
        verify(validateRequest).getTenantId();
    }

    @Test
    void testFindBy() throws DataAccessException {
        when(namedParameterJdbcTemplate.query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any()))
                .thenReturn(new ArrayList<>());
        assertNull(tokenRepository.findBy(new TokenSearchCriteria("01234567-89AB-CDEF-FEDC-BA9876543210", "42")));
        verify(namedParameterJdbcTemplate).query((String) any(), (Map<String, Object>) any(), (RowMapper<Object>) any());
    }

    @Test
    void testUpdateTTL() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertEquals(1,
                tokenRepository.updateTTL(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime, 1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        verify(namedParameterJdbcTemplate).update((String) any(), (Map<String, Object>) any());
        verify(otpConfiguration).getTtl();
    }

    @Test
    void testUpdateTTL2() throws DataAccessException {
        when(namedParameterJdbcTemplate.update((String) any(), (Map<String, Object>) any())).thenReturn(1);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(otpConfiguration.getTtl())
                .thenThrow(new TokenUpdateException(new Token("42", "id", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime, 1L, 10L, true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()))));
        LocalDateTime expiryDateTime1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(TokenUpdateException.class,
                () -> tokenRepository.updateTTL(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210",
                        expiryDateTime1, 1L, 10L, true, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()))));
        verify(otpConfiguration).getTtl();
    }
}

