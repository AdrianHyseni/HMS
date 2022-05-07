package com.hms.hmsfx.data;

public class CostData {

    String type;
    String name;
    String date;
    Double costPrice;

    public CostData() {

    }

    public CostData(String type, String name, String date, Double costPrice) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.costPrice = costPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
}
