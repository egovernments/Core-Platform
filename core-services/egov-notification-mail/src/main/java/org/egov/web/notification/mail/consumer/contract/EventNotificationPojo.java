package org.egov.web.notification.mail.consumer.contract;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class EventNotificationPojo {

    private String tenantID;
    private String uuid;
    private String message;

}