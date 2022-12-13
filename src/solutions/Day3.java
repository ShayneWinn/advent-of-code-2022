package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Day3 {
	// DAY 3 : find common items between each half of each rucksack
	public static void p1() {
		// Rucksacks packed with items (a-z + A-Z)
		// Equal numbers on both sides
		// Find single item overlap (both sides contain the character)

		int priorties = 0;
		try {
			File input = new File("inputs/d3.input");
			Scanner scanner = new Scanner(input);

			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();

				String[] strings = new String[2];

				strings[0] = line.substring(0, line.length()/2);
				strings[1] = line.substring(line.length()/2, line.length());

				priorties += findIntersection(strings);

				/* first solution
				int[] count = new int[52];
				for (char item : first.toCharArray()) {
					item -= item < 'a' ? 38 : 'a' - 1;

					count[item-1] += 1;
				}

				for (int item : second.toCharArray()) {
					item -= item < 'a' ? 38 : 'a' - 1;

					if(count[item-1] != 0){
						priorties += item;
						break;
					}
				}
				*/
			}
		} catch (FileNotFoundException e) {
			System.out.println("d3p1:  INPUT FILE NOT FOUND");
			return;
		}

		System.out.println(String.format("d3p1:  Total Priority = %d", priorties));

	}

	// DAY 3 : find common item between each group of three rucksacks
	public static void p2() {
		// each group of three "rucksacks" contains ONE common item. 
		// intersection of n strings

		int priorities = 0;
		try {
			File input = new File("inputs/d3.input");
			Scanner scanner = new Scanner(input);

			while(scanner.hasNextLine()) {
				try{
					String[] strings = new String[3];
					strings[0] = scanner.nextLine();
					strings[1] = scanner.nextLine();
					strings[2] = scanner.nextLine();

					char common = findIntersection(strings);

					priorities += common;
				}
				catch (NoSuchElementException e) { // ran out of lines
					System.out.println("Number of lines in input was not a multiple of three");
					e.printStackTrace();
				}
			
			}

		} catch (FileNotFoundException e) {
			System.out.println("d3p2:  INPUT FILE NOT FOUND");
			return;
		}

		System.out.println(String.format("d3p2:  Total = %d", priorities));
	}

	// finds the first common character in any number of strings; returns 0 if no character found
	// only works on strings containing ONLY a-z & A-Z
	// returns "value" of character. ie a-z = 1-26 & A-Z = 27-52
	private static char findIntersection(String[] strings) {

		int[] counts = new int[52]; // holds count information for all strings

		// Go through each character of each string; if it was found in all previous strings increment the count.
		// All characters with a count equal to the number of strings was found in every string.
		for(int i = 0; i < strings.length; i++){
			for (char character : strings[i].toCharArray()) {
				int index = character - (character < 'a' ? 39 : 'a'); // a-z = 0-25; A-Z = 26-51
				if(counts[index] == i)
					counts[index]++;
			}
		}

		for (int i = 0; i < counts.length; i++) {
			if(counts[i] == strings.length){
				return (char)(i+1);
			}
			
		}
		return 0;
	}
}
