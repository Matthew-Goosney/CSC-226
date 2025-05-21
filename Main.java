/**
 * Name: Matthew Goosney
 * V-Number: V01040408
 * Date: March 17th, 2025
 */

 import java.util.*;
 import edu.princeton.cs.algs4.FlowNetwork;
 import edu.princeton.cs.algs4.FlowEdge;
 import edu.princeton.cs.algs4.FordFulkerson;
 
 public class Main {
     // Source and sink vertices
     private static final int SOURCE = 0;
     private static int SINK;
     
     // Mapping structures for vertices
     private static Map<String, Integer> flightToVertex;
     private static Map<Integer, Character> vertexToRoute;
     private static int[] pilotToVertex;
     private static int totalFlights;
 
     /** 
      * Function: Pair
      * - - - - - - - - - - -
      * Helper class to store flight-pilot pairing
      * 
      */
     private static class Pair<K, V> {
         private K key;
         private V value;
         public Pair(K key, V value) {
             this.key = key;
             this.value = value;
         }
         public K getKey()   { return key; }
         public V getValue() { return value; }
     }
 
     /**
      * Function: solveAllocationProblem
      * - - - - - - - - - - -
      * Creates a Max-Flow using the Ford-Fulkerson algorithm (draws a lot of functions from the algs4 library)
      * 
      * @param flightPilotPairings List of flight-to-pilot pairings
      */
     private static void solveAllocationProblem(List<Pair<String, String>> flightPilotPairings) {
   
         flightToVertex = new HashMap<>();
         vertexToRoute = new HashMap<>();
         pilotToVertex = new int[10];
         totalFlights = 0;
         
         // Map flights to vertices
         for (Pair<String, String> pair : flightPilotPairings) {
             String flightInfo = pair.getKey();
             char route = flightInfo.charAt(0);
             int numFlights = Character.getNumericValue(flightInfo.charAt(1));
             
             for (int i = 0; i < numFlights; i++) {
                 totalFlights++;
                 String flightId = route + "_" + i;
                 flightToVertex.put(flightId, totalFlights);
                 vertexToRoute.put(totalFlights, route);
             }
         }
         
         int V = 1 + totalFlights + 10 + 1;
         SINK = V - 1;
         
         // Map pilots to vertices
         for (int i = 0; i < 10; i++) {
             pilotToVertex[i] = totalFlights + 1 + i;
         }
         
         // Create flow network
         FlowNetwork flowNetwork = new FlowNetwork(V);
         
         // Add edges from source to flights (capacity 1)
         for (int i = 1; i <= totalFlights; i++) {
             flowNetwork.addEdge(new FlowEdge(SOURCE, i, 1.0));
         }
         
         // Add edges from pilots to sink (capacity 1)
         for (int i = 0; i < 10; i++) {
             flowNetwork.addEdge(new FlowEdge(pilotToVertex[i], SINK, 1.0));
         }
         
         // Add edges between flights and pilots
         for (Pair<String, String> pair : flightPilotPairings) {
             String flightInfo = pair.getKey();
             String pilotInfo = pair.getValue();
             char route = flightInfo.charAt(0);
             int numFlights = Character.getNumericValue(flightInfo.charAt(1));
             
             for (int i = 0; i < numFlights; i++) {
                 String flightId = route + "_" + i;
                 int flightVertex = flightToVertex.get(flightId);
                 
                 // Connect flight to eligible pilots
                 for (int j = 0; j < pilotInfo.length(); j++) {
                     int pilotIndex = Character.getNumericValue(pilotInfo.charAt(j));
                     int pilotVertex = pilotToVertex[pilotIndex];
                     flowNetwork.addEdge(new FlowEdge(flightVertex, pilotVertex, 1.0));
                 }
             }
         }
         
         // Find maximum flow
         FordFulkerson maxflow = new FordFulkerson(flowNetwork, SOURCE, SINK);
         
         // Check if all flights can be assigned
         if (maxflow.value() == totalFlights) {
             char[] assignment = new char[10];
             Arrays.fill(assignment, '_'); 
 
             Map<Integer, Boolean> flightAssigned = new HashMap<>();
             for (int i = 0; i <= totalFlights; i++) {
                 flightAssigned.put(i, false);
             }
 
             // Extract assignments from flow network
             for (int flightVertex = 1; flightVertex <= totalFlights; flightVertex++) {
                 if (flightAssigned.get(flightVertex)) continue;
                 
                 char route = vertexToRoute.get(flightVertex);
                 
                 for (int pilotIndex = 0; pilotIndex < 10; pilotIndex++) {
                     int pilotVertex = pilotToVertex[pilotIndex];
                     
                     boolean isAssigned = false;
                     for (FlowEdge e : flowNetwork.adj(flightVertex)) {
                         if (e.from() == flightVertex && e.to() == pilotVertex && e.flow() > 0) {
                             isAssigned = true;
                             break;
                         }
                     }
                     
                     // Assign the route to pilot if not already assigned
                     if (isAssigned && assignment[pilotIndex] == '_') {
                         assignment[pilotIndex] = route;
                         flightAssigned.put(flightVertex, true);
                         break;
                     }
                 }
             }
             
             // Output the assigned flight paths
             System.out.print(new String(assignment));
         } else {
             // No valid assignment possible
             System.out.print("!");
         }
     }
 
     /**
      * Function: processInputData
      * - - - - - - - - - - -
      * Process input data day by day
      * 
      * @param lines List of input lines to process
      */
     private static void processInputData(List<String> lines) {
         int i = 0;
         while (i < lines.size()) {
             List<Pair<String, String>> dayPairings = new ArrayList<>();
 
             // Process lines (data-parsing given input)
             while (i < lines.size() && !lines.get(i).isEmpty()) {
                 String line = lines.get(i++);
                 
                 int spaceIndex = line.indexOf(' ');
                 if (spaceIndex > 0) {
                     String flightInfo = line.substring(0, spaceIndex).trim();
                     String pilotInfo = line.substring(spaceIndex + 1).trim();
                     
                     // Remove trailing semicolons
                     if (pilotInfo.endsWith(";")) {
                         pilotInfo = pilotInfo.substring(0, pilotInfo.length() - 1);
                     }
                     
                     dayPairings.add(new Pair<>(flightInfo, pilotInfo));
                 }
             }
 
             if (!dayPairings.isEmpty()) {
                 solveAllocationProblem(dayPairings);
                 System.out.println();
             }
 
             // Skip any empty lines (between the given datasets for each day. (i.e) input1.txt)
             while (i < lines.size() && lines.get(i).isEmpty()) {
                 i++;
             }
         }
     }
 
     /**
      * Function: Main method 
      * - - - - - - - - - - -
      * Reads input from stdin and processes the data
      * 
      * @param args Command line arguments
      */
     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         List<String> inputLines = new ArrayList<>();
         
         while (sc.hasNextLine()) {
             inputLines.add(sc.nextLine());
         }
         sc.close();
 
         processInputData(inputLines);
     }
 }