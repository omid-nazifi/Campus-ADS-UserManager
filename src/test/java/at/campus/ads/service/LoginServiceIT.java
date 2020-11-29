package at.campus.ads.service;

import at.campus.ads.common.utils.PasswordUtils;
import at.campus.ads.persistence.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceIT {

    private static final String USERNAME = "user";
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "teser";
    private static final String PASSWORD = "pass";

    @Test
    void GIVEN_noUser_WHEN_checkEntriesAndLogin_THEN_returnFalse() {
        LoginService loginService = new LoginService();
        Optional<User> optionalUser = loginService.checkEntriesAndLogin(USERNAME, PASSWORD);
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void GIVEN_CreatedUser_WHEN_checkEntriesAndLogin_THEN_returnTrue() {
        // GIVEN
        RegisterService registerService = new RegisterService();
        registerService.register(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD);
        LoginService loginService = new LoginService();

        //WHEN
        Optional<User> optionalUser = loginService.checkEntriesAndLogin(USERNAME, PASSWORD);
        assertTrue(optionalUser.isPresent());
    }
}
