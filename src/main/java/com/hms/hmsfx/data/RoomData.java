package com.hms.hmsfx.data;

public class RoomData {

     int roomID;
      String roomName;
      String roomDesc;
      int roomPrice;
      String roomType;
      boolean roomStatus;
      int roomCreatedBy;

    public RoomData(int roomID,String roomName, String roomDesc, int roomPrice, String roomType,boolean roomStatus ){
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDesc = roomDesc;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
        this.roomStatus = roomStatus;

    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getRoomCreatedBy() {
        return roomCreatedBy;
    }

    public void setRoomCreatedBy(int roomCreatedBy) {
        this.roomCreatedBy = roomCreatedBy;
    }
}
