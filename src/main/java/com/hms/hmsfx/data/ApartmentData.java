package com.hms.hmsfx.data;

public class ApartmentData {

     int apartmentID;
      String apartmentName;
      String apartmentDesc;
      int apartmentPrice;
      String apartmentType;
      boolean apartmentStatus;
      String apartmentSpecs;
      int apartmentCreatedBy;

    public ApartmentData(int apartmentID, String apartmentName, String apartmentDesc, int apartmentPrice, String apartmentType, boolean apartmentStatus,String apartmentSpecs){
        this.apartmentID = apartmentID;
        this.apartmentName = apartmentName;
        this.apartmentDesc = apartmentDesc;
        this.apartmentPrice = apartmentPrice;
        this.apartmentType = apartmentType;
        this.apartmentStatus = apartmentStatus;
        this.apartmentSpecs = apartmentSpecs;

    }

    public int getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(int apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentDesc() {
        return apartmentDesc;
    }

    public void setApartmentDesc(String apartmentDesc) {
        this.apartmentDesc = apartmentDesc;
    }

    public int getApartmentPrice() {
        return apartmentPrice;
    }

    public void setApartmentPrice(int apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }

    public boolean isApartmentStatus() {
        return apartmentStatus;
    }

    public void setApartmentStatus(boolean apartmentStatus) {
        this.apartmentStatus = apartmentStatus;
    }

    public int getApartmentCreatedBy() {
        return apartmentCreatedBy;
    }

    public void setApartmentCreatedBy(int apartmentCreatedBy) {
        this.apartmentCreatedBy = apartmentCreatedBy;
    }

    public String getApartmentSpecs() {
        return apartmentSpecs;
    }

    public void setApartmentSpecs(String apartmentSpecs) {
        this.apartmentSpecs = apartmentSpecs;
    }
}
