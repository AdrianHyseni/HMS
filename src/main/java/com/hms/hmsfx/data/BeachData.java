package com.hms.hmsfx.data;

public class BeachData {
    String name;
    String date;
    double totalPrice;

    public BeachData(String name, String date, double totalPrice) {
        this.name = name;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
