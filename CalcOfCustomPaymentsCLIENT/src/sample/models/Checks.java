package sample.models;

public class Checks {
    private String date, time;
    private int id_order, id_client, min_pay, status_check, status_pay, full_pay, id_user;

    public Checks(String date, String time, int id_order, int id_client, int min_pay, int status_check, int status_pay, int full_pay, int id_user) {
        this.date = date;
        this.time = time;
        this.id_order = id_order;
        this.id_client = id_client;
        this.min_pay = min_pay;
        this.status_check = status_check;
        this.status_pay = status_pay;
        this.full_pay = full_pay;
        this.id_user = id_user;
    }



    public String getDate() {
        return date + " " + time;
    }

    public String getTime() {
        return time;
    }

    public int getID_order() {
        return id_order;
    }

    public int getID_client() {
        return id_client;
    }

    public int getMin_pay() {
        return min_pay;
    }

    public int getStatus_check() {
        return status_check;
    }

    public int getStatus_pay() {
        return status_pay;
    }

    public int getFull_pay() {
        return full_pay;
    }

    public int getID_user() {
        return id_user;
    }

    public void setStatus_check(int status_check) {
        this.status_check = status_check;
    }

    public void setStatus_pay(int status_pay) {
        this.status_pay = status_pay;
    }
}
