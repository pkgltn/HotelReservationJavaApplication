package model;

import java.util.Objects;

public class Room implements IRoom {
    private  String roomNumber;
    private  Double price;

    private  RoomType roomType;

    private boolean isFree;

    public Room(String roomNumber,Double price,RoomType roomType){
        this.roomNumber=roomNumber;
        this.price=price;
        this.roomType=roomType;
        this.isFree=true;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    public void setRoomNumber(String roomNumber) {

        this.roomNumber=roomNumber;
    }


    public void setRoomPrice(Double price) {
        this.price=price;
    }


    public void setRoomType(RoomType roomType) {
        this.roomType=roomType;
    }


    public void setIsFree(boolean isFree) {
        this.isFree=isFree;
    }

    @Override
    public String toString() {
       return "Room number: " +this.getRoomNumber()+" "+this.getRoomType().toString().substring(0,1)+this.getRoomType().toString().substring(1,getRoomType().toString().length()).toLowerCase()+" bed Room Price: $"+this.getRoomPrice() ;
    }

    @Override
    public int hashCode(){
        return Objects.hash(roomNumber);
    }

    @Override
    public boolean equals(Object o){
        if(this==o) {return true;}
        if(o==null||getClass()!=o.getClass()) {return false;}
        Room r =(Room)o;
        return roomNumber.equals(r.roomNumber);
    }


}
