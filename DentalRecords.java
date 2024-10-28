import java.util.Scanner;
/**
 * This is the DentalRecords class
 * @author Vanna Walker
 */
public class DentalRecords {

    private static final Scanner keyboard = new Scanner(System.in);
    private static final int MAX_FAMILY_MEMBERS = 6;
    private static final int MAX_TEETH = 8;
    private static final char INCISOR = 'I';
    private static final char BICUSPID = 'B';
    private static final char MISSING = 'M';

    /**
     * This is the main method
     * @param args;
     */

    public static void main(String[] args) {
        // Storing family member names and teeth records in arrays.


        // Welcome message being displayed.
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        String[] familyNames = new String[MAX_FAMILY_MEMBERS];
        char[][][] toothRecords = new char[MAX_FAMILY_MEMBERS][2][MAX_TEETH];
        int familySize = getValidFamilySize();
        int index;

        // Getting family member teeth records and names.
        for (index = 0; index < familySize; index++) {
            System.out.print("Please enter the name for family member " + (index + 1) + "   : ");
            familyNames[index] = keyboard.nextLine();
            toothRecords[index][0] = getValidTeethRow("uppers", familyNames[index]);
            toothRecords[index][1] = getValidTeethRow("lowers", familyNames[index]);
        }

        // Main menu loop
        char choice;
        do {
            choice = getMenuChoice();
            switch (Character.toUpperCase(choice)) {
                case 'P':
                    printFamilyTeethRecords(familyNames, toothRecords, familySize);
                    break;
                case 'E':
                    extractTooth(familyNames, toothRecords, familySize);
                    break;
                case 'R':
                    reportRootCanalIndex(familySize, toothRecords);
                    break;
                case 'X':
                    System.out.println("Exiting the Floridian Tooth Records :-)");
                    break;
                default:
                    System.out.print("Invalid menu option, try again: ");
            }
        } while (choice != 'X');
    }// end of the main method

    /**
     * This is the getMenuChoice method
     * @return size
     */

    // Retrieving the user's menu choice and returning it as a character.
    public static char getMenuChoice() {
        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
        String input = keyboard.nextLine().trim().toUpperCase();

        while (!input.equals("P") && !input.equals("E") && !input.equals("R") && !input.equals("X")) {
            System.out.print("Invalid menu option, try again: ");
            input = keyboard.nextLine().trim().toUpperCase();
        }
        return input.charAt(0);
    }// end of the getMenuChoice method

    /**
     * This is the getValidFamily size method
     * @return size
     */

    // Validating the number of family members ensuring its within the limit.
    public static int getValidFamilySize() {
        int size = 0;
        System.out.print("Please enter number of people in the family : ");

        // Loop until a valid size is entered
        while (size < 1 || size > MAX_FAMILY_MEMBERS) {
            if (keyboard.hasNextInt()) {
                size = keyboard.nextInt();
                keyboard.nextLine();
            } else {
                keyboard.next();
            }
            if (size < 1 || size > MAX_FAMILY_MEMBERS) {
                System.out.print("Invalid number of people, try again         : ");
            }
        }
        return size;
    }// end of the getValidFamilySize method

    /**
     * This is the getValidTeethRow method
     * @param layer;
     * @param name;
     * @return teeth
     */

    // Getting and validating teeth row input for the upper or lower rows of each family member.
    public static char[] getValidTeethRow(String layer, String name) {
        char[] teeth = new char[MAX_TEETH];
        boolean validInput;
        String teethInput;
        int index;
        char t;

        System.out.print("Please enter the " + layer + " for " + name + "       : ");
        do {
            validInput = true;
            teethInput = keyboard.nextLine().toUpperCase();

            if (teethInput.length() > MAX_TEETH) {
                System.out.print("Too many teeth, try again: ");
                validInput = false;
            } else {
                for (index = 0; index < teethInput.length(); index++) {
                    t = teethInput.charAt(index);
                    if (t != INCISOR && t != BICUSPID && t != MISSING) {
                        System.out.print("Invalid teeth types, try again: ");
                        validInput = false;
                        break;
                    }
                    teeth[index] = t;
                }
            }
        } while (!validInput);
        return teeth;
    }// end of the getValidTeethRow method

    /**
     * This is the printFamilyTeethRecords method
     * @param familyNames;
     * @param toothRecords;
     * @param familySize;
     */

    // Printing teeth records for each family member.
    public static void printFamilyTeethRecords(String[] familyNames, char[][][] toothRecords, int familySize) {
        int index;
        for (index = 0; index < familySize; index++) {
            System.out.println(familyNames[index]);
            System.out.print("  Uppers: ");
            printTeethRow(toothRecords[index][0]);
            System.out.println();
            System.out.print("  Lowers: ");
            printTeethRow(toothRecords[index][1]);
            System.out.println();
        }
    }// end of the printFamilyTeethRecords method

