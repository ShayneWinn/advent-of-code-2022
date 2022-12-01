import java.io.File; // d1
import java.io.FileNotFoundException; // d1
import java.util.Collections; // d1
import java.util.Scanner; // d1
import java.util.ArrayList; // d1

public class Questions {

    public static void main(String[] args) {
        System.out.println("Hello World!\nWelcome to Advent of Code 2022!");

        day1p1();
        day1p2();
    }

    // find the largest calorie count in a list of calorie counts
    public static void day1p1() {
        int maxCalorieCount = 0;
        int currentCalorieCount = 0;
        try {
            File input = new File("inputs/d1.input");
            Scanner scanner = new Scanner(input);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // if we have reached the end of the elf or end of file
                if(line.length() == 0 || !scanner.hasNextLine()) {
                    // check to see if we have found the largest calorie count
                    if (currentCalorieCount > maxCalorieCount) {
                        maxCalorieCount = currentCalorieCount;
                    }
                    // reset the calorie count for the next elf
                    currentCalorieCount = 0;
                }
                else {
                    currentCalorieCount += Integer.parseInt(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("d1p1:  Maximum Calorie Count = " + maxCalorieCount);
    }

    // find the largest three calorie counts and add them together
    public static void day1p2() {
        ArrayList<Integer> calorieCounts = new ArrayList<Integer>();
        int currentCalorieCount = 0;

        try {
            File input = new File("inputs/d1.input");
            Scanner scanner = new Scanner(input);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // if we have reached the end of the elf or end of file
                if(line.length() == 0 || !scanner.hasNextLine()) {
                    // add calorie count to list
                    calorieCounts.add(currentCalorieCount);
                    // reset for next elf
                    currentCalorieCount = 0;
                }
                else {
                    currentCalorieCount += Integer.parseInt(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        calorieCounts.sort(Collections.reverseOrder());

        System.out.println("d1p2:  Top Three Calorie Counts:");
        System.out.println(String.format(
                "       %d, %d, %d", calorieCounts.get(0), calorieCounts.get(1), calorieCounts.get(2)));
        System.out.println(String.format(
                "       Total = %d", (calorieCounts.get(0) + calorieCounts.get(1) + calorieCounts.get(2))));
    }
}
