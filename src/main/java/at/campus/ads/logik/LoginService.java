package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.PasswordUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

public class LoginService {

    public LoginService()  {

    }
    public static User login() throws IOException{
        int MaxErlaubteAnmeldeVersuche = 3;
        int anmeldeVersuche = 0;
        boolean loggedIn = false;
        String username;
        String password;
        User user = null;


        while (!loggedIn && anmeldeVersuche < MaxErlaubteAnmeldeVersuche) {
            username = readLineFromConsole("Benutzername:");
            password = readLineFromConsole("Passwort:");
            user = checkCredentials(username, password);
            if (user != null) loggedIn = true;
            anmeldeVersuche++;
        }
        return user;
    }

    private static User checkCredentials(String username, String password) {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();

        for (User user : users) {
            if (user.getUsername().equals(username) && PasswordUtils.verifyUserPassword(password, user.getPassword())) {
                System.out.println("Login erfolgreich! Herzlich willkommen " + username);
                return user;
            }
        }
        System.out.println("Username oder Password nicht korrekt");
        return null;
    }

    // TODO use ConsoleUtils instead to be consistent
    private static String readLineFromConsole(String beschreibungFuerConsole) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(beschreibungFuerConsole);
        return br.readLine();
    }


}
