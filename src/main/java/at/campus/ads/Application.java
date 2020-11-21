package at.campus.ads;

import at.campus.ads.domain.User;
import at.campus.ads.logik.Login;
import at.campus.ads.logik.Register;
import at.campus.ads.persistence.UserDao;
import at.campus.ads.utils.ActionEnum;
import at.campus.ads.utils.Menu;
import at.campus.ads.utils.PageEnum;
import javassist.NotFoundException;

import java.io.IOException;

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
                ActionEnum action = showMenuForPage(currentPage);
                currentPage = doAction(action);
            } catch (Exception e) {
                // TODO wrong number
                isRun = false;
            }
        }
    }

    private static void initData() {
        User user = new User("Omid", "Nazifi", "onazifi", "pass");
        UserDao userDao = new UserDao();
        userDao.save(user);
    }

    private static ActionEnum showMenuForPage(PageEnum page) throws NotFoundException {
        int actionIndex = -1;
        switch (page) {
            case START_PAGE:
                actionIndex = Menu.showStartPageMenu();
                break;
            case HOME:
                actionIndex = Menu.showHomeMenu();
                break;
        }
        return ActionEnum.get(actionIndex);
    }

    private static PageEnum doAction(ActionEnum action) throws IOException {
        switch (action) {
            case EXIT:
                System.exit(0);
            case LOGIN:
                Login login = new Login();
                // TODO: login must return user object to keep as session
                return PageEnum.HOME;
            case REGISTER:
                Register register = new Register();
                boolean registerResult = register.doRegister();
                if(registerResult) {
                    return doAction(ActionEnum.LOGIN);
                }
                return PageEnum.START_PAGE;
            case DELETE_ACCOUNT:
                // TODO go to DELETE
                // return true or false
                // if true then go to Start_Page else ...
                break;
            case CHANGE_PASSWORD:
                // TODO Change Password
                break;
        }
        return PageEnum.START_PAGE;
    }
}