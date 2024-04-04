package XML.Bll;

import XML.Be.User;
import XML.Dal.IUser;
import XML.Dal.db.UserDAO;

import java.util.List;

public class UserManager {
    private IUser userDAO;

    public UserManager() throws Exception {
        userDAO = new UserDAO();
    }

    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }

    public User getUser() throws Exception {
        return userDAO.getUser("Admin");
    }

    public String getUserPasswordForAuthentication(String Username) throws Exception {
        return userDAO.getUserPasswordForAuthentication(Username);
    }




}
