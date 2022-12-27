package org.egov.persistence.repository;

import org.egov.domain.model.DuplicateMessageIdentityException;
import org.egov.domain.model.MessagePersistException;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DataIntegrityViolationExceptionTransformerTest {

    @Test
    void testConstructor() {
        new DataIntegrityViolationExceptionTransformer(new DataIntegrityViolationException("Msg"));
    }

    @Test
    void testTransform() {
        assertThrows(MessagePersistException.class,
            () -> (new DataIntegrityViolationExceptionTransformer(new DataIntegrityViolationException("Msg")))
                .transform());
        assertThrows(DuplicateMessageIdentityException.class, () -> (new DataIntegrityViolationExceptionTransformer(
            new DataIntegrityViolationException("unique_message_entry"))).transform());
    }

}

