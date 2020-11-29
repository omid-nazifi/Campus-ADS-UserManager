package at.campus.ads.view;

import at.campus.ads.common.exception.UserLoginException;
import at.campus.ads.common.utils.ConsoleUtils;
import at.campus.ads.persistence.domain.User;
import at.campus.ads.service.LoginService;
import at.campus.ads.service.RegisterService;
import at.campus.ads.service.SessionService;
import at.campus.ads.service.UserService;
import javassist.NotFoundException;

import java.util.Optional;

public class Menu {

    private static SessionService sessionService;

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
        //TODO validate action before return it
        return ActionEnum.get(actionIndex);
    }

    public static PageEnum doAction(ActionEnum action) throws RuntimeException {
        LoginPage loginPage = new LoginPage(new LoginService());
        RegisterPage registerPage = new RegisterPage(new RegisterService());
        UserProfilePage userProfilePage = new UserProfilePage(new UserService());
        if (sessionService != null && !sessionService.isSessionActive()) {
            showInactiveSessionMessage();
            sessionService.deleteUserFromSession();
            return PageEnum.START_PAGE;
        }

        switch (action) {
            case EXIT:
                System.exit(0);
            case LOGIN:
                try {
                    Optional<User> loggedInUser = loginPage.showLogin();
                    if (loggedInUser.isPresent()) {
                        sessionService = new SessionService(loggedInUser.get());
                        return PageEnum.HOME;
                    }
                } catch (UserLoginException e) {
                    showTooManyFailedAttemptsMessage(e.getMessage());
                }
                return PageEnum.START_PAGE;
            case REGISTER:
                boolean registerResult = registerPage.doRegister();
                if (registerResult) {
                    return doAction(ActionEnum.LOGIN);
                }
                return PageEnum.START_PAGE;
            case DELETE_ACCOUNT:
                if (userProfilePage.deleteUser(sessionService.getUser())) {
                    sessionService.deleteUserFromSession();
                    return PageEnum.START_PAGE;
                } else {
                    sessionService.updateSessionExpireTime();
                    return PageEnum.HOME;
                }
            case CHANGE_PASSWORD:
                sessionService.updateSessionExpireTime();
                userProfilePage.changePassword(sessionService.getUser());
                return PageEnum.HOME;
            case LOGOUT:
                sessionService.deleteUserFromSession();
        }
        return PageEnum.START_PAGE;
    }

    public static void showWelcomeMessage() {
        System.out.println();
        System.out.println("Herzlich willkommen.");
    }

    public static void showWrongInputMessage() {
        System.out.println();
        System.out.println("Ungültiger Eingabewert. Bitte versuchen Sie es nochmal:");
    }

    public static void showSystemErrorMessage() {
        System.out.println();
        System.out.println("Ein Serverfehler ist aufgetreten. Bitte kontaktieren Sie den Administrator!");
    }

    public static void showTooManyFailedAttemptsMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    public static int showRegisterMenu() {
        return ConsoleUtils.readNumberFromConsole("\t0. Abbrechen \n\t1. Nochmals versuchen \n");
    }

    private static int showStartPageMenu() {
        System.out.println("Bitte loggen ein, Wenn Sie einen Konto haben, sonst legen neuen Konto an:");
        return ConsoleUtils.readNumberFromConsole("\t1. Einloggen \n\t2. Registrierung \n\t0. Ausgang \n");
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
