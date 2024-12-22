package Algorithms;

import java.io.*;
import java.util.*;

/**
 * Implementation of Topological Sort algorithm.
 */
public class TopologicalSort {

    private Map<String, List<String>> adjacencyList;
    private Set<String> exploredVertices;
    private Set<String> recursiveCallStack;
    private List<List<String>> detectedCycles;

    /**
     * Executes the topological sorting algorithm.
     * 
     * filePath Path to the input file containing the graph data.
     */
    public void executeTopologicalSorting(String filePath) {
        try {
            
            adjacencyList = new HashMap<>();
            exploredVertices = new HashSet<>();
            recursiveCallStack = new HashSet<>();
            detectedCycles = new ArrayList<>();

            Scanner fileScanner = new Scanner(new File(filePath));
            int numVertices = fileScanner.nextInt();
            int numEdges = fileScanner.nextInt();
            System.out.println("Number of Vertices: " + numVertices);
            System.out.println("Number of Edges: " + numEdges);

            String graphType = fileScanner.next();

            fileScanner.nextLine();

            // Build the graph
            for (int i = 0; i < numEdges; i++) {
                // Read source and destination vertices
                String[] edgeLine = fileScanner.nextLine().split(" ");
                String sourceVertex = edgeLine[0];
                String destinationVertex = edgeLine[1];

                // Add edge to adjacency list
                adjacencyList.computeIfAbsent(sourceVertex, k -> new ArrayList<>()).add(destinationVertex);
            }
            fileScanner.close();

            for (String vertex : adjacencyList.keySet()) {
                if (!exploredVertices.contains(vertex)) {
                    isCyclic(vertex, new HashSet<>(), new ArrayList<>());
                }
            }

            // Check if cycles were detected
            if (detectedCycles.isEmpty()) {
                // Graph is acyclic, perform topological sorting
                System.out.println("The graph is acyclic. Performing topological sorting...");
                long startTime = System.nanoTime();
                List<String> sortedVertices = topologicalSort();
                long endTime = System.nanoTime();
                System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
                System.out.println("Topological sorting sequence:");
                for (String vertex : sortedVertices) {
                    System.out.print(vertex + " ");
                }
                System.out.println();
            } else {
                // Graph contains cycles
                System.out.println("The graph contains cycles.");
                System.out.println("Cycles along with their lengths:");
                int cycleIndex = 1;
                long startTime = System.nanoTime();
                for (List<String> cycle : detectedCycles) {
                    System.out.print("Cycle " + cycleIndex + ": ");
                    for (int i = 0; i < cycle.size(); i++) {
                        System.out.print(cycle.get(i));
                        if (i < cycle.size() - 1) {
                            System.out.print(" -> ");
                        }
                    }
                    System.out.println(" (Length: " + cycle.size() + ")");
                    cycleIndex++;
                }
                long endTime = System.nanoTime();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

    /**
     * Checks if the graph contains cycles using DFS.
     * 
     * vertex Current vertex.
     * visitedVertices Set of visited vertices.
     * currentPath Current path.
     */
    private void isCyclic(String vertex, Set<String> visitedVertices, List<String> currentPath) {
        // Check if vertex is already in recursion stack
        if (recursiveCallStack.contains(vertex)) {
            // Cycle detected, add to detected cycles list
            List<String> cycle = new ArrayList<>(currentPath.subList(currentPath.indexOf(vertex), currentPath.size()));
            detectedCycles.add(cycle);
            return;
        }
        // Mark vertex as visited and add to recursion stack
        if (!exploredVertices.contains(vertex)) {
            exploredVertices.add(vertex);
            recursiveCallStack.add(vertex);
            currentPath.add(vertex);

            // Recur for neighboring vertices
            List<String> neighbors = adjacencyList.getOrDefault(vertex, new ArrayList<>());
            for (String neighbor : neighbors) {
                isCyclic(neighbor, visitedVertices, currentPath);
            }

            // Remove vertex from recursion stack
            recursiveCallStack.remove(vertex);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    /**
     * Performs topological sorting using DFS.
     * 
     * return List of vertices in topological order.
     */
    private List<String> topologicalSort() {
        List<String> sortedVertices = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> recursiveCallStack = new HashSet<>();

        // Perform DFS traversal
        for (String vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                topologicalSortDFS(vertex, visited, recursiveCallStack, sortedVertices);
            }
        }

        // Reverse the sorted list
        Collections.reverse(sortedVertices);
        return sortedVertices;
    }

    /**
     * Recursive DFS function for topological sorting.
     * 
     * vertex Current vertex.
     * visited Set of visited vertices.
     * recursiveCallStack Recursion stack.
     * sortedVertices List of sorted vertices.
     */
    private void topologicalSortDFS(String vertex, Set<String> visited, Set<String> recursiveCallStack, List<String> sortedVertices) {
        // Mark vertex as visited and add to recursion stack
        visited.add(vertex);
        recursiveCallStack.add(vertex);

        // Recur for neighboring vertices
        List<String> neighbors = adjacencyList.getOrDefault(vertex, new ArrayList<>());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                topologicalSortDFS(neighbor, visited, recursiveCallStack, sortedVertices);
            }
        }
        // Remove vertex from recursion stack and add to sorted list
        recursiveCallStack.remove(vertex);
        sortedVertices.add(vertex);
    }
}