import java.io.Serializable;

/**
 * This is the Boat class
 * @author Vanna Walker
 */


public class Boat implements Serializable{
    public enum BoatType { SAILING, POWER }

    private BoatType type;
    private String name;
    private int yearOfManufacture;
    private String makeModel;
    private int length;
    private double purchasePrice;
    private double expenses;

    /**
     * This is the Boat method
     * @param type;
     * @param name;
     * @param yearOfManufacture;
     * @param makeModel;
     * @param length;
     * @param purchasePrice;
     * @param expenses;
     */

    public Boat(BoatType type, String name, int yearOfManufacture, String makeModel, int length, double purchasePrice, double expenses) {
        this.type = type;
        this.name = name;
        this.yearOfManufacture = yearOfManufacture;
        this.makeModel = makeModel;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.expenses = expenses;
    }

    /**
     * This is the canSpend method
     * @param amount;
     * @return (expenses + amount) <= purchasePrice
     */

    // Ensure spending is within budget
    public boolean canSpend(double amount) {

        return (expenses + amount) <= purchasePrice;
    }// end of  the canSpend

    /**
     * This is the addExpense method
     * @param amount;
     */

    // Increase expenses by the given amount
    public void addExpense(double amount) {
        this.expenses += amount;
    }// end of the addExpense method

    /**
     * This is the getYearOfManufacture method
     * @return yearOfManufacture
     */

    public int getYearOfManufacture(){
        return yearOfManufacture;
    }// end of the getYearOfManufacture method

    /**
     * This is the BoatType method
     * @return type
     */

    public BoatType getType() {

        return type;
    }// end of the getType method

    /**
     * This is the getName method
     * @return name
     */

    public String getName() {

        return name;
    }// end of the getName method

    /**
     * This is the getMaintenanceExpenses method
     * @return expenses
     */

    public double getMaintenanceExpenses() {

        return expenses;
    }// end of the getMaintenanceExpenses method

    /**
     * This is the getPurchasePrice method
     * @return purchasePrice
     */

    public double getPurchasePrice() {

        return purchasePrice;
    }// end of the getPurchasePrice method

    /**
     * This is the getLength method
     * @return length
     */

    public int getLength(){
        return length;
    }// end of the getLength method

    /**
     * This is the getMakeModel method
     * @return makeModel
     */

    public String getMakeModel(){
        return makeModel;
    }// end of the getMakeModel method

    /**
     * This is the toString method
     * @return String.format
     */

    // Formatting the boat into a readble string
    @Override
    public String toString() {
        return String.format(
                "%-8s %-20s %4d %-12s %3d' : Paid $%10.2f : Spent $%10.2f",
                type, name, yearOfManufacture, makeModel, length, purchasePrice, expenses
        );
    }// end of the toString method


}// end of the Boat class
