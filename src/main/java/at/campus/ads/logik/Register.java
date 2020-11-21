package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.Menu;
import at.campus.ads.utils.PasswordUtils;

public class Register {

    public boolean doRegister() {
        String username = ConsoleUtils.readLineFromConsole("Benutzername:");

        if(!isUsernameExistingInDatabase(username)) {
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
            return true;
        }

        System.out.println("„Username existiert bereits");
        int entryCode = Menu.showRegisterMenu();
        if(entryCode == 1) {
            doRegister();
        }

        return false;
    }

    private boolean isUsernameExistingInDatabase(String username) {
        UserDao userDao = new UserDao();
        return userDao.existsUsername(username);
    }

}
