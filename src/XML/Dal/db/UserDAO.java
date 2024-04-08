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
        String sql = "SELECT Id, Username, FirstName, LastName, Email, Password, UserType FROM Users";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String username = rs.getString("Username");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int userType = rs.getInt("UserType");

                User user = new User(id, username, firstName, lastName, email, password, userType);
                allUsers.add(user);
            }
        }
        return allUsers;
    }

    @Override
    public User getUser(String usernameOfUserToFetch) throws Exception {
        User user = null;
        String sql = "SELECT Id, Username, FirstName, LastName, Email, Password, UserType FROM Users WHERE Username = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOfUserToFetch);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                String username = rs.getString("Username");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int userType = rs.getInt("UserType");

                user = new User(id, username, firstName, lastName, email, password, userType);
            }
        }
        return user;
    }

    @Override
    public String getUserPasswordForAuthentication(String usernameOfUserToFetch) throws Exception {
        String password = "";
        String sql = "SELECT Password FROM Users WHERE Username = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usernameOfUserToFetch);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                password = rs.getString("Password");
            }
        }
        return password;
    }

    @Override
    public User createUser(User user) throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {
        //Implement method
    }

    @Override
    public void deleteUser(User user) throws Exception {
        //Implement method
    }
}
