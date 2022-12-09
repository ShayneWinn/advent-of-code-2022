package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4 {
    // Day 4 : count number of assignments that fully contain an assignment
    public static void p1() {

        int total = 0;

        try {
            File file = new File("inputs/d4.input");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] assignments = line.split(",");
                String[] assignment1 = assignments[0].split("-");
                String[] assignment2 = assignments[1].split("-");

                int a11 = Integer.parseInt(assignment1[0]);
                int a12 = Integer.parseInt(assignment1[1]);
                int a21 = Integer.parseInt(assignment2[0]);
                int a22 = Integer.parseInt(assignment2[1]);

                if (a11 <= a21 && a12 >= a22)
                    total++;
                else if (a21 <= a11 && a22 >= a12)
                    total++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("d4p1:  FILE NOT FOUND");
            return;
        }

        System.out.println(String.format("d4p1:  Total total overlaps = %d", total));
    }

    // DAY 4 : count the number of assignmeents that overlap eachother at all
    public static void p2() {
        
        int total = 0;

        try {
            File file = new File("inputs/d4.input");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] assignments = line.split(",");
                String[] assignment1 = assignments[0].split("-");
                String[] assignment2 = assignments[1].split("-");

                int a11 = Integer.parseInt(assignment1[0]);
                int a12 = Integer.parseInt(assignment1[1]);
                int a21 = Integer.parseInt(assignment2[0]);
                int a22 = Integer.parseInt(assignment2[1]);

                if((a11 >= a21 && a11 <= a22) || (a12 >= a21 && a12 <= a22)) // a1x sits inside a2
                    total++;
                else if((a21 >= a11 && a21 <= a12) || (a22 >= a11 && a22 <= a12)) // a2x sits inside a1
                    total++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("d4p2:  FILE NOT FOUND");
            return;
        }

        System.out.println(String.format("d4p1:  Total partial overlaps = %d", total));
    }
}
