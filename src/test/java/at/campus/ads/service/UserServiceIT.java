package at.campus.ads.service;

import at.campus.ads.persistence.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceIT {

    private static final String USERNAME = "user";
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "teser";
    private static final String PASSWORD = "pass";

    @Test
    void GIVEN_noUser_WHEN_deleteUser_THEN_throwException() {
        UserService userService = new UserService();
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD);
        assertThrows(IllegalStateException.class, () -> {
            userService.deleteUser(user);
        });
    }

    @Test
    void GIVEN_createdUser_WHEN_deleteUser_THEN_doneSuccessfully() {
        // GIVEN
        UserService userService = new UserService();
        RegisterService registerService = new RegisterService();
        registerService.register(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD);
        Optional<User> optionalUser = new LoginService().checkEntriesAndLogin(USERNAME, PASSWORD);

        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        //WHEN
        userService.deleteUser(user);
    }

    @Test
    void GIVEN_twoDifferentPasswords_WHEN_changePassword_THEN_returnFalse() {
        // GIVEN
        UserService userService = new UserService();
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD);

        //WHEN
        boolean result = userService.changePassword(user, "pass1", "pass2");
        assertFalse(result);
    }

    @Test
    void GIVEN_twoSamePasswords_WHEN_changePassword_THEN_returnTrue() {
        // GIVEN
        UserService userService = new UserService();
        RegisterService registerService = new RegisterService();
        registerService.register(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD);
        Optional<User> optionalUser = new LoginService().checkEntriesAndLogin(USERNAME, PASSWORD);

        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        //WHEN
        boolean result = userService.changePassword(user, "pass123", "pass123");

        //THEN
        assertTrue(result);
    }
}
