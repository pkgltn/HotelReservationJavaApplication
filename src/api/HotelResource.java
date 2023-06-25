package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static CustomerService customerService;

    public HotelResource(){
        customerService=CustomerService.getInstance();
    }

    public Collection<Customer> getCustomers(){return customerService.getAllCustomers();}

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){

        customerService.addCustomer(email,firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return customerService.getReservationService().getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer=this.getCustomer(customerEmail);
        return customerService.reserveARoom(customer, room,checkInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        Customer customer=this.getCustomer(customerEmail);
        return customerService.getReservationService().getCustomersReservation(customer);
    }
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut){
        Collection<IRoom> roomsAvailable=customerService.getReservationService().findRooms(checkIn,checkOut);
        return roomsAvailable;
    }

    public Collection<IRoom> findRoomsWithNewDates(Date checkIn, Date checkOut){
        Collection<IRoom> roomsAvailable=customerService.getReservationService().findRoomsWithNewDates(checkIn,checkOut);
        return roomsAvailable;
    }
}
