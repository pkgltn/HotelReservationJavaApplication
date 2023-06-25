package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;

    private Date checkOutDate;
    private final String dateRegex="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    private final Pattern pattern = Pattern.compile(dateRegex);

    public Reservation(Customer customer,IRoom room, Date checkInDate, Date checkOutDate){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String strCheckInDate = dateFormat.format(checkInDate);
        String strCheckOutDate = dateFormat.format(checkOutDate);
        if(!pattern.matcher(strCheckInDate).matches() || !pattern.matcher(strCheckOutDate).matches()){
            throw new IllegalArgumentException("Error, Invalid date format!");
        }
        int checkInDateYear=Integer.parseInt(strCheckInDate.split("/")[2]);
        int checkInDateMonth=Integer.parseInt(strCheckInDate.split("/")[0])-1;
        int checkInDateDay=Integer.parseInt(strCheckInDate.split("/")[1]);
        if((checkInDateMonth==4||checkInDateMonth==6||checkInDateMonth==9||checkInDateMonth==11)&&(checkInDateDay>30)){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        if(((checkInDateMonth==2)&&(checkInDateDay>29))||((checkInDateMonth==2)&&(checkInDateDay>28)&&!(((checkInDateYear%4 == 0) && (checkInDateYear%100 != 0)) || (checkInDateYear%400==0)))){
            throw new IllegalArgumentException("Error, Invalid date!");
        }

        int checkOutDateYear=Integer.parseInt(strCheckOutDate.split("/")[2]);
        int checkOutDateMonth=Integer.parseInt(strCheckOutDate.split("/")[0])-1;
        int checkOutDateDay=Integer.parseInt(strCheckOutDate.split("/")[1]);
        if((checkOutDateMonth==4||checkOutDateMonth==6||checkOutDateMonth==9||checkOutDateMonth==11)&&(checkOutDateDay>30)){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        if(((checkOutDateMonth==2)&&(checkOutDateDay>29))||((checkOutDateMonth==2)&&(checkOutDateDay>28)&&!(((checkOutDateYear%4 == 0) && (checkOutDateYear%100 != 0)) || (checkOutDateYear%400==0)))){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        this.customer=customer;
        this.room=room;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;

    }

    public Customer getCustomer(){
        return customer;
    }

    public IRoom getRoom(){
        return room;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }

    public void setCustomer(Customer customer){
        this.customer=customer;
    }

    public void setRoom(IRoom room){
        this.room=room;
    }

    void setCheckInDate(String checkInDate)
    {
        if(!pattern.matcher(checkInDate).matches()){
            throw new IllegalArgumentException("Error, Invalid date format!");
        }
        int checkInDateYear=Integer.parseInt(checkInDate.substring(6,10));
        int checkInDateMonth=Integer.parseInt(checkInDate.substring(0,2))-1;
        int checkInDateDay=Integer.parseInt(checkInDate.substring(3,5));
        if((checkInDateMonth==4||checkInDateMonth==6||checkInDateMonth==9||checkInDateMonth==11)&&(checkInDateDay>30)){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        if(((checkInDateMonth==2)&&(checkInDateDay>29))||((checkInDateMonth==2)&&(checkInDateDay>28)&&!(((checkInDateYear%4 == 0) && (checkInDateYear%100 != 0)) || (checkInDateYear%400==0)))){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(checkInDateYear, checkInDateMonth, checkInDateDay);
        Date givenCheckInDate = calendar.getTime();
        this.checkInDate=givenCheckInDate;
    }

    void setCheckOutDate(String checkOutDate)
    {
        if(!pattern.matcher(checkOutDate).matches()){
            throw new IllegalArgumentException("Error, Invalid date format!");
        }
        int checkOutDateYear=Integer.parseInt(checkOutDate.substring(6,10));
        int checkOutDateMonth=Integer.parseInt(checkOutDate.substring(0,2))-1;
        int checkOutDateDay=Integer.parseInt(checkOutDate.substring(3,5));
        if((checkOutDateMonth==4||checkOutDateMonth==6||checkOutDateMonth==9||checkOutDateMonth==11)&&(checkOutDateDay>30)){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        if(((checkOutDateMonth==2)&&(checkOutDateDay>29))||((checkOutDateMonth==2)&&(checkOutDateDay>28)&&!(((checkOutDateYear%4 == 0) && (checkOutDateYear%100 != 0)) || (checkOutDateYear%400==0)))){
            throw new IllegalArgumentException("Error, Invalid date!");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(checkOutDateYear,checkOutDateMonth, checkOutDateDay);
        Date givenCheckOutDate = calendar.getTime();
        this.checkOutDate=givenCheckOutDate;
    }

    @Override
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("E MMM dd  yyyy");
        String strCheckInDate = dateFormat.format(this.getCheckInDate());
        String strCheckOutDate = dateFormat.format(this.getCheckOutDate());
        return "Reservation\n" + getCustomer().getFirstName() + " " + getCustomer().getLastName() + "\nRoom: " + getRoom().getRoomNumber() + "-" + getRoom().getRoomType().toString().toLowerCase()+" bed\nPrice: $" + getRoom().getRoomPrice() + " price per night\nCheckin date: " + strCheckInDate+"\nCheckout date: " + strCheckOutDate+ "\n";
    }
}
