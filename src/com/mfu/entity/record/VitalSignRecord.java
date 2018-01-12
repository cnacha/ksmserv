package com.mfu.entity.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import kmt.Point;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mfu.dao.record.PatientDAO;
import com.mfu.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalSignRecord {

	private String codeHN;
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date updateDate;
	
	private HashMap<String, Double> measures;
	
	public String getCodeHN() {
		return codeHN;
	}
	public void setCodeHN(String codeHN) {
		this.codeHN = codeHN;
	}
	
	
	public double getMeasureValue(String property){
		return measures.get(property);
	}
	
	public void addMeasureValue(String s, Double d){
		measures.put(s, d);
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public HashMap<String, Double> getMeasures() {
		return measures;
	}
	public void setMeasures(HashMap<String, Double> measures) {
		this.measures = measures;
	}
	
	public double[] getCoordinatePoint(String[] propList){
		ArrayList<Double> coordinate = new ArrayList<Double>();
		for(String prop:propList){
		//	System.out.println(" getting prop:"+prop);
			try {
				coordinate.add(measures.get(prop));
			} catch (Exception e) {
			}
		}
		double[] result = new double[coordinate.size()];
		for(int i=0;i< coordinate.size();i++){
			result[i] = coordinate.get(i).doubleValue();
		}
		return result;
	}
	
	public String getBpAnalysis(){
		double bpDiastolic = this.measures.get("bpDiastolic");
		double bpSystolic = this.measures.get("bpSystolic");
		if(bpDiastolic <= 60 && bpSystolic <= 90 ){
			return "Low";
		} else if (bpDiastolic <= 80 && bpSystolic <= 120){
			return "Normal";
		} else if (bpDiastolic <= 90 && bpSystolic <= 140){
			return "Pre-high";
		} else if (bpDiastolic <= 100 && bpSystolic <= 190){
			return "High";
		} else 
			return "N/A";
	}
	
	public String getBodyAnalysis(){
		
		double bmi = this.getBMI();
		if(bmi >= 30)
			return "Obese";
		else if(bmi >= 25 && bmi <30)
			return "Overweight";
		else if(bmi >=18.5 && bmi<25)
			return "Normal";
		else if(bmi < 18.5)
			return "Underweight";
		else
			return "N/A";
				
	}
	
	public double getBMI(){
		double bodyWeight = this.measures.get("bodyWeight");
		double bodyHeight = this.measures.get("bodyHeight");
		double hm = bodyHeight / 100;
		return bodyWeight / (hm*hm); 
	}
	
	public double getREE(){
		double bodyWeight = this.measures.get("bodyWeight");
		double bodyHeight = this.measures.get("bodyHeight");
		PatientDAO dao = new PatientDAO();
		try {
			Patient patient = dao.findPatientsByHospitalNumber(codeHN);
			if(patient.getGender() != null && patient.getGender().equals("Female")){
				return (10 * bodyWeight) + (6.25 * bodyHeight) - (5 * patient.getAge()) - 161;
			} else {
				return (10 * bodyWeight) + (6.25 * bodyHeight) - (5 * patient.getAge()) + 5;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return 0;
	}
	
}
