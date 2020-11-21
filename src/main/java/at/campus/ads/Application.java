package at.campus.ads;

import at.campus.ads.domain.User;
import at.campus.ads.logik.Login;
import at.campus.ads.persistence.UserDao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * A very simple class that shows how to load the driver, create a database,
 * create a table, and insert some data.
 */
public class Application {

    public static void main(String[] args) {

        User user = new User("Omid", "Nazifi", "onazifi", "pass");
        UserDao userDao = new UserDao();
        Integer userId = userDao.save(user);
        System.out.println("LOG [INFO] add new User" + user.toString());

        Optional<User> optionalUser = userDao.get(userId);
        optionalUser.ifPresent(value -> System.out.println("LOG [INFO] get user by id" + value.toString()));

        try {
            Login login = new Login();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.setUsername("NewUserName");
        userDao.update(user);
        System.out.println("LOG [INFO] update current user with new username" + user.getUsername());

        List<User> users = userDao.getAll();
        users.forEach(us -> System.out.println("LOG [INFO]" + us.toString()));

        userDao.delete(user);
        System.out.println("LOG [INFO] delete user from DB");

    }

}