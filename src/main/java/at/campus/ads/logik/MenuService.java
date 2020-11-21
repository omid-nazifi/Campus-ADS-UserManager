package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuService {

    private User user;
    private UserDao userDao;

    public MenuService(User user, UserDao userDao) {
        this.user = user;
        this.userDao = userDao;
    }

    public void showMenu() throws IOException {
        System.out.println("");
        System.out.println("Was möchten Sie tun?");
        System.out.println("1. Aktuellen User löschen");
        System.out.println("2. Some other menu option");
        System.out.println("3. Beenden");
        System.out.println("");
        System.out.println("Bitte geben Sie eine Nummber von 1-3 ein:");

        takeMenuInput();
    }

    public void takeMenuInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        switch (input) {
            case "1":
                UserService.deleteUser(user, userDao, reader);
                showMenu();
                break;
            case "2":
                System.out.println("OPTION TWO");
                showMenu();
                break;
            case "3":
                exitMenu();
                break;
            default:
                System.out.println("Ungültige Eingabe, bitte nochmal:");
                takeMenuInput();
        }

    }

    public void exitMenu() {
        System.out.println("");
        System.out.println("Sie haben das Menü verlassen.");
    }

    public static boolean confirmUserInput(String text, BufferedReader reader) throws IOException {
        System.out.println(text + " ( y / other key )");
        String userInputConfirmation = reader.readLine();
        return userInputConfirmation.equals("y");
    }

}
