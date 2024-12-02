import java.util.ArrayList;
import java.io.*;

/**
 * This is the FleetManagement class
 * @author Vanna Walker
 */

public class FleetManagement {
    private static final String DB_FILE = "FleetData.db"; // File name for database
    private ArrayList<Boat> fleet;// Array List to store the fleet of boats

    /**
     * This is the FleetManagement method
     */

    public FleetManagement() {
        fleet = new ArrayList<>(); // Initializing the fleet as an empty list
    }// end of the FleetManagement method

    /**
     * This is the loadFleetFromCVS method
     * @param filePath;
     */

    public void loadFleetFromCSV(String filePath) {
        int year;
        int length;
        double purchasePrice;
        double expenses;
        fleet.clear(); // Clearing the current fleet data so it won't repeat
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) { // Reading each line of the CSV file
                String[] data = line.split(",");
                if (data.length >= 6) { // Ensuring enough data fields
                    try {
                        Boat.BoatType type = Boat.BoatType.valueOf(data[0].toUpperCase());
                        String name = data[1];
                        year = Integer.parseInt(data[2]);
                        String makeModel = data[3];
                        length = Integer.parseInt(data[4]);
                        purchasePrice = Double.parseDouble(data[5]);
                        expenses = (data.length >= 7) ? Double.parseDouble(data[6]) : 0.0;

                        Boat boat = new Boat(type, name, year, makeModel, length, purchasePrice, expenses);
                        fleet.add(boat); // Adding the boat to the fleet
                    } catch (Exception e) {
                        System.out.println("Error processing line: " + line); // For debugging errors
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading CSV file: " + e.getMessage()); // For debugging errors
        }
    }// end of the loadFleetFromCSV method

    /**
     * This is the saveFleetToDB method
     * Saves the fleet data to the database file
     * @throws IOException;
     */



    public void saveFleetToDB() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("FleetData.db"))) {
            oos.writeObject(fleet); // Writing the fleet list to the file
        }
    }// end of the saveFleetToDB method

    /**
     * This is the loadFleetFromDB
     * Loads the fleet data from the database file
     * @throws IOException;
     * @throws ClassNotFoundException;
     */

    public void loadFleetFromDB() throws IOException, ClassNotFoundException {
        fleet.clear(); // Clearing current fleet data so it won't repeat
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("FleetData.db"))) {
            fleet = (ArrayList<Boat>) ois.readObject(); // Reading the fleet from the file
        }
    }// end of the loadFleetFromDB method

    /**
     * This is the displayMenu method
     */

    public void displayMenu() {

        System.out.print("(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it : ");
    }// end of the displayMenu method

    /**
     * This is the printInventory method
     */

    public void printInventory() {
        double totalPaid ;
        double totalSpent;

        totalPaid = 0;
        totalSpent = 0;

        // Debugging
        if (fleet.isEmpty()) {
            System.out.println("The fleet is empty.");
            return;
        }

        System.out.println("Fleet report:");
        // Iterating through the boats in the fleet
        for (Boat boat : fleet) {
            System.out.printf("%-8s %-20s %4d %-10s %4d' : Paid $%10.2f : Spent $%10.2f\n",
                    boat.getType(), boat.getName(), boat.getYearOfManufacture(), boat.getMakeModel(),
                    boat.getLength(), boat.getPurchasePrice(), boat.getMaintenanceExpenses());
            totalPaid += boat.getPurchasePrice();
            totalSpent += boat.getMaintenanceExpenses();
        }
        System.out.printf("Total                                         : Paid $%10.2f : Spent $%10.2f\n", totalPaid, totalSpent);

    }// end of the printInventory method

    /**
     * This is the addBoat method;
     * @param csvData;
     */

    public void addBoat(String csvData) {
        int yearOfManufacture;
        int length;
        double purchasePrice;
        double expenses = 0.0;
        String makeModel;

        String[] data = csvData.split(","); // Splitting the CSV data
        if (data.length >= 6) { // Making sure there's enough data fields
            try {
                Boat.BoatType type = Boat.BoatType.valueOf(data[0].toUpperCase());
                String name = data[1];
                yearOfManufacture = Integer.parseInt(data[2]);
                makeModel = data[3];
                length = Integer.parseInt(data[4]);
                purchasePrice = Double.parseDouble(data[5]);

                if (data.length == 7) {
                    expenses = Double.parseDouble(data[6]);
                }

                 fleet.add(new Boat(type, name, yearOfManufacture, makeModel, length, purchasePrice, expenses));
                System.out.println("Boat added successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error adding boat: Invalid data format.");
            }
        } else {
            System.out.println("Error adding boat: Incorrect number of fields.");
        }
    }// end of the addBoat method

    /**
     * This is the removeBoat method
     * @param name;
     */

    public void removeBoat(String name) {
        for (Boat boat : fleet) {
            if (boat.getName().equalsIgnoreCase(name)) {
                fleet.remove(boat);
                return;
            }
        }
        System.out.println("Cannot find boat " + name);
    }// end of the removeBoat method

    /**
     * This is the expenseRequest method
     * @param name;
     * @param amount;
     */

    public void expenseRequest(String name, double amount) {


        boolean boatFound = false;
        double remaining;
        double totalExpenses;

        for (Boat boat : fleet) {
            if (boat.getName().trim().equalsIgnoreCase(name.trim())) {
                boatFound = true;
                // Checking if the boat can afford the expense
                if (boat.canSpend(amount)) {
                    boat.addExpense(amount);
                    totalExpenses = boat.getMaintenanceExpenses();
                    System.out.printf("Expense authorized, $%.2f spent.\n", totalExpenses);
                } else {
                    remaining = boat.getPurchasePrice() - boat.getMaintenanceExpenses();
                    System.out.printf("Expense not permitted, only $%.2f left to spend.\n", remaining);
                }
                break;
            }
        }

        if (!boatFound) {
            System.out.println("Cannot find boat " + name);
        }
    }// end of the expense request method



}// end of the FleetManagement class