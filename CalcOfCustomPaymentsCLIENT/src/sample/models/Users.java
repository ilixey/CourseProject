package sample.models;

public class Users {
    private int id_user, role, online, ban;
    private String login, password;

    public Users(int id_user, int role, String login, String password, int online) {
        this.id_user = id_user;
        this.role = role;
        this.login = login;
        this.password = password;
        this.online = online;
    }

    public int getId_user() {
        return id_user;
    }

    public int getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOnline() {
        return online;
    }

    public int getBan() {
        return ban;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }
}
