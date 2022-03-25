package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.models.Clients;

import java.util.ArrayList;

public class ClientEditController {
    private Main main;
    private ClientSocket clientSocket;

    public Button btnDelete;
    public TableView<Clients> tableClients;
    public TableColumn<Clients, Integer> columnID;
    public TableColumn<Clients, String> columnSurname;
    public TableColumn<Clients, String> columnName;
    public TableColumn<Clients, String> columnNumber_passport;
    public TableColumn<Clients, Integer> columnSum_pays;

    private Clients selectedClient;


    public void initialize() {
        tableClients.setEditable(true);

        columnID.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("id"));

        columnSurname.setCellValueFactory(new PropertyValueFactory<Clients, String>("surname"));
        columnSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSurname.setOnEditCommit(event -> {
            String newSurname = event.getNewValue();

            selectedClient.setSurname(newSurname);
            updateClient("Surname", newSurname);
        });

        columnName.setCellValueFactory(new PropertyValueFactory<Clients, String>("name"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            String newName = event.getNewValue();

            selectedClient.setName(newName);
            updateClient("Name", newName);
        });


        columnNumber_passport.setCellValueFactory(new PropertyValueFactory<Clients, String>("number_passport"));
        columnNumber_passport.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNumber_passport.setOnEditCommit(event -> {
            String newNumPassport = event.getNewValue();

            selectedClient.setNum_passport(newNumPassport);
            updateClient("Num_passport", newNumPassport);
        });

        columnSum_pays.setCellValueFactory(new PropertyValueFactory<Clients, Integer>("sum_pays"));

        tableClients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Clients>() {
            @Override
            public void changed(ObservableValue<? extends Clients> observable, Clients oldValue, Clients newValue) {
                if (newValue != null) {
                    selectedClient = newValue;
                    btnDelete.setDisable(false);
                } else
                    btnDelete.setDisable(true);
            }
        });

    }

    public void showClients(){
        ObservableList<Clients> clients = FXCollections.observableArrayList(getClients());

        tableClients.setItems(clients);
    }

    private ArrayList<Clients> getClients() {
        ArrayList<Clients> lClients = new ArrayList<>();
        clientSocket.setRequest("GET_CLIENTS");
        ArrayList<String> sClients = clientSocket.getRequestList();
        System.out.println(sClients.toString());
        for (String s :
                sClients) {
            String[] arrayS = s.split(" ");
            int id = Integer.parseInt(arrayS[0]);
            String surname = arrayS[1];
            String name = arrayS[2];
            String num_passport = arrayS[3];
            int sum_pays = Integer.parseInt(arrayS[4]);
//            int online = Integer.parseInt(arrayS[4]);
            lClients.add(new Clients(id, sum_pays, surname, name, num_passport));
        }
        return lClients;
    }


    public void updateClient(String column, String newVariable) {
        clientSocket.setRequest("EDIT_CLIENT " + selectedClient.getId() + " " + column+ " " + newVariable);
        String response = clientSocket.getRequest();
        if (response.equals("ERROR_EDIT_CLIENT")) {
            System.out.println("Ошибка при редактировании клиента");
        }
    }

    public void deleteClient(ActionEvent event) {
        clientSocket.setRequest("DELETE_CLIENT " + selectedClient.getId());

        String response = clientSocket.getRequest();
        if (response.equals("ERROR_DELETE_CLIENT")) {
            System.out.println("Ошибка при удалении клиента");
        } else {
            tableClients.getItems().remove(selectedClient);
            tableClients.getSelectionModel().clearSelection();
            tableClients.refresh();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void back(ActionEvent event) {
        main.afterLoginAdmin();
    }
}
