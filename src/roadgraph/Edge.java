package roadgraph;

import java.util.ArrayList;



import geography.GeographicPoint;

public class Edge {
	String nameStreet;
	String roadType;
	double distance;
	Vertex startV;
	Vertex endV;
	
	
	public Edge(GeographicPoint start, GeographicPoint end, String name, String type,double distance)
	{
		this.nameStreet=name;
		this.distance = distance;
		this.startV = new Vertex(start);
		this.endV = new Vertex(end);
	    this.roadType=type;
	}

}
