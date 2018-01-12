package kmt;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KMeanEngine {
	
	private static final Logger log = Logger.getLogger(KMeanEngine.class.getName());
    
    private static int NUM_CLUSTERS = 3;
    private static int NUM_DIMENSION = 2;
    
    private List<Point> points;
    private List<Cluster> clusters;
    
    public KMeanEngine() {
    	this.points = new ArrayList<Point>();
    	this.clusters = new ArrayList<Cluster>();    	
    }

	public void init(Point[] initialPoint, Point[] samplingPoint) {
		NUM_DIMENSION = initialPoint[0].getCoordinate().length;
		points = new ArrayList<Point>();
		for (int i = 0; i< samplingPoint.length; i++) {			
    		points.add(new Point(samplingPoint[i].getCoordinate()));
    		
    	}
		for (int i = 0; i< initialPoint.length; i++) {			
    		points.add(new Point(initialPoint[i].getCoordinate()));
    		
    	}
		log.info("Initial Number of sample points "+points.size());
    	//Create Centroids
    	for (int i = 0; i< NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		log.info("	adding centroid "+initialPoint[i].toString());
    		cluster.setCentroid(new Point(initialPoint[i].getCoordinate()));
    		clusters.add(cluster);
    	}
    	//log.info(" 12Number of sample points "+points.size());
	}
	
	 public List<Point> learn() {
	        boolean finish = false;
	        int iteration = 0;
	        // Add in new data, one at a time, recalculating centroids with each new one. 
	        List<Point> lastCentroids = new ArrayList<Point>();
	        while(!finish) {
	        	clearClusters();
	        	
	        	lastCentroids = getCentroids();
	        	log.info("AFTER getCentroids");
	        	this.plotClusters();
	        	assignCluster();
	        	log.info("AFTER assignCluster");
	        	this.plotClusters();
	        	calculateCentroids();
	        	log.info("AFTER CALCULATION");
	        	this.plotClusters();
	        	
	        	iteration++;
	        	
	        	List<Point> currentCentroids = getCentroids();
	        	double distance = 0;
	        	for(int i = 0; i < lastCentroids.size(); i++) {
	        	
	        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
	        	}
	        	log.info("#################################");
	        	log.info("Iteration: " + iteration);
	        	log.info("Centroid distances: " + distance);
	        	plotClusters();	        	     	
	        	    
	        	if(distance == 0) {
	        		finish = true;
	        		log.info("\nNormal :"+lastCentroids.get(0)+"\nRisk :"+lastCentroids.get(1)
		        	+"\nDisease :"+lastCentroids.get(2)+"\n");
	      
	        	}
	        }
	        return lastCentroids;
	    }
	 
	 private void plotClusters() {
	    	for (int i = 0; i < NUM_CLUSTERS; i++) {
	    		Cluster c = clusters.get(i);
	    		c.plotCluster();
	    	}
	    }
	 
	 private void clearClusters() {
	    	for(Cluster cluster : clusters) {
	    		cluster.clear();
	    	}
	    }
	    
	    private List getCentroids() {
	    	List centroids = new ArrayList(NUM_CLUSTERS);
	    	for(Cluster cluster : clusters) {
	    		Point aux = cluster.getCentroid();
	    		log.info("	getCentroids() "+aux);
	    		Point point = new Point(aux.getCoordinate());
	    		centroids.add(point);
	    	}
	    	return centroids;
	    }
	    
	    private void assignCluster() {
	        double max = Double.MAX_VALUE;
	        double min = max; 
	        int cluster = 0;                 
	        double distance = 0.0; 
	    //    log.info("	#### Assign Cluster	##### "+points.size());
	        for(Point point : points) {
	        	min = max;
	  //      	log.info("		point: "+point.toString());
	            for(int i = 0; i < NUM_CLUSTERS; i++) {
	              Cluster c = clusters.get(i);
	 //            log.info("		Compare to centroid: "+c.getCentroid().toString());
	              distance = Point.distance(point, c.getCentroid());
	                if(distance < min){
	                    min = distance;
	                    cluster = i;
	                   
	                }
	            }
	//            log.info("	FINDING CLuster: "+point.toString()+" cluster: "+cluster);
	            point.setCluster(cluster);
	            clusters.get(cluster).addPoint(point);
	        }
	    }
	    
	    private void calculateCentroids() {
	        for(Cluster cluster : clusters) {
	            double[] avgCoordinate = new double[NUM_DIMENSION];
	            List<Point> list = cluster.getPoints();
	            int n_points = list.size();
	            
	            for(int i=0; i<NUM_DIMENSION; i++){
	            	double sum = 0;
		            for(Point point : list) {
		            	 sum+=point.getCoordinate()[i];
		            }
		            avgCoordinate[i] = sum / n_points;
	            }
	            
	            Point centroid = cluster.getCentroid();
	            if(n_points > 0) {
	            	 centroid.setCoordinate(avgCoordinate);
	            	 cluster.setCentroid(centroid);
	            }
	    //        log.info("	recalculated centroid: "+centroid.toString());
	        }
	    }

		public List<Point> getPoints() {
			return points;
		}

		public void setPoints(List<Point> points) {
			this.points = points;
		}

		public List<Cluster> getClusters() {
			return clusters;
		}

		public void setClusters(List<Cluster> clusters) {
			this.clusters = clusters;
		}
}