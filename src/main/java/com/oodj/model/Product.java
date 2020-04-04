package com.oodj.model;

import java.util.Objects;

public class Product {
private String id;
private String name;
private double price;
private int rate;

    public Product() {
    }

    public Product(String id, String name, double price, int rate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof Product) {
            Product p = (Product) obj;
            return (p.getId() == null && id == null)
                    || (p.getId().equals(id));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
