package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.models.Checks;


import java.util.ArrayList;

public class ChecksEditController {

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


    public void initialize() {

        columnID_order.setCellValueFactory(new PropertyValueFactory<>("ID_order"));
        columnID_client.setCellValueFactory(new PropertyValueFactory<>("ID_client"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        columnMin_pay.setCellValueFactory(new PropertyValueFactory<>("Min_pay"));
        columnStatus_check.setCellValueFactory(new PropertyValueFactory<>("Status_check"));
//            columnStatus_check.setCellValueFactory();
        columnStatus_pay.setCellValueFactory(new PropertyValueFactory<>("Status_pay"));
        columnFull_pay.setCellValueFactory(new PropertyValueFactory<>("Full_pay"));
        columnID_user.setCellValueFactory(new PropertyValueFactory<>("ID_user"));
//        if (main.actRole == 1) {
//
//        }
        tableChecks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Checks>() {
            @Override
            public void changed(ObservableValue<? extends Checks> observable, Checks oldValue, Checks newValue) {
                if (newValue != null) {
                    selectedCheck = newValue;
//                    btnDelete.setDisable(false);
                }
//                    btnDelete.setDisable(true);
            }
        });


    }


    public void showChecks() {
        ObservableList<Checks> checks = FXCollections.observableArrayList(getChecks());

        tableChecks.setItems(checks);
    }

    private ArrayList<Checks> getChecks() {
        ArrayList<Checks> lChecks = new ArrayList<>();
        clientSocket.setRequest("EDIT_CHECKS");
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

    public void delete(ActionEvent event) {
        clientSocket.setRequest("DELETE_CHECK " + selectedCheck.getID_order());
        String response = clientSocket.getRequest();
        if (response.equals("ERROR_DELETE_CHECK")) {
            System.out.println("Ошибка при удалении чека");
        } else {
            tableChecks.getItems().remove(selectedCheck);
            tableChecks.getSelectionModel().clearSelection();
            tableChecks.refresh();
        }
    }

    public void back(ActionEvent event) {
        if (main.actRole == 1) {
            main.afterLoginAdmin();
        } else {
            main.afterLogin();
        }
    }

    public void refresh(ActionEvent event) {
        showChecks();
    }

}
