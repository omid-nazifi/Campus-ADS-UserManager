package at.campus.ads.utils;

import at.campus.ads.logik.Login;
import at.campus.ads.logik.Register;
import at.campus.ads.logik.UserService;
import javassist.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    public static ActionEnum showMenuForPage(PageEnum page) throws NotFoundException {
        int actionIndex = -1;
        switch (page) {
            case START_PAGE:
                actionIndex = Menu.showStartPageMenu();
                break;
            case HOME:
                actionIndex = Menu.showHomeMenu();
                break;
        }
        return ActionEnum.get(actionIndex);
    }

    public static PageEnum doAction(ActionEnum action) throws IOException {
        switch (action) {
            case EXIT:
                System.exit(0);
            case LOGIN:
                Login login = new Login();
                // TODO: login must return user object to keep as session
                return PageEnum.HOME;
            case REGISTER:
                Register register = new Register();
                boolean registerResult = register.doRegister();
                if(registerResult) {
                    return doAction(ActionEnum.LOGIN);
                }
                return PageEnum.START_PAGE;
            case DELETE_ACCOUNT:
                if( UserService.deleteUser()) {
                    return PageEnum.START_PAGE;
                } else {
                    return PageEnum.HOME;
                }
            case CHANGE_PASSWORD:
                // TODO Change Password
                break;
        }
        return PageEnum.START_PAGE;
    }


    public static void showWelcomeMessage() {
        System.out.println();
        System.out.println("Herzlich willkommen.");
    }

    public static void showWrongMessage() {
        System.out.println();
        System.out.println("Ungültiger Eingabewert. Bitte versuchen Sie es nochmal:");
    }

    public static int showRegisterMenu() {
        return ConsoleUtils.readNumberFromConsole("\t0. Abbrechen \n\t1. Nochmals versuchen \n");
    }

    public static boolean confirmUserInput(String text) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(text + " ( y / other key )");
        String userInputConfirmation = reader.readLine();
        return userInputConfirmation.equals("y");
    }

    private static int showStartPageMenu() {
        System.out.println("Herzlich willkommen. Bitte loggen ein, Wenn Sie einen Konto haben, sonst legen neuen Konto an:");
        return ConsoleUtils.readNumberFromConsole("\t1. Einloggen \n\t2. Registrierung \n\t0.Ausgang \n");
    }

    private static int showHomeMenu() {
        System.out.println("Liebe Kundin / Lieber Kunde, Sie sind in Ihr Profil.");
        return ConsoleUtils.readNumberFromConsole("\t3. Passwort ändern \n\t4. Konto löschen \n\t5. Ausloggen \n");
    }

}
