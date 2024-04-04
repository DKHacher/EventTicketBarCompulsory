package XML.Gui.Models;

import XML.Bll.UserManager;

import java.util.Objects;

public class UserModel {

    private UserManager userManager;

    public UserModel() throws Exception {
        userManager = new UserManager();
    }

    public boolean authenticateUser(String username, String password) throws Exception {
        String fetchedPassword = userManager.getUserPasswordForAuthentication(username);
        return Objects.equals(password.trim(), fetchedPassword.trim());
    }
}
