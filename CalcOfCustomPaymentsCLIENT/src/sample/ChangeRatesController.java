package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class
ChangeRatesController {
    private Main main;
    private Stage stage;
    public ChoiceBox<String> choiceBox;

    private ClientSocket clientSocket;
    public TextField tfValue;

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(){
        choiceBox.setItems(FXCollections.observableArrayList("e", "c", "n", "i"));
        choiceBox.getSelectionModel().select(0);
    }

    public void accept(ActionEvent event){
        String valueRC = tfValue.getText();
        String type = choiceBox.getSelectionModel().getSelectedItem();
        try {
            if (!valueRC.equals("")){
                int value = Integer.parseInt(valueRC);
                clientSocket.setRequest("NEW_RC " + type + " " + value);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_CHANGE_RC")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Внимание");
                    alert.setContentText("Ошибка, возможно сервер отключён.");
                    alert.show();
                } else{
                    stage.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Внимание");
                alert.setContentText("Незаполенное поля для ввода");
                alert.show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Внимание!");
            alert.setContentText("Введи целочисленное значение (0-100) %");
            alert.show();
        }
    }

    public void back(ActionEvent event){
        stage.close();
    }

}
