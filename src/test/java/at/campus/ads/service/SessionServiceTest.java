package at.campus.ads.service;

import at.campus.ads.persistence.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    private SessionService sessionService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("pass");
        user.setFirstName("fname");
        user.setLastName("lname");
        sessionService = new SessionService(user);
    }

    @Test
    void getUser() {
        assertEquals(user.getUsername(), sessionService.getUser().getUsername());
    }

    @Test
    void deleteUserFromSession() {
        sessionService.deleteUserFromSession();
        assertEquals(null, sessionService.getUser());
    }

    @Test
    void isSessionActive_returnTrue() throws InterruptedException {
        Thread.currentThread().sleep(10000);
        assertTrue(sessionService.isSessionActive());
    }

    @Test
    void isSessionActive_returnFalse() throws InterruptedException {
        Thread.currentThread().sleep(61000);
        assertFalse(sessionService.isSessionActive());
    }
}