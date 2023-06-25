package api;

import model.Customer;
import model.IRoom;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static ReservationService reservationService;
    public AdminResource(){
        reservationService=ReservationService.getInstance();
    }


    public Customer getCustomer(String email){
        return reservationService.getCustomerService().getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        for (IRoom r: rooms){
            reservationService.addRoom(r);
        }
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return reservationService.getCustomerService().getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }

}
