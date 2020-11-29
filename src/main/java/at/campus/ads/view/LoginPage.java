package at.campus.ads.view;

import at.campus.ads.common.exception.UserLoginException;
import at.campus.ads.common.utils.ConsoleUtils;
import at.campus.ads.persistence.domain.User;
import at.campus.ads.service.LoginService;

import java.util.Optional;

public class LoginPage {
    private static final int MAX_ALLOWED_LOGIN_ATTEMPTS = 3;
    private final LoginService loginService;

    public LoginPage(LoginService loginService) {
        this.loginService = loginService;
    }

    public Optional<User> showLogin() throws UserLoginException {
        int loginAttempts = 0;
        boolean loggedIn = false;
        String username;
        String password;
        Optional<User> userOptional = Optional.empty();

        while (!loggedIn && loginAttempts < MAX_ALLOWED_LOGIN_ATTEMPTS) {
            username = ConsoleUtils.readLineFromConsole("Benutzername:");
            password = ConsoleUtils.readLineFromConsole("Passwort:");
            userOptional = loginService.checkEntriesAndLogin(username, password);
            if (userOptional.isPresent()) {
                loggedIn = true;
                System.out.println("Login erfolgreich! Herzlich willkommen " + username);
            } else {
                System.out.println("Username oder Password nicht korrekt");
            }
            loginAttempts++;
        }

        if (userOptional.isEmpty() && loginAttempts >= MAX_ALLOWED_LOGIN_ATTEMPTS) {
            throw new UserLoginException("Nicht verfügbar wegen zu vieler gescheiterter Versuche. Versuchen Sie es in ein paar Minuten noch einmal oder kontaktieren Sie den Administrator!");
        }

        return userOptional;
    }
}
