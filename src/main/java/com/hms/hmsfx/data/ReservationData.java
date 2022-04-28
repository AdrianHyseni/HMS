package com.hms.hmsfx.data;

public class ReservationData {
    String clientName;
    String clientSurname;
    String clientId;
    String type;
    String envStaying;
    String reference;
    String checkIn;
    String checkOut;
    int primaryPrice;
    int discount;
    int totalPrice;
    int createdBy;

    public ReservationData(){}

    @Override
    public String toString() {
        return "ReservationData{" +
                "clientName='" + clientName + '\'' +
                ", clientSurname='" + clientSurname + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type='" + type + '\'' +
                ", envStaying='" + envStaying + '\'' +
                ", reference='" + reference + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", primaryPrice=" + primaryPrice +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", createdBy=" + createdBy +
                '}';
    }

    public ReservationData(String clientName, String clientSurname, String clientId, String type, String envStaying, String reference, String checkIn, String checkOut, int primaryPrice, int discount, int totalPrice, int createdBy) {
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.clientId = clientId;
        this.type = type;
        this.envStaying = envStaying;
        this.reference = reference;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.primaryPrice = primaryPrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.createdBy = createdBy;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnvStaying() {
        return envStaying;
    }

    public void setEnvStaying(String envStaying) {
        this.envStaying = envStaying;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getPrimaryPrice() {
        return primaryPrice;
    }

    public void setPrimaryPrice(int primaryPrice) {
        this.primaryPrice = primaryPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}

