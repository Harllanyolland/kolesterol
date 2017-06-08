package com.example.sergio.kolestrol_1.Model;

/**
 * Created by Sergio on 6/8/2017.
 */

public class ServerRequest {
    private String operation;
    private User user;

    public void setOperation (String operation) {this.operation=operation; }

    public void setUser(User user) {this.user = user; }
}
