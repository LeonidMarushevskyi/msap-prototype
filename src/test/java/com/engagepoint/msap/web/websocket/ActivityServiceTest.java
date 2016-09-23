package com.engagepoint.msap.web.websocket;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.repository.UserRepository;
import com.engagepoint.msap.web.websocket.dto.ActivityDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.HashMap;

import static com.engagepoint.msap.MsapTestUtil.prepareUser;
import static com.engagepoint.msap.MsapTestUtil.setCurrentUser;
import static com.engagepoint.msap.config.WebsocketConfiguration.IP_ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActivityServiceTest {
    private static final String SESSION_ID = "123";
    private static final String CURRENT_LOGIN = "current1";
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 80;
    private static final String PAGE = "/";

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    private Principal principal;

    private ActivityDTO activityDTO;

    private StompHeaderAccessor stompHeaderAccessor;

    private ActivityService activityService;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        activityService = new ActivityService();
    }

    private void initSendActivity() throws Exception {
        setCurrentUser(prepareUser(userRepository, passwordEncoder, CURRENT_LOGIN));
        principal = () -> CURRENT_LOGIN;

        activityDTO = new ActivityDTO();
        activityDTO.setPage(PAGE);

        stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.CONNECT);
        stompHeaderAccessor.setSessionId(SESSION_ID);
        stompHeaderAccessor.setSessionAttributes(new HashMap<String, Object>() {{
            put(IP_ADDRESS, new InetSocketAddress(HOSTNAME, PORT));
        }});
    }

    @Test
    public void testSendActivity() throws Exception {
        initSendActivity();

        ActivityDTO resultDTO = activityService.sendActivity(activityDTO, stompHeaderAccessor, principal);

        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.getSessionId()).isEqualTo(SESSION_ID);
        assertThat(resultDTO.getUserLogin()).isEqualTo(CURRENT_LOGIN);
        assertThat(resultDTO.getIpAddress()).isEqualTo("/" + HOSTNAME + ":" + PORT);
        assertThat(resultDTO.getPage()).isEqualTo(PAGE);
        assertThat(resultDTO.getTime()).isNotNull();
    }
}
