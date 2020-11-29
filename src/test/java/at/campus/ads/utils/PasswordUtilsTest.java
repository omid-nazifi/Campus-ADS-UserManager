package at.campus.ads.utils;


import at.campus.ads.common.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateSecurePassword() {
        try {
            String password  = "123";
            String generatedPassword  = PasswordUtils.generateSecurePassword(password);
            assertEquals("Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=", generatedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void verifyUserPassword() {
        try {
            String password  = "123";
            String securePassword = "Gcf70h4aWQ1T9NMxE03XM3nq3nCmFGihnO4xMzHMgP0=";
            boolean verified  = PasswordUtils.verifyUserPassword(password, securePassword);
            assertTrue(verified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}