    /**
     * This is the printTeethRow method
     * @param teeth;
     */

    // Printing teeth row with indices.
    public static void printTeethRow(char[] teeth) {
        int index;
        for (index = 0; index < teeth.length; index++) {
            if (teeth[index] != 0) {
                System.out.print((index + 1) + ":" + teeth[index] + " ");
            }
        }
    }// end of the printTeethRow method

    /**
     * This is the getFamilyMemberIndex method
     * @param familyNames;
     * @param familySize;
     * @param member;
     * @return -1
     */

    // Getting the index of a family member in the array, returning -1 if not found.
    public static int getFamilyMemberIndex(String[] familyNames, int familySize, String member) {
        int index;
        for (index = 0; index < familySize; index++) {
            if (familyNames[index].equalsIgnoreCase(member)) {
                return index;
            }
        }
        return -1;
    }// end of the getFamilyMemberIndex method

    /**
     * This is the extractTooth method
     * @param familyNames;
     * @param toothRecords;
     * @param familySize;
     */

    //Extracting a tooth from a family member's record.
    public static void extractTooth(String[] familyNames, char[][][] toothRecords, int familySize) {
        System.out.print("Which family member      : ");
        int familyMemberIndex;
        String member;
        int layerIndex = -1;
        char layer;
        int toothNum;
        int maxTooth;

        do {
            member = keyboard.nextLine().trim();
            familyMemberIndex = getFamilyMemberIndex(familyNames, familySize, member);
            if (familyMemberIndex == -1) {
                System.out.print("Invalid family member, try again      : ");
            }
        } while (familyMemberIndex == -1);

        System.out.print("Which tooth layer (U)pper or (L)ower: ");
        do {
            layer = keyboard.nextLine().trim().toUpperCase().charAt(0);
            if (layer != 'U' && layer != 'L') {
                System.out.print("Invalid layer, try again : ");
            }
        } while (layer != 'U' && layer != 'L');

        layerIndex = (layer == 'U') ? 0 : 1;
        maxTooth = toothRecords[familyMemberIndex][layerIndex].length;
        System.out.print("Which tooth number: ");
        toothNum = keyboard.nextInt() - 1;
        keyboard.nextLine();

        while (toothNum < 0 || toothNum >= maxTooth) {
            System.out.print("Invalid tooth number, try again: ");
            toothNum = keyboard.nextInt() - 1;
            keyboard.nextLine();
        }

        while (toothRecords[familyMemberIndex][layerIndex][toothNum] == MISSING) {
            System.out.print("Missing tooth, try again : ");
            toothNum = keyboard.nextInt() - 1;
            keyboard.nextLine();
            while (toothNum < 0 || toothNum >= maxTooth) {
                System.out.print("Invalid tooth number, try again: ");
                toothNum = keyboard.nextInt() - 1;
                keyboard.nextLine();
            }
        }
        toothRecords[familyMemberIndex][layerIndex][toothNum] = MISSING;
        System.out.println("Tooth extracted.");
    }// end of the extractTooth method

    /**
     * This is the reportRootCanalIndex method
     * @param familySize;
     * @param toothRecords;
     */

    // Computing and displaying root canal indices for each family member.
    public static void reportRootCanalIndex(int familySize, char[][][] toothRecords) {
        int I = 0;
        int B = 0;
        int M = 0;
        int incisors;
        int bicuspids;
        int missing;
        double root1;
        double root2;
        double discriminant;
        char tooth;
        int index;

        for (index = 0; index < familySize; index++) {
            incisors = countTeeth(toothRecords[index][0], INCISOR) + countTeeth(toothRecords[index][1], INCISOR);
            bicuspids = countTeeth(toothRecords[index][0], BICUSPID) + countTeeth(toothRecords[index][1], BICUSPID);
            missing = -countTeeth(toothRecords[index][0], MISSING) - countTeeth(toothRecords[index][1], MISSING);

            I += incisors;
            B += bicuspids;
            M += missing;
        }

        discriminant = (B * B) - 4 * I * -M;
        root1 = (-B + Math.sqrt(discriminant)) / (2 * I);
        root2 = (-B - Math.sqrt(discriminant)) / (2 * I);
        System.out.printf("One root canal at%10.2f%n", root1);
        System.out.printf("Another root canal at%10.2f%n", root2);
    }// end of the reportRootCanalIndex method

    /**
     * This is the countTeeth method
     * @param teethRow;
     * @param toothType;
     * @return count
     */

    public static int countTeeth(char[] teethRow, char toothType) {
        int count = 0;
        for (char tooth : teethRow) {
            if (tooth == toothType) {
                count++;
            }
        }
        return count;
    }// end of the countTeeth method
}// end of the DentalRecords class
