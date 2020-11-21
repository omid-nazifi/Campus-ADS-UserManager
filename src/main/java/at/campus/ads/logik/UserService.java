package at.campus.ads.logik;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;

import java.io.BufferedReader;
import java.io.IOException;

public class UserService {

    public static void deleteUser (User user, UserDao userDao, BufferedReader reader) throws IOException {
        boolean inputConfirmed = MenuService.confirmUserInput("Sind Sie sicher, dass sie den aktuell eingeloggten User löschen möchten?", reader);

        if (inputConfirmed) {
            System.out.println("");
            userDao.delete(user);
            System.out.println("Der aktuelle User wurde gelöscht. Aktuelle User in DB: " + userDao.findAll().size());

        }
    }
}
