package kmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pp {
	private double x = 0;
	private double y = 0;
	
	private int cluster_number = 0;

	public Pp(){
		
	}
	public Pp(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return this.x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return this.y;
	}

	public void setCluster(int n) {
		this.cluster_number = n;
	}

	public int getCluster() {
		return this.cluster_number;
	}
	
	protected static double distance(Pp p, Pp centroid) {
		return Math.sqrt(
				Math.pow((centroid.getY() - p.getY()), 2)
				+ Math.pow((centroid.getX() - p.getX()), 2)
				);
	}
	
	protected static Pp createRandomPoint(int min, int max) {
		Random r = new Random();
		double x = min + (max - min) * r.nextDouble();
		double y = min + (max - min) * r.nextDouble();
		return new Pp(x, y);
	}
	
	public static Pp setXaxisAndYaxis(double xaxis, double yaxis) {
		double x = xaxis;
		double y = yaxis;
		return new Pp(x, y);
	}
	
	public static Pp setXaxisAndYaxisD(double xaxis, double yaxis) {
		double x = xaxis;
		double y = yaxis;
		return new Pp(x, y);
	}
	
	public Cc findCluster(List<Cc> clusters) {
		Cc closestCluster = null;
		double closestDistance = Double.MAX_VALUE;
		double d;
		for (Cc c : clusters) {
			d = Pp.distance(this, c.getCentroid());
			if (d < closestDistance) {
				closestDistance = d;
				closestCluster = c;
			}
			System.out.println("Y is " + c.getCentroid().getY());
			System.out.println("distance from Cluster" + c.getId()%3 + " : " + d);
		}
		return closestCluster;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
