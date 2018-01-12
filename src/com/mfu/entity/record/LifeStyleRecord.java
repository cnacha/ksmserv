package com.mfu.entity.record;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mortbay.log.Log;

import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeSerializer;

public class LifeStyleRecord {
	
	public static final String ID_PREFIX_ACTIVITY_COMMON = "1";
	public static final String ID_PREFIX_ACTIVITY_EXERCISE = "2";
	public static final String ID_PREFIX_ACTIVITY_SLEEP = "101";
	public static final String ID_PREFIX_ACTIVITY_WORK = "102";
	
	private String keyString;
	
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
		//System.out.println("measures "+measures);
		if(measures!=null)
			return measures.get(property);
		else
			return 0;
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
	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	public static boolean isExercise(String actCode){
		if(actCode.startsWith(ID_PREFIX_ACTIVITY_EXERCISE))
			return true;
		else
			return false;
	}
	
	public static boolean isCommon(String actCode){
		if(actCode.startsWith(ID_PREFIX_ACTIVITY_COMMON))
			return true;
		else
			return false;
	}
	
	public int getHour(String prefixActivityTypeCode){
		int hour = 0;
		if(getMeasures()!=null){
			HashMap<String, Double> measures =new HashMap<String, Double>(this.getMeasures()) ;
			Iterator it = measures.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				if(((String)pair.getKey()).startsWith(prefixActivityTypeCode) ) {
					hour+= (Double)pair.getValue();
				}
			//	System.out.println(" calculateExerciseHour"+pair.getKey() + " = " + pair.getValue());
			}
		}
		return hour;
	}
	
	
	public static int calculateExerciseHour(List<LifeStyleRecord> lifeRecordList){
		int exerciseHour = 0;
		for(LifeStyleRecord rec: lifeRecordList){
			
			if(rec.getMeasures()!=null){
				HashMap<String, Double> measures =new HashMap<String, Double>(rec.getMeasures()) ;
				Iterator it = measures.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					if(LifeStyleRecord.isExercise((String)pair.getKey()) ) {
						exerciseHour+= (Double)pair.getValue();
					}
				//	System.out.println(" calculateExerciseHour"+pair.getKey() + " = " + pair.getValue());
				}
			}
		}
		return exerciseHour;
	}
	
	public static int calculateWorkHour(List<LifeStyleRecord> lifeRecordList){
		int hour = 0;
		for(LifeStyleRecord rec: lifeRecordList){
			
			if(rec.getMeasures()!=null){
				HashMap<String, Double> measures = new HashMap<String, Double>(rec.getMeasures()) ;
				Iterator it = measures.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					if(((String)pair.getKey()).startsWith(ID_PREFIX_ACTIVITY_WORK) ) {
						hour+= (Double)pair.getValue();
					}
				//	System.out.println(pair.getKey() + " = " + pair.getValue());
				}
			}
		}
		return hour;
	}
	
	public static int calculateSleepHour(List<LifeStyleRecord> lifeRecordList){
		int hour = 0;
		for(LifeStyleRecord rec: lifeRecordList){
			
			if(rec.getMeasures()!=null){
				HashMap<String, Double> measures = new HashMap<String, Double>(rec.getMeasures()) ;
				Iterator it = measures.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					if(((String)pair.getKey()).startsWith(ID_PREFIX_ACTIVITY_SLEEP) ) {
						hour+= (Double)pair.getValue();
					}
				//	System.out.println(" calculateSleepHour: "+pair.getKey() + " = " + pair.getValue());
				}
			}
		}
		return hour;
	}
	
	public static int calculateExerciseNumber(List<LifeStyleRecord> lifeRecordList){
		int exerciseNumber = 0;
		//Log.info("liferec size: "+lifeRecordList.size());
		for(LifeStyleRecord rec: lifeRecordList){
			//Log.info("    record: "+rec);
			
			if(rec.getMeasures()!=null ){
				HashMap<String, Double> measures = new HashMap<String, Double>(rec.getMeasures()) ;
				Iterator it = measures.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry)it.next();
					if(LifeStyleRecord.isExercise((String)pair.getKey()) ) {
						exerciseNumber+= 1;
					}
				//	System.out.println(pair.getKey() + " = " + pair.getValue());
				}
			}
		}
		return exerciseNumber;
	}
	
	
	
}
