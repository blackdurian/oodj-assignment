package com.oodj.model;

public class Customer extends User{

    private String icNum;
    private String contactNum;
    private String address;

    public Customer() {
    }

    public Customer(String id, String name, String username, String password, String icNum,
            String address, String contactNum) { //parameter
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.icNum = icNum;
        this.address = address;
        this.contactNum = contactNum;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
