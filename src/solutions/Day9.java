package solutions;

import java.util.ArrayList;

public class Day9 {
    private static class RopeNode {
        int x;
        int y;
        RopeNode tail;

        public RopeNode(int x, int y, RopeNode tail){
            this.x = x;
            this.y = y;
            this.tail = tail;
        }

        public RopeNode(RopeNode tail){
            this(0, 0, tail);
        }

        public void move(int dx, int dy){
            this.x += dx;
            this.y += dy;

            if(this.tail != null){
                if(tail.x <= x - 2){ // left
                    if(tail.y < y)
                        tail.move(1, 1);
                    else if(tail.y > y)
                        tail.move(1, -1);
                    else
                        tail.move(1, 0);
                }
                if(tail.x >= x + 2){ // right
                    if(tail.y < y)
                        tail.move(-1, 1);
                    else if(tail.y > y)
                        tail.move(-1, -1);
                    else
                        tail.move(-1, 0);
                }
                if(tail.y <= y - 2) { // up
                    if(tail.x < x)
                        tail.move(1, 1);
                    else if(tail.x > x)
                        tail.move(-1, 1);
                    else
                        tail.move(0, 1);
                }
                if(tail.y >= y + 2){ // down
                    if(tail.x < x)
                        tail.move(1, -1);
                    else if(tail.x > x)
                        tail.move(-1, -1);
                    else
                        tail.move(0, -1);
                }
            }
        }
    }

    private static class Coord {
        int x;
        int y;

        public Coord(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o){
            if (o instanceof Coord){
                return ((Coord)o).x == x && ((Coord)o).y == y;
            }
            return false;
        }
    }

    public static void run() {
        // /=========\
        // |  SETUP  |
        // \=========/
        String[] inputLines = Common.FileToLines("inputs/d9.input");
        if(inputLines == null) {
            System.out.println("d9p1:  STRING ARRAY IS NULL");
            return;
        }

        int debugRadius = 14;

        // /==========\
        // |  PART 1  |
        // \==========/
        RopeNode tail = new RopeNode(null);
        RopeNode head = new RopeNode(tail);
        ArrayList<Coord> visited = new ArrayList<>();

        for(String line : inputLines){
            String[] tokens = line.split(" ");
            int dx = 0; int dy = 0;
            switch(tokens[0]){
                case "U":
                    dx = 0; dy = -1;
                break;
                case "D":
                    dx = 0; dy = 1;
                break;
                case "L":
                    dx = -1; dy = 0;
                break;
                case "R":
                    dx = 1; dy = 0;
                break;
            }
            // System.out.println(line);
            for(int c = 0; c < Integer.parseInt(tokens[1]); c++){
                head.move(dx, dy);
                Coord tailPos = new Coord(tail.x, tail.y);
                if(!visited.contains(tailPos)){
                    visited.add(tailPos);
                }
                
                //PrintBoard(head, visited, debugRadius);
            }
        }
        System.out.println(String.format("d9p1:  Total visited cells = %d", visited.size()));

        // /==========\
        // |  PART 2  |
        // \==========/

        int n = 10;
        tail = new RopeNode(null);
        head = new RopeNode(tail);
        visited = new ArrayList<>();
        for(int i = 0; i < n-2; i++) {
            head = new RopeNode(head);
        }

        for(String line : inputLines){
            String[] tokens = line.split(" ");
            int dx = 0; int dy = 0;
            switch(tokens[0]){
                case "U":
                    dx = 0; dy = -1;
                break;
                case "D":
                    dx = 0; dy = 1;
                break;
                case "L":
                    dx = -1; dy = 0;
                break;
                case "R":
                    dx = 1; dy = 0;
                break;
            }
            // System.out.println(line);
            for(int c = 0; c < Integer.parseInt(tokens[1]); c++){
                head.move(dx, dy);
                Coord tailPos = new Coord(tail.x, tail.y);
                if(!visited.contains(tailPos)){
                    visited.add(tailPos);
                }

                //PrintBoard(head, visited, debugRadius);
            }

        }
        System.out.println(String.format("d9p2:  Total visited cells = %d", visited.size()));
    }

    private static void PrintBoard(RopeNode rope, ArrayList<Coord> visited, int debugRadius){
        System.out.println("=================");
        char[][] board = new char[debugRadius*2+1][debugRadius*2+1];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = '.';
            }
        }
        for(Coord coord : visited){
            try {
                board[coord.y + debugRadius][coord.x + debugRadius] = ',';
            } catch (IndexOutOfBoundsException e){}
        }
        RopeNode node = rope;
        while(node != null){
            try {
                board[node.y + debugRadius][node.x + debugRadius] = '#';
            } catch (IndexOutOfBoundsException e){}
            node = node.tail;
        }
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                System.out.print(board[y][x]);
            }
            System.out.println("");
        }
        System.out.println(String.format("visited = %d", visited.size()));
        System.out.println("=================");
    }
}
