package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;

public class Login {
    private String username;
    private String password;
    private int MaxErlaubteAnmeldeVersuche = 3;
    private int anmeldeVersuche = 0;
    private boolean loggedIn = false;

    public Login() throws IOException {
        while(loggedIn==false && anmeldeVersuche<MaxErlaubteAnmeldeVersuche) {
            this.username = readLineFromConsole("Benutzername:");
            this.password = readLineFromConsole("Passwort:");
            System.out.println("LOG [INFO] " + login());
            anmeldeVersuche++;
        }
    }

    private String login() {
        String meldung="";
        UserDao userDao = new UserDao();
        List<User> users = userDao.getAll();

        for (User user : users)
        {
            if(user.getUsername().equals(this.username) && user.getPassword().equals(this.password))
            {
                meldung = "Login erfolgreich! Herzlich willkommen "+this.username;
                loggedIn = true;
            }
            else
            {
                meldung = "username oder password nicht korrekt";
            }
        }
        return meldung;
    }

    private String readLineFromConsole(String beschreibungFuerConsole) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(beschreibungFuerConsole);
        return br.readLine();
    }


}
