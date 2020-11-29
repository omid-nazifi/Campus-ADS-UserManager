package at.campus.ads.view;

import at.campus.ads.common.utils.ConsoleUtils;
import at.campus.ads.service.RegisterService;

public class RegisterPage {

    private final RegisterService registerService;

    public RegisterPage(RegisterService registerService) {
        this.registerService = registerService;
    }

    public boolean doRegister() {
        String username = ConsoleUtils.readLineFromConsole("Benutzername:");
        String firstname;
        String lastname;
        String password;
        if (!registerService.isUsernameExistingInDatabase(username)) {
            firstname = ConsoleUtils.readLineFromConsole("Vorname:");
            lastname = ConsoleUtils.readLineFromConsole("Nachname:");
            password = ConsoleUtils.readLineFromConsole("Passwort:");
            if (registerService.register(username, firstname, lastname, password)) {
                System.out.println("Herzlichen Glückwunsch, Ihre Registrierung war erfolgreich.");
            } else {
                System.out.println("Leider war Ihre Registrierung nicht erfolgreich.");
            }
            return true;
        } else {
            System.out.println("Username existiert bereits!");
            int entryCode = Menu.showRegisterMenu();
            if (entryCode == 1) {
                return doRegister();
            }
            return false;
        }
    }
}
