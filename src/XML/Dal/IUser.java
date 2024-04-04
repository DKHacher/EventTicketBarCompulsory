package XML.Dal;

import XML.Be.User;

import java.util.List;

public interface IUser {
    public List<User> getAllUsers() throws Exception;

    // Method for getting the user type?

    public User createUser(User user) throws Exception;

    public void updateUser(User user) throws Exception;

    public void deleteUser(User user) throws Exception;

}
