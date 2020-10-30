package model.entity;

import model.repository.ManagerRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String username;
    private String password;

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

