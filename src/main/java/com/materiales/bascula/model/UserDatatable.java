package com.materiales.bascula.model;

import java.util.List;

public class UserDatatable {

    private List<User> users;
    private int count;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
