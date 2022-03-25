package sample;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ShowBVController {
    private Main main;
    private ClientSocket clientSocket;
    public Stage stage;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showBV(){
        clientSocket.setRequest("SHOW_BV");
        String response = clientSocket.getRequest();

    }
}
