package XML.Dal.db;

import XML.Be.User;
import XML.Dal.DatabaseConnector;
import XML.Dal.IUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUser {
    private DatabaseConnector databaseConnector;

    public UserDAO() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        ArrayList<User> allUsers = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql =
                    """
                    SELECT User.Id, User.Username, User.FirstName, User.LastName, User.Email, User.Password, User.UserType
                    FROM User
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            // Loop through rows from the database result set

            while (rs.next()) {
                int id = rs.getInt("Id");
                String username = rs.getString("Username");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int userType =  rs.getInt("UserType");

                User user = new User(id, username, firstName, lastName, email, password,userType);
                allUsers.add(user);
            }}
        return allUsers;
    }

    public User getUser(String usernameOfUserToFetch) throws Exception {
        User user = null;
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql =
                    """
                    SELECT User.Id, User.Username, User.FirstName, User.LastName, User.Email, User.Password, User.UserType
                    FROM User Where User.Username = usernameOfUserToFetch
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            // Loop through rows from the database result set

            while (rs.next()) {
                int id = rs.getInt("Id");
                String username = rs.getString("Username");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int userType =  rs.getInt("UserType");

                user = new User(id, username, firstName, lastName, email, password,userType);
            }}
        return user;
    }

    @Override
    public String getUserPasswordForAuthentication(String usernameOfUserToFetch) throws Exception {
        String password = "";
        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql =
                    """
                    SELECT User.Password
                    FROM User Where User.Username = usernameOfUserToFetch
                    """;

            ResultSet rs = stmt.executeQuery(sql);
            // Loop through rows from the database result set

            while (rs.next()) {
                String passwordFromDB = rs.getString("Password");
                password = passwordFromDB;
            }}
        return password;
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
