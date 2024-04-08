package XML.Bll;

import XML.Be.User;
import XML.Dal.IUser;
import XML.Dal.db.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserManager {
    private IUser userDAO;

    public UserManager() throws Exception {
        userDAO = new UserDAO();
    }

    public List<User> getAllUsers() throws Exception {
        List<User> curatedUserList = new ArrayList<>();
        for (User user: userDAO.getAllUsers()) {
            if (user.getUserType() != 0){
                curatedUserList.add(user);
            }
        }
        return curatedUserList;
    }

    public User getUser() throws Exception {
        return userDAO.getUser("Admin");
    }

    public boolean authenticateUser(String Username, String Password) throws Exception {
        return Objects.equals(userDAO.getUserPasswordForAuthentication(Username), Password);
    }



}