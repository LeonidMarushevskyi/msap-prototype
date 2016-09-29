package com.engagepoint.msap.domain;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.repository.DraftRepository;
import com.engagepoint.msap.repository.InboxRepository;
import com.engagepoint.msap.repository.MailBoxRepository;
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
import static com.engagepoint.msap.MsapTestUtil.prepareMailBox;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MailBoxTest {
    @Inject
    private MailBoxRepository mailBoxRepository;

    @Inject
    private InboxRepository inboxRepository;

    @Inject
    private OutboxRepository outboxRepository;

    @Inject
    private DraftRepository draftRepository;

    private MailBox createEntity() {
        return prepareMailBox(mailBoxRepository, inboxRepository, outboxRepository, draftRepository);
    }

    @Test
    @Transactional
    public void testEntityFields() throws Exception {
        MailBox mailBox = createEntity();

        MailBox testMailBox = mailBoxRepository.findOne(mailBox.getId());
        assertThat(testMailBox).isNotNull();
        assertThat(testMailBox.getInbox()).isNotNull();
        assertThat(testMailBox.getOutbox()).isNotNull();
        assertThat(testMailBox.getDraft()).isNotNull();
    }

    @Test
    @Transactional
    public void testIdentity() throws Exception {
        MailBox mailBox1 = createEntity();
        MailBox mailBox2 = createEntity();
        MailBox foundEntity = mailBoxRepository.findOne(mailBox2.getId());

        assertObjectIdentity(mailBox1, mailBox2, foundEntity, null);
    }
}
