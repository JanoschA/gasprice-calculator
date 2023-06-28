package com.dejaad.gpc.service;

import com.dejaad.gpc.APIntegrationTest;
import com.dejaad.gpc.domain.oauth.User;
import com.dejaad.gpc.exception.ResourceNotFoundException;
import com.dejaad.gpc.repository.UserRepository;
import com.dejaad.gpc.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@APIntegrationTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserById() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);

        UserPrincipal resultUser = (UserPrincipal) userService.loadUserById(user.getId());

        assertEquals(user.getId(), resultUser.getId());
    }

    @Test
    void loadUserById_not_found() {
        assertThrows(ResourceNotFoundException.class, () -> userService.loadUserById("1"));
    }
}
