package at.campus.ads.service;

import at.campus.ads.persistence.domain.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessionService {
    private static final long MAX_SESSION_PERIOD_PER_SECOND = 60;

    private User user;
    private LocalDateTime lastActivityTime;

    public SessionService(User user) {
        if (user == null) {
            throw new NullPointerException("null user object passed to the session!");
        }

        this.user = user;
        this.lastActivityTime = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void deleteUserFromSession() {
        user = null;
    }

    public boolean isSessionActive() {
        if (user != null) {
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(now, lastActivityTime);
            long diff = Math.abs(duration.toSeconds());
            return diff < MAX_SESSION_PERIOD_PER_SECOND;
        }
        return true;
    }

    public void updateSessionExpireTime() {
        lastActivityTime = LocalDateTime.now();
    }
}
