package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;

public class Day1{
        // DAY 1 : find the largest calorie count in a list of calorie counts
        public static void p1() {
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
                System.out.println("d1p1:  INPUT FILE NOT FOUND");
                return;
            }
    
            System.out.println("d1p1:  Maximum Calorie Count = " + maxCalorieCount);
        }
    
        // DAY 1 : find the largest three calorie counts and add them together
        public static void p2() {
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
                System.out.println("d1p2:  INPUT FILE NOT FOUND");
                return;
            }
    
            calorieCounts.sort(Collections.reverseOrder());
    
            System.out.println("d1p2:  Top Three Calorie Counts:");
            System.out.println(String.format(
                    "       %d, %d, %d", calorieCounts.get(0), calorieCounts.get(1), calorieCounts.get(2)));
            System.out.println(String.format(
                    "       Total = %d", (calorieCounts.get(0) + calorieCounts.get(1) + calorieCounts.get(2))));
        }
}