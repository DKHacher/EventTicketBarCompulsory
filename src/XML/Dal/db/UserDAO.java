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
        String sql = "INSERT INTO Users (Username, FirstName, LastName, Email, Password, UserType) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setInt(6, user.getUserType());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user = new User(generatedKeys.getInt(1), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUserType());
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
        return user;
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
