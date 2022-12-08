package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Common {
    public static String[] FileToLines(String filename) {
        ArrayList<String> array = new ArrayList<String>();

        try {
            File file = new File(filename);
            try (Scanner scanner = new Scanner(file)) {
                while(scanner.hasNext()){
                    array.add(scanner.nextLine());
                }
            }

            String[] tmp = new String[1];
            return array.toArray(tmp);

        } catch (FileNotFoundException e) {
            System.out.println("Common.FileToLines(): FILE NOT FOUND");
            return null;
        }
    }
}
