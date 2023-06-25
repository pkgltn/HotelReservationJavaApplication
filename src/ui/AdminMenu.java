package ui;
import api.AdminResource;
import api.HotelResource;
import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public AdminResource adminResource;
    private HotelResource hotelResource;
    public AdminMenu(HotelResource hotelResource){
        this.hotelResource=hotelResource;
        adminResource = new AdminResource();
        boolean keepRunning=true;
            while(keepRunning){
                try {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Admin Menu");
                    System.out.println("------------------------------------");
                    System.out.println("1 - See all Customers");
                    System.out.println("2 - See all Rooms");
                    System.out.println("3 - See all Reservations");
                    System.out.println("4 - Add a Room");
                    System.out.println("5 - Back to Main Menu");
                    System.out.println("------------------------------------");
                    System.out.println("Please select a number for the menu option");
                    int selection=Integer.parseInt(scanner.nextLine());
                    switch(selection) {
                        case 1:
                            String listOfCustomers = "";
                            for (Customer c : hotelResource.getCustomers()) {
                                listOfCustomers += c.toString() + "\n";
                            }
                            if (listOfCustomers.isEmpty()) {
                                System.out.println("There are no Customers");
                            } else {
                                System.out.println(listOfCustomers);
                            }
                            keepRunning = true;
                            break;
                        case 2:
                            String listOfRooms = "";
                            for (IRoom r : adminResource.getAllRooms()) {
                                listOfRooms += r.toString() + "\n";
                            }
                            if (listOfRooms.isEmpty()) {
                                System.out.println("There are no Rooms");
                            } else {
                                System.out.println(listOfRooms);
                            }
                            keepRunning = true;
                            break;
                        case 3:
                            adminResource.displayAllReservations();
                            keepRunning = true;
                            break;
                        case 4:
                            boolean entry = true;
                            List<IRoom> roomsToInsert = new ArrayList<IRoom>();
                            while (entry) {
                                System.out.println("Enter room number");
                                int roomNumber = Integer.parseInt(scanner.nextLine());
                                while (!(roomNumber > 0)&&(hotelResource.getRoom(String.valueOf(roomNumber)).getRoomNumber().equals(String.valueOf(roomNumber)))) {
                                    System.out.println("Enter a valid room number");
                                    roomNumber = Integer.parseInt(scanner.nextLine());
                                }
                                System.out.println("Enter price per night");
                                double pricePerNight = Double.parseDouble(scanner.nextLine());
                                while (!(pricePerNight >= 0)) {
                                    System.out.println("Enter a valid price per night");
                                    pricePerNight = Double.parseDouble(scanner.nextLine());
                                }
                                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                                int roomType = Integer.parseInt(scanner.nextLine());
                                while (!(roomType > 0 && roomType < 3)) {
                                    System.out.println("Enter a valid room type");
                                    roomType = Integer.parseInt(scanner.nextLine());
                                }
                                System.out.println("Would you like to add another room y/n");
                                String answer = scanner.nextLine();
                                while (!(answer.equals("y") || answer.equals("n") || answer.equals("Y") || answer.equals("N"))) {
                                    System.out.println("Please enter Y/y (Yes) or N/n (No)");
                                    answer = scanner.nextLine();
                                }
                                String roomNumberString = String.valueOf(roomNumber);
                                RoomType roomTypeEnumerate;
                                if (roomType == 1) {
                                    roomTypeEnumerate = RoomType.SINGLE;
                                } else {
                                    roomTypeEnumerate = RoomType.DOUBLE;
                                }
                                if (pricePerNight > 0) {
                                    IRoom room = new Room(roomNumberString, pricePerNight, roomTypeEnumerate);
                                    roomsToInsert.add(room);
                                } else {
                                    IRoom room = new FreeRoom(roomNumberString, roomTypeEnumerate);
                                    roomsToInsert.add(room);
                                }
                                if (answer.equals("n") || answer.equals("N")) {
                                    entry = false;
                                }
                            }
                            adminResource.addRoom(roomsToInsert);
                            keepRunning = true;
                            break;
                        case 5:
                            System.out.println("Returning to Main Menu...");
                            keepRunning = false;
                            break;
                        default:
                            System.out.println("Please enter a number between 1 and 5");
                            break;
                        }
                }catch(Exception ex){
                    System.out.println("\nError: Invalid input\n");
                }
            }
        }
    }