package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.Menu;

public class Register {
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    public boolean doRegister() {
        this.username = ConsoleUtils.readLineFromConsole("Benutzername:");

        if(!isUsernameExistingInDatabase(username)) {
            this.firstname = ConsoleUtils.readLineFromConsole("Vorname:");
            this.lastname = ConsoleUtils.readLineFromConsole("Nachname:");
            this.password = ConsoleUtils.readLineFromConsole("Passwort:");

            User user = new User();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setUsername(username);
            user.setPassword(password);

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
