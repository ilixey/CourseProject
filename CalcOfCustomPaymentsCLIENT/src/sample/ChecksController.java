package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import sample.models.Checks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChecksController {
    protected Main main;
    protected ClientSocket clientSocket;


    public TableView<Checks> tableChecks;
    public TableColumn<Checks, Integer> columnID_order;
    public TableColumn<Checks, Integer> columnID_client;
    public TableColumn<Checks, String> columnDate;
    public TableColumn<Checks, Integer> columnMin_pay;
    public TableColumn<Checks, Integer> columnStatus_check;
    public TableColumn<Checks, Integer> columnStatus_pay;
    public TableColumn<Checks, Integer> columnFull_pay;
    public TableColumn<Checks, Integer> columnID_user;

    public Checks selectedCheck;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void initialize(){
        tableChecks.setEditable(true);

        columnID_order.setCellValueFactory(new PropertyValueFactory<>("ID_order")); // getter s "value"
        columnID_client.setCellValueFactory(new PropertyValueFactory<>("ID_client"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        columnMin_pay.setCellValueFactory(new PropertyValueFactory<>("Min_pay"));

        columnStatus_check.setCellValueFactory(new PropertyValueFactory<Checks, Integer>("Status_check"));
        columnStatus_check.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnStatus_check.setOnEditCommit(event -> {
            String newStatusCheck = String.valueOf(event.getNewValue());
            selectedCheck.setStatus_check(event.getNewValue());
            updateCheck("status_check", newStatusCheck);
//            int newRole;
//            newRole = event.getNewValue();
//            selectedUser.setPassword(newRole);
//            updateUser("Role", newRole);
        });

        columnStatus_pay.setCellValueFactory(new PropertyValueFactory<Checks, Integer>("Status_pay"));
        columnStatus_pay.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnStatus_pay.setOnEditCommit(event -> {
            String newStatusPay = String.valueOf(event.getNewValue());
            selectedCheck.setStatus_pay(event.getNewValue());
            updateCheck("status_pay", newStatusPay);
        });
        columnFull_pay.setCellValueFactory(new PropertyValueFactory<>("Full_pay"));
        columnID_user.setCellValueFactory(new PropertyValueFactory<>("ID_user"));

        tableChecks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Checks>() {
            @Override
            public void changed(ObservableValue<? extends Checks> observable, Checks oldValue, Checks newValue) {
                if (newValue != null) {
                    selectedCheck = newValue;
 //                 btnDelete.setDisable(false);
                }
//                  btnDelete.setDisable(true);
            }
        });

    }

    public void back(ActionEvent event) {
        if (main.actRole == 1) {
            main.afterLoginAdmin();
        } else {
            main.afterLogin();
        }
    }

    public void updateCheck(String column, String newVariable) {
        clientSocket.setRequest("EDIT_CHECK_STATUSES " + selectedCheck.getID_order() + " " + column + " " + newVariable);
        String response = clientSocket.getRequest();
        if (response.equals("ERROR_EDIT_CHECK_STATUSES")) {
            System.out.println("Ошибка при редактировании чека");
        } else System.out.println("Успех");
    }
    public void refresh(ActionEvent event) {
        showChecks();
    }

    public void otchet(ActionEvent event) throws IOException {
        File file = new File("c://CourseProject//Otchet.txt");
        ArrayList<Checks> lChecks = getChecks();
        int fullPay = 0;
        int paid = 0;
        for (Checks checks : lChecks){
            fullPay += checks.getFull_pay();
            paid += checks.getStatus_pay();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateCur = simpleDateFormat.format(calendar.getTime());
        bufferedWriter.newLine();
        bufferedWriter.write("Отчет на " + dateCur);
        bufferedWriter.newLine();
        bufferedWriter.write("Общая сумма сборов: " + fullPay);
        bufferedWriter.newLine();
        bufferedWriter.write("Оплачено: " + paid);
        bufferedWriter.newLine();
        bufferedWriter.write("  Дата  " + "  Время  " + " Cумма " + "Оплачено" );
        bufferedWriter.newLine();
        for (Checks checks: lChecks){
            bufferedWriter.write(checks.getDate() + " " + checks.getFull_pay() + " " + checks.getStatus_pay());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public void showChecks() {
        ObservableList<Checks> checks = FXCollections.observableArrayList(getChecks());

        tableChecks.setItems(checks);
    }

    private ArrayList<Checks> getChecks() {
        ArrayList<Checks> lChecks = new ArrayList<>();
        clientSocket.setRequest("GET_CHECKS_UNPAID_UNCONF");
        ArrayList<String> sChecks = clientSocket.getRequestList();
        for (String s :
                sChecks) {
            String[] arrayS = s.split(" ");
            int id_order = Integer.parseInt(arrayS[0]);
            int id_client = Integer.parseInt(arrayS[1]);
            String date = arrayS[2];
            String time = arrayS[3];
            int min_pay = Integer.parseInt(arrayS[4]);
            int status_check = Integer.parseInt(arrayS[5]);
            int status_pay = Integer.parseInt(arrayS[6]);
            int full_pay = Integer.parseInt(arrayS[7]);
            int id_user = Integer.parseInt(arrayS[8]);

            lChecks.add(new Checks(date, time, id_order, id_client, min_pay, status_check, status_pay, full_pay, id_user));
        }
        return lChecks;
    }

    public void delete(){

    }
}
