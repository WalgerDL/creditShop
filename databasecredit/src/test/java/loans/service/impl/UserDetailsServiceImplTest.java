package loans.service.impl;

import loans.domain.entity.Role;
import loans.domain.entity.User;
import loans.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest {
    private static final String TEST_ROLE_NAME = "Some role name";
    private static final String TEST_USERNAME = "Some username";
    private static final String TEST_USER_PASS = "Some password";

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_USER_PASS);
        user.setRoles(
                Collections.singleton(
                        new Role() {{
                            setId(1L);
                            setName(TEST_ROLE_NAME);
                        }}
                )
        );

        when(userRepository.findByUsername(anyString())).thenReturn(user);
    }

    @Test
    void loadUserByUsername() {

        UserDetails loadedUser = userDetailsServiceImpl.loadUserByUsername(TEST_USERNAME);

        assertEquals(
                TEST_USERNAME,
                loadedUser.getUsername()
        );
        assertEquals(
                TEST_USER_PASS,
                loadedUser.getPassword()
        );
        assertEquals(
                TEST_ROLE_NAME,
                loadedUser.getAuthorities().stream().findFirst().get().getAuthority()
        );
    }

    @Test()
    void loadUserByUsernameExc() {

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsServiceImpl.loadUserByUsername(TEST_USERNAME)
        );
    }

}
