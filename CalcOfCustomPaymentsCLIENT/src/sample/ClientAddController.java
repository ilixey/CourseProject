package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientAddController {
    private Main main;
    private ClientSocket clientSocket;

    public TextField tfSurname;
    public TextField tfName;
    public TextField tfNumPassport;


    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void accept(ActionEvent event){
        String surname = tfSurname.getText();
        String name = tfName.getText();
        String numPassport = tfNumPassport.getText();

        try {
            if (!surname.equals("") && !name.equals("") && !numPassport.equals("")){
                clientSocket.setRequest("ADD_CLIENT " + surname + " " + name + " " + numPassport);
                String response = clientSocket.getRequest();
                if (response.equals("ERROR_ADD_CLIENT")){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setTitle("Внимание");
                    alert.setContentText("Ошибка. Возможно сервер отключён");
                    alert.show();
                } else{
                    stage.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Внимание");
                alert.setContentText("Незаполенное поле/я для ввода.");
                alert.show();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void back(ActionEvent event) {
        stage.close();
    }

}
