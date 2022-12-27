package org.egov.persistence.repository.rowmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.egov.domain.model.Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TokenRowMapper.class})
@ExtendWith(SpringExtension.class)
class TokenRowMapperTest {
    @Autowired
    private TokenRowMapper tokenRowMapper;

    @Test
    void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString((String) any())).thenReturn("String");
        when(resultSet.getDate((String) any())).thenReturn(mock(Date.class));
        when(resultSet.getLong((String) any())).thenReturn(1L);
        Token actualMapRowResult = tokenRowMapper.mapRow(resultSet, 10);
        assertFalse(actualMapRowResult.isValidated());
        assertEquals("String", actualMapRowResult.getUuid());
        assertEquals(1L, actualMapRowResult.getTimeToLiveInSeconds().longValue());
        assertEquals("String", actualMapRowResult.getTenantId());
        assertEquals("String", actualMapRowResult.getNumber());
        assertEquals("String", actualMapRowResult.getIdentity());
        assertNull(actualMapRowResult.getExpiryDateTime());
        assertEquals(1L, actualMapRowResult.getCreatedTime().longValue());
        verify(resultSet, atLeast(1)).getString((String) any());
        verify(resultSet).getDate((String) any());
        verify(resultSet, atLeast(1)).getLong((String) any());
    }

    @Test
    void testMapRow2() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString((String) any())).thenReturn("Y");
        when(resultSet.getDate((String) any())).thenReturn(mock(Date.class));
        when(resultSet.getLong((String) any())).thenReturn(1L);
        Token actualMapRowResult = tokenRowMapper.mapRow(resultSet, 10);
        assertTrue(actualMapRowResult.isValidated());
        assertEquals("Y", actualMapRowResult.getUuid());
        assertEquals(1L, actualMapRowResult.getTimeToLiveInSeconds().longValue());
        assertEquals("Y", actualMapRowResult.getTenantId());
        assertEquals("Y", actualMapRowResult.getNumber());
        assertEquals("Y", actualMapRowResult.getIdentity());
        assertNull(actualMapRowResult.getExpiryDateTime());
        assertEquals(1L, actualMapRowResult.getCreatedTime().longValue());
        verify(resultSet, atLeast(1)).getString((String) any());
        verify(resultSet).getDate((String) any());
        verify(resultSet, atLeast(1)).getLong((String) any());
    }

    @Test
    void testMapRow3() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString((String) any())).thenThrow(new SQLException());
        when(resultSet.getDate((String) any())).thenThrow(new SQLException());
        when(resultSet.getLong((String) any())).thenThrow(new SQLException());
        assertThrows(SQLException.class, () -> tokenRowMapper.mapRow(resultSet, 10));
        verify(resultSet).getString((String) any());
    }

    @Test
    void testIsValidated() {
        assertFalse(tokenRowMapper.isValidated("2020-03-01"));
        assertTrue(tokenRowMapper.isValidated("Y"));
    }
}

