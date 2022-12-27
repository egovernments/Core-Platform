package org.egov.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokensTest {


    @Test
    public void test_should_return_true_when_single_non_expired_token_is_present() {
        final Token token1 = mock(Token.class);
        final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        when(token1.isExpired(now)).thenReturn(false);
        final Tokens tokens = new Tokens(Collections.singletonList(token1));

        Assertions.assertTrue(tokens.hasSingleNonExpiredToken(now));
        Assertions.assertEquals(token1, tokens.getNonExpiredToken(now));
    }

    @Test
    public void test_should_return_false_when_no_matching_tokens_are_present() {
        final Tokens tokens = new Tokens(Collections.emptyList());
        final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        Assertions.assertFalse(tokens.hasSingleNonExpiredToken(now));
        Assertions.assertNull(tokens.getNonExpiredToken(now));
    }

    @Test
    public void test_should_return_false_when_matching_tokens_is_null() {
        final Tokens tokens = new Tokens(null);
        final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        Assertions.assertFalse(tokens.hasSingleNonExpiredToken(now));
    }

    @Test
    public void test_should_return_false_when_all_tokens_are_expired() {
        final Token token1 = mock(Token.class);
        final LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        when(token1.isExpired(now)).thenReturn(true);
        final Token token2 = mock(Token.class);
        when(token2.isExpired(now)).thenReturn(true);

        final Tokens tokens = new Tokens(Arrays.asList(token1, token2));

        Assertions.assertFalse(tokens.hasSingleNonExpiredToken(now));
    }
}