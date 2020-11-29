package at.campus.ads.service;

import at.campus.ads.common.utils.PasswordUtils;
import at.campus.ads.persistence.dao.UserDao;
import at.campus.ads.persistence.domain.User;

public class UserService {

    public void deleteUser(User user) throws NullPointerException {
        UserDao userDao = new UserDao();
        userDao.delete(user);
    }

    public boolean changePassword(final User user, final String newPassword, final String confirmedPassword) throws RuntimeException {
        if (newPassword.equals(confirmedPassword)) {
            try {
                user.setPassword(PasswordUtils.generateSecurePassword(newPassword));
            } catch (Exception e) {
                throw new RuntimeException("Eine Exception bei der Passwort-Änderung!", e);
            }
            UserDao userDao = new UserDao();
            userDao.update(user);
            return true;
        }
        return false;
    }
}
