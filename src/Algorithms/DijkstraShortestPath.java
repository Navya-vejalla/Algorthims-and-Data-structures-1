package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Implementation of Dijkstra's Shortest Path algorithm.
 */
public class DijkstraShortestPath {
    
    private int startingVertex;
    private int numVertices;
    private int numEdges;
    private Map<Integer, List<Edge>> adjacencyList;
    private int[] shortestDistances;
    private boolean[] visitedNodes;
    private String alphabetLabels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Executes Dijkstra's Shortest Path algorithm.
     * 
     * filePath Path to the input file containing the graph data.
     */
    public void executeDijkstra(String filePath) {
        try {
            readGraphFile(filePath);
            long startTime = System.nanoTime();
            calculateShortestPaths();
            long endTime = System.nanoTime();
            printResults(startTime, endTime);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Reads the graph from the input file.
     * 
     * filePath Path to the input file.
     * throws FileNotFoundException
     */
    private void readGraphFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        numVertices = scanner.nextInt();
        numEdges = scanner.nextInt();
        char graphType = scanner.next().charAt(0);

        adjacencyList = new HashMap<>();
        shortestDistances = new int[numVertices];
        visitedNodes = new boolean[numVertices];

        for (int i = 0; i < numEdges; i++) {
            String sourceNode = scanner.next();
            String destinationNode = scanner.next();
            int edgeWeight = scanner.nextInt();

            int sourceIndex = alphabetLabels.indexOf(sourceNode.charAt(0));
            int destinationIndex = alphabetLabels.indexOf(destinationNode.charAt(0));
            adjacencyList.computeIfAbsent(sourceIndex, k -> new ArrayList<>())
                    .add(new Edge(destinationIndex, edgeWeight));
            if (graphType == 'U') {
                adjacencyList.computeIfAbsent(destinationIndex, k -> new ArrayList<>())
                        .add(new Edge(sourceIndex, edgeWeight));
            }
        }

        startingVertex = alphabetLabels.indexOf(scanner.next().charAt(0));
        scanner.close();
    }

    /**
     * Calculates shortest paths using Dijkstra's algorithm.
     */
    private void calculateShortestPaths() {
        
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        Arrays.fill(shortestDistances, Integer.MAX_VALUE);
        shortestDistances[startingVertex] = 0;
        minHeap.offer(new Node(startingVertex, 0));

        // Loop until priority queue is empty
        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            int vertex = currentNode.getVertex();
            int distance = currentNode.getDistance();

            if (visitedNodes[vertex]) continue;
            visitedNodes[vertex] = true;
            
            // Iterate over neighbors of current vertex
            for (Edge neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
                int nextVertex = neighbor.getDestination();
                int weight = neighbor.getWeight();
                int newDistance = distance + weight; // Calculate new distance to neighbor
                // Update shortest distance if new distance is shorter
                if (newDistance < shortestDistances[nextVertex]) {
                    shortestDistances[nextVertex] = newDistance;
                    minHeap.offer(new Node(nextVertex, newDistance));
                }
            }
        }
    }

    /**
     * Prints results.
     * 
     * startTime Start time of algorithm execution.
     * endTime End time of algorithm execution.
     */
    private void printResults(long startTime, long endTime) {
        System.out.println("Number of Vertices: " + numVertices);
        System.out.println("Number of Edges: " + numEdges);
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
        System.out.println("Shortest Path Tree from source vertex " + alphabetLabels.charAt(startingVertex) + ":");
        System.out.println("Path from " + alphabetLabels.charAt(startingVertex) + " to other vertices:");

        for (int i = 0; i < numVertices; i++) {
            if (i != startingVertex) {
                String path = reconstructPath(i);
                System.out.println(alphabetLabels.charAt(startingVertex) + path + " Path cost: " + shortestDistances[i]);
            }
        }
    }

    /**
     * Reconstructs the shortest path from starting vertex to destination vertex.
     * 
     * destination Destination vertex.
     * return Shortest path as a string.
     */
    private String reconstructPath(int destination) {
        StringBuilder path = new StringBuilder();
        int currentVertex = destination;
        while (currentVertex != startingVertex) {
            path.insert(0, " --> " + alphabetLabels.charAt(currentVertex));
            currentVertex = findPreviousVertex(currentVertex);
        }
        return path.toString();
    }

    /**
     * Finds the previous vertex in the shortest path.
     * 
     * vertex Current vertex.
     * return Previous vertex in the shortest path.
     */
    private int findPreviousVertex(int vertex) {
        for (int i = 0; i < numVertices; i++) {
            int v = i;
            if (adjacencyList.getOrDefault(v, Collections.emptyList()).stream()
                    .anyMatch(edge -> edge.getDestination() == vertex && shortestDistances[v] + edge.getWeight() == shortestDistances[vertex])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Node class representing a vertex and its distance.
     */
    private static class Node {
        private int vertex;
        private int distance;

        public Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
        }
    }

    /**
     * Edge class representing a weighted edge between two vertices.
     */
    private static class Edge {
        private int destination;
        private int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public int getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }
    }
}