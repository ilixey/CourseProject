package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthorizationController {
    private Main main;
    private ClientSocket clientSocket;
    public TextField loginTextField;
    public PasswordField passwordTextField;



    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void onButtonClick(ActionEvent actionEvent) {
        logIn();
    }

    public void exit(ActionEvent actionEvent){
        main.close();
    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            logIn();
    }

    private void logIn() {
        String login = loginTextField.getText(), password = passwordTextField.getText();
        if (!login.equals("") && !password.equals("")) {
            clientSocket.setRequest("AUTHORISATION " + login + " " + password);
            String response = clientSocket.getRequest();
            System.out.println("Response: " + response);
            String[] commands = response.split(" ");
            if ("OK".equals(commands[0])) {
                int online = Integer.valueOf(commands[3]);
                if (online == 0) {
                    int id = Integer.parseInt(commands[1]);
                    int role = Integer.valueOf(commands[2]);
                    int ban = Integer.valueOf(commands[4]);
                    main.actRole = role;
                    main.login = login;

                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("logs.txt", true));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date now = new Date();
                        String currDate = simpleDateFormat.format(now);
                        String text = "AUTHORISATION " + login + " " + currDate + "\r\n";
                        bufferedWriter.write(text);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                    clientSocket.setCurrentUserId(id);


                    if (ban == 1) {
                        System.out.println("ERROR!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Ошибка!");
                        alert.setContentText("Вы забанены. Обратитесь к администратору.");
                        alert.show();
                        passwordTextField.setStyle("-fx-text-box-border: red");
                        passwordTextField.setText("");
                    } else {
                        if (role == 0) {
                            main.afterLogin();
                        } else {
                            main.afterLoginAdmin();
                            System.out.println("I am admin");
                        }
                    }
                }
                } else {

                    System.out.println("ERROR ERROR_MESSAGE");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Ошибка!");
                    alert.setContentText("Ошибка. Обратитесь к администратору.");
                    alert.show();
                    passwordTextField.setStyle("-fx-text-box-border: red");
                    passwordTextField.setText("");
                }
            }

        }
    }
