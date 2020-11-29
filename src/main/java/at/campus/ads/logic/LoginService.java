package at.campus.ads.logic;

import at.campus.ads.domain.User;
import at.campus.ads.exception.UserLoginException;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class LoginService {
    private static final int MAX_ALLOWED_LOGIN_ATTEMPTS = 3;

    public static Optional<User> login() throws RuntimeException, UserLoginException {
        int loginAttempts = 0;
        boolean loggedIn = false;
        String username;
        String password;
        Optional<User> userOptional = Optional.empty();

        while (!loggedIn && loginAttempts < MAX_ALLOWED_LOGIN_ATTEMPTS) {
            username = ConsoleUtils.readLineFromConsole("Benutzername:");
            password = ConsoleUtils.readLineFromConsole("Passwort:");
            userOptional = checkCredentials(username, password);
            if (userOptional.isPresent()) loggedIn = true;
            loginAttempts++;
        }

        if(userOptional.isEmpty() && loginAttempts >= MAX_ALLOWED_LOGIN_ATTEMPTS) {
            throw new UserLoginException("Nicht verfügbar wegen zu vieler gescheiterter Versuche. Versuchen Sie es in ein paar Minuten noch einmal oder kontaktieren Sie den Administrator!");
        }

        return userOptional;
    }

    private static Optional<User> checkCredentials(String username, String password) throws RuntimeException {
        try {
            UserDao userDao = new UserDao();
            Optional<User> userOptional = userDao.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (PasswordUtils.verifyUserPassword(password, user.getPassword())) {
                    System.out.println("Login erfolgreich! Herzlich willkommen " + username);
                    return userOptional;
                }
            }
            System.out.println("Username oder Password nicht korrekt");
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Eine Exception beim Login!", e);
        }
    }

}
