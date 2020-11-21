package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ConsoleUtils;

public class UserService {

    public static boolean deleteUser(User user) {
        boolean inputConfirmed = ConsoleUtils.confirmUserInput("Sind Sie sicher, dass sie den aktuell eingeloggten User löschen möchten?");

        if (inputConfirmed) {
            UserDao userDao = new UserDao();

            System.out.println("");
            userDao.delete(user);
            System.out.println("Der aktuelle User wurde gelöscht. Aktuelle User in DB: " + userDao.findAll().size());

            return true;
        }
        return false;
    }
}
