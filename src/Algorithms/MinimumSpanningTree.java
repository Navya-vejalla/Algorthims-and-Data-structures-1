package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements Kruskal's algorithm to find the Minimum Spanning Tree (MST) of a graph.
 */
public class MinimumSpanningTree {
    private Scanner fileScanner;
    private int numVertices, numEdges;
    private int edgeCount;
    private Edge[] edgeList, mstEdges;
    private String vertexLabels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Represents a link between two vertices.
     */
    class Link {
        int vertexA, vertexB;
    }

    /**
     * Represents a weighted edge between two vertices.
     */
    class Edge implements Comparable<Edge> {
        int startVertex, endVertex, weight;

        @Override
        public int compareTo(Edge otherEdge) {
            return this.weight - otherEdge.weight;
        }
    }

    /**
     * Executes Kruskal's algorithm on the graph loaded from the specified file.
     * 
     * filePath Path to the file containing the graph data.
     */
    public void executeAlgorithm(String filePath) {
        try {
            readGraphFromFile(filePath);
            long startTime = System.nanoTime();
            runKruskalAlgorithm();
            long endTime = System.nanoTime();
            displayResults(startTime, endTime);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Reads the graph data from the specified file.
     * 
     * filePath Path to the file containing the graph data.
     * throws FileNotFoundException
     */
    private void readGraphFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        fileScanner = new Scanner(file);
        numVertices = fileScanner.nextInt();
        numEdges = fileScanner.nextInt();
        System.out.println("Number of Vertices: " + numVertices);
        System.out.println("Number of Edges: " + numEdges);
        fileScanner.next().charAt(0); // Consume newline character
        edgeList = new Edge[numEdges];
        for (int i = 0; i < numEdges; ++i) {
            edgeList[i] = new Edge();
        }
        for (int i = 0; i < numEdges; i++) {
            int sourceVertex = vertexLabels.indexOf(fileScanner.next().charAt(0));
            int destinationVertex = vertexLabels.indexOf(fileScanner.next().charAt(0));
            int weight = fileScanner.nextInt();
            edgeList[i].startVertex = sourceVertex;
            edgeList[i].endVertex = destinationVertex;
            edgeList[i].weight = weight;
        }
        fileScanner.close();
    }

    /**
     * Runs Kruskal's algorithm to find the Minimum Spanning Tree.
     */
    void runKruskalAlgorithm() {
        mstEdges = new Edge[numVertices];
        edgeCount = 0;
        int index;
        for (index = 0; index < numVertices; ++index) {
            mstEdges[index] = new Edge();
        }
        Arrays.sort(edgeList);
        Link[] vertexLinks = new Link[numVertices];
        for (index = 0; index < numVertices; ++index) {
            vertexLinks[index] = new Link();
        }
        for (int i = 0; i < numVertices; ++i) {
            vertexLinks[i].vertexA = i;
            vertexLinks[i].vertexB = 0;
        }
        index = 0;
        while (edgeCount < numVertices - 1) {
            Edge nextEdge = edgeList[index++];
            int vertexX = find(vertexLinks, nextEdge.startVertex);
            int vertexY = find(vertexLinks, nextEdge.endVertex);
            if (vertexX != vertexY) {
                mstEdges[edgeCount++] = nextEdge;
                union(vertexLinks, vertexX, vertexY);
            }
        }
    }

    /**
     * Unites two vertices in the disjoint set.
     * 
     * vertexLinks Array of vertex links.
     * vertexX     First vertex.
     * vertexY     Second vertex.
     */
    void union(Link[] vertexLinks, int vertexX, int vertexY) {
        int value1 = find(vertexLinks, vertexX);
        int value2 = find(vertexLinks, vertexY);
        if (vertexLinks[value1].vertexB > vertexLinks[value2].vertexB)
            vertexLinks[value2].vertexA = value1;
        else if (vertexLinks[value1].vertexB < vertexLinks[value2].vertexB)
            vertexLinks[value1].vertexA = value2;
        else {
            vertexLinks[value2].vertexA = value1;
            vertexLinks[value1].vertexB++;
        }
    }

    /**
     * Finds the representative vertex of a set.
     * 
     * vertexLinks Array of vertex links.
     * vertex Vertex to find.
     * return Representative vertex.
     */
    int find(Link[] vertexLinks, int vertex) {
        if (vertexLinks[vertex].vertexA != vertex)
            vertexLinks[vertex].vertexA = find(vertexLinks, vertexLinks[vertex].vertexA);
        return vertexLinks[vertex].vertexA;
    }

    /**
     * Displays the results of Kruskal's algorithm.
     * 
     * startTime Start time of algorithm execution.
     * endTime   End time of algorithm execution.
     */
    public void displayResults(long startTime, long endTime) {
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
        int totalCost = 0;
        System.out.println("\nMinimum Spanning Tree: \n");
        for (int i = 0; i < edgeCount; ++i) {
            System.out.println(vertexLabels.charAt(mstEdges[i].startVertex) + " --> " +
                    vertexLabels.charAt(mstEdges[i].endVertex) + " Cost: " + mstEdges[i].weight);
            totalCost += mstEdges[i].weight;
        }
        System.out.println("\nTotal Cost of MST: " + totalCost);
    }
}