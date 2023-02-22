package org.egov.web.notification.mail.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egov.web.notification.mail.consumer.contract.Email;
import org.egov.web.notification.mail.consumer.contract.EmailRequest;
import org.egov.web.notification.mail.repository.UserRepository;
import org.egov.web.notification.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class EmailNotificationListener {

    
    private EmailService emailService;
    
    private ObjectMapper objectMapper;

    private UserRepository userRepository;

    @Value("${email.subject}")
    private String subject;

    @Autowired
    public EmailNotificationListener(EmailService emailService, ObjectMapper objectMapper, UserRepository userRepository) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "${kafka.topics.notification.mail.name}")
    public void listen(final HashMap<String, Object> record) {
        EmailRequest emailRequest = objectMapper.convertValue(record, EmailRequest.class);
        List<String> emails =  userRepository.getEmailsByUuid(emailRequest.getEventNotificationPojo().getTenantID(), Collections.singletonList(emailRequest.getEventNotificationPojo().getUuid()));
        if(!CollectionUtils.isEmpty(emails)){
            emailService.sendEmail(getEmailReq(getValideEmails(emails), emailRequest.getEventNotificationPojo().getMessage()));
        }
    }



    private Email getEmailReq(Set<String> emails, String msg) {
        return Email.builder().emailTo(emails).body(msg).subject(subject).build();
    }

    private static Set<String> getValideEmails(List<String> emails) {
        Set<String> validUniqueEmails = new HashSet<>();
        for (String email : emails) {
            if (isValid(email))
                validUniqueEmails.add(email);
        }

        return validUniqueEmails;
    }

    private static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
