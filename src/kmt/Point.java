package kmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {
	private double coordinate[];
	
	private int cluster_number = 0;

	public Point(){
		
	}
	public Point(double[] coordinate) {
		this.coordinate = coordinate;
	}
	public Point(Double[] coordinate){
		double[] c = new double[coordinate.length];
		for(int i=0; i<coordinate.length; i++){
			c[i] = coordinate[i];
		}
		this.coordinate = c;
	}

	public double[] getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(double[] coordinate) {
		this.coordinate = coordinate;
	}
	public void setCluster(int n) {
		this.cluster_number = n;
	}

	public int getCluster() {
		return this.cluster_number;
	}
	
	public static double distance(Point p, Point centroid) {
		double sum = 0;
		for(int i=0; i< p.getCoordinate().length; i++){
			sum += Math.pow((p.getCoordinate()[i] - centroid.getCoordinate()[i]), 2);
		}
		return Math.sqrt(sum);
	}
/**	
	public static Point setXaxisAndYaxis(double xaxis, double yaxis) {
		double x = xaxis;
		double y = yaxis;
		return new Point(x, y);
	}
	
	public static Point setXaxisAndYaxisD(double xaxis, double yaxis) {
		double x = xaxis;
		double y = yaxis;
		return new Point(x, y);
	}
**/	
	public Cluster findCluster(List<Cluster> clusters) {
		Cluster closestCluster = null;
		double closestDistance = Double.MAX_VALUE;
		double d;
		for (Cluster c : clusters) {
			d = this.distance(this, c.getCentroid());
			if (d < closestDistance) {
				closestDistance = d;
				closestCluster = c;
			}
			System.out.println("Y is " + c.getCentroid());
			System.out.println("distance from Cluster" + c.getId()%3 + " : " + d);
		}
		return closestCluster;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "(");
		for(double p: coordinate){
			sb.append( p+",");
		}
		sb.append(")");
		return sb.toString();
	}
}
