package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AfterLoginAdminController {
    private Main main;
    private ClientSocket clientSocket;

    public void setMain(Main main) {
        this.main = main;
    }
    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void deauthorize(ActionEvent event) throws IOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("logs.txt", true));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String currDate = simpleDateFormat.format(now);
            String text = "DEAUTHORIZATION " + main.login + " " + currDate;
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

    public void showUsers(ActionEvent event){
        main.vUsers();
    }
    public void showClients(ActionEvent event){ main.editClients();}
    public void showProfile(ActionEvent event){main.showProfile();}
    public void editBV(ActionEvent event){main.editBV();}
    public void editRC(ActionEvent event){main.editRC();}
    public void editChecks(ActionEvent event){main.editChecks();}
    public void showChecksA(ActionEvent event){main.showChecks();}


    public void initialize(){
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");


    }
}
