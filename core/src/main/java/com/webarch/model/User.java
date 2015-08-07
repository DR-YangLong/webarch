package com.webarch.model;

public class User {
    
    private Integer id;

    private String username;

    private String password;

    private Integer roler;

    private String status;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoler() {
        return roler;
    }

    
    public void setRoler(Integer roler) {
        this.roler = roler;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }
}