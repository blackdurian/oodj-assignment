package com.oodj.model;

public class Customer extends User{

    public Customer() {
    }

    public Customer(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return (c.getId() == null && id == null)
                    || (c.getId().equals(id));
        }
        return false;
    }
}
