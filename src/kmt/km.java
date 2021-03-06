package kmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mfu.dao.ha.ImpacFactorClusterDAO;
import com.mfu.entity.ha.ImpacFactorCluster;

import kmt.Cc;
import kmt.Pp;

public class km {
    
    private static int NUM_CLUSTERS = 3;
    
    private List<Pp> points;
    private List<Cc> clusters;
    private int diseaseLevel;
    
    public km() {
    	this.points = new ArrayList<Pp>();
    	this.clusters = new ArrayList<Cc>();    	
    }

	public void init(Pp[] initialPoint,Pp[] samplingPoint) {
		
		points = new ArrayList<Pp>();
		for (int i = 0; i< samplingPoint.length; i++) {			
    		Pp point = Pp.setXaxisAndYaxis(samplingPoint[i].getX(), samplingPoint[i].getY());
    		points.add(point);
    		
    	}
		for (int i = 0; i< initialPoint.length; i++) {			
    		Pp point = Pp.setXaxisAndYaxis(initialPoint[i].getX(),initialPoint[i].getY());
    		points.add(point);
    		
    	}
		System.out.println(" 1Number of sample points "+points.size());
    	//Create Centroids
    	for (int i = 0; i< NUM_CLUSTERS; i++) {
    		Cc cluster = new Cc(i);
    		
    		Pp centroid = Pp.setXaxisAndYaxis(initialPoint[i].getX(), initialPoint[i].getY());
    		System.out.println("adding centroid "+centroid.toString());
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	System.out.println(" 12Number of sample points "+points.size());
	}
	
	 public List<Pp> learn(String key,String impactName,String symptomName) {
	        boolean finish = false;
	        int iteration = 0;
	        // Add in new data, one at a time, recalculating centroids with each new one. 
	        List<Pp> lastCentroids = new ArrayList<Pp>();
	        while(!finish) {
	        	clearClusters();
	        	lastCentroids = getCentroids();
	        	assignCluster();
	            
	        	calculateCentroids();
	        	
	        	iteration++;
	        	
	        	List<Pp> currentCentroids = getCentroids();
	        	
	        	double distance = 0;
	        	for(int i = 0; i < lastCentroids.size(); i++) {
	        		distance += Pp.distance(lastCentroids.get(i),currentCentroids.get(i));
	        	}
	        	System.out.println("#################");
	        	System.out.println("Iteration: " + iteration);
	        	System.out.println("Centroid distances: " + distance);
	        	plotClusters();	        	     	
	        	    
	        	if(distance == 0) {
	        		finish = true;
	        		System.out.println("\nNormal :"+lastCentroids.get(0)+"\nRisk :"+lastCentroids.get(1)
		        	+"\nDisease :"+lastCentroids.get(2)+"\n");
	        /*				
	        		ImpacFactorClusterDAO imold = new ImpacFactorClusterDAO();
	        			try {
	        				if(imold.findClusteringByImpactFactorID(key) == null){
	        					for(int i=0;i < lastCentroids.size();i++){      						
	        						ImpacFactorClusterDAO imserv = new ImpacFactorClusterDAO();
	        	        			ImpacFactorCluster impact = new ImpacFactorCluster();
	        						impact.setImpactFactorID(key);
	        						impact.setImName(impactName);
	        						impact.setSymptomName(symptomName);
	        						impact.setCentroidX(lastCentroids.get(i).getX());
	        						impact.setCentroidY(lastCentroids.get(i).getY());
	        						impact.setLevel(i);
	        						imserv.saveClustering(impact);
	        						imserv.closeEntityManager();
	        					}	
	        				}else{
	        					for(int i=0;i < lastCentroids.size();i++){
	        						ImpacFactorClusterDAO imserv = new ImpacFactorClusterDAO();
	        	        			ImpacFactorCluster impact = new ImpacFactorCluster();
	        	        			impact.setKey(imold.findClusteringByLevel(i).getKey());
	        						impact.setImpactFactorID(imold.findClusteringByLevel(i).getImpactFactorID());
	        						impact.setImName(impactName);
	        						impact.setSymptomName(symptomName);
	        						impact.setCentroidX(lastCentroids.get(i).getX());
	        						impact.setCentroidY(lastCentroids.get(i).getY());
	        						impact.setLevel(i);
	        						imserv.updateClustering(impact);
	        						imserv.closeEntityManager();
	        					}
	        				}
	        			
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        		}finally{
	        			imold.closeEntityManager();
	        		}
	        		
	        		*/
	        	}
	        }
	        return lastCentroids;
	    }
	 
	 private void plotClusters() {
	    	for (int i = 0; i < NUM_CLUSTERS; i++) {
	    		Cc c = clusters.get(i);
	    		c.plotCluster();
	    	}
	    }
	 
	 private void clearClusters() {
	    	for(Cc cluster : clusters) {
	    		cluster.clear();
	    	}
	    }
	    
	    private List getCentroids() {
	    	List centroids = new ArrayList(NUM_CLUSTERS);
	    	for(Cc cluster : clusters) {
	    		Pp aux = cluster.getCentroid();
	    		Pp point = new Pp(aux.getX(),aux.getY());
	    		centroids.add(point);
	    	}
	    	return centroids;
	    }
	    
	    private void assignCluster() {
	    	
	    	
	        double max = Double.MAX_VALUE;
	        double min = max; 
	        int cluster = 0;                 
	        double distance = 0.0; 
	   //     System.out.println("	#### Assign Cluster	##### "+points.size());
	        for(Pp point : points) {
	        	min = max;
	     //   	System.out.println("		point: "+point.toString());
	            for(int i = 0; i < NUM_CLUSTERS; i++) {
	            	Cc c = clusters.get(i);
	        //    	System.out.println("		Compare to centroid: "+c.getCentroid().toString());
	                distance = Pp.distance(point, c.getCentroid());
	                if(distance < min){
	                    min = distance;
	                    cluster = i;
	                   
	                }
	            }
	       //     System.out.println("	FINDING CLuster: "+point.toString()+" cluster: "+cluster);
	            point.setCluster(cluster);
	            clusters.get(cluster).addPoint(point);
	        }
	    }
	    
	    private void calculateCentroids() {
	        for(Cc cluster : clusters) {
	            double sumX = 0;
	            double sumY = 0;
	            List<Pp> list = cluster.getPoints();
	            int n_points = list.size();
	            
	            for(Pp point : list) {
	            	sumX += point.getX();
	                sumY += point.getY();
	            }
	            
	            Pp centroid = cluster.getCentroid();
	            if(n_points > 0) {
	            	double newX = sumX / n_points;
	            	double newY = sumY / n_points;
	                centroid.setX(newX);
	                centroid.setY(newY);
	            }
	        }
	    }

		public List<Pp> getPoints() {
			return points;
		}

		public void setPoints(List<Pp> points) {
			this.points = points;
		}

		public List<Cc> getClusters() {
			return clusters;
		}

		public void setClusters(List<Cc> clusters) {
			this.clusters = clusters;
		}
}