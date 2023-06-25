package service;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class CustomerService {

    // initialize the only object in Server Singleton class
    private static CustomerService reference = new CustomerService();
    private ReservationService reservationService;
    private Set<Customer> customers;

    // this private constructor prevents the client app
    // from creating the CustomerService class instance
    // and the Singleton class has access to the customers set
    private CustomerService() {
        this.customers = new HashSet<Customer>();
        this.reservationService=ReservationService.getInstance();
    }

    public static CustomerService getInstance(){
        return reference;
    }

    public final ReservationService getReservationService(){
        return reservationService;
    }

    public void addCustomer(String email,String firstName,String lastName){
        Customer customer = new Customer(firstName,lastName,email);
        customers.add(customer);
        }


    public Customer getCustomer(String customerEmail){
        Customer customer=null;
        for(Customer c:customers){
            if(c.getEmail().equals(customerEmail)){
                customer=c;
            }
        }
        return customer;
    }

    public Collection<Customer> getAllCustomers(){
        return customers;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return CustomerService.getInstance().getReservationService().reserveARoom(customer, room,checkInDate,checkOutDate);
    }
}
