package com.engagepoint.msap.web.rest.util;

import com.engagepoint.msap.Application;
import com.engagepoint.msap.domain.Authority;
import com.engagepoint.msap.domain.User;
import com.engagepoint.msap.repository.AuthorityRepository;
import com.engagepoint.msap.security.AuthoritiesConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.HashSet;

import static com.engagepoint.msap.MsapTestUtil.addUserRole;
import static com.engagepoint.msap.MsapTestUtil.assertThatConstructorIsPrivate;
import static org.assertj.core.api.StrictAssertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContactUtilTest {

    @Inject
    private AuthorityRepository authorityRepository;

    @Test
    public void testConstructorIsPrivate() throws Exception {
        assertThatConstructorIsPrivate(ContactUtil.class);
    }

    private void assertExtractRoleDescription(User user, String actualRole, String expectedRoleDescription) {
        if (actualRole != null) {
            user.setAuthorities(new HashSet<>());
            addUserRole(authorityRepository, user, actualRole);
        }
        assertThat(ContactUtil.extractRoleDescription(user)).isEqualTo(expectedRoleDescription);
    }

    @Test
    public void testExtractRoleDescription() throws Exception {
        User user = new User();
        assertExtractRoleDescription(user, null, "");

        user.setAuthorities(new HashSet<>());
        assertExtractRoleDescription(user, null, "");

        user.getAuthorities().add(new Authority(){{
            setName("test");
        }});
        assertExtractRoleDescription(user, null, "");

        assertExtractRoleDescription(user, AuthoritiesConstants.USER, "User");
        assertExtractRoleDescription(user, AuthoritiesConstants.ADMIN, "Admin");
        assertExtractRoleDescription(user, AuthoritiesConstants.PARENT, "Parent");
        assertExtractRoleDescription(user, AuthoritiesConstants.CASE_WORKER, "Case Worker");
    }
}
