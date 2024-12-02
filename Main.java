import java.util.Scanner;

/**
 * This is the main class
 * @author Vanna Walker
 */

public class Main {
    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Fleet Management System");
        System.out.println("--------------------------------------");
        FleetManagement manager = new FleetManagement();

        boolean exit;
        boolean validInput;
        double amount;
        String choice;

//        try {
//            // Load fleet from DB
//            manager.loadFleetFromDB();
//            System.out.println("Fleet data loaded successfully.");
//        } catch (Exception e) {
//            System.out.println("No existing fleet data found. Starting fresh.");
//        }

        // Loading fleet data from CSV file otherwise, loading from database
        try {
            if (args.length > 0) {
                manager.loadFleetFromCSV(args[0]);
            } else {
                manager.loadFleetFromDB();
            }

            exit = false;

            // Loop to display menu
            while (!exit) {
                manager.displayMenu();
                choice = keyboard.nextLine().trim().toUpperCase();
                switch (choice) {
                    case "P":
                        manager.printInventory();
                        break;
                    case "A":
                        System.out.print("Please enter the new boat CSV data: ");
                        manager.addBoat(keyboard.nextLine());
                        break;
                    case "R":
                        System.out.print("Which boat do you want to remove? ");
                        manager.removeBoat(keyboard.nextLine());
                        break;
                    case "E":
                        System.out.print("Which boat do you want to spend on? ");
                        String name = keyboard.nextLine().trim();
                        amount = 0;
                        validInput = false;
                        // Loop to ensure valid expense input
                        while (!validInput) {
                            try {
                                System.out.print("How much do you want to spend? ");
                                amount = Double.parseDouble(keyboard.nextLine());
                                validInput = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount. Please enter a valid number.");
                            }
                        }
                        manager.expenseRequest(name, amount);
                        break;
                    case "X":
                        try {
                            manager.saveFleetToDB();
                                System.out.println("Exiting the Fleet Management System.");
                            } catch (Exception e) { // For errors when saving
                                System.out.println("Error saving fleet data: " + e.getMessage());
                            }finally{
                                exit = true;
                            }
                            break;
                        default:
                            System.out.println("Invalid menu option, try again.");
                    }
                }
            } catch (Exception e) { // Catching unhandled exceptions
                e.printStackTrace(); // For debugging
            }
        }
    }// end of the main class

