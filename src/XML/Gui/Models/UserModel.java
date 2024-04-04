package XML.Gui.Models;

import XML.Bll.UserManager;

public class UserModel {

    private UserManager userManager;

    public UserModel() throws Exception {
        userManager = new UserManager();
    }

    public boolean authenticateUser(String username, String password) throws Exception {
        String fetchedPassword = userManager.getUserPasswordForAuthentication(username);
        if (password == fetchedPassword){
            return true;
        }
        else {
            return false;
        }
    }
}
