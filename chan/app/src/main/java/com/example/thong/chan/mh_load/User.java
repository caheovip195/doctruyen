package com.example.thong.chan.mh_load;

public class User {
    public User(String login, int id, String avata_URL) {
        this.login = login;
        this.id = id;
        this.avata_URL = avata_URL;
    }

    public String login;
    public int id;
    public String avata_URL;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvata_URL() {
        return avata_URL;
    }

    public void setAvata_URL(String avata_URL) {
        this.avata_URL = avata_URL;
    }
}
