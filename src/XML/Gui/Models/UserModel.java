package XML.Gui.Models;

import XML.Be.User;
import XML.Bll.UserManager;

import java.util.List;

public class UserModel {

    private UserManager userManager;

    public UserModel() throws Exception {
        userManager = new UserManager();
    }

    public boolean authenticateUser(String username, String password) throws Exception {
        return userManager.authenticateUser(username, password);
    }

    public List<User> getAllUsers() throws Exception{
        return userManager.getAllUsers();
    }


}
