# CSC-226
Large coding assignments tied to CSC 226 at the University of Victoria.


## Flight Allocation Problem 
Matthew Goosney (V01040408), March 19th 2025 


### Approach 
In this assignment, we were tasked with creating a Maximum-Flow that assigns a flight path to a 
corresponding pilot with the proper qualifications to fly said path. Initially, I tried to use the method 
mentioned in the skeleton code we were provided, that being the Edmonds-Karp Algorithm, however, I 
eventually gave up due to how much more difficult it was to design a flow algorithm from scratch. 
Instead, I used another method that was mentioned in our professor’s announcement (and in the 
assignment doc), which allowed the use of the algs4 library. My method drew from this library and used 
the Ford-Fulkerson algorithm to make the Maximum Flow work, while using the FlowNetwork and 
FlowEdge algorithms to solve the flight-to-pilot problem more easily.
  
### Structures: 
flightToVertex — Maps flight identifiers to network vertices 
vertexToRoute — Maps vertices back to their corresponding routes 
pilotToVertex — Array mapping pilots to network vertices 
Pair<K,V> — Class that stores flight-pilot pairing 

The generalization of my method was to parse the input data (the given flight routes, number of flights per 
route, and the qualified pilots), and map each flight and pilot to specific vertices in the flow network. 
Much like the problem we worked on in our last lab, the FlowNetwork is structured with a source node 
(vertex 0) and a sink node (last vertex in the network), which creates an entry and exit point for the flow. 
Once the network and all of its vertices are created, I used the Ford-Fulkerson algorithm to find the 
maximum possible flow from my source to sink—which, in turn, represents the maximum number of 
flights that can be assigned. If this value equals the total number of flights, a complete assignment is 
possible, and the output is printed. If not, a “!” is printed, as per the instructions. 
When fed with input1.txt, my implementation outputs the following: 
_AAAAQPPPP 

Note: This differs slightly from the output we are shown in the assignment document, but it is still entirely 
valid, as pilots 0,1,2,3,4 are all qualified to fly the four listed A routes. Meaning, the underscore can be 
any of the five spaces.
