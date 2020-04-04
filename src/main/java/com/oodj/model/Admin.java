package com.oodj.model;

public class Admin extends User{

    public Admin(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Admin() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Admin) {
            Admin a = (Admin) obj;
            return (a.getId() == null && id == null)
                    || (a.getId().equals(id));
        }
        return false;
    }

}
