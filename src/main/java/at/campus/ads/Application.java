package at.campus.ads;

import at.campus.ads.domain.User;
import at.campus.ads.logic.Menu;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ActionEnum;
import at.campus.ads.utils.PageEnum;
import at.campus.ads.utils.PasswordUtils;
import javassist.NotFoundException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A very simple class that shows how to load the driver, create a database,
 * create a table, and insert some data.
 */
public class Application {

    private final static Logger LOGGER = Logger.getLogger(Application.class.getName());


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
                logError(e);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                isRun = false;
                logError(e);
            }
        }
    }

    private static void initData() {
        User user;
        try {
            user = new User("Omid", "Nazifi", "onazifi", PasswordUtils.generateSecurePassword("pass"));
            UserDao userDao = new UserDao();
            userDao.save(user);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logError(e);
        }
    }

    private static void logError(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
    }
}