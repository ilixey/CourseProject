package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckClientPassportController extends AfterLoginAdminController {
    private Main main;
    private ClientSocket clientSocket;
    private Stage stage;

    public TextField tfNumPassport;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void check(ActionEvent event){
        String numPassport = tfNumPassport.getText();
        try{
            if(!numPassport.equals("")){
                clientSocket.setRequest("CHECK_CLIENT_PASSPORT " + numPassport);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_HAVENT_CLIENT")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Поиск");
                    alert.setContentText("Клиент не найден.");
                    alert.show();
                    stage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Поиск");
                    alert.setContentText(response);
                    alert.show();
                    stage.close();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Поиск");
                alert.setContentText("Незаполенное поля для ввода.");
                alert.show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void back(ActionEvent event){
        stage.close();
    }


}
