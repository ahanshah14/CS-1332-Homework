import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * My implementation of various different graph algorithms.
 *
 * @author Jalo Moster
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When exploring a vertex, make sure to explore in the order that the
     * adjacency list returns the neighbors to you. Failure to do so may cause
     * you to lose points.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List},
     * {@code java.util.Queue}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex not found in graph");
        }

        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> answer = new LinkedList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        answer.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            for (Edge<T> edge : adjList.get(queue.remove())) {
                if (!answer.contains(edge.getV())) {
                    answer.add(edge.getV());
                    queue.add(edge.getV());
                }
            }
        }
        return answer;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex not found in graph");
        }

        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        List<Vertex<T>> answer = new LinkedList<>();
        Stack<Vertex<T>> stack = new Stack<>();
        depthFirstSearch(start, adjList, answer, stack);
        return answer;
    }

    /**
     * {@link #depthFirstSearch(Vertex, Graph)} helper method.
     *
     * @param vertex the vertex visted
     * @param map the map that represents an adjacent list
     * @param list a list of visited nodes
     * @param stack a Stack that contains open nodes to visit
     * @param <T> the generic typing of the data
     */
    private static <T> void depthFirstSearch(Vertex<T> vertex,
                                             Map<Vertex<T>, List<Edge<T>>> map,
                                             List<Vertex<T>> list,
                                             Stack<Vertex<T>> stack) {
        list.add(vertex);
        stack.push(vertex);
        for (Edge<T> edge : map.get(stack.pop())) {
            if (!list.contains(edge.getV())) {
                depthFirstSearch(edge.getV(), map, list, stack);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you terminate the
     * algorithm once either all vertices have been visited or the PQ becomes
     * empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        } else if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Vertex not found in graph");
        }

        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        Queue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> answer = new HashMap<>();

        for (Vertex<T> v : adjList.keySet()) {
            if (v.equals(start)) {
                answer.put(v, 0);
            } else {
                answer.put(v, Integer.MAX_VALUE);
            }
        }
        queue.add(new Edge<>(start, start, 0));
        while (!queue.isEmpty()) {
            Edge<T> temp = queue.remove();
            for (Edge<T> edge : adjList.get(temp.getV())) {
                if (answer.get(edge.getV()) > temp.getWeight()
                        + edge.getWeight()) {
                    answer.put(edge.getV(), temp.getWeight()
                            + edge.getWeight());
                    queue.add(new Edge<>(edge.getU(), edge.getV(),
                            temp.getWeight() + edge.getWeight()));
                }
            }
        }
        return answer;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and return the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * opposite edge to the set as well. This is for testing purposes.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * TODO: Algorithm doesn't work. Fix bugs/errors.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        Set<Edge<T>> answer = new HashSet<>();
        DisjointSet<Vertex<T>> vertSet = new DisjointSet<>(
                graph.getAdjList().keySet());

        for (Edge<T> edge : graph.getEdges()) {
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (vertSet.find(u) != vertSet.find(v)
                || vertSet.find(u) == null
                || vertSet.find(v) == null) {
                vertSet.union(u, v);
                answer.add(edge);
            }
        }
        return answer;
    }
}