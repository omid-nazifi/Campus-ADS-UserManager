package at.campus.ads.service;

import at.campus.ads.common.utils.PasswordUtils;
import at.campus.ads.persistence.dao.UserDao;
import at.campus.ads.persistence.domain.User;

public class RegisterService {

    public boolean register(final String username, final String firstname, final String lastname, final String password) throws RuntimeException {
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);
        try {
            user.setPassword(PasswordUtils.generateSecurePassword(password));
        } catch (Exception e) {
            throw new RuntimeException("Eine Exception bei der Registrierung!", e);
        }

        UserDao userDao = new UserDao();
        Integer savedId = userDao.save(user);
        return savedId != null;

    }

    public boolean isUsernameExistingInDatabase(String username) {
        UserDao userDao = new UserDao();
        return userDao.existsUsername(username);
    }

}
