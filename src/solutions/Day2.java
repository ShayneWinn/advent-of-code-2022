package solutions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
        // DAY 2 : calculate score depending on outcome of rock-paper-scissors games
        public static void p1() {
            // Input looks like:
            // A Y
            // B X
            // C Z
            // where the first character is the opponents move and the second is your move
            // A = Rock, B = Paper, C = Scissors
            // X = Rock, Y = Paper, Z = Scissors
            // Scoring works by adding the outcome value to the shape you played
            // Win = 6, Draw = 3, Lose = 0  +  Rock = 1, Paper = 2, Scissors = 3
    
            int score = 0;
            try {
                File input = new File("inputs/d2.input");
                Scanner scanner = new Scanner(input);
    
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
    
                    String[] round = line.split(" ");
                    switch (round[0]) {
                        case "A": // rock
                            if (round[1].equals("X")) // rock
                                score += 3;
                            else if (round[1].equals("Y")) // paper
                                score += 6;
                            else // scissors
                                score += 0;
                            break;
    
                        case "B": // paper
                            if (round[1].equals("Y")) // paper
                                score += 3;
                            else if (round[1].equals("Z")) // scissors
                                score += 6;
                            else // rock
                                score += 0;
                            break;
    
                        case "C": // scissors
                            if (round[1].equals("Z")) // scissors
                                score += 3;
                            else if (round[1].equals("X")) // rock
                                score += 6;
                            else // paper
                                score += 0;
    
                    }
                    switch (round[1]) {
                        case "X": // rock
                            score += 1;
                            break;
                        case "Y": // paper
                            score += 2;
                            break;
                        case "Z": // scissors
                            score += 3;
                            break;
                    }
                }
    
            } catch (FileNotFoundException e) {
                System.out.println("d2p1:  FILE NOT FOUND");
                return;
            }
    
            System.out.println(String.format("d2p1:  Total Score: %d", score));
        }
    
        // DAY 2 : calculate score depending on outcome of rock-paper-scissors games
        public static void p2() {
            // Input looks like:
            // A Y
            // B X
            // C Z
            // where the first character is the opponents move and the second is if you should win, lose, or draw
            // A = Rock, B = Paper, C = Scissors
            // X = Lose, Y = Draw, Z = Win
            // Scoring works by adding the outcome value to the shape you played
            // Win = 6, Draw = 3, Lose = 0  +  Rock = 1, Paper = 2, Scissors = 3
    
            int score = 0;
            try {
                File input = new File("inputs/d2.input");
                Scanner scanner = new Scanner(input);
    
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
    
                    String[] round = line.split(" ");
                    switch(round[0]) {
                        case "A": // rock
                            if(round[1].equals("X")) // lose
                                score += 0 + 3;
                            else if(round[1].equals("Y")) // draw
                                score += 3 + 1;
                            else // win
                                score += 6 + 2;
                            break;
                        case "B": // paper
                            if(round[1].equals("X")) // lose
                                score += 0 + 1;
                            else if(round[1].equals("Y")) // draw
                                score += 3 + 2;
                            else // win
                                score += 6 + 3;
                            break;
                        case "C": // scissors
                            if(round[1].equals("X")) // lose
                                score += 0 + 2;
                            else if(round[1].equals("Y")) // draw
                                score += 3 + 3;
                            else // win
                                score += 6 + 1;
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("d2p2:  FILE NOT FOUND");
                return;
            }
    
            System.out.println(String.format("d2p2:  Total Score: %d", score));
        }
}
