package org.egov.access.persistence.repository.rowmapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionValidationRowMapperTest {
	@Mock
	private ResultSet resultSet;

	@Test
	public void testActionValidationRowMapperShouldReturnTrueIfActionIsValidated() throws SQLException {
		ActionValidationRowMapper actionValidationRowMapper = new ActionValidationRowMapper();
		when(resultSet.getBoolean("exists")).thenReturn(true);
		assert actionValidationRowMapper.mapRow(resultSet, 1).isAllowed();

		when(resultSet.getBoolean("exists")).thenReturn(false);
		Assertions.assertFalse(actionValidationRowMapper.mapRow(resultSet, 1).isAllowed());
	}
}
