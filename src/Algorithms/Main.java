package Algorithms;

import java.util.*;

public class Main {
    public static void main(String[] args) {
    	DijkstraShortestPath algorithm = new DijkstraShortestPath();
        MinimumSpanningTree algorithms = new MinimumSpanningTree();
        TopologicalSort topologicalSort = new TopologicalSort();

        Scanner sc = new Scanner(System.in);
        int ipFile;

        System.out.println("Select an Algorithm:\n" +
                "1. Dijkstra's Algorithm (Calculates Shortest Path)\n" +
                "2. Kruskal's Algorithm (Minimum Spanning Tree)\n" +
                "3. Topological Sorting (if cyclic, print cycles; if not, print topological order)");

        int ch = Integer.parseInt(sc.nextLine());
        switch (ch) {
            case 1:
                // Dijkstra's Algorithm
                System.out.println("Select an Input File\n" +
                        "1. Undirected Graph-1\n" +
                        "2. Undirected Graph-2\n" +
                        "3. Undirected Graph-3.\n" +
                        "4. Undirected Graph-4.\n"+
                        "5. Directed Graph-1\n" +
                        "6. Directed Graph-2\n" +
                        "7. Directed Graph-3.\n" +
                        "8. Directed Graph-4.");
                ipFile = Integer.parseInt(sc.nextLine());
                String filePath = getFilePath(ipFile);
                System.out.println("========== Dijkstra's Algorithm ===========");
                algorithm.executeDijkstra(filePath);
                break;
            case 2:
                // Kruskal's Algorithm
                System.out.println("Select an Input File\n" +
                        "1. Undirected Graph-1\n" +
                        "2. Undirected Graph-2\n" +
                        "3. Undirected Graph-3.\n" +
                        "4. Undirected Graph-4.");
                ipFile = Integer.parseInt(sc.nextLine());
                String filePaths = getFilePath(ipFile);
                System.out.println("========== Kruskal's Algorithm ==========");
                algorithms.executeAlgorithm(filePaths);
                break;
            case 3:
                // Topological Sorting
                System.out.println("Select an Input File\n" +
                        "5. DAG-1\n" +
                        "6. DAG-2\n" +
                        "7. Cyclic Graph-3.\n" +
                        "8. Cyclic Graph-4.");
                ipFile = Integer.parseInt(sc.nextLine());
                String filePathTopo = getFilePath(ipFile);
                System.out.println("========== Topological Sorting ==========");
                topologicalSort.executeTopologicalSorting(filePathTopo);
                break;
            default:
                System.out.println("Invalid Input!");
                break;
        }
        sc.close();
    }

    static String getFilePath(int ipfile) {
        String fPath;
        switch (ipfile) {
            case 1:
                fPath = "./ipfiles/Undirected_Graph_1.txt";
                break;
            case 2:
                fPath = "./ipfiles/Undirected_Graph_2.txt";
                break;
            case 3:
                fPath = "./ipfiles/Undirected_Graph_3.txt";
                break;
            case 4:
                fPath = "./ipfiles/Undirected_Graph_4.txt";
                break;
            case 5:
                fPath = "./ipfiles/Directed_Graph_1.txt";
                break;
            case 6:
                fPath = "./ipfiles/Directed_Graph_2.txt";
                break;
            case 7:
                fPath = "./ipfiles/Directed_Graph_3.txt";
                break;
            case 8:
                fPath = "./ipfiles/Directed_Graph_4.txt";
                break;
            default:
                System.out.println("Invalid Input!\n");
                fPath = "ipFiles/Undirected_Graph_1.txt";
                break;
        }
        return fPath;
    }
}
