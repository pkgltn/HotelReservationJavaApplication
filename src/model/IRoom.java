package model;

public interface IRoom {
    public abstract String getRoomNumber();

    public abstract  Double getRoomPrice();

    public abstract RoomType getRoomType();

    public abstract boolean isFree();



}
