package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CheckAddController {
    private Main main;
    private ClientSocket clientSocket;
    public Stage stage;
    public TextField tfPassport, tfCount;
    public Button btnAccept, btnAdd;
    public ChoiceBox<String> chbType;
    public RadioButton speedBut;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        chbType.setItems(FXCollections.observableArrayList("e", "c", "n", "i"));
        chbType.getSelectionModel().select(0);
    }

    public void accept(ActionEvent event) {
        String value = tfPassport.getText();
        try {
            if (!value.equals("")) {
                clientSocket.setRequest("ADD_CHECK " + value + " " + main.login);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_ADD_CHECK")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Внимание!");
                    alert.setContentText("Ошибка. Возможно сервер отключён");
                    alert.show();
                } else if (response.equals("SUCCESS_ADD_CHECK")) {
                    tfPassport.setDisable(true);
                    btnAccept.setDisable(true);
                    chbType.setDisable(false);
                    btnAdd.setDisable(false);
                    tfCount.setDisable(false);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Внимание!");
                alert.setContentText("Незаполенное поля для ввода");
                alert.show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(ActionEvent event) {
        try {
            String countS = tfCount.getText();
            String typeCurr = chbType.getSelectionModel().getSelectedItem();
            boolean isSpeed = speedBut.isSelected();
            int speed;
            if (isSpeed) speed = 1;
            else speed = 0;

            if (!countS.equals("")) {
                int countI = Integer.parseInt(countS);

                clientSocket.setRequest("ADD_ORDER " + typeCurr + " " + countI + " " + speed);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_ADD_ORDER")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Ошибка, возможно сервер отключен");
                    alert.show();
                } else {
                    tfCount.setText("");
                    System.out.println("Успешно добавлен новый товар.");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Внимание");
                alert.setContentText("Незаполенное поле для ввода");
                alert.show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Внимание");
            alert.setContentText("Введите целочисленное значение количества");
            alert.show();
        }
    }

    public void back(ActionEvent event) {
        stage.close();
    }
}
