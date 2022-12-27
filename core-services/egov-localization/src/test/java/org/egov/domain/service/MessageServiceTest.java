package org.egov.domain.service;


import org.egov.domain.model.*;
import org.egov.persistence.repository.MessageCacheRepository;
import org.egov.persistence.repository.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MessageService.class})
@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    private static final String ENGLISH_INDIA = "en_IN";
    private static final String TENANT_ID = "tenant_123";
    private static final String MR_IN = "mr_IN";

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageCacheRepository messageCacheRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testUpsert3() {
        doNothing().when(messageRepository)
            .upsert((String) any(), (String) any(), (String) any(), (List<Message>) any(), (AuthenticatedUser) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        Tenant tenant = new Tenant("42");
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getLocale()).thenReturn("en");
        Message message = mock(Message.class);
        when(message.getLocale()).thenReturn("en");
        when(message.getModule()).thenReturn("Module");
        when(message.getTenant()).thenReturn("Tenant");
        when(message.getMessageIdentity()).thenReturn(messageIdentity);

        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(message);
        messageService.upsert(tenant, messageList, new AuthenticatedUser(123L));
        verify(messageRepository).upsert((String) any(), (String) any(), (String) any(), (List<Message>) any(),
            (AuthenticatedUser) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(message).getLocale();
        verify(message).getModule();
        verify(message).getTenant();
        verify(message).getMessageIdentity();
        verify(messageIdentity).getLocale();
    }

    @Test
    void testCreate() {
        doNothing().when(messageRepository).save((List<Message>) any(), (AuthenticatedUser) any());
        Tenant tenant = new Tenant("42");
        ArrayList<Message> messages = new ArrayList<>();
        messageService.create(tenant, messages, new AuthenticatedUser(123L));
        verify(messageRepository).save((List<Message>) any(), (AuthenticatedUser) any());
    }

    @Test
    void testCreate2() {
        doNothing().when(messageRepository).save((List<Message>) any(), (AuthenticatedUser) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        Tenant tenant = new Tenant("42");
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getLocale()).thenReturn("en");
        Message message = mock(Message.class);
        when(message.getMessageIdentity()).thenReturn(messageIdentity);

        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(message);
        messageService.create(tenant, messageList, new AuthenticatedUser(123L));
        verify(messageRepository).save((List<Message>) any(), (AuthenticatedUser) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(message).getMessageIdentity();
        verify(messageIdentity).getLocale();
    }

    @Test
    void testUpdateMessagesForModule() {
        Tenant tenant = new Tenant("42");
        ArrayList<Message> messages = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,
            () -> messageService.updateMessagesForModule(tenant, messages, new AuthenticatedUser(123L)));
    }

    @Test
    void testUpdateMessagesForModule2() {
        doNothing().when(messageRepository)
            .update((String) any(), (String) any(), (String) any(), (List<Message>) any(), (AuthenticatedUser) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        Tenant tenant = mock(Tenant.class);
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getLocale()).thenReturn("en");
        Message message = mock(Message.class);
        when(message.getLocale()).thenReturn("en");
        when(message.getModule()).thenReturn("Module");
        when(message.getTenant()).thenReturn("Tenant");
        when(message.getMessageIdentity()).thenReturn(messageIdentity);

        ArrayList<Message> messageList = new ArrayList<>();
        messageList.add(message);
        messageService.updateMessagesForModule(tenant, messageList, new AuthenticatedUser(123L));
        verify(messageRepository).update((String) any(), (String) any(), (String) any(), (List<Message>) any(),
            (AuthenticatedUser) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(message).getLocale();
        verify(message).getModule();
        verify(message).getTenant();
        verify(message).getMessageIdentity();
        verify(messageIdentity).getLocale();
    }

    @Test
    void testBustCache() {
        doNothing().when(messageCacheRepository).bustCache();
        messageService.bustCache();
        verify(messageCacheRepository).bustCache();
    }

    @Test
    void testBustCache2() {
        doThrow(new IllegalArgumentException()).when(messageCacheRepository).bustCache();
        assertThrows(IllegalArgumentException.class, () -> messageService.bustCache());
        verify(messageCacheRepository).bustCache();
    }

    @Test
    void testGetFilteredMessages3() {
        when(messageCacheRepository.getComputedMessages((String) any(), (Tenant) any())).thenReturn(new ArrayList<>());
        Tenant tenantId = new Tenant("42");
        assertTrue(
            messageService.getFilteredMessages(new MessageSearchCriteria(tenantId, "en", "Module", new HashSet<>()))
                .isEmpty());
        verify(messageCacheRepository).getComputedMessages((String) any(), (Tenant) any());
    }

    @Test
    public void test_should_return_computed_messages_from_cache_when_present() {
        String tenantId = "a.b.c";
        final Tenant defaultTenant = new Tenant(Tenant.DEFAULT_TENANT);
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("code1")
            .locale(ENGLISH_INDIA)
            .module("module")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("default message1")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("code2")
            .locale(ENGLISH_INDIA)
            .module("module")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("default message2")
            .build();
        List<Message> expectedMessages = Arrays.asList(defaultMessage1, defaultMessage2);
        when(messageCacheRepository.getComputedMessages(MR_IN, new Tenant(tenantId)))
            .thenReturn(expectedMessages);
        final MessageSearchCriteria searchCriteria = MessageSearchCriteria.builder()
            .locale(MR_IN)
            .tenantId(new Tenant(tenantId))
            .module("module")
            .build();

        List<Message> actualMessages = messageService.getFilteredMessages(searchCriteria);

        Assertions.assertEquals(0, actualMessages.size());
    }

    @Test
    public void test_should_return_messages_filtered_by_module_name() {
        String tenantId = "a.b.c";
        final Tenant defaultTenant = new Tenant(Tenant.DEFAULT_TENANT);
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("code1")
            .locale(ENGLISH_INDIA)
            .module("module1")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("default message1")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("code2")
            .locale(ENGLISH_INDIA)
            .module("module2")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("default message2")
            .build();
        List<Message> expectedMessages = Arrays.asList(defaultMessage1, defaultMessage2);
        when(messageCacheRepository.getComputedMessages(MR_IN, new Tenant(tenantId)))
            .thenReturn(expectedMessages);
        final MessageSearchCriteria searchCriteria = MessageSearchCriteria.builder()
            .locale(MR_IN)
            .tenantId(new Tenant(tenantId))
            .module("module1")
            .build();

        List<Message> actualMessages = messageService.getFilteredMessages(searchCriteria);

        Assertions.assertEquals(0, actualMessages.size());
    }

  /*  @Test
    public void test_should_return_un_filtered_messages_when_module_is_not_present() {
        String tenantId = "a.b.c";
        final Tenant defaultTenant = new Tenant(Tenant.DEFAULT_TENANT);
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("code1")
            .locale(ENGLISH_INDIA)
            .module("module1")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("default message1")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("code2")
            .locale(ENGLISH_INDIA)
            .module("module2")
            .tenant(defaultTenant)
            .build();
        Message defaultMessage2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("default message2")
            .build();
        List<Message> expectedMessages = Arrays.asList(defaultMessage1, defaultMessage2);
        when(messageCacheRepository.getComputedMessages(MR_IN, new Tenant(tenantId)))
            .thenReturn(expectedMessages);
        final MessageSearchCriteria searchCriteria = MessageSearchCriteria.builder()
            .locale(MR_IN)
            .tenantId(new Tenant(tenantId))
            .module(null)
            .build();

        List<Message> actualMessages = messageService.getFilteredMessages(searchCriteria);

        assertEquals(2, actualMessages.size());
    }*/

    @Test
    public void test_should_save_messages() {
        List<Message> modelMessages = getMessages();
        final Tenant tenant = new Tenant(TENANT_ID);
        final AuthenticatedUser user = new AuthenticatedUser(1L);

        messageService.create(tenant, modelMessages, user);

        verify(messageRepository).save(modelMessages, user);
    }

    @Test
    public void test_should_bust_cache_for_newly_persisted_messages() {
        List<Message> modelMessages = getMessages();
        final Tenant tenant = new Tenant(TENANT_ID);
        final AuthenticatedUser user = new AuthenticatedUser(1L);

        messageService.create(tenant, modelMessages, user);

        verify(messageCacheRepository, times(1)).bustCacheEntry(MR_IN, tenant);
        verify(messageCacheRepository, times(1)).bustCacheEntry(ENGLISH_INDIA, tenant);
    }

    @Test
    public void test_should_update_messages() {
        final Tenant tenant = new Tenant(TENANT_ID);
        final String module = "module";
        final AuthenticatedUser user = new AuthenticatedUser(1L);
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("core.msg.OTPvalidated")
            .locale(MR_IN)
            .module(module)
            .tenant(tenant)
            .build();
        Message message1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("OTP यशस्वीपणे प्रमाणित")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("core.lbl.imageupload")
            .locale(MR_IN)
            .module(module)
            .tenant(tenant)
            .build();
        Message message2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("प्रतिमा यशस्वीरित्या अपलोड")
            .build();

        List<Message> modelMessages = Arrays.asList(message1, message2);

        messageService.updateMessagesForModule(tenant, modelMessages, user);

        verify(messageRepository).update(TENANT_ID, MR_IN, module, modelMessages, user);
    }

    @Test
    public void test_should_bust_cache_entries_for_update_messages() {
        final Tenant tenant = new Tenant(TENANT_ID);
        final String module = "module";
        final AuthenticatedUser user = new AuthenticatedUser(1L);
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("core.msg.OTPvalidated")
            .locale(MR_IN)
            .module(module)
            .tenant(tenant)
            .build();
        Message message1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("OTP यशस्वीपणे प्रमाणित")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("core.lbl.imageupload")
            .locale(MR_IN)
            .module(module)
            .tenant(tenant)
            .build();
        Message message2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("प्रतिमा यशस्वीरित्या अपलोड")
            .build();

        List<Message> modelMessages = Arrays.asList(message1, message2);

        messageService.updateMessagesForModule(tenant, modelMessages, user);

        verify(messageCacheRepository, times(1)).bustCacheEntry(MR_IN, tenant);
    }

    @Test
    public void test_should_delete_messages() {
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("code1")
            .locale(MR_IN)
            .module("module1")
            .tenant(new Tenant("tenant1"))
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("code2")
            .locale(ENGLISH_INDIA)
            .module("module2")
            .tenant(new Tenant("tenant1"))
            .build();
        final MessageIdentity messageIdentity3 = MessageIdentity.builder()
            .code("code3")
            .locale(ENGLISH_INDIA)
            .module("module3")
            .tenant(new Tenant("tenant2"))
            .build();

        List<MessageIdentity> messageIdentities = Arrays.asList(messageIdentity1, messageIdentity2, messageIdentity3);

        messageService.delete(messageIdentities);

        verify(messageRepository, times(1))
            .delete("tenant1", MR_IN, "module1", Collections.singletonList("code1"));
        verify(messageRepository, times(1))
            .delete("tenant1", ENGLISH_INDIA, "module2", Collections.singletonList("code2"));
        verify(messageRepository, times(1))
            .delete("tenant2", ENGLISH_INDIA, "module3", Collections.singletonList("code3"));
    }

    @Test
    void testDelete() {
        messageService.delete(new ArrayList<>());
    }

    @Test
    void testDelete2() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    void testDelete4() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity1 = mock(MessageIdentity.class);
        when(messageIdentity1.getCode()).thenReturn("Code");
        when(messageIdentity1.getModule()).thenReturn("Module");
        when(messageIdentity1.getLocale()).thenReturn("en");
        when(messageIdentity1.getTenant()).thenReturn(new Tenant("42"));

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity1);
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity1).getCode();
        verify(messageIdentity1, atLeast(1)).getLocale();
        verify(messageIdentity1).getModule();
        verify(messageIdentity1).getTenant();
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    void testDelete6() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity1 = mock(MessageIdentity.class);
        when(messageIdentity1.getCode()).thenReturn("Code");
        when(messageIdentity1.getModule()).thenReturn("Module");
        when(messageIdentity1.getLocale()).thenReturn("en");
        when(messageIdentity1.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity2 = mock(MessageIdentity.class);
        when(messageIdentity2.getCode()).thenReturn("Code");
        when(messageIdentity2.getModule()).thenReturn("foo");
        when(messageIdentity2.getLocale()).thenReturn("en");
        when(messageIdentity2.getTenant()).thenReturn(new Tenant("42"));

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity2);
        messageIdentityList.add(messageIdentity1);
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository, atLeast(1)).delete((String) any(), (String) any(), (String) any(),
            (List<String>) any());
        verify(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity2).getCode();
        verify(messageIdentity2, atLeast(1)).getLocale();
        verify(messageIdentity2).getModule();
        verify(messageIdentity2).getTenant();
        verify(messageIdentity1).getCode();
        verify(messageIdentity1, atLeast(1)).getLocale();
        verify(messageIdentity1).getModule();
        verify(messageIdentity1).getTenant();
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    void testDelete7() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity1 = mock(MessageIdentity.class);
        when(messageIdentity1.getCode()).thenReturn("Code");
        when(messageIdentity1.getModule()).thenReturn("Module");
        when(messageIdentity1.getLocale()).thenReturn("en");
        when(messageIdentity1.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity2 = mock(MessageIdentity.class);
        when(messageIdentity2.getCode()).thenReturn("Code");
        when(messageIdentity2.getModule()).thenReturn("foo");
        when(messageIdentity2.getLocale()).thenReturn("42");
        when(messageIdentity2.getTenant()).thenReturn(new Tenant("42"));

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity2);
        messageIdentityList.add(messageIdentity1);
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository, atLeast(1)).delete((String) any(), (String) any(), (String) any(),
            (List<String>) any());
        verify(messageCacheRepository, atLeast(1)).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity2).getCode();
        verify(messageIdentity2, atLeast(1)).getLocale();
        verify(messageIdentity2).getModule();
        verify(messageIdentity2).getTenant();
        verify(messageIdentity1).getCode();
        verify(messageIdentity1, atLeast(1)).getLocale();
        verify(messageIdentity1).getModule();
        verify(messageIdentity1).getTenant();
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    void testDelete8() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity1 = mock(MessageIdentity.class);
        when(messageIdentity1.getCode()).thenReturn("Code");
        when(messageIdentity1.getModule()).thenReturn("Module");
        when(messageIdentity1.getLocale()).thenReturn("en");
        when(messageIdentity1.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity2 = mock(MessageIdentity.class);
        when(messageIdentity2.getCode()).thenReturn("Code");
        when(messageIdentity2.getModule()).thenReturn("foo");
        when(messageIdentity2.getLocale()).thenReturn("en");
        when(messageIdentity2.getTenant()).thenReturn(new Tenant("Tenant Id"));

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity2);
        messageIdentityList.add(messageIdentity1);
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository, atLeast(1)).delete((String) any(), (String) any(), (String) any(),
            (List<String>) any());
        verify(messageCacheRepository, atLeast(1)).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity2).getCode();
        verify(messageIdentity2, atLeast(1)).getLocale();
        verify(messageIdentity2).getModule();
        verify(messageIdentity2).getTenant();
        verify(messageIdentity1).getCode();
        verify(messageIdentity1, atLeast(1)).getLocale();
        verify(messageIdentity1).getModule();
        verify(messageIdentity1).getTenant();
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    void testDelete9() {
        doNothing().when(messageRepository).delete((String) any(), (String) any(), (String) any(), (List<String>) any());
        doNothing().when(messageCacheRepository).bustCacheEntry((String) any(), (Tenant) any());
        MessageIdentity messageIdentity = mock(MessageIdentity.class);
        when(messageIdentity.getCode()).thenReturn("Code");
        when(messageIdentity.getModule()).thenReturn("Module");
        when(messageIdentity.getLocale()).thenReturn("en");
        when(messageIdentity.getTenant()).thenReturn(new Tenant("42"));
        MessageIdentity messageIdentity1 = mock(MessageIdentity.class);
        when(messageIdentity1.getCode()).thenReturn("Code");
        when(messageIdentity1.getModule()).thenReturn("Module");
        when(messageIdentity1.getLocale()).thenReturn("en");
        when(messageIdentity1.getTenant()).thenReturn(new Tenant("42"));
        Tenant tenant = mock(Tenant.class);
        when(tenant.getTenantId()).thenReturn("42");
        MessageIdentity messageIdentity2 = mock(MessageIdentity.class);
        when(messageIdentity2.getCode()).thenReturn("Code");
        when(messageIdentity2.getModule()).thenReturn("foo");
        when(messageIdentity2.getLocale()).thenReturn("en");
        when(messageIdentity2.getTenant()).thenReturn(tenant);

        ArrayList<MessageIdentity> messageIdentityList = new ArrayList<>();
        messageIdentityList.add(messageIdentity2);
        messageIdentityList.add(messageIdentity1);
        messageIdentityList.add(messageIdentity);
        messageService.delete(messageIdentityList);
        verify(messageRepository, atLeast(1)).delete((String) any(), (String) any(), (String) any(),
            (List<String>) any());
        verify(messageCacheRepository, atLeast(1)).bustCacheEntry((String) any(), (Tenant) any());
        verify(messageIdentity2).getCode();
        verify(messageIdentity2, atLeast(1)).getLocale();
        verify(messageIdentity2).getModule();
        verify(messageIdentity2).getTenant();
        verify(tenant).getTenantId();
        verify(messageIdentity1).getCode();
        verify(messageIdentity1, atLeast(1)).getLocale();
        verify(messageIdentity1).getModule();
        verify(messageIdentity1).getTenant();
        verify(messageIdentity).getCode();
        verify(messageIdentity, atLeast(1)).getLocale();
        verify(messageIdentity).getModule();
        verify(messageIdentity).getTenant();
    }

    @Test
    public void test_should_delete_cache_for_deleted_messages() {
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("code1")
            .locale(MR_IN)
            .module("module1")
            .tenant(new Tenant("tenant1"))
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("code2")
            .locale(ENGLISH_INDIA)
            .module("module2")
            .tenant(new Tenant("tenant1"))
            .build();
        final MessageIdentity messageIdentity3 = MessageIdentity.builder()
            .code("code3")
            .locale(ENGLISH_INDIA)
            .module("module3")
            .tenant(new Tenant("tenant2"))
            .build();

        List<MessageIdentity> messageIdentities = Arrays.asList(messageIdentity1, messageIdentity2, messageIdentity3);

        messageService.delete(messageIdentities);

        verify(messageCacheRepository, times(1))
            .bustCacheEntry(MR_IN, new Tenant("tenant1"));
        verify(messageCacheRepository, times(1))
            .bustCacheEntry(ENGLISH_INDIA, new Tenant("tenant2"));
    }


    private List<Message> getMessages() {
        final MessageIdentity messageIdentity1 = MessageIdentity.builder()
            .code("core.msg.OTPvalidated")
            .locale(MR_IN)
            .module("module")
            .tenant(new Tenant(TENANT_ID))
            .build();
        Message message1 = Message.builder()
            .messageIdentity(messageIdentity1)
            .message("OTP यशस्वीपणे प्रमाणित")
            .build();
        final MessageIdentity messageIdentity2 = MessageIdentity.builder()
            .code("core.lbl.imageupload")
            .locale(MR_IN)
            .module("module")
            .tenant(new Tenant(TENANT_ID))
            .build();
        Message message2 = Message.builder()
            .messageIdentity(messageIdentity2)
            .message("प्रतिमा यशस्वीरित्या अपलोड")
            .build();
        final MessageIdentity messageIdentity3 = MessageIdentity.builder()
            .code("core.msg.entermobileno")
            .locale(ENGLISH_INDIA)
            .module("module")
            .tenant(new Tenant(TENANT_ID))
            .build();
        Message message3 = Message.builder()
            .messageIdentity(messageIdentity3)
            .message("EnterMobileNumber")
            .build();
        final MessageIdentity messageIdentity4 = MessageIdentity.builder()
            .code("core.msg.enterfullname")
            .locale(ENGLISH_INDIA)
            .module("module")
            .tenant(new Tenant(TENANT_ID))
            .build();
        Message message4 = Message.builder()
            .messageIdentity(messageIdentity4)
            .message("Enter fullname")
            .build();

        return (Arrays.asList(message1, message2, message3, message4));
    }

}