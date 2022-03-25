package service;

import db.DBConnector;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminService {
    public ArrayList<String> getUsers() {
        ArrayList<String> usersL = new ArrayList<>();
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
//            stmt.setString(1, username);
            ResultSet users = stmt.executeQuery(); // запрос к бд
            String login = "", password = "";
            int role = -1, id = -1, online = -1;
            while (users.next()) {
                id = users.getInt("id_user");
                login = users.getString("login");
                password = users.getString("password");
                role = users.getInt("role");
                online = users.getInt("online");
                usersL.add(id + " " + login + " " + password + " " + role + " " + online);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersL;
    }

    public ArrayList<String> getClients() {
        ArrayList<String> clientsL = new ArrayList<>();
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM clients");
//            stmt.setString(1, username);
            ResultSet clients = stmt.executeQuery(); // запрос к бд
            String surname = "", name = "", num_passport = "";
            int pays_for_period = -1, id_cli = -1;
            while (clients.next()) {
                id_cli = clients.getInt("id_cli");
                surname = clients.getString("surname");
                name = clients.getString("name");
                num_passport = clients.getString("num_passport");
                pays_for_period = clients.getInt("pays_for_period");
                clientsL.add(id_cli + " " + surname + " " + name + " " + num_passport + " " + pays_for_period);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientsL;
    }


    public String addUser(String login, String password, boolean isRole, String surname, String name, String numPassport, String dateOfEmp) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(login, password, role, online) VALUES(?, ?, ?, 0)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, isRole ? 1 : 0);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT id_user FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();
            int lastId = 0;
            while (resultSet.next()){
                if (lastId < resultSet.getInt("id_user")) lastId = resultSet.getInt("id_user");
            }
            //resultSet.last();

            preparedStatement = connection.prepareStatement("INSERT INTO employees(id_emp ,surname, name, check_per_period, need_vacation, date_of_emp, num_passport) VALUES (?, ?, ?, 0, 0, ?, ?)");
            preparedStatement.setInt(1, lastId);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, dateOfEmp);
            preparedStatement.setString(5, numPassport);
            preparedStatement.executeUpdate(); // для добавления, executeQuery для селекта

            return "SUCCESS_ADD_USER " + lastId;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_ADD_USER";
    }

    public String deleteUser(int id) {
        try {
            Connection connection = DBConnector.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employees WHERE id_emp=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id_user=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();


            return "SUCCESS_DELETE_USER";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_DELETE_USER";
    }

    public String deleteClient(int id) {
        try {
            Connection connection = DBConnector.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE id_cli=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
//
//            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id_user=?");
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();


            return "SUCCESS_DELETE_CLIENT";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_DELETE_CLIENT";
    }

    public String editUser(int id, String column, String newVariable) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET " + column + "=? WHERE id_user=?");

            if ("role".equals(column) || "online".equals(column))
                preparedStatement.setInt(1, Integer.parseInt(newVariable));
            else
                preparedStatement.setString(1, newVariable);

            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return "SUCCESS_EDIT_USER";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_EDIT_USER";
    }

    public String editClient(int id_cli, String column, String newVariable) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clients SET " + column + "=? WHERE id_cli=?");
            preparedStatement.setString(1, newVariable);

            preparedStatement.setInt(2, id_cli);
            preparedStatement.executeUpdate();
            return "SUCCESS_EDIT_CLIENT";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_EDIT_CLIENT";
    }

    public String editBV(int value) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cond_v SET conditional_v=?");
            preparedStatement.setInt(1, value);
            preparedStatement.executeUpdate();

            return "SUCCESS_CHANGE_BV";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_CHANGE_BV";
    }

    public String editRC(String type, int value) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE type_item SET perc_atop=? WHERE type=?");
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, type);

            preparedStatement.executeUpdate();

            return "SUCCESS_CHANGE_RC";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_CHANGE_RC";
    }

    public ArrayList<String> showChecksEdit() {
        ArrayList<String> checksEL = new ArrayList<>();
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM check_order");
//            stmt.setString(1, username);
            ResultSet checks = preparedStatement.executeQuery(); // запрос к бд
            String date = "", time = "";
            int id_order = -1, id_client = -1, min_pay = -1, status_check = -1, status_pay = -1, full_pay = -1,
                    id_user = -1;
            while (checks.next()) {
                id_order = checks.getInt("id_order");
                id_client = checks.getInt("id_client");
                date = checks.getString("date");
                time = checks.getString("time");
                min_pay = checks.getInt("min_pay");
                status_check = checks.getInt("status_check");
                status_pay = checks.getInt("status_pay");
                full_pay = checks.getInt("full_pay");
                id_user = checks.getInt("id_user");

                checksEL.add(id_order + " " + id_client + " " + date + " " + time + " " +
                        min_pay + " " + status_check + " " + status_pay + " " + full_pay + " " + id_user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checksEL;
    }

    public String deleteCheck(int id_order){
        try {
            Connection connection = DBConnector.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE id_order=?");
            preparedStatement.setInt(1, id_order);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM check_order WHERE id_order=?");
            preparedStatement1.setInt(1, id_order);
            preparedStatement1.executeUpdate();

            return "SUCCESS_DELETE_CHECK";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_DELETE_CHECK";
    }

    public String ban(int id){
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET ban=? WHERE id_user = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return "SUCCESS";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "ERROR_BAN";
    }
}
