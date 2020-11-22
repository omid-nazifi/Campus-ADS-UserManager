package at.campus.ads.logic;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Register {

    public boolean doRegister() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String username = ConsoleUtils.readLineFromConsole("Benutzername:");

        if (!isUsernameExistingInDatabase(username)) {
            String firstname = ConsoleUtils.readLineFromConsole("Vorname:");
            String lastname = ConsoleUtils.readLineFromConsole("Nachname:");
            String password = ConsoleUtils.readLineFromConsole("Passwort:");

            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setUsername(username);
            user.setPassword(PasswordUtils.generateSecurePassword(password));

            UserDao userDao = new UserDao();
            userDao.save(user);
            System.out.println("Herzlichen Glückwunsch, Ihre Registrierung war erfolgreich.");
            return true;
        }

        System.out.println("Username existiert bereits");
        int entryCode = Menu.showRegisterMenu();
        if (entryCode == 1) {
            doRegister();
        }

        return false;
    }

    private boolean isUsernameExistingInDatabase(String username) {
        UserDao userDao = new UserDao();
        return userDao.existsUsername(username);
    }

}
