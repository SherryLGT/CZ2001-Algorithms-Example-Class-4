import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Application of Breadth First Search (BFS) to flight scheduling
 *
 * @author Sherry Lau Geok Teng
 * @version 1.0
 * @since 01/04/2017
 */

public class BreadthFirstSearch {
	
	private static int size;
	private static FlightMap flightMap;
	private static String cities[];
	private static int flights[][];
	
	/**
	 * Method to perform a BFS algorithm running on graphs represented by adjacency matrix
	 */
	private static int[] BFS(int s) {
		// Array of mark flags for each vertex (initialized to false)
		boolean[] marks = new boolean[size];
		// Queue L to know which vertex to visit next
		Queue<Integer> L = new LinkedList<>();
		// Mark starting vertex
		marks[s] = true;
		// Add starting vertex to queue
		L.add(s);
		// Nearest predecessors
		int[] paths = new int[size];

		// While there are still vertices in queue
		while (!L.isEmpty()) {
			// Dequeue a vertex from queue
			int v = L.remove();
			// Loop through all the vertices
			for (int w = 0; w < size; w++) {
				// Check if vertices is adjacent to v
				if (flights[v][w] == 1) {
					// If vertex is unmarked
					if (!marks[w]) {
						// Mark vertex and add to queue
						marks[w] = true;
						L.add(w);
						// Set predecessor index to array
						paths[w] = v;
					}
				}
			}
		}
		return paths;
	}
    
    /**
     * Printing of paths
     */
    private static void query(int from, int to, int[] paths, ArrayList<Integer> shortestPath) {
    	// Store relevant path into array
    	shortestPath.add(to);
    	
    	// If path is not origin
    	if(paths[to] != from) {
    		// Recursive to get relevant path
    		query(from, paths[to], paths, shortestPath);
    	}
    	// Else path is origin
    	else {
    		// Store path into array
    		shortestPath.add(from);
    		// Printing of flight paths
    		for(int i = shortestPath.size()-1; i >= 0; i--) {
    			System.out.print(cities[shortestPath.get(i)]);
    			if(i > 0)
    				System.out.print(" -> ");
    		}
    	}
    }
    
	@SuppressWarnings("resource")
	public static void main (String[] args) {		
		Scanner sc = new Scanner(System.in);
		int from, to;
		long start, end;
		
		do {
			System.out.print("Enter size of flight data: ");
			size = sc.nextInt();
		} while (size <= 0 || size > 50);
		
		flightMap = new FlightMap();
		// Get list of cities according to input size
		cities = flightMap.getCities(size);
		// Get list of flights according to input size
		flights = flightMap.getFlights(size);
		// Print list of cities
		flightMap.printCitiesList(size);
		
		System.out.println();
		
		while(true) {
			// Get travel origin city
			do {
				System.out.print("Select origin city of travel: ");
				from = sc.nextInt() - 1;
			} while (from < 0 || from > size-1);
			System.out.println("Selected origin city: " + cities[from]);
	
			System.out.println();
			
			// Get travel destination city
			do {
				System.out.print("Select destination city of travel: ");
				to = sc.nextInt() - 1;
			} while (to < 0 || to > size-1 || to == from);
			System.out.println("Selected destination city: " + cities[to]);
			
			System.out.println("\nShortest flight path:");
			start = System.nanoTime();
			// Perform BFS and print results
			query(from, to , BFS(from), new ArrayList<Integer>());
			end = System.nanoTime();
			System.out.println("\n\nCPU time: " + (end - start) + "ns\n");
		}
	}
}
