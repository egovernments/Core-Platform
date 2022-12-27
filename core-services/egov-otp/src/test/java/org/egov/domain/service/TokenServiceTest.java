package org.egov.domain.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.egov.domain.exception.TokenValidationFailureException;

import org.egov.domain.model.Token;
import org.egov.domain.model.TokenRequest;
import org.egov.domain.model.TokenSearchCriteria;
import org.egov.domain.model.Tokens;
import org.egov.domain.model.ValidateRequest;
import org.egov.persistence.repository.TokenRepository;
import org.egov.web.util.OtpConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TokenService.class})
@ExtendWith(SpringExtension.class)
class TokenServiceTest {
    @MockBean
    private OtpConfiguration otpConfiguration;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    void testCreate() {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Token token = new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));

        when(tokenRepository.save((Token) any())).thenReturn(token);
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(otpConfiguration.getOtpLength()).thenReturn(3);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertSame(token, tokenService.create(new TokenRequest("Identity", "42")));
        verify(tokenRepository).save((Token) any());
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(otpConfiguration).getTtl();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testCreate2() {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenRepository.save((Token) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                        true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(otpConfiguration.getOtpLength()).thenReturn(3);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenThrow(new TokenValidationFailureException());
        assertThrows(TokenValidationFailureException.class,
                () -> tokenService.create(new TokenRequest("Identity", "42")));
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testCreate4() {
        Token token = mock(Token.class);
        doNothing().when(token).setNumber((String) any());
        when(tokenRepository.save((Token) any())).thenReturn(token);
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(otpConfiguration.getOtpLength()).thenReturn(3);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        tokenService.create(new TokenRequest("Identity", "42"));
        verify(tokenRepository).save((Token) any());
        verify(token).setNumber((String) any());
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(otpConfiguration).getTtl();
        verify(passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testCreate5() {
        Token token = mock(Token.class);
        doNothing().when(token).setNumber((String) any());
        when(tokenRepository.save((Token) any())).thenReturn(token);
        when(otpConfiguration.isEncryptOTP()).thenReturn(false);
        when(otpConfiguration.getOtpLength()).thenReturn(3);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        tokenService.create(new TokenRequest("Identity", "42"));
        verify(tokenRepository).save((Token) any());
        verify(token).setNumber((String) any());
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(otpConfiguration).getTtl();
    }

    @Test
    void testCreate6() {
        Token token = mock(Token.class);
        doNothing().when(token).setNumber((String) any());
        when(tokenRepository.save((Token) any())).thenReturn(token);
        when(otpConfiguration.isEncryptOTP()).thenReturn(false);
        when(otpConfiguration.getOtpLength()).thenReturn(0);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        tokenService.create(new TokenRequest("Identity", "42"));
        verify(tokenRepository).save((Token) any());
        verify(token).setNumber((String) any());
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(otpConfiguration).getTtl();
    }

    @Test
    void testCreate10() {
        Token token = mock(Token.class);
        doNothing().when(token).setNumber((String) any());
        when(tokenRepository.save((Token) any())).thenReturn(token);
        when(otpConfiguration.isEncryptOTP()).thenReturn(false);
        when(otpConfiguration.getOtpLength()).thenReturn(0);
        when(otpConfiguration.getTtl()).thenReturn(1L);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        tokenService.create(new TokenRequest("Identity", "org.egov.domain.model.TokenRequest"));
        verify(tokenRepository).save((Token) any());
        verify(token).setNumber((String) any());
        verify(otpConfiguration).isEncryptOTP();
        verify(otpConfiguration).getOtpLength();
        verify(otpConfiguration).getTtl();
    }

    @Test
    void testValidate() {
        when(tokenRepository.findByIdentityAndTenantId((ValidateRequest) any()))
                .thenReturn(new Tokens(new ArrayList<>()));
        assertThrows(TokenValidationFailureException.class,
                () -> tokenService.validate(new ValidateRequest("42", "Otp", "Identity")));
        verify(tokenRepository).findByIdentityAndTenantId((ValidateRequest) any());
    }

    @Test
    void testValidate2() {
        ArrayList<Token> tokenList = new ArrayList<>();
        LocalDateTime expiryDateTime = LocalDateTime.of(4, 4, 4, 4, 4);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Token token = new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 4L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));

        tokenList.add(token);
        Tokens tokens = new Tokens(tokenList);
        LocalDateTime expiryDateTime1 = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenRepository.markAsValidated((Token) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime1, 1L,
                        10L, true, Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())));
        when(tokenRepository.findByIdentityAndTenantId((ValidateRequest) any())).thenReturn(tokens);
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertSame(token, tokenService.validate(new ValidateRequest("42", "Otp", "Identity")));
        verify(tokenRepository).markAsValidated((Token) any());
        verify(tokenRepository).findByIdentityAndTenantId((ValidateRequest) any());
        verify(otpConfiguration, atLeast(1)).isEncryptOTP();
        verify(passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testValidate3() {
        ArrayList<Token> tokenList = new ArrayList<>();
        LocalDateTime expiryDateTime = LocalDateTime.of(4, 4, 4, 4, 4);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        tokenList.add(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 4L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        Tokens tokens = new Tokens(tokenList);
        when(tokenRepository.markAsValidated((Token) any())).thenThrow(new TokenValidationFailureException());
        when(tokenRepository.findByIdentityAndTenantId((ValidateRequest) any())).thenReturn(tokens);
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertThrows(TokenValidationFailureException.class,
                () -> tokenService.validate(new ValidateRequest("42", "Otp", "Identity")));
        verify(tokenRepository).markAsValidated((Token) any());
        verify(tokenRepository).findByIdentityAndTenantId((ValidateRequest) any());
        verify(otpConfiguration, atLeast(1)).isEncryptOTP();
        verify(passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testValidate5() {
        Token token = mock(Token.class);
        when(token.getNumber()).thenReturn("42");

        ArrayList<Token> tokenList = new ArrayList<>();
        tokenList.add(token);
        Tokens tokens = new Tokens(tokenList);
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tokenRepository.markAsValidated((Token) any()))
                .thenReturn(new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                        true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant())));
        when(tokenRepository.findByIdentityAndTenantId((ValidateRequest) any())).thenReturn(tokens);
        when(otpConfiguration.isEncryptOTP()).thenReturn(true);
        when(passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        tokenService.validate(new ValidateRequest("42", "Otp", "Identity"));
        verify(tokenRepository).markAsValidated((Token) any());
        verify(tokenRepository).findByIdentityAndTenantId((ValidateRequest) any());
        verify(token).getNumber();
        verify(otpConfiguration, atLeast(1)).isEncryptOTP();
        verify(passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testSearch() {
        LocalDateTime expiryDateTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Token token = new Token("42", "Identity", "42", "01234567-89AB-CDEF-FEDC-BA9876543210", expiryDateTime, 1L, 10L,
                true, Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));

        when(tokenRepository.findBy((TokenSearchCriteria) any())).thenReturn(token);
        assertSame(token, tokenService.search(new TokenSearchCriteria("01234567-89AB-CDEF-FEDC-BA9876543210", "42")));
        verify(tokenRepository).findBy((TokenSearchCriteria) any());
    }

    @Test
    void testSearch2() {
        when(tokenRepository.findBy((TokenSearchCriteria) any())).thenThrow(new TokenValidationFailureException());
        assertThrows(TokenValidationFailureException.class,
                () -> tokenService.search(new TokenSearchCriteria("01234567-89AB-CDEF-FEDC-BA9876543210", "42")));
        verify(tokenRepository).findBy((TokenSearchCriteria) any());
    }
}

