package solutions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Day11 {
    private static class Monkey {
        public enum Operations {
            ADD,
            MULTIPLY,
            ADD_OLD,
            MULTIPLY_OLD
        }
        
        int number;
        Queue<Long> items;
        Operations operation;
        int operationNumber;
        int testNumber;
        int trueInt;
        int falseInt;

        int inspectionCount;

        public Monkey (String[] input) {
            this.items = new LinkedList<Long>();
            this.inspectionCount = 0;

            // get number
            String[] tokens = input[0].split(" ");
            if(tokens[0].equals("Monkey")) {
                this.number = Integer.parseInt(tokens[1].substring(0, 1));
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }

            // get starting items
            tokens = input[1].split(":");
            if(tokens[0].equals("  Starting items")) {
                tokens = tokens[1].split(", ");
                for (String token : tokens) {
                    this.items.add(Long.parseLong(token.replaceAll("\\s+", "")));
                }
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }

            // get operation
            tokens = input[2].split(":");
            if(tokens[0].equals("  Operation")) {
                tokens = tokens[1].split(" ");
                if(tokens[5].equals("old")) {
                    this.operation = tokens[4].equals("+") ? Operations.ADD_OLD : Operations.MULTIPLY_OLD;
                }
                else {
                    this.operation = tokens[4].equals("+") ? Operations.ADD : Operations.MULTIPLY;
                    this.operationNumber = Integer.parseInt(tokens[5]);
                }
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }

            // get test value
            tokens = input[3].split(":");
            if(tokens[0].equals("  Test")) {
                tokens = tokens[1].split(" ");
                this.testNumber = Integer.parseInt(tokens[tokens.length-1]);
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }

            // get true partner
            tokens = input[4].split(":");
            if(tokens[0].equals("    If true")) {
                tokens = tokens[1].split(" ");
                this.trueInt = Integer.parseInt(tokens[tokens.length-1]);
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }

            // get false partner       
            tokens = input[5].split(":");
            if(tokens[0].equals("    If false")) {
                tokens = tokens[1].split(" ");
                this.falseInt = Integer.parseInt(tokens[tokens.length-1]);
            }
            else {
                System.out.println("d11:   UNEXPECTED TOKEN '" + tokens[0] + "'");
            }
        }

        public void inspectItems(Monkey truePartner, Monkey falsePartner, boolean divide, int lcm) {
            while(this.items.size() > 0) {
                long item = this.items.remove();
                this.inspectionCount++;

                // item is inspected
                switch(this.operation){
                    case ADD:
                        item += this.operationNumber;
                        break;
                    case MULTIPLY:
                        item *= this.operationNumber;
                        break;
                    case ADD_OLD:
                        item += item;
                        break;
                    case MULTIPLY_OLD:
                        item *= item;
                        break;
                }

                // you relax / "keep worry levels under control"
                if(divide)
                    item /= 3;
                else {
                    item %= lcm;
                }

                // item is thrown
                if(item % testNumber == 0){
                    truePartner.items.add(item);
                }
                else {
                    falsePartner.items.add(item);
                }
            }
        }
    }


    public static void run() {
        String[] lines = Common.FileToLines("inputs/d11.input");
        if(lines == null) {
            System.out.println("d11:   INPUT FILE NOT FOUND");
            return;
        }

        // count monkeys
        int totalMonkeys = 0;
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].split(" ")[0].equals("Monkey")){
                totalMonkeys++;
                i += 5;
            }
        }

        // create monkeys
        Monkey[] monkeys = new Monkey[totalMonkeys];
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].split(" ")[0].equals("Monkey")){
                Monkey newMonkey = new Monkey(Arrays.copyOfRange(lines, i, i+6));
                monkeys[newMonkey.number] = newMonkey;
            }
        }

        // simmulate 20 rounds with /3
        for(int r = 0; r < 20; r++) {
            for (Monkey monkey : monkeys) {
                monkey.inspectItems(monkeys[monkey.trueInt], monkeys[monkey.falseInt], true, 0);
            }
        }

        /* debug print
        for (Monkey monkey : monkeys) {
            System.out.println("Monkey " + ((Integer)monkey.number).toString() + ": " + monkey.items.toString() + 
            " inspected " + ((Integer)monkey.inspectionCount).toString() + " items");
        }
        //*/

        // find most active monkeys
        long highestCount = 0;
        long secondHighestCount = 0;
        for (Monkey monkey : monkeys) {
            if(monkey.inspectionCount > highestCount){
                secondHighestCount = highestCount;
                highestCount = monkey.inspectionCount;
            }
            else if(monkey.inspectionCount > secondHighestCount) {
                secondHighestCount = monkey.inspectionCount;
            }
        }
        long monkeyBuissness = highestCount * secondHighestCount;

        System.out.println("d11p1: Monkey Buisness = " + ((Long)monkeyBuissness).toString());


        // PART 2


        // re-create monkeys
        monkeys = new Monkey[totalMonkeys];
        for(int i = 0; i < lines.length; i++) {
            if(lines[i].split(" ")[0].equals("Monkey")){
                Monkey newMonkey = new Monkey(Arrays.copyOfRange(lines, i, i+6));
                monkeys[newMonkey.number] = newMonkey;
            }
        }

        // figure out lcm to "keep worry reasonable"
        int lcm = monkeys[0].testNumber;
        for(int i = 1; i < totalMonkeys; i++) {
            lcm = (lcm * monkeys[i].testNumber)/gcd(monkeys[i].testNumber, lcm);
        }

        // simmulate 10000 rounds no /3
        for(int r = 0; r < 10000; r++) {
            for (Monkey monkey : monkeys) {
                monkey.inspectItems(monkeys[monkey.trueInt], monkeys[monkey.falseInt], false, lcm);
            }
        }

        // find most active monkeys
        highestCount = 0;
        secondHighestCount = 0;
        for (Monkey monkey : monkeys) {
            if(monkey.inspectionCount > highestCount){
                secondHighestCount = highestCount;
                highestCount = monkey.inspectionCount;
            }
            else if(monkey.inspectionCount > secondHighestCount) {
                secondHighestCount = monkey.inspectionCount;
            }
        }
        monkeyBuissness = highestCount * secondHighestCount;

        System.out.println("d11p2: Monkey Buisness = " + ((Long)monkeyBuissness).toString());
    }

    private static int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }
}
