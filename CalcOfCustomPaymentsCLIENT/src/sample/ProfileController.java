package sample;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProfileController {
    private Main main;
    private ClientSocket clientSocket;

    //    public Label lbSurname;
    public Label lbName;
    public Label lbCheckPer;
    public Label lbDateOfEmp;
    public CheckBox cbPsycho;
    public Label dateCurrent;


    public void setMain(Main main) {
        this.main = main;
    }

    public void setClientSocket(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String dateCurr() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateCur = simpleDateFormat.format(calendar.getTime());
        dateCurrent.setText(dateCur);
        return dateCur;
    }

    public void showProfile() {
        clientSocket.setRequest("SHOW_PROFILE " + main.login);
        String response = clientSocket.getRequest();
        if (response.startsWith("ERROR")) {
            System.out.println("ERROR!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Ошибка!");
            alert.setContentText("Ошибка. Обратитесь к администратору.");
            alert.show();

        } else {
            String[] arrayS = response.split(" ");
            String surname = arrayS[0];
            String name = arrayS[1];
            String check_per = arrayS[2];
            //int check_per = Integer.parseInt(arrayS[2]);
            boolean need_vacation = Boolean.parseBoolean(arrayS[3]);
            String dateOfEmp = arrayS[4];

//        lbSurname.setText(surname);
            lbName.setText(name + " " + surname);
            lbCheckPer.setText(check_per);

            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateCur = simpleDateFormat.format(calendar.getTime());
            System.out.println(dateCur);
            dateCurrent.setText(dateCur);

            if (dateOfEmp.equals(dateCur)) {
                lbDateOfEmp.setText(dateOfEmp + " Поздравляем с первым днём!");
            } else {
                lbDateOfEmp.setText(dateOfEmp);
            }
            cbPsycho.setSelected(need_vacation);
        }
    }


    public void changePsycho(ActionEvent event) {
        clientSocket.setRequest("CHANGE_STATUS_VACATION " + main.login + " " + (cbPsycho.isSelected() ? 1 : 0));
        String response = clientSocket.getRequest();
        if (response.equals("SUCCESS_UPD_VACATION_1")) {
            cbPsycho.setSelected(true);
        } else if (response.equals("SUCCESS_UPD_VACATION_0")) {
            cbPsycho.setSelected(false);
        }
    }

    public void showBV(ActionEvent event) {
        clientSocket.setRequest("SHOW_BV");
        String response = clientSocket.getRequest();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Базовая величина");
        alert.setContentText("Величина БВ: " + Integer.parseInt(response));
        alert.show();
    }


    public void showRates() {
        clientSocket.setRequest("SHOW_RATES");
        String response = clientSocket.getRequest();
        String[] arrayS = response.split(" ");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Процентные ставки на: " + dateCurr());
        alert.setContentText("Потребительские (c): : " + Integer.parseInt(arrayS[0]) + "%\nЭлектроника (e): " + Integer.parseInt(arrayS[1]) +
                "%\nПромышленные (i): " + Integer.parseInt(arrayS[2]) + "%\nНепродовольственные (n): " + arrayS[3] + "%");
        alert.show();
    }


    public void back() {
        if (main.actRole == 1) {
            main.afterLoginAdmin();
        } else {
            main.afterLogin();
        }
    }

}
