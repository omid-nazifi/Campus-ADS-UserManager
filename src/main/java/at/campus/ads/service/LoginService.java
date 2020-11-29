package at.campus.ads.service;

import at.campus.ads.common.utils.PasswordUtils;
import at.campus.ads.persistence.dao.UserDao;
import at.campus.ads.persistence.domain.User;

import java.util.Optional;

public class LoginService {

    public Optional<User> checkEntriesAndLogin(String username, String password) {
        try {
            UserDao userDao = new UserDao();
            Optional<User> userOptional = userDao.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (PasswordUtils.verifyUserPassword(password, user.getPassword())) {
                    return userOptional;
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Eine Exception beim Login!", e);
        }
    }

}
