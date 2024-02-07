import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a test class for graph operations. This class is designed to allow users to input graph data
 * and then compute and display various graph properties such as all paths, all cycles, and unique paths within the graph.
 */
public class GraphPathCoverageCalculator {

    /**
     * The main method serves as the entry point for the program. It handles user input for graph creation
     * and initiates the process to find and display paths and cycles within the graph.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numberOfVertices = inputScanner.nextInt();

        System.out.print("Enter the number of edges: ");
        int numberOfEdges = inputScanner.nextInt();

        Graph graph = new Graph(numberOfVertices);

        System.out.print("Enter " + numberOfEdges + " edges: ");
        for (int i = 0; i < numberOfEdges; i++) {
            int startVertex = inputScanner.nextInt();
            int endVertex = inputScanner.nextInt();
            graph.addEdge(startVertex, endVertex);
        }

        inputScanner.close();
        System.out.println();

        List<List<Integer>> allPathsAndCycles = graph.getAllPaths();
        allPathsAndCycles.addAll(graph.getAllCycles());
        allPathsAndCycles.sort((path1, path2) -> {
            if (path1.size() == path2.size()) {
                for (int i = 0; i < path1.size(); i++) {
                    if (!path1.get(i).equals(path2.get(i))) {
                        return path1.get(i) - path2.get(i);
                    }
                }
            }
            return path1.size() - path2.size();
        });
        System.out.println("All paths and cycles:");
        for (List<Integer> path : allPathsAndCycles) {
            System.out.println(path);
        }
        System.out.println("Total of paths and cycles: " + allPathsAndCycles.size() + "\n");

        List<List<Integer>> allUniquePaths = graph.getUniquePaths();
        allUniquePaths.sort((path1, path2) -> {
            if (path1.size() == path2.size()) {
                for (int i = 0; i < path1.size(); i++) {
                    if (!path1.get(i).equals(path2.get(i))) {
                        return path1.get(i) - path2.get(i);
                    }
                }
            }
            return path1.size() - path2.size();
        });

        System.out.println("All Prime paths:");
        for (List<Integer> uniquePath : allUniquePaths) {
            System.out.println(uniquePath);
        }
        System.out.println("Total of Prime paths: " + allUniquePaths.size() + "\n");
    }
}

/**
 * Represents a graph with directed edges. It allows adding edges, and computing all paths, all cycles,
 * and unique paths within the graph.
 */
class Graph {
    private int numberOfVertices;
    private LinkedList<Integer>[] adjacencyLists;

    /**
     * Constructs a Graph with a specified number of vertices.
     * @param numberOfVertices The number of vertices in the graph.
     */
    @SuppressWarnings("unchecked")
    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjacencyLists = new LinkedList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)
            adjacencyLists[i] = new LinkedList<>();
    }

    /**
     * Adds a directed edge from one vertex to another.
     * @param startVertex The starting vertex of the edge.
     * @param endVertex The ending vertex of the edge.
     */
    public void addEdge(int startVertex, int endVertex) {
        adjacencyLists[startVertex].add(endVertex);
    }

    /**
     * Computes all paths between any two vertices in the graph.
     * @return A list of all paths, where each path is represented as a list of vertices.
     */
    public List<List<Integer>> getAllPaths() {
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                boolean[] isVisited = new boolean[numberOfVertices];
                ArrayList<Integer> pathList = new ArrayList<Integer>();
                pathList.add(i);
                paths.addAll(getAllPathsUtil(i, j, isVisited, pathList));
            }
        }
        return paths;
    }

    /**
     * Helper method to recursively find all paths from a start vertex to an end vertex.
     * @param startVertex The starting vertex.
     * @param endVertex The ending vertex.
     * @param isVisited An array to keep track of visited vertices.
     * @param localPathList The current path being explored.
     * @return A list of paths found from startVertex to endVertex.
     */
    private List<List<Integer>> getAllPathsUtil(Integer startVertex, Integer endVertex, boolean[] isVisited, List<Integer> localPathList) {
        List<List<Integer>> paths = new ArrayList<>();
        if (startVertex.equals(endVertex)) {
            paths.add(new ArrayList<>(localPathList));
            return paths;
        }

        isVisited[startVertex] = true;

        for (Integer neighbor : adjacencyLists[startVertex]) {
            if (!isVisited[neighbor]) {
                localPathList.add(neighbor);
                paths.addAll(getAllPathsUtil(neighbor, endVertex, isVisited, localPathList));
                localPathList.remove(neighbor);
            }
        }

        isVisited[startVertex] = false;
        return paths;
    }

    /**
     * Computes all cycles within the graph.
     * @return A list of all cycles, where each cycle is represented as a list of vertices.
     */
    public List<List<Integer>> getAllCycles() {
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            boolean[] isVisited = new boolean[numberOfVertices];
            ArrayList<Integer> pathList = new ArrayList<>();
            pathList.add(i);
            paths.addAll(getAllCyclesUtil(i, isVisited, pathList));
        }
        return paths;
    }

    /**
     * Helper method to recursively find all cycles starting from a given vertex.
     * @param startVertex The starting vertex of the cycle.
     * @param isVisited An array to keep track of visited vertices.
     * @param localPathList The current path being explored.
     * @return A list of cycles found starting from startVertex.
     */
    private List<List<Integer>> getAllCyclesUtil(Integer startVertex, boolean[] isVisited, List<Integer> localPathList) {
        List<List<Integer>> paths = new ArrayList<>();
        isVisited[startVertex] = true;

        for (Integer neighbor : adjacencyLists[startVertex]) {
            if (!isVisited[neighbor]) {
                localPathList.add(neighbor);
                paths.addAll(getAllCyclesUtil(neighbor, isVisited, localPathList));
                localPathList.remove(neighbor);
            } else if (neighbor.equals(localPathList.get(0))) {
                localPathList.add(localPathList.get(0));
                paths.add(new ArrayList<>(localPathList));
                localPathList.remove(localPathList.size() - 1);
            }
        }

        isVisited[startVertex] = false;
        return paths;
    }

    /**
     * Computes all unique paths within the graph. A unique path is one that is not a subpath of any other path.
     * @return A list of all unique paths, where each path is represented as a list of vertices.
     */
    public List<List<Integer>> getUniquePaths() {
        List<List<Integer>> paths = new ArrayList<>();
        paths.addAll(getAllPaths());
        paths.addAll(getAllCycles());
        List<List<Integer>> uniquePaths = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            boolean isUnique = true;
            for (int j = 0; j < paths.size(); j++) {
                if (i != j && isSubPath(paths.get(i), paths.get(j))) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                uniquePaths.add(paths.get(i));
            }
        }
        return uniquePaths;
    }

    /**
     * Determines if one path is a subpath of another.
     * @param path1 The potential subpath.
     * @param path2 The path to check against.
     * @return True if path1 is a subpath of path2, false otherwise.
     */
    private boolean isSubPath(List<Integer> path1, List<Integer> path2) {
        if (path1.size() > path2.size()) {
            return false;
        }
        for (int i = 0; i < path2.size() - path1.size() + 1; i++) {
            boolean isSubPath = true;
            for (int j = 0; j < path1.size(); j++) {
                if (!path1.get(j).equals(path2.get(i + j))) {
                    isSubPath = false;
                    break;
                }
            }
            if (isSubPath) {
                return true;
            }
        }
        return false;
    }
}