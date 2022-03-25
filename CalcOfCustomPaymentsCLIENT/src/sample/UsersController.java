package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.converter.IntegerStringConverter;
import sample.models.Users;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class UsersController {
    private Main main;
    private ClientSocket clientSocket;

    public TableView<Users> tableUsers;
    public TableColumn<Users, Integer> columnID;
    public TableColumn<Users, String> columnLogin;
    public TableColumn<Users, String> columnPassword;
    public TableColumn<Users, Integer> columnRole;
    public TableColumn<Users, Integer> columnOnline;

    public TextField tfLogin, tfPassword;
    public CheckBox cbIsAdmin;
    public Button btnDelete;
    public Button banButton;
    private Users selectedUser;

    public TextField tfSurname, tfName, tfNumPassport;
    public DatePicker dpDateOfEmp;

    public void initialize() {
        tableUsers.setEditable(true);

        columnID.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id_user"));

        columnLogin.setCellValueFactory(new PropertyValueFactory<Users, String>("login"));
        columnLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        columnLogin.setOnEditCommit(event -> {
            String newLogin = event.getNewValue();
            selectedUser.setLogin(newLogin);
            updateUser("login", newLogin);
        });

        columnPassword.setCellValueFactory(new PropertyValueFactory<Users, String>("Password"));
        columnPassword.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPassword.setOnEditCommit(event -> {
            String newPassword = newPassword = event.getNewValue();
            selectedUser.setPassword(newPassword);
            updateUser("password", newPassword);

        });

        columnRole.setCellValueFactory(new PropertyValueFactory<Users, Integer>("Role"));
        columnRole.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnRole.setOnEditCommit(event -> {
            String newRole = String.valueOf(event.getNewValue());
            selectedUser.setRole(event.getNewValue());
            updateUser("actRole", newRole);
//            int newRole;
//            newRole = event.getNewValue();
//            selectedUser.setPassword(newRole);
//            updateUser("Role", newRole);
        });

        columnOnline.setCellValueFactory(new PropertyValueFactory<Users, Integer>("Online"));
        columnOnline.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnOnline.setOnEditCommit(event -> {
            String newOnline = String.valueOf(event.getNewValue());
            selectedUser.setOnline(event.getNewValue());
            updateUser("online", newOnline);
//            String.valueOf(event.getNewValue());
        });
        //
        tableUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Users>() {
            @Override
            public void changed(ObservableValue<? extends Users> observable, Users oldValue, Users newValue) {
                if (newValue != null) {
                    selectedUser = newValue;
                    btnDelete.setDisable(false);
                    banButton.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                    banButton.setDisable(true);
                }
            }
        });
    }

    public void showUsers() {
        ObservableList<Users> users = FXCollections.observableArrayList(getUsers());

        tableUsers.setItems(users);
    }

    private ArrayList<Users> getUsers() {
        ArrayList<Users> lUsers = new ArrayList<>();
        clientSocket.setRequest("GET_USERS");
        ArrayList<String> sUsers = clientSocket.getRequestList();
        for (String s :
                sUsers) {
            String[] arrayS = s.split(" ");
            int id = Integer.parseInt(arrayS[0]);
            String login = arrayS[1];
            String password = arrayS[2];
            int role = Integer.parseInt(arrayS[3]);
            int online = Integer.parseInt(arrayS[4]);
            lUsers.add(new Users(id, role, login, password, online));
        }
        return lUsers;
    }


    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void back(ActionEvent event) {
        main.afterLoginAdmin();
    }

    public void add(ActionEvent event) {
        String login = tfLogin.getText();
        String password = tfPassword.getText();
        boolean isRole = cbIsAdmin.isSelected();

        String surname = tfSurname.getText();
        String name = tfName.getText();
        String numPassport = tfNumPassport.getText();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
//        dpDateOfEmp.setValue(simpleDateFormat.format(now));
        dpDateOfEmp.setValue(LocalDate.now());
        String dateOfEmp = simpleDateFormat.format(now);
        if (!dpDateOfEmp.getValue().equals("")) {
            dateOfEmp = dpDateOfEmp.getValue().toString();
        }

        if (!login.equals("") && !password.equals("") && !surname.equals("") && !name.equals("") && !numPassport.equals("") && !dateOfEmp.equals("")) {
            clientSocket.setRequest("ADD_USER " + login + " " + password + " " + isRole + " " + surname + " " + name + " " + numPassport + " " + dateOfEmp);
            String response = clientSocket.getRequest();
            if (response.equals("ERROR_ADD_USER")) {
                System.out.println("Ошибка при добавлении пользователя");
            } else {
                int id_user = Integer.parseInt(response.split(" ")[1]);
                Users users = new Users(id_user, isRole ? 1 : 0, login, password, 0);
                tableUsers.getItems().add(users);
                tableUsers.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Внимание");
            alert.setContentText("Незаполненно одно из полей");
            alert.show();
        }
    }

    public void updateUser(String column, String newVariable) {
        clientSocket.setRequest("EDIT_USER " + selectedUser.getId_user() + " " + column + " " + newVariable);
        String response = clientSocket.getRequest();
        if (response.equals("ERROR_EDIT_USER")) {
            System.out.println("Ошибка при редактировании пользователя");
        }
    }

    public void delete(ActionEvent event) {
        clientSocket.setRequest("DELETE_USER " + selectedUser.getId_user());

        String response = clientSocket.getRequest();
        if (response.equals("ERROR_DELETE_USER")) {
            System.out.println("Ошибка при редактировании пользователя");
        } else {
            tableUsers.getItems().remove(selectedUser);
            tableUsers.getSelectionModel().clearSelection();
            tableUsers.refresh();
        }
    }

    public void ban(ActionEvent event){
        clientSocket.setRequest("BAN_USER " + selectedUser.getId_user());

        String response = clientSocket.getRequest();
        if (response.equals("ERROR_BAN_USER")) System.out.println("Ошибка!");
        else System.out.println("Пользователь успешко заблокирован");
    }

    public void pieChart(ActionEvent event) {
        main.pieChartShow(getUsers());
    }

    public void refresh(ActionEvent event) {
        showUsers();
    }

}
