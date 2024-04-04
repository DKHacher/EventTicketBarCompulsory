package XML.Dal.db;

import XML.Be.User;
import XML.Dal.DatabaseConnector;
import XML.Dal.IUser;

import java.util.List;

public class UserDAO implements IUser {
    private DatabaseConnector databaseConnector;

    public UserDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return null;
    }

    @Override
    public User createUser(User user) throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {
        return;
    }

    @Override
    public void deleteUser(User user) throws Exception {
        return;
    }
}
