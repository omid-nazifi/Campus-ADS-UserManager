package at.campus.ads.logic;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;
import at.campus.ads.utils.PasswordUtils;

public class UserService {

    public static boolean deleteUser(User user) {
        boolean inputConfirmed = ConsoleUtils.confirmUserInput("Sind Sie sicher, dass sie den aktuell eingeloggten User löschen möchten?");

        if (inputConfirmed) {
            UserDao userDao = new UserDao();

            System.out.println();
            userDao.delete(user);
            System.out.println("Der aktuelle User wurde gelöscht. Sie können nicht mehr mit dem Benutzer \"" + user.getUsername() + "\" einloggen.");

            return true;
        }
        return false;
    }

    public static void changePassword(User user) {
        String newPassword = ConsoleUtils.readLineFromConsole("Bitte geben Sie das neue Kennwort: ");
        String confirmedPassword = ConsoleUtils.readLineFromConsole("Bitte bestätigen Sie das neue Kennwort: ");

        if (newPassword.equals(confirmedPassword)) {
            user.setPassword(PasswordUtils.generateSecurePassword(newPassword));
            UserDao userDao = new UserDao();
            userDao.update(user);
            System.out.println("Das Kennwort wurde erfolgreich geändert");
        } else {
            System.out.println("Kennwörter nicht gleich ausgeben");
        }


    }
}
