package solutions;

public class Day8 {
    public static void run(){
        // /=========\
        // |  SETUP  |
        // \=========/

        // input
        String[] inputLines = Common.FileToLines("inputs/d8.input");
        if(inputLines == null){
            System.out.println("d8:    INPUT FILE NOT FOUND");
            return;
        }
        char[][] treeGrid = new char[inputLines.length][inputLines[0].length()];
        for(int i = 0; i < inputLines.length; i++){
            treeGrid[i] = inputLines[i].toCharArray();
        }

        // /==========\
        // |  PART 1  |
        // \==========/
        boolean[][] visibleGrid = new boolean[treeGrid.length][treeGrid[0].length];

        // scan for trees
        // horizontal
        for(int y = 0; y < treeGrid.length; y++){
            char highestRL = '0' - 1;
            char highestLR = '0' - 1;
            for(int i = 0; i < treeGrid[0].length; i++){
                // right to left
                if(treeGrid[y][i] > highestRL){
                    visibleGrid[y][i] = true;
                    highestRL = treeGrid[y][i];
                }

                // left to right
                int x = treeGrid[0].length - i - 1;
                if(treeGrid[y][x] > highestLR){
                    visibleGrid[y][x] = true;
                    highestLR = treeGrid[y][x];
                }
            }
        }

        // vertical
        for(int x = 0; x < treeGrid[0].length; x++){
            char highestTB = '0' - 1;
            char highestBT = '0' - 1;
            for(int i = 0; i < treeGrid.length; i++){
                // top to bottom
                if(treeGrid[i][x] > highestTB){
                    visibleGrid[i][x] = true;
                    highestTB = treeGrid[i][x];
                }

                // bottom to top
                int y = treeGrid.length - i - 1;
                if(treeGrid[y][x] > highestBT){
                    visibleGrid[y][x] = true;
                    highestBT = treeGrid[y][x];
                }
            }
        }

        /* debug prints
        for (char[] row : treeGrid){
            for(char ch : row){
                System.out.print(ch);
            }
            System.out.println("");
        }

        for (boolean[] row : visibleGrid){
            for(boolean bo : row){
                System.out.print(bo?'1':'0');
            }
            System.out.println("");
        }
        //*/

        // count visible trees
        int count = 0;
        for(boolean[] row : visibleGrid){
            for(boolean bo : row){
                if(bo) ++count;
            }
        }
        System.out.println(String.format("d8p1:  Total visible trees = %d", count));

        // /==========\
        // |  PART 2  |
        // \==========/
        int[][] scenicGrid = new int[treeGrid.length][treeGrid[0].length];
        for(int y = 0; y < treeGrid.length; y++){
            for(int x = 0; x < treeGrid[y].length; x++){
                int tally = 0;
                int score = 1;
                // right
                for(int i = x+1; i < treeGrid[y].length; i++){
                    ++tally;
                    if(treeGrid[y][i] >= treeGrid[y][x]){
                        break;
                    }
                }
                score *= tally;
                tally = 0;
                // left
                for(int i = x-1; i >= 0; i--){
                    ++tally;
                    if(treeGrid[y][i] >= treeGrid[y][x]){
                        break;
                    }
                }
                score *= tally;
                tally = 0;
                // up
                for(int i = y-1; i >= 0; i--){
                    ++tally;
                    if(treeGrid[i][x] >= treeGrid[y][x]){
                        break;
                    }
                }
                score *= tally;
                tally = 0;
                // down
                for(int i = y+1; i < treeGrid.length; i++){
                    ++tally;
                    if(treeGrid[i][x] >= treeGrid[y][x]){
                        break;
                    }
                }
                score *= tally;
                scenicGrid[y][x] = score;
            }
        }

        int currentBest = 0;
        for(int[] row : scenicGrid){
            for(int score : row){
                if(score > currentBest){
                    currentBest = score;
                }
            }
        }

        System.out.println(String.format("d8p2:  Best scenic score = %d", currentBest));
    }
}
