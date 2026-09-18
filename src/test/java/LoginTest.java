import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void testValidUsername() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkUserName());
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(login.checkUserName());
    }

    @Test
    public void testValidPassword() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testValidCellPhoneNumber() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellPhoneNumber() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testSuccessfulRegistration() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals("Registration successful.", login.registerUser());
    }

    @Test
    public void testSuccessfulLogin() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        login.registerUser();

        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testFailedLogin() {
        Login login = new Login(
                "Musa",
                "Mavuso",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        login.registerUser();

        assertFalse(login.loginUser("wrong", "password"));
    }
}
