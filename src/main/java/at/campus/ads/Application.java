package at.campus.ads;

import at.campus.ads.domain.User;
import at.campus.ads.logger.FileLogger;
import at.campus.ads.logic.Menu;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ActionEnum;
import at.campus.ads.utils.PageEnum;
import at.campus.ads.utils.PasswordUtils;
import javassist.NotFoundException;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public static void main(String[] args) {
        initApp();
        Menu.showWelcomeMessage();

        PageEnum currentPage = PageEnum.START_PAGE;
        boolean isRun = true;
        while (isRun) {
            try {
                ActionEnum action = Menu.showMenuForPage(currentPage);
                currentPage = Menu.doAction(action);
            } catch (NotFoundException | InputMismatchException e) {
                Menu.showWrongInputMessage();
                logError(e);
            } catch (RuntimeException e) {
                isRun = false;
                Menu.showSystemErrorMessage();
                logError(e);
            }
        }
    }

    private static void initApp() {
        try {
            FileLogger.setup();
            initData();
        } catch (Exception e) {
            Menu.showSystemErrorMessage();
            logError(e);
            System.exit(0);
        }
    }

    private static void initData() {
        User user;
        try {
            user = new User("Omid", "Nazifi", "onazifi", PasswordUtils.generateSecurePassword("pass"));
            UserDao userDao = new UserDao();
            userDao.save(user);
        } catch (Exception e) {
            logError(e);
        }
    }

    private static void logError(Exception e) {
        String message = e.getMessage();
        if(message != null) {
            LOGGER.log(Level.SEVERE, message, e);
        } else {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}