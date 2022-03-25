package service;

import db.DBConnector;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserService {


    public String checkClient(String numPassport) {
        ResultSet resultSet;
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients WHERE num_passport=?");
            preparedStatement.setString(1, numPassport);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String id_cli = resultSet.getString("id_cli");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String num_passport = resultSet.getString("num_passport");
            int pays_for_period = resultSet.getInt("pays_for_period");
            return "ID_клиента: " + id_cli + ", фамилия: " + surname + ", имя: " + name + ", номер паспорта: " + num_passport + ", оплачено: " + pays_for_period + " копеек";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "ERROR_HAVENT_CLIENT";
    }

    public String addClient(String surname, String name, String numPassport) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO clients(surname, name, num_passport, pays_for_period) VALUES(?, ?, ?, 0)");
            preparedStatement.setString(1, surname);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, numPassport);
            preparedStatement.executeUpdate();

            return "SUCCESS_ADD_CLIENT";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "ERROR_ADD_CLIENT";
    }

    public String profileInfo(String login) {
        ResultSet resultSet;
        try {

            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE(SELECT id_user FROM users WHERE login=?)=id_emp");
            preparedStatement.setString(1, login);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            int checkPer = resultSet.getInt("check_per_period");
            boolean needVacation = resultSet.getBoolean("need_vacation");
            String dateOfEmp = resultSet.getString("date_of_emp");


            return surname + " " + name + " " + checkPer + " " + needVacation + " " + dateOfEmp;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "ERROR_SHOW_PROFILE";
    }

    public String updateVacation(String login, int Vacation) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET need_vacation=? WHERE(id_emp=(SELECT id_user FROM users WHERE login=?))");
            preparedStatement.setInt(1, Vacation);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            if (Vacation == 1) {
                return "SUCCESS_UPD_VACATION_1";
            } else if (Vacation == 0) {
                return "SUCCESS_UPD_VACATION_0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "ERROR_CHANGE_PSYCHO";
    }

    public String showBV() {
        ResultSet resultSet;
        try {
            Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM cond_v");
            resultSet.next();
            String BV = resultSet.getString("conditional_v");
//            resultSet = preparedStatement.executeQuery();
//            resultSet.next();
            return BV;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_READ_BV";
    }

    public String showRates() {
        ResultSet resultSet;
        try {
            Connection connection = DBConnector.getConnection();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM type_item");
            String response = "";
            while (resultSet.next()) {
                String value = resultSet.getString("perc_atop");
                response += value + " ";
            }
//            resultSet = preparedStatement.executeQuery();
//            resultSet.next();
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_READ_BV";
    }

    public ArrayList<String> showChecksUnpaidUnconf() {
        ArrayList<String> checksL = new ArrayList<>();
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

                checksL.add(id_order + " " + id_client + " " + date + " " + time + " " +
                        min_pay + " " + status_check + " " + status_pay + " " + full_pay + " " + id_user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checksL;
    }

    public String editCheckUser(int id_order, String column, String newVariable) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE check_order SET " + column + "=? WHERE id_order=?");
            preparedStatement.setString(1, newVariable);

            preparedStatement.setInt(2, id_order);
            preparedStatement.executeUpdate();
            return "SUCCESS_EDIT_CHECK_STATUSES";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_EDIT_CHECK_STATUSES";
    }

    public String addCheck(String numPassport, String login) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT id_cli FROM clients " +
                    "WHERE num_passport=?");
            preparedStatement1.setString(1, numPassport);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int id_cli = resultSet1.getInt("id_cli");

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM cond_v");
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            resultSet2.next();
            int condV = resultSet2.getInt("conditional_v");

            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT id_user FROM users WHERE login=?");
            preparedStatement3.setString(1, login);
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            resultSet3.next();
            int id_user = resultSet3.getInt("id_user");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date1 = new Date();
            String dateCurr = simpleDateFormat.format(date1);
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
            java.util.Date date2 = new Date();
            String timeCurr = simpleDateFormat1.format(date2);

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO check_order(" +
                    "id_client, date, time, min_pay, status_check, status_pay, full_pay, id_user) " +
                    "VALUES (?, ?, ?, ?, 0, 0, ?, ?)");
            preparedStatement.setInt(1, id_cli);
            preparedStatement.setString(2, dateCurr);
            preparedStatement.setString(3, timeCurr);
            preparedStatement.setInt(4, condV);
            preparedStatement.setInt(5, condV);
            preparedStatement.setInt(6, id_user);
            preparedStatement.executeUpdate();
            return "SUCCESS_ADD_CHECK";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ERROR_ADD_CHECK";
    }

    public String addOrder(String typeCurr, int countI, int speed) {
        try {
            Connection connection = DBConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_order FROM check_order ");
//            preparedStatement.setString(1, numPassport);
            ResultSet resultSet = preparedStatement.executeQuery();
//            while ()
            int id_order = 1;
            while (resultSet.next()){
                if (id_order < resultSet.getInt("id_order")) id_order = resultSet.getInt("id_order");
            }


            preparedStatement = connection.prepareStatement("SELECT id_item FROM orders WHERE id_order=?");
            preparedStatement.setInt(1, id_order);
            resultSet = preparedStatement.executeQuery();
            int id_item = 1;
            while (resultSet.next()){
                if (id_item < resultSet.getInt("id_order")) id_order = resultSet.getInt("id_order");
            }
            id_item++;

            Statement statement;
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM cond_v");
            resultSet.next();
            int conditional_v = resultSet.getInt("conditional_v");

            preparedStatement = connection.prepareStatement("INSERT INTO orders(id_order, id_item, type, count, quick_registration)" +
                    "VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, id_order);
            preparedStatement.setInt(2, id_item);
            preparedStatement.setString(3, typeCurr);
            preparedStatement.setInt(4, countI);
            preparedStatement.setInt(5, speed);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT * FROM type_item WHERE type=?");
            preparedStatement.setString(1, typeCurr);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int typeValue = resultSet.getInt("perc_atop");

            preparedStatement = connection.prepareStatement("SELECT full_pay FROM check_order WHERE id_order=?");
            preparedStatement.setInt(1, id_order);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int fullPay = resultSet.getInt("full_pay");
            fullPay += countI * conditional_v * typeValue / 100;
            if (speed == 1) fullPay += 500;

            preparedStatement = connection.prepareStatement("UPDATE check_order SET full_pay=? WHERE id_order=?");
            preparedStatement.setInt(1, fullPay);
            preparedStatement.setInt(2, id_order);
            preparedStatement.executeUpdate();

            return "SUCCESS_ADD_ORDER";
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "ERROR_ADD_ORDER";
    }

//    public ArrayList<String> showChecksEdit() {
//        ArrayList<String> checksEL = new ArrayList<>();
//        try {
//            Connection connection = DBConnector.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM check_order");
//            stmt.setString(1, username);
//            ResultSet checks = preparedStatement.executeQuery(); // запрос к бд
//            String date = "", time = "";
//            int id_order = -1, id_client = -1, min_pay = -1, status_check = -1, status_pay = -1, full_pay = -1,
//                    id_user = -1;
//            while (checks.next()) {
//                id_order = checks.getInt("id_order");
//                id_client = checks.getInt("id_client");
//                date = checks.getString("date");
//                time = checks.getString("time");
//                min_pay = checks.getInt("min_pay");
//                status_check = checks.getInt("status_check");
//                status_pay = checks.getInt("status_pay");
//                full_pay = checks.getInt("full_pay");
//                id_user = checks.getInt("id_user");
//
//                checksEL.add(id_order + " " + id_client + " " + date + " " + time + " " +
//                        min_pay + " " + status_check + " " + status_pay + " " + full_pay + " " + id_user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return checksEL;
//    }

}
