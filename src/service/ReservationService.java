package service;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;
public class ReservationService {
    // initialize the only object in Server Singleton class
    private static ReservationService reference = new ReservationService();
    CustomerService customerService;
    private Set<Reservation> reservations;
    private Set<IRoom> rooms;
    // this private constructor prevents the client app
    // from creating the CustomerService class instance
    // and the Singleton class has access to the customers set
    private ReservationService() {
        this.rooms = new HashSet<IRoom>();
        this.reservations=new HashSet<Reservation>();
        this.customerService=CustomerService.getInstance();
    }
    public CustomerService getCustomerService(){
        return customerService;
    }
    public static ReservationService getInstance(){
        return reference;
    }

    public void addRoom(IRoom room){
        rooms.add(room);
        }

    public IRoom getARoom(String roomID){
        IRoom roomToGet=null;
        for(IRoom room:rooms){
            if(room.getRoomNumber().equals(roomID)){
                roomToGet=room;
            }
        }
        return roomToGet;
    }
    Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    private Date addDays(int daysToAdd,Date date){
        return new Date(date.getTime() + (1000 * 60 * 60 * 24 * daysToAdd));
    }

    public Collection<IRoom> findRoomsWithNewDates(Date checkInDate, Date checkOutDate){
        int daysToAdd=7;
        Set<IRoom> freeRooms= new HashSet<IRoom>();
        for (IRoom r: rooms){
            freeRooms.add(r);
        }

        Date newCheckInDate = addDays(daysToAdd,checkInDate);
        Date newCheckOutDate = addDays(daysToAdd,checkOutDate);
        System.out.println("There are no available rooms on the defined dates. Checking if there are available rooms from "+ daysToAdd+ " days from now on");
        for(Reservation r: reservations){
            if ((r.getCheckInDate().after(newCheckInDate) && r.getCheckInDate().before(newCheckOutDate))
                    ||(r.getCheckOutDate().after(newCheckInDate) &&(r.getCheckOutDate().before(newCheckOutDate))))
            {
                freeRooms.remove(r.getRoom());
            }
        }
        return freeRooms;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Set<IRoom> freeRooms= new HashSet<IRoom>();
        for (IRoom r: rooms){
            freeRooms.add(r);
        }
        for(Reservation r: reservations){
            if ((r.getCheckInDate().after(checkInDate) && r.getCheckInDate().before(checkOutDate))
                    ||(r.getCheckOutDate().after(checkInDate) &&(r.getCheckOutDate().before(checkOutDate))))
            {
                freeRooms.remove(r.getRoom());
            }
        }

        return freeRooms;
    }
    public Collection<Reservation> getCustomersReservation(Customer customer){
        Set<Reservation> customerReservation= new HashSet<Reservation>();
        for(Reservation r:reservations){
            if(customer.equals(r.getCustomer())){
                customerReservation.add(r);
            }
        }
        return customerReservation;
    }
    public void printAllReservation(){
        String listOfReservations="";
        for (Reservation r:reservations){
            listOfReservations+=r.toString()+"\n";
        }
        if(listOfReservations.isEmpty()){
            System.out.println("There are no Reservations");
        }
        else{
        System.out.println(listOfReservations);}
    }
    public Collection<IRoom> getAllRooms(){
        return rooms;
    }
}