package at.campus.ads.view;

import at.campus.ads.common.utils.ConsoleUtils;
import at.campus.ads.persistence.domain.User;
import at.campus.ads.service.UserService;

public class UserProfilePage {

    private final UserService userService;

    public UserProfilePage(UserService userService) {
        this.userService = userService;
    }

    public boolean deleteUser(User user) throws NullPointerException {
        if (user == null) {
            throw new NullPointerException("null user object for the delete action!");
        }
        boolean inputConfirmed = ConsoleUtils.confirmUserInput("Sind Sie sicher, dass sie den aktuell eingeloggten User löschen möchten?");
        if (inputConfirmed) {
            userService.deleteUser(user);
            System.out.println("Der aktuelle User wurde gelöscht. Sie können nicht mehr mit dem Benutzer \"" + user.getUsername() + "\" einloggen.");
            return true;
        }
        return false;
    }

    public void changePassword(User user) throws RuntimeException {
        if (user == null)
            throw new NullPointerException("null user object for the change password action!");

        String newPassword = ConsoleUtils.readLineFromConsole("Bitte geben Sie das neue Kennwort: ");
        String confirmedPassword = ConsoleUtils.readLineFromConsole("Bitte bestätigen Sie das neue Kennwort: ");
        if (!newPassword.equals(confirmedPassword)) {
            System.out.println("Kennwörter nicht gleich ausgeben.");
        } else {
            boolean result = userService.changePassword(user, newPassword, confirmedPassword);
            if (result) {
                System.out.println("Das Kennwort wurde erfolgreich geändert.");
            } else {
                System.out.println("leider wurde das Kennwort nicht erfolgreich geändert.");
            }
        }
    }
}
