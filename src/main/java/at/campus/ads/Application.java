package at.campus.ads;

import at.campus.ads.domain.User;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ActionEnum;
import at.campus.ads.logic.Menu;
import at.campus.ads.utils.PageEnum;
import at.campus.ads.utils.PasswordUtils;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.InputMismatchException;

/**
 * A very simple class that shows how to load the driver, create a database,
 * create a table, and insert some data.
 */
public class Application {

    public static void main(String[] args) {

        initData();
        Menu.showWelcomeMessage();

        PageEnum currentPage = PageEnum.START_PAGE;
        boolean isRun = true;
        while (isRun) {
            try {
                ActionEnum action = Menu.showMenuForPage(currentPage);
                currentPage = Menu.doAction(action);
            } catch (NotFoundException | InputMismatchException e) {
                Menu.showWrongMessage();
            } catch (IOException e) {
                e.printStackTrace();
                isRun = false;
            }
        }
    }

    private static void initData() {
        User user = new User("Omid", "Nazifi", "onazifi", PasswordUtils.generateSecurePassword("pass"));
        UserDao userDao = new UserDao();
        userDao.save(user);
    }
}