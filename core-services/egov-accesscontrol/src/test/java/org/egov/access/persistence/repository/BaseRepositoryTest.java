package org.egov.access.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.egov.access.persistence.repository.querybuilder.BaseQueryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BaseRepository.class})
@ExtendWith(SpringExtension.class)
class BaseRepositoryTest {
    @Autowired
    private BaseRepository baseRepository;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    void testRun() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query((String) any(), (RowMapper<Object>) any())).thenReturn(objectList);
        BaseQueryBuilder baseQueryBuilder = mock(BaseQueryBuilder.class);
        when(baseQueryBuilder.build()).thenReturn("Build");
        List<Object> actualRunResult = baseRepository.run(baseQueryBuilder, mock(RowMapper.class));
        assertSame(objectList, actualRunResult);
        assertTrue(actualRunResult.isEmpty());
        verify(jdbcTemplate).query((String) any(), (RowMapper<Object>) any());
        verify(baseQueryBuilder).build();
    }
}

