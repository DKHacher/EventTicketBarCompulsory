package XML.Bll;

import XML.BCrypt;
import XML.Be.User;
import XML.Dal.IUser;
import XML.Dal.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

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
        return BCrypt.checkpw(Password, userDAO.getUserPasswordForAuthentication(Username));
    }

    public User createUser(User newUser) throws Exception {
        if (getUserByUsername(newUser.getUsername()) != null) {
            throw new Exception("Username already exists.");
        }
        String EncryptedPassword = BCryptPassword(newUser.getPassword());
        newUser.setPassword(EncryptedPassword);

        return userDAO.createUser(newUser);
    }

    private User getUserByUsername(String username) throws Exception {
        //Implement a method to check if a user exists by username?
        return userDAO.getUser(username);
    }

    public void assignCoordinator(int userId) throws Exception {
        userDAO.updateUserType(userId, 1); //Coordinator
    }

    public void unassignCoordinator(int userId) throws Exception {
        userDAO.updateUserType(userId, 2); //User
    }

    public void deleteUser(User user) throws Exception {
        userDAO.deleteUser(user);
    }


    private static String BCryptPassword(String input){
        String salt = BCrypt.gensalt(10);
        String output = BCrypt.hashpw(input, salt);

        return output;
    }


}
