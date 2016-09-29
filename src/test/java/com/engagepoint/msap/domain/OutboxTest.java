package com.engagepoint.msap.domain;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.repository.MailBoxRepository;
import com.engagepoint.msap.repository.MessageRepository;
import com.engagepoint.msap.repository.OutboxRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static com.engagepoint.msap.MsapTestUtil.assertObjectIdentity;
import static com.engagepoint.msap.MsapTestUtil.prepareMessage;
import static com.engagepoint.msap.MsapTestUtil.setMessage;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OutboxTest {
    @Inject
    private OutboxRepository outboxRepository;

    @Inject
    private MailBoxRepository mailBoxRepository;

    @Inject
    private MessageRepository messageRepository;

    private Outbox createEntity(String messageSubject, String messageBody) {
        Outbox outbox = new Outbox();
        outbox.setMailBox(mailBoxRepository.saveAndFlush(new MailBox()));
        Message message = prepareMessage(messageRepository, messageSubject, messageBody, null, null);
        return setMessage(outboxRepository, outbox, message);
    }

    @Test
    @Transactional
    public void testEntityFields() throws Exception {
        Outbox outbox = createEntity("message subject", "message body");
        Message message = outbox.getMessages().iterator().next();

        Outbox testOutbox = outboxRepository.findOne(outbox.getId());
        assertThat(testOutbox).isNotNull();
        assertThat(testOutbox.getMessages()).isNotNull();
        assertThat(testOutbox.getMessages().size()).isGreaterThan(0);

        Message testMessage = testOutbox.getMessages().iterator().next();
        assertThat(testMessage.getSubject()).isEqualTo(message.getSubject());
        assertThat(testMessage.getBody()).isEqualTo(message.getBody());

        MailBox testMailBox = testOutbox.getMailBox();
        assertThat(testMailBox).isNotNull();
    }

    @Test
    @Transactional
    public void testIdentity() throws Exception {
        Outbox outbox1 = createEntity("message subject 1", "message body 1");
        Outbox outbox2 = createEntity("message subject 2", "message body 2");
        Outbox foundEntity = outboxRepository.findOne(outbox2.getId());

        assertObjectIdentity(outbox1, outbox2, foundEntity, null);
    }
}
