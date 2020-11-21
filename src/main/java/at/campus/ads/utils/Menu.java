package at.campus.ads.utils;

import at.campus.ads.domain.User;
import at.campus.ads.logik.Login;
import at.campus.ads.logik.Register;
import at.campus.ads.logik.UserService;
import at.campus.ads.persistence.UserDao;
import javassist.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    private static Session session;

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
        if (session != null && !session.isSessionActive()) {
            showInactiveSessionMessage();
            session.deleteUser();
            return PageEnum.START_PAGE;
        }
        switch (action) {
            case EXIT:
                System.exit(0);
            case LOGIN:
                Login login = new Login();
                // TODO: login must return user object to keep as session
                User user = new UserDao().findByUsername("onazifi").get(); // this is temporary
                session = new Session(user);
                return PageEnum.HOME;
            case REGISTER:
                Register register = new Register();
                boolean registerResult = register.doRegister();
                if(registerResult) {
                    return doAction(ActionEnum.LOGIN);
                }
                return PageEnum.START_PAGE;
            case DELETE_ACCOUNT:
                if( UserService.deleteUser(session.getUser())) {
                    session.deleteUser();
                    return PageEnum.START_PAGE;
                } else {
                    session.updateSessionExpireTime();
                    return PageEnum.HOME;
                }
            case CHANGE_PASSWORD:
                session.updateSessionExpireTime();
                // TODO Change Password
                break;
            case LOGOUT:
                session.deleteUser();
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

    private static int showStartPageMenu() {
        System.out.println("Bitte loggen ein, Wenn Sie einen Konto haben, sonst legen neuen Konto an:");
        return ConsoleUtils.readNumberFromConsole("\t1. Einloggen \n\t2. Registrierung \n\t0.Ausgang \n");
    }

    private static int showHomeMenu() {
        System.out.println("Liebe Kundin / Lieber Kunde, Sie sind in Ihr Profil.");
        return ConsoleUtils.readNumberFromConsole("\t3. Passwort ändern \n\t4. Konto löschen \n\t5. Ausloggen \n");
    }

    public static void showInactiveSessionMessage() {
        System.out.println();
        System.out.println("Session war ungültig, Bitte logge nochmal ein!");
    }

}
