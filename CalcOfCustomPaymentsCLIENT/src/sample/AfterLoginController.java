package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AfterLoginController {
    private Main main;
    private ClientSocket clientSocket;
    public Label dateCurrent;

    public void dateCurr() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateCur = simpleDateFormat.format(calendar.getTime());
        dateCurrent.setText(dateCur);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void deauthorize(ActionEvent event) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("logs.txt", true));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String currDate = simpleDateFormat.format(now);
            String text = "DEAUTHORIZATION " + main.login + " " + currDate + "\r\n";
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
        clientSocket.setRequest("DISCONNECT " + clientSocket.getCurrentUserId());
        main.close();
    }

    public void showProfile(ActionEvent event){main.showProfile();}
    public void showChecks(ActionEvent event){main.showChecks();}
    public void addNewCheck(ActionEvent event){main.addNewCheck();}


    public void checkClientNumPassport(ActionEvent event) {main.checkClientNumPassport();}
    public void addClient(ActionEvent event){
        main.addClient();
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void initialize(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateCur = simpleDateFormat.format(calendar.getTime());
        dateCurrent.setText(dateCur);
    }


}
