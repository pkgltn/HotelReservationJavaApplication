package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber,0.0,roomType);
    }
    @Override
    public String toString() {
        return "Room number: " +this.getRoomNumber()+" "+this.getRoomType().toString().substring(0,1)+this.getRoomType().toString().substring(1,getRoomType().toString().length()).toLowerCase()+" bed Room Price: $0.0";
    }
}
