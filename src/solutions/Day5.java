package solutions;

import java.util.ArrayList;
import java.util.Stack;

public class Day5 {
    // DAY 5 : rearranging crate stacks
    public static void p1(){
        // problem:
        // crates are stored in stacks and need to be rearranged
        // input includes original stacks and rearrangemnt steps
        // which crates will end up on top at the end?

        // input:
        // [D]
        // [N] [C]
        // [Z] [M] [P]
        //  1   2   3
        // 
        // move 1 from 2 to 1
        // move 3 from 1 to 3
        // move 2 from 2 to 1
        // move 1 from 1 to 2

        String[] lines = Common.FileToLines("inputs/d5.input");

        if(lines == null){
            System.out.println("Day5.p1(): STRING ARRAY IS NULL");
            return;
        }

        // find index of bottom of diagram
        int bottomIndex = -1;
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].length() == 0){
                bottomIndex = i-1;
            }
        }

        // read stack diagram
        int numStacks = lines[bottomIndex].split("   ").length;
        ArrayList<Stack<String>> stacks = new ArrayList<Stack<String>>(numStacks);
        for(int i = 0; i < numStacks; i++){
            stacks.add(new Stack<String>());
        }

        for(int i = bottomIndex-1; i >= 0; i--) {
            for(int s = 0; s < numStacks; s++){
                String crateChar = lines[i].substring((s*4) + 1, (s*4) + 2);
                if(!crateChar.equals(" ")){
                    stacks.get(s).push(crateChar + "");
                }
            }
        }

        // perform moves
        for(int i = bottomIndex+2; i < lines.length; i++){
            String[] tokens = lines[i].split(" ");
            int count = Integer.parseInt(tokens[1]);
            int firstCrate = Integer.parseInt(tokens[3])-1;
            int secondCrate = Integer.parseInt(tokens[5])-1;

            for(int m = 0; m < count; m++){
                stacks.get(secondCrate).push(
                    stacks.get(firstCrate).pop());
            }
        }

        String answer = "";
        for(int i = 0; i < stacks.size(); i++) {
            answer += stacks.get(i).peek();
        }

        System.out.println("d5p1:  Top crates = " + answer);

    }

    // DAY 5 : rearranging stacked crates 2
    public static void p2() {
        // PROBLEM:
        // crates are stored in stacks and need to be rearranged
        // input inclides original stacks and rearrangemnt steps
        // which crates will end up on top at the end?
        // crates move in chunks and keep their order

        // input:
        // [D]
        // [N] [C]
        // [Z] [M] [P]
        //  1   2   3
        // 
        // move 1 from 2 to 1
        // move 3 from 1 to 3
        // move 2 from 2 to 1
        // move 1 from 1 to 2

        String[] lines = Common.FileToLines("inputs/d5.input");

        if(lines == null){
            System.out.println("Day5.p2(): STRING ARRAY IS NULL");
            return;
        }

        // find index of bottom of diagram
        int bottomIndex = -1;
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].length() == 0){
                bottomIndex = i-1;
            }
        }

        // read stack diagram
        int numStacks = lines[bottomIndex].split("   ").length;
        ArrayList<Stack<String>> stacks = new ArrayList<Stack<String>>(numStacks);
        for(int i = 0; i < numStacks; i++){
            stacks.add(new Stack<String>());
        }

        for(int i = bottomIndex-1; i >= 0; i--) {
            for(int s = 0; s < numStacks; s++){
                char crateChar = lines[i].charAt(s*4 + 1);
                if(crateChar != ' '){
                    stacks.get(s).push(crateChar + "");
                }
            }
        }

        // perform moves
        for(int i = bottomIndex+2; i < lines.length; i++){
            String[] tokens = lines[i].split(" ");
            int count = Integer.parseInt(tokens[1]);
            int firstCrate = Integer.parseInt(tokens[3])-1;
            int secondCrate = Integer.parseInt(tokens[5])-1;

            Stack<String> tempStack = new Stack<String>();
            for(int m = 0; m < count; m++){
                tempStack.push(
                    stacks.get(firstCrate).pop());
            }
            for(int m = 0; m < count; m++){
                stacks.get(secondCrate).push(
                    tempStack.pop());
            }
        }

        String answer = "";
        for(int i = 0; i < stacks.size(); i++) {
            answer += stacks.get(i).peek();
        }

        System.out.println("d5p2:  Top crates = " + answer);
    }
}
