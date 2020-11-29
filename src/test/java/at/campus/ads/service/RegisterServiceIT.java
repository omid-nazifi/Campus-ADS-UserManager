package at.campus.ads.service;

import at.campus.ads.common.utils.ConsoleUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceIT {

    private static final String USERNAME = "user";
    private static final String FIRST_NAME = "test";
    private static final String LAST_NAME = "teser";
    private static final String PASSWORD = "pass";

    @Test
    void GIVEN_noPassword_WHEN_register_THEN_throwException() {
        RegisterService registerService =  new RegisterService();
        assertThrows(RuntimeException.class, () -> {
            registerService.register(USERNAME, FIRST_NAME, LAST_NAME, null);
        });
    }

    @Test
    void GIVEN_correctData_WHEN_register_THEN_returnTrue() {
        RegisterService registerService =  new RegisterService();
        assertTrue(registerService.register(USERNAME, FIRST_NAME, LAST_NAME, PASSWORD));
    }
}
