package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import sample.models.Users;

import java.util.ArrayList;

public class PieChartController {
    private Main main;
    private ClientSocket clientSocket;
    private Stage stage;
    public PieChart pctUsersOnline;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void pieChartShow(ArrayList<Users> users) {
        int countAdmins = 0, countUsers = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getOnline() == 1){
                if (users.get(i).getRole() == 1){
                    countAdmins++;
                } else {
                    countUsers++;
                }
            }
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Администраторы", countAdmins),
                        new PieChart.Data("Пользователи", countUsers));
        pctUsersOnline.setData(pieChartData);
        pctUsersOnline.setTitle("Пользователи онлайн");
    }

    public void back(ActionEvent event) {
        stage.close();
    }
}
