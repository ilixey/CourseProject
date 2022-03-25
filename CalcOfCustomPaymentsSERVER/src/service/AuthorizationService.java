package service;

import db.DBConnector;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationService {

    private int roleUser;
    public String authorization(String username, String password) {
        User user = getUserByUsername(username);
        String response = "";
        if (user != null) {
            if (password.equals(user.getPassword())) {
                updateUserOnline(user.getId(), 1);
                response = "OK " + user.getId() + " " + user.getRole() + " " + user.getOnline() + " " + user.getBan();
            } else {
                response = "ERROR WRONG_CREDENTIALS";
            }
        } else {
            response = "ERROR WRONG_CREDENTIALS";
        }
        return response;
    }

    public void disconnect(int id) {
        updateUserOnline(id, 0);
    }


    public User getUserByUsername(String username) {
        User user = null;
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
            stmt.setString(1, username);
            ResultSet users = stmt.executeQuery();
            String login = "", password = "";
            int role = -1, id = -1, online = -1, ban = 0;
            while (users.next()) {
                id = users.getInt("id_user");
                login = users.getString("login");
                password = users.getString("password");
                role = users.getInt("role");
                online = users.getInt("online");
                ban = users.getInt("ban");
            }
            if (id != -1) {
                user = new User(id, login, password, role, online, ban);
                roleUser = role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUserOnline(int id, int online) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET online=? WHERE id_user=?");
            preparedStatement.setInt(1, online);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}