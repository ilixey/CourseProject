package entity;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private int online;
    private int ban;

    public User(int id, String username, String password, int role, int online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.online = online;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public int getOnline() {
        return online;
    }

    public User(int id, String username, String password, int role, int online, int ban) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.online = online;
        this.ban = ban;
    }

    public int getBan() {
        return ban;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }
}
