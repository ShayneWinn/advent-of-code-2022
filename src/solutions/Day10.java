package solutions;

public class Day10 {
    public static void run(){
        String[] lines = Common.FileToLines("inputs/d10.input");
        if(lines == null){
            System.out.println("d10p1: STRING ARRAY IS NULL");
        }

        int xReg = 1;
        int totalStrength = 0;
        char[][] crtScreen = new char[6][40];
        int counter = 1;
        int cycles = 1;
        int pc = 0;
        while(pc < lines.length) {
            String[] instruction = lines[pc].split(" ");

            if((cycles - 20) % 40 == 0){
                totalStrength += cycles * xReg;
                //System.out.println(String.format("cycle %d : %d", cycles, cycles * xReg));
            }

            int crtXpos = (cycles-1) % 40;
            int crtYpos = Math.floorDiv(cycles - 1, 40);
            if(crtXpos >= xReg - 1 && crtXpos <= xReg + 1){
                crtScreen[crtYpos][crtXpos] = '#';
            }
            else {
                crtScreen[crtYpos][crtXpos] = '.';
            }

            switch(instruction[0]){
                case "addx":
                    if(counter == 0){
                        xReg += Integer.parseInt(instruction[1]);
                        counter = 1;
                        ++pc;
                    }
                    else {
                        --counter;
                    }
                break;

                case "noop":
                    ++pc;
                break;
            }

            ++cycles;
        }

        System.out.println(String.format("d10p1: Total signal Strength = %d", totalStrength));
        System.out.println("d10p2:");
        for(char[] row : crtScreen){
            for(char ch : row) {
                System.out.print(ch);
            }
            System.out.println("");
        }
    }
}
