package loans.service.impl;


import loans.domain.entity.Role;
import loans.domain.entity.User;
import loans.repository.RoleRepository;
import loans.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceImplTest.UserServiceConfig.class)
public class UserServiceImplTest {
    private static final String TEST_ROLE_NAME = "Some role name";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    public void setUp() throws Exception {

        user = new User();
        user.setId(1L);
        user.setUsername("Some username");
        user.setPassword("Some password");

        when(roleRepository.findById(anyLong()))
                .thenReturn(
                        Optional.of(
                                new Role() {{
                                    setId(1L);
                                    setName(TEST_ROLE_NAME);
                                }}
                        )
                );
    }

    @Test
    void save() {

        String userPassword = user.getPassword();

        user.setPassword(bCryptPasswordEncoder.encode(userPassword));
        //Role all user ROLE_USER
        Optional<Role> ro = roleRepository.findById(1l);
        user.setRoles(Collections.singleton(ro.get()));

        assertTrue(
                bCryptPasswordEncoder.matches(
                        userPassword,
                        user.getPassword()
                )
        );
        assertEquals(
                TEST_ROLE_NAME,
                user.getRoles().stream().findFirst().get().getName()
        );
    }

    @Configuration
    static class UserServiceConfig {

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public RoleRepository roleRepository() {
            return mock(RoleRepository.class);
        }
    }

}
