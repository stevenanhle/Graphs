/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private static final Exception IllegalArgumentException = null;
	//TODO: Add your member variables here in WEEK 2
	
	int numVertices;
	private Map<GeographicPoint,ArrayList<Edge>> adjListsMap;
	int numEdges;

	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		adjListsMap = new HashMap<GeographicPoint,ArrayList<Edge>>();
		this.numVertices=0;
		this.numEdges=0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		
		Set keyset= adjListsMap.keySet();
		return keyset;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		//Vertex node = new Vertex(location);
		//if(adjListsMap.containsKey(node)==true)
		
		if(adjListsMap.get(location)==null)
		{
		ArrayList<Edge> streets = new ArrayList<Edge>();
		adjListsMap.put(location, streets);
		numVertices ++;
		return true;
		}
		else
		return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
        //Check to see if there are existing nodes 
		boolean isExist = adjListsMap.containsKey(from)&&adjListsMap.containsKey(to);
		//Check to see if there a is street between two nodes
		boolean isStreet=false;
		if(isExist==true)
		{
			List<Edge> streets = new ArrayList<Edge>();
			streets = adjListsMap.get(from);
			for(Edge street: streets)
			{
				 if(street.endV.location.equals(to))
				 {
					 isStreet = true;
				     break;
				 }
				 else
					 isStreet=false;
			}
		}
		
		if(isStreet==false && isExist==true)
		{
		  Edge line = new Edge(from,to,roadName,roadType, length);
		  (adjListsMap.get(from)).add(line);
		  numEdges++;
		}
		//TODO: Implement this method in WEEK 2
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		LinkedList<GeographicPoint> queue = new LinkedList<>();
		LinkedList<GeographicPoint> visited = new LinkedList<>();
		LinkedList<GeographicPoint> smallMap = new LinkedList<>();
		Map<GeographicPoint,GeographicPoint> parentMap = new HashMap<GeographicPoint,GeographicPoint>();
	   
		GeographicPoint trace= new GeographicPoint(0.0,1.0);
	    trace=goal;
	    //Print Graph
	   /* for(java.util.Map.Entry<GeographicPoint, ArrayList<Edge>> entry: adjListsMap.entrySet())
	    {
	    	for(Edge e: entry.getValue())
	    	{
	    System.out.println("My grpah" +entry.getKey()+"-->"+ e.startV.location+"_"+e.endV.location);
	        }
	    }*/
		queue.addLast(start);
		visited.addLast(start);
		while(queue.isEmpty()==false)
		{   GeographicPoint curr = queue.getFirst();
			queue.removeFirst();
			if(curr.equals(goal))
			{   nodeSearched.accept(curr);
			    while(trace.equals(start)==false)
			     { 
			    	 for (java.util.Map.Entry<GeographicPoint, GeographicPoint> entry : parentMap.entrySet()) 
			    	 {
			    	        if (entry.getKey().equals(trace)) 
			    	        {   smallMap.addFirst(trace);
			    	        	trace = entry.getValue();
			    	        	if(trace.equals(start))
					    	      smallMap.addFirst(trace);
			    	            break;
			    	        }
			    	 }	    
			     }
			     return smallMap;
			}//end of if
			else		
			{
				for(Edge g: adjListsMap.get(curr))
				{   GeographicPoint next= g.endV.location;
					if(visited.contains(next)==false)
					{
					nodeSearched.accept(next);
					visited.addLast(next);
					parentMap.put(next,curr);
					queue.addLast(next);
					}
				}
			}
		}
		return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		TreeMap<Double,GeographicPoint> queue = new TreeMap<Double,GeographicPoint>();
		List<GeographicPoint> visited = new ArrayList<GeographicPoint>();
		LinkedList<GeographicPoint> result = new LinkedList<>();
		Map<Infor,Infor> parentMap = new HashMap<Infor,Infor>();
		
		Infor trace = new Infor(1000.0,goal);
	    double length =0.0;
	    queue.put(0.0, start);
	    Infor from = new Infor (0.0, start);
	    while(queue.isEmpty()==false)
		{   GeographicPoint curr = queue.get(queue.firstKey());
			Double currLength = queue.firstKey();
			queue.remove(queue.firstKey());
			if(visited.contains(curr)==false)
			{   visited.add(curr);
			// This part uses parentMap for retrieving path from start to goal. 
				if(curr.equals(goal))
				{    trace = new Infor(length,goal);
					 while(trace.equals(from)==false)
				     { 
				    	 for (java.util.Map.Entry<Infor, Infor> entry : parentMap.entrySet()) 
				    	 {      if (entry.getKey().equals(trace)) 
					    	        {   result.addFirst(trace.position);
					    	        	trace = entry.getValue();
					    	        	if(trace.equals(from))
							    	      result.addFirst(trace.position);
					    	            break;
					    	        }	 
				    	  }	  //End of for
				     }//End of while. We return the path from start to goal
				     return result;
				}
			    else// Else of if statement. Codes for Bfs Algorithm implementation. 
				{   for(Edge g: adjListsMap.get(curr))
					{   GeographicPoint next= g.endV.location;
						if(visited.contains(next)==false)
						{       double nextLength=currLength+g.distance;
								Infor nextinfor = new Infor(nextLength, next);
								Infor currinfor = new Infor(currLength, curr);
								parentMap.put(nextinfor,currinfor);
								nodeSearched.accept(next);
								queue.put(nextLength, next);
								if(next.equals(goal))
									length = nextLength;
						}
					}//end for
				}//End of else statement. We return Hashmap parentMap. parentMap record child(as key)--->parentNode(as value)
			}//End of big if
		}//End of big while
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		TreeMap<Double,GeographicPoint> queue = new TreeMap<Double,GeographicPoint>();
		List<GeographicPoint> visited = new ArrayList<GeographicPoint>();
		LinkedList<GeographicPoint> result = new LinkedList<>();
		Map<Infor,List<Infor>> parentMap = new HashMap<Infor,List<Infor>>();
	   

		Infor trace = new Infor(1000.0,goal);
	    double length =0.0;
	    double dist = 1000000.0;
	    queue.put(0.0, start);
	    Infor from = new Infor (0.0, start);
	    
		
	    while(queue.isEmpty()==false)
		{
			GeographicPoint curr = queue.get(queue.firstKey());
			Double currLength = queue.firstKey();
			queue.remove(queue.firstKey());
			//System.out.println("Current is " +curr);
			if(visited.contains(curr)==false)
			{
				visited.add(curr);
				if(curr.equals(goal))
				{    trace = new Infor(length,goal);
					 while(trace.equals(from)==false)
				     {  //System.out.println("trace is "+ trace.position +trace.Length);
				    	 for (java.util.Map.Entry<Infor, List<Infor>> entry : parentMap.entrySet()) 
				    	 {
				    		    
					    		 for(Infor p: entry.getValue())
					    		 {
					    			
					    			//System.out.println("Entry is" +p.position +"   "+p.Length+ "---trace is "+ trace.position +"  "+trace.Length+trace.equals(p));
					    	        if (trace.equals(p)) 
					    	        	
					    	        {
					    	        	//System.out.println("My inside trace" +trace);
					    	        	result.addFirst(trace.position);
					    	        	trace = entry.getKey();
					    	        	//System.out.println("My inside trace" +trace);
					    	        	if(trace.equals(from))
							    	      result.addFirst(trace.position);
					    	        	//System.out.println("My........" +smallMap);
					    	            break;
					    	        }
					    		 }
				    	  }	  //for  
				     }//while nho
				     return result;
				    	
				}//end of if
		
			    else// cua if nho
	
				{  
					//System.out.println("PrentMap is" +parentMap);
					List<Infor> points = new ArrayList<>();
					for(Edge g: adjListsMap.get(curr))
					{   
						GeographicPoint next= g.endV.location;
					    //System.out.println("Next is " +adjListsMap);
					    //System.out.println("Visited " +visited);
						if(visited.contains(next)==false)
						{
							//System.out.println("Distance is " +g.distance);
							
								//System.out.println("Distance is " +g.distance);
								double nextLength=currLength+g.distance;
								Infor nextinfor = new Infor(nextLength, next);
								points.add(nextinfor);
								Infor currinfor = new Infor(currLength, curr);
								parentMap.put(currinfor,points);
								nodeSearched.accept(next);
								queue.put(nextLength, next);
								if(next.equals(goal))
									length = nextLength;
								//System.out.println("PrentMap is" +parentMap);
							    //System.out.println("QP is " +queue);
						}
					}//end for
				}//end else
			}//end if lon
		}//end of While lon
		
	    //Print Graph
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		/*
    	//You can use this method for testing.  
		
		//Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);*/

	
		
	}
	
}
