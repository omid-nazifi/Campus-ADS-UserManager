package at.campus.ads.logic;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class LoginService {
    private static final int MAX_ALLOWED_LOGIN_ATTEMPTS = 3;

    public static User login() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int loginAttempts = 0;
        boolean loggedIn = false;
        String username;
        String password;
        User user = null;

        while (!loggedIn && loginAttempts < MAX_ALLOWED_LOGIN_ATTEMPTS) {
            username = ConsoleUtils.readLineFromConsole("Benutzername:");
            password = ConsoleUtils.readLineFromConsole("Passwort:");
            user = checkCredentials(username, password);
            if (user != null) loggedIn = true;
            loginAttempts++;
        }
        return user;
    }

    private static User checkCredentials(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserDao userDao = new UserDao();
        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordUtils.verifyUserPassword(password, user.getPassword())) {
                System.out.println("Login erfolgreich! Herzlich willkommen " + username);
                return user;
            }
        }
        System.out.println("Username oder Password nicht korrekt");
        return null;
    }

}
