package at.campus.ads.logic;

import at.campus.ads.domain.User;
import at.campus.ads.utils.ActionEnum;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.PageEnum;
import javassist.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

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

    public static PageEnum doAction(ActionEnum action) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (session != null && !session.isSessionActive()) {
            showInactiveSessionMessage();
            session.deleteUserFromSession();
            return PageEnum.START_PAGE;
        }
        switch (action) {
            case EXIT:
                System.exit(0);
            case LOGIN:
                Optional<User> loggedInUser = LoginService.login();
                if (loggedInUser.isPresent()) {
                    session = new Session(loggedInUser.get());
                    return PageEnum.HOME;
                }
                return PageEnum.START_PAGE;
            case REGISTER:
                boolean registerResult = RegisterService.register();
                if (registerResult) {
                    return doAction(ActionEnum.LOGIN);
                }
                return PageEnum.START_PAGE;
            case DELETE_ACCOUNT:
                if (UserService.deleteUser(session.getUser())) {
                    session.deleteUserFromSession();
                    return PageEnum.START_PAGE;
                } else {
                    session.updateSessionExpireTime();
                    return PageEnum.HOME;
                }
            case CHANGE_PASSWORD:
                session.updateSessionExpireTime();
                UserService.changePassword(session.getUser());
                return PageEnum.HOME;
            case LOGOUT:
                session.deleteUserFromSession();
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
        System.out.println("Liebe Benutzerin / Lieber Benutzer, Sie sind momentan in Ihr Profil.");
        return ConsoleUtils.readNumberFromConsole("\t3. Passwort ändern \n\t4. Konto löschen \n\t5. Ausloggen \n");
    }

    public static void showInactiveSessionMessage() {
        System.out.println();
        System.out.println("Session war ungültig, Bitte logge nochmal ein!");
    }

}
