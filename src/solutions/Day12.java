package solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Day12 {
    public static void run() {
        boolean debug = false;
        if(debug) System.out.println("-== START OF DAY 12 ==-");

        String filename = "inputs/d12.input";
        String[] inputLines = Common.FileToLines(filename);
        if(inputLines == null) {
            System.out.println("d12:   INPUT FILE '"+filename+"' NOT FOUND");
        }

        // initialize height map
        int gridWidth = inputLines[0].length();
        int gridHeight = inputLines.length;
        int[][] heightMap = new int[gridHeight][gridWidth];
        int startX = 0, startY = 0;
        int endX = 0, endY = 0;

        for(int y = 0; y < gridHeight; y++) {
            for(int x = 0; x < gridWidth; x++) {
                char ch = inputLines[y].charAt(x);
                heightMap[y][x] = ch - 'a';
                if(ch == 'S') {
                    startX = x; startY = y;
                    heightMap[y][x] = 'a' - 'a';
                }
                if(ch == 'E') {
                    endX = x; endY = y;
                    heightMap[y][x] = 'z' - 'a';
                }
            }
        }

        if(debug) {
            System.out.println("Iinital grid:");
            for(int[] row : heightMap) {
                for(int height : row) {
                    System.out.print((char)(height + 'a'));
                }
                System.out.println("");
            }
        }        

        // create nodes
        Node[][] nodeGraph = new Node[gridHeight][gridWidth];
        for(int y = 0; y < gridHeight; y++) {
            for(int x = 0; x < gridWidth; x++) {
                nodeGraph[y][x] = new Node(x, y);
            }
        }

        // connect nodes
        for(int y = 0; y < gridHeight; y++) {
            for(int x = 0; x < gridWidth; x++) {
                if(x-1 >= 0) {
                    if(heightMap[y][x-1] <= heightMap[y][x] + 1)
                        nodeGraph[y][x].addNeighbor(nodeGraph[y][x-1]);
                }
                if(x+1 < gridWidth) {
                    if(heightMap[y][x+1] <= heightMap[y][x] + 1)
                        nodeGraph[y][x].addNeighbor(nodeGraph[y][x+1]);
                }

                if(y-1 >= 0) {
                    if(heightMap[y-1][x] <= heightMap[y][x] + 1)
                        nodeGraph[y][x].addNeighbor(nodeGraph[y-1][x]);
                }
                if(y+1 < gridHeight) {
                    if(heightMap[y+1][x] <= heightMap[y][x] + 1)
                        nodeGraph[y][x].addNeighbor(nodeGraph[y+1][x]);
                }
            }
        }

        // PART 1
        double pathLength = findShortestPath(nodeGraph.clone(), startX, startY, endX, endY);
        System.out.println(String.format("d12p1: Total Path Cost = %f", pathLength));

        if(debug) {
            System.out.println("Found path:");

            // reconstruct path
            ArrayList<Node> path = new ArrayList<Node>();
            path.add(nodeGraph[endY][endX]);
            Node current = path.get(0);
            while(current.cameFrom != null) {
                current = current.cameFrom;
                path.add(current);
            }
            Collections.reverse(path);

            // init grid
            char[][] charGrid = new char[gridHeight][gridWidth];
            for(int y = 0; y < gridHeight; y++) {
                for(int x = 0; x < gridWidth; x++) {
                    charGrid[y][x] = '.';
                }
            }
            
            // graw path
            for(int i = 0; i < path.size(); i++) {
                if(i+1 < path.size()) {
                    current = path.get(i);
                    Node next = path.get(i+1);
                    if(next.x > current.x) {
                        charGrid[current.y][current.x] = '>';
                    }
                    else if(next.x < current.x) {
                        charGrid[current.y][current.x] = '<';
                    }
                    else if(next.y > current.y) {
                        charGrid[current.y][current.x] = 'V';
                    }
                    else if(next.y < current.y) {
                        charGrid[current.y][current.x] = '^';
                    }
                }
            }
            charGrid[startY][startX] = 'S';
            charGrid[endY][endX] = 'E';

            // print graph
            for(char[] row : charGrid) {
                for(char ch : row) {
                    System.out.print(ch);
                }
                System.out.println("");
            }
        }

        // PART 2
        double shortestPath = Double.MAX_VALUE;
        for(int y = 0; y < gridHeight; y++) {
            for(int x = 0; x < gridWidth; x++) {
                if(heightMap[y][x] == 0) {
                    pathLength = findShortestPath(nodeGraph, x, y, endX, endY);
                    if(pathLength > 0) {
                        if(pathLength < shortestPath) {
                            shortestPath = pathLength;
                        }
                    }
                }
            }
        }
        System.out.println(String.format("d12p2: Shortest path = %f", shortestPath));

    }


    private static double findShortestPath(Node[][] nodeGraph, int startX, int startY, int endX, int endY) {
        // initialize nodes
        for(Node[] row : nodeGraph) {
            for(Node node : row) {
                node.reset();
                double dx = Math.abs(endX - node.x);
                double dy = Math.abs(endY - node.y);
                double h  = Math.sqrt((dx*dx)+(dy*dy));
                node.h = h;
            }
        }

        // perform a*
        PriorityQueue<Node> openSet = new PriorityQueue<Node>();
        Node startNode = nodeGraph[startY][startX];
        Node endNode = nodeGraph[endY][endX];
        openSet.add(startNode);
        startNode.g = 0;
        startNode.f = startNode.h;

        while(openSet.size() > 0) {
            Node current = openSet.poll();
            if(current == endNode) {
                return current.g;
            }

            for(Node neighbor : current.neighbors) {
                double tentG = current.g + 1;
                if(tentG < neighbor.g) {
                    neighbor.g = tentG;
                    neighbor.f = neighbor.h + tentG;
                    neighbor.cameFrom = current;

                    if(!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return -1;
    }


    private static class Node implements Comparable<Node> {
        public List<Node> neighbors;
        public Node cameFrom = null;

        public int x;
        public int y;

        public double f = Double.MAX_VALUE; // g + h
        public double g = Double.MAX_VALUE; // cost from start to node
        public double h = Double.MAX_VALUE; // pre-calculated heuristic

        public Node(int x, int y) {
            this.neighbors = new ArrayList<Node>();
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node n) {
            return Double.compare(this.f, n.f);
        }

        public void addNeighbor(Node n) {
            this.neighbors.add(n);
        }

        public void reset() {
            this.cameFrom = null;
            this.g = Double.MAX_VALUE;
            this.f = Double.MAX_VALUE;
            this.h = Double.MAX_VALUE;
        }
    }
}
