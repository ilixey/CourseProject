package sample.models;

public class Clients {
    private int id_cli, pays_for_period;
    private String surname, name, num_passport;

    public Clients(int id_cli, int pays_for_period, String surname, String name, String num_passport) {
        this.id_cli = id_cli;
        this.pays_for_period = pays_for_period;
        this.surname = surname;
        this.name = name;
        this.num_passport = num_passport;
    }

    public int getId() {
        return id_cli;
    }

    public void setId(int id_cli) {
        this.id_cli = id_cli;
    }

    public int getSum_pays() {
        return pays_for_period;
    }

    public void setPays_for_period(int pays_for_period) {
        this.pays_for_period = pays_for_period;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber_passport() {
        return num_passport;
    }

    public void setNum_passport(String num_passport) {
        this.num_passport = num_passport;
    }
}
