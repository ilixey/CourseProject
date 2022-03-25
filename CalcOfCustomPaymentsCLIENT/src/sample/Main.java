package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.Users;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    private Stage primaryStage;
    private BorderPane root_layout;
    private ClientSocket clientSocket;
    public int actRole;
    public String login;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Система расчёта таможенных платежей");
        clientSocket = new ClientSocket();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setOnCloseRequest(Event::consume);    never close application3
        loadRootLayout();
    }

    public void loadRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/root_layout.fxml"));
            root_layout = loader.load();

            Scene scene = new Scene(root_layout);
            primaryStage.setScene(scene);

            primaryStage.show();
            loadAuthorization();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAuthorization() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/authorization.fxml"));

            root_layout.setCenter(loader.load());
            AuthorizationController authorizationController = loader.getController();
            authorizationController.setMain(this);
            authorizationController.setClientSocket(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afterLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/afterLogin.fxml"));

            root_layout.setCenter(loader.load());
            AfterLoginController afterLoginController = loader.getController();
            afterLoginController.setMain(this);
            afterLoginController.setClientSocket(clientSocket);
            //afterLoginController.dateCurr();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afterLoginAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/afterLoginAdmin.fxml"));

            root_layout.setCenter(loader.load());
            AfterLoginAdminController afterLoginAdminController = loader.getController();
            afterLoginAdminController.setMain(this);
            afterLoginAdminController.setClientSocket(clientSocket);
            //afterLoginAdminController.dateCurr();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vUsers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/users.fxml"));

            root_layout.setCenter(loader.load());
            UsersController usersController = loader.getController();
            usersController.setMain(this);
            usersController.setClientSocket(clientSocket);
            usersController.showUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editClients() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/clientEdit.fxml"));

            root_layout.setCenter(loader.load());
            ClientEditController clientEditController = loader.getController();
            clientEditController.setMain(this);
            clientEditController.setClientSocket(clientSocket);
            clientEditController.showClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProfile() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/profile.fxml"));

            root_layout.setCenter(loader.load());
            ProfileController profileController = loader.getController();
            profileController.setMain(this);
            profileController.setClientSocket(clientSocket);
            profileController.showProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showChecks() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/checks.fxml"));

            root_layout.setCenter(loader.load());
            ChecksController checksController = loader.getController();
            checksController.setMain(this);
            checksController.setClientSocket(clientSocket);
            checksController.showChecks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editChecks(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/checksEdit.fxml"));

            root_layout.setCenter(loader.load());
            ChecksEditController checksEditController = loader.getController();
            checksEditController.setMain(this);
            checksEditController.setClientSocket(clientSocket);
            checksEditController.showChecks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void showBV(){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("showBV.fxml"));
//            Stage stage = new Stage();
//            stage.setTitle("Базовая величина");
//            stage.initModality(Modality.WINDOW_MODAL);
//            stage.initOwner(primaryStage);
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//
//            ShowBVController showBVController = loader.getController();
//            showBVController.setMain(this);
//            showBVController.setClientSocket(clientSocket);
//            showBVController.setStage(stage);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void checkClientNumPassport(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/checkClientPassport.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Поиск");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            CheckClientPassportController checkClientPassportController = loader.getController();
            checkClientPassportController.setMain(this);
            checkClientPassportController.setClientSocket(clientSocket);
            checkClientPassportController.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void editBV(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/changeBV.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Базовая величина");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            ChangeBVController changeBVController = loader.getController();
            changeBVController.setMain(this);
            changeBVController.setClientSocket(clientSocket);
            changeBVController.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editRC(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/changeRates.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Процентные ставки");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            ChangeRatesController changeRatesController = loader.getController();
            changeRatesController.setMain(this);
            changeRatesController.setClientSocket(clientSocket);
            changeRatesController.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/clientAdd.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Новый клиент");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            ClientAddController clientAddController = loader.getController();
            clientAddController.setMain(this);
            clientAddController.setClientSocket(clientSocket);
            clientAddController.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewCheck(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/checkAdd.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Новый чек");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            CheckAddController checkAddController = loader.getController();
            checkAddController.setMain(this);
            checkAddController.setClientSocket(clientSocket);
            checkAddController.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pieChartShow(ArrayList<Users> users){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/pieChartUsers.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Стастистика активности пользователей");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            PieChartController pieChartController = loader.getController();
            pieChartController.setMain(this);
            pieChartController.setClientSocket(clientSocket);
            pieChartController.setStage(stage);
            pieChartController.pieChartShow(users);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
