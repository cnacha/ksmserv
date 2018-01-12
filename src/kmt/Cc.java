package kmt;

import java.util.ArrayList;
import java.util.List;

public class Cc {
	public List<Pp> points;
	public Pp centroid;
	public int id;
	
	//Creates a new Cluster
	public Cc(int id) {
		this.id = id;
		this.points = new ArrayList<Pp>();
		this.centroid = null;
	}
 
	public List getPoints() {
		return points;
	}
	
	public void addPoint(Pp point) {
		points.add(point);
	}
 
	public void setPoints(List points) {
		this.points = points;
	}
 
	public Pp getCentroid() {
		return centroid;
	}
 
	public void setCentroid(Pp centroid) {
		this.centroid = centroid;
	}
 
	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	
	public void plotCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for(Pp p : points) {
			System.out.println(p);
		}
		System.out.println("]");
	}
}
