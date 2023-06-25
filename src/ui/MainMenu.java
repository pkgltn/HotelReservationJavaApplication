package ui;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    public HotelResource hotelResource;


    static final String dateRegex="^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
    public static final Pattern patternDate = Pattern.compile(dateRegex);

    static final String emailRegex="^(.+)@(.+).com$";
    public static final Pattern patternEmail = Pattern.compile(emailRegex);

    private static Map<Boolean,String> validateDate(Scanner scanner,String dateType, String checkInDateInput){
        boolean b=false;
        Map<Boolean,String> mapOfResult=new HashMap<Boolean,String>();
        while(!patternDate.matcher(checkInDateInput).matches()){
            System.out.println("Enter a valid " +dateType +" Date");
            checkInDateInput=scanner.nextLine();
        }
        b=true;
        int checkInDateYear=Integer.parseInt(checkInDateInput.split("/")[2].toString());
        int checkInDateMonth=Integer.parseInt(checkInDateInput.split("/")[0].toString());
        int checkInDateDay=Integer.parseInt(checkInDateInput.split("/")[1].toString());
        while((checkInDateMonth==4||checkInDateMonth==6||checkInDateMonth==9||checkInDateMonth==11)&&(checkInDateDay>30) || ((checkInDateMonth==2)&&(checkInDateDay>29))||((checkInDateMonth==2)&&(checkInDateDay>28)&&!(((checkInDateYear%4 == 0) && (checkInDateYear%100 != 0)) || (checkInDateYear%400==0)))) {
            System.out.println("Enter a valid " +dateType +" Date");
            checkInDateInput=scanner.nextLine();
            b= Boolean.parseBoolean(validateDate(scanner,dateType,checkInDateInput).get(Boolean.TRUE));
        }
        mapOfResult.put(b,checkInDateInput);
        return mapOfResult;
    }
    public MainMenu(){
        hotelResource=new HotelResource();
        boolean keepRunning=true;
        while(keepRunning){
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Welcome to the Hotel Reservation Application");
                System.out.println();
                System.out.println("------------------------------------");
                System.out.println("1 - Find and reserve a room");
                System.out.println("2 - See my reservations");
                System.out.println("3 - Create an account");
                System.out.println("4 - Admin");
                System.out.println("5 - Exit");
                System.out.println("------------------------------------");
                System.out.println("Please select a number for the menu option");
                int selection=Integer.parseInt(scanner.nextLine());
                switch(selection){
                    case 1:
                        Date givenCheckInDate = new Date();
                        Date givenCheckOutDate = new Date();
                        boolean checkOutDateSuperiorCheckInDate=true;
                        while(checkOutDateSuperiorCheckInDate) {
                            System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
                            String checkInDateInput = scanner.nextLine();
                            Map<Boolean, String> mapValidDate = validateDate(scanner, "CheckIn", checkInDateInput);
                            checkInDateInput = mapValidDate.get(true);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Integer.parseInt(checkInDateInput.split("/")[2].toString()), Integer.parseInt(checkInDateInput.split("/")[0].toString()) - 1, Integer.parseInt(checkInDateInput.split("/")[1].toString()));
                            givenCheckInDate = calendar.getTime();
                            String toRemove = (String) mapValidDate.remove(true);
                            System.out.println("Enter CheckOut Date mm/dd/yyyy example 02/21/2020");
                            String checkOutDateInput = scanner.nextLine();
                            mapValidDate = validateDate(scanner, "CheckOut", checkOutDateInput);
                            calendar.set(Integer.parseInt(mapValidDate.get(true).split("/")[2].toString()), Integer.parseInt(mapValidDate.get(true).split("/")[0]) - 1, Integer.parseInt(mapValidDate.get(true).split("/")[1].toString()));
                            givenCheckOutDate = calendar.getTime();
                            if(givenCheckOutDate.after(givenCheckInDate)){
                                checkOutDateSuperiorCheckInDate=false;
                            }
                            else{
                                System.out.println("The CheckOut Date can never be before the CheckIn Date. Please insert both dates again.");
                            }
                        }
                        List<String> roomNumbers=new ArrayList<String>();
                        Collection<IRoom> roomsAvailable = hotelResource.findARoom(givenCheckInDate,givenCheckOutDate);
                        if(roomsAvailable.isEmpty()){
                            roomsAvailable=hotelResource.findRoomsWithNewDates(givenCheckInDate,givenCheckOutDate);
                            Date tempCheckInDate=new Date(givenCheckInDate.getTime() + (1000 * 60 * 60 * 24 * 7));
                            Date tempCheckOutDate=new Date(givenCheckOutDate.getTime() + (1000 * 60 * 60 * 24 * 7));
                            givenCheckInDate=tempCheckInDate;
                            givenCheckOutDate=tempCheckOutDate;
                        }
                        for (IRoom r:roomsAvailable){
                            System.out.println(r);
                            roomNumbers.add(r.getRoomNumber());
                        }
                        System.out.println();
                        if(!roomNumbers.isEmpty()){
                            System.out.println("Would you like to book a room? y/n");
                            String bookRoom=scanner.nextLine();
                            while(!(bookRoom.equals("y")||bookRoom.equals("n")||bookRoom.equals("Y")||bookRoom.equals("N"))){
                                System.out.println("Please enter Y/y (Yes) or N/n (No)");
                                bookRoom=scanner.nextLine();
                            }
                            if(bookRoom.equals("y")||bookRoom.equals("Y")){
                                System.out.println("Do you have an account with us? y/n");
                                String hasAccount=scanner.nextLine();
                                while(!(hasAccount.equals("y")||hasAccount.equals("n")||hasAccount.equals("Y")||hasAccount.equals("N"))){
                                    System.out.println("Please enter Y/y (Yes) or N/n (No)");
                                    hasAccount=scanner.nextLine();
                                }
                                if(hasAccount.equals("y")||hasAccount.equals("Y")){
                                    System.out.println("Enter e-mail format: name@domain.com");
                                    String emailToCheck=scanner.nextLine();
                                    boolean pass=true;
                                    while(pass){
                                        if(!patternEmail.matcher(emailToCheck).matches()){
                                            System.out.println("Enter a valid email");
                                            emailToCheck=scanner.nextLine();
                                        }
                                        Customer customer = null;
                                        for(Customer c: hotelResource.getCustomers()){
                                            if(c.getEmail().equals(emailToCheck)){
                                                customer=c;
                                                pass=false;
                                            }}
                                        if(pass){
                                            System.out.println("Enter a valid email");
                                            emailToCheck=scanner.nextLine();}
                                    }
                                    System.out.println("What room number would you like to reserve");
                                    String roomNumber=scanner.nextLine();
                                    while(!roomNumbers.contains(roomNumber)){
                                        System.out.println("Enter valid room number");
                                        roomNumber=scanner.nextLine();
                                    }
                                    IRoom room =hotelResource.getRoom(roomNumber);
                                    Reservation reservation = hotelResource.bookARoom(emailToCheck,room,givenCheckInDate,givenCheckOutDate);
                                    System.out.println(reservation);
                                }else{System.out.println("Please create an account");}
                            }else{System.out.println("Returning to the main menu");}
                        }else{System.out.println("There are no available rooms to book");}
                        keepRunning=true;
                        break;
                    case 2:
                        System.out.println("Enter e-mail format: name@domain.com");
                        String email=scanner.nextLine();
                        boolean pass=true;
                        while(pass &&!patternEmail.matcher(email).matches()){
                            System.out.println("Enter a valid email");
                            email=scanner.nextLine();
                            Customer customer = null;
                            for(Customer c: hotelResource.getCustomers()){
                                if(c.getEmail().equals(email)){
                                    customer=c;
                                    pass=false;
                                }
                                else{System.out.println("There are no reservations");break;

                                }
                            }}
                        if(!hotelResource.getCustomersReservations(email).isEmpty()){
                            String listOfCustomerReservations="";
                            for (Reservation r:hotelResource.getCustomersReservations(email)){
                                listOfCustomerReservations+=r.toString()+"\n";
                            }
                        System.out.println(listOfCustomerReservations);}
                        else{System.out.println("There are no reservations");}
                        keepRunning=true;
                        break;
                    case 3:
                        System.out.println("Enter e-mail format: name@domain.com");
                        String emailNew=scanner.nextLine();
                        //while(!patternEmail.matcher(emailNew).matches()){
                        //    System.out.println("Enter a valid email");
                        //    emailNew=scanner.nextLine();
                        //}
                        System.out.println("First Name");
                        String firstName=scanner.nextLine();
                        System.out.println("Last Name");
                        String lastname=scanner.nextLine();
                        try {
                            hotelResource.createACustomer(emailNew, firstName, lastname);
                        }catch(Exception ex){
                            System.out.println(ex.getMessage());
                            }
                        keepRunning=true;
                        break;
                    case 4:
                        AdminMenu adminMenu=new AdminMenu(hotelResource/*rooms,reservations,customers*/);
                        keepRunning=true;
                        break;
                    case 5:
                        System.out.println("The application is exiting...");
                        keepRunning=false;
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 5");break;
                }
                }catch(Exception ex){
                    //System.out.println(ex.getMessage());
                    System.out.println("\nError: Invalid input\n");
                }
            }
        }
    }
