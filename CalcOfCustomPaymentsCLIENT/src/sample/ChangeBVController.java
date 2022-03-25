package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeBVController {
    private Main main;
    private Stage stage;


    private ClientSocket clientSocket;
    public TextField tfValueBV;

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void accept(ActionEvent event){
        String valueBV = tfValueBV.getText();
        try {
            if (!valueBV.equals("")){
                int value = Integer.parseInt(valueBV);
                clientSocket.setRequest("NEW_BV " + value);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_CHANGE_BV")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Внимание");
                    alert.setContentText("Ошибка. Возможно сервер отключён.");
                    alert.show();
                } else{
                    stage.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Внимание");
                alert.setContentText("Незаполенное поля для ввода.");
                alert.show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Внимание");
            alert.setContentText("Введи целочисленное значение (в копейках)");
            alert.show();
        }
    }

    public void back(ActionEvent event){
        stage.close();
    }

}
