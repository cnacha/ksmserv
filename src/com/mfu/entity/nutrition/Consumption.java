package com.mfu.entity.nutrition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.dao.common.UserDAO;
import com.mfu.dao.record.LifeStyleDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.dao.record.VitalSignDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.record.LifeStyleRecord;
import com.mfu.entity.record.Patient;
import com.mfu.entity.record.VitalSignRecord;
import com.mfu.util.DateTimeDeserializer;
import com.mfu.util.DateTimeSerializer;
import com.mfu.web.controller.HealthAnalysisController;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consumption implements Serializable{
	
	private static final Logger log = Logger
			.getLogger(Consumption.class.getName());
	
	public static final String MEALTYPE_BREAKFAST = "breakfast";
	public static final String MEALTYPE_LUNCH = "lunch";
	public static final String MEALTYPE_DINNER = "dinner";
	public static final String MEALTYPE_SNACK = "snack";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Key key;
	
	private String foodCode;
	private String foodName;
	private String description;
	private double calories;
	private double protein;
	private double fiber;
	private double fat;
	private double carbohydrates;
	private double sugars;
	
	private String patientKey;
	
	private double caloriesLimit = 0;
	
	@JsonDeserialize(using = DateTimeDeserializer.class)
	@JsonSerialize(using = DateTimeSerializer.class)
	private Date consumptionTime;
	
	private int unitCount;
	private String mealType;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	
	public String getFoodCode() {
		return foodCode;
	}

	public void setFoodCode(String foodCode) {
		this.foodCode = foodCode;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFiber() {
		return fiber;
	}

	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public double getSugars() {
		return sugars;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public String getKeyString() {
		if(key!=null)
			return KeyFactory.keyToString(key);
		else
			return null;
	}	

	public void setKeyString(String keyString) {
		key = KeyFactory.stringToKey(keyString);
	}

	public int getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(int unitCount) {
		this.unitCount = unitCount;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public Date getConsumptionTime() {
		return consumptionTime;
	}

	public void setConsumptionTime(Date consumptionTime) {
		this.consumptionTime = consumptionTime;
	}

	public String getPatientKey() {
		return patientKey;
	}

	public void setPatientKey(String patientKey) {
		this.patientKey = patientKey;
	}
	
	
	
	public double getCaloriesLimit() {
		if(this.caloriesLimit!=0)
			return this.caloriesLimit;
		
		if(this.mealType!=null && this.mealType.equals("summary")){
			// caculateREE
			UserDAO pDAO = new UserDAO();		
			User p = pDAO.findUserByKey(this.patientKey);
			pDAO.closeEntityManager();
			VitalSignDAO vDAO = new VitalSignDAO();
			log.info(this.patientKey+" found pateint"+p);
			if(p!=null){
				VitalSignRecord vs = vDAO.findLastVitalSignByHN(p.getCodeHN());
				double ree = vs.getREE();
				LifeStyleDAO ldao = new LifeStyleDAO();
				Date findDate = this.consumptionTime;
				List<LifeStyleRecord> lifestyleList = new ArrayList<LifeStyleRecord>();
				for(int i=0; i<7; i++){
					LifeStyleRecord rec = ldao.findRecordByDate(p.getCodeHN(), findDate);
					if(rec==null)
						rec = new LifeStyleRecord();
					lifestyleList.add(rec);
					
					findDate = new Date(findDate.getTime()-(24 * 60 * 60 * 1000));
				}
				int exercisenum =  LifeStyleRecord.calculateExerciseNumber(lifestyleList);
				log.info("calculate limit "+exercisenum+" "+ree);
				if(exercisenum>7){
					return ree * 1.9;
				} else if(exercisenum >=6){
					return ree * 1.725;
				} else if(exercisenum >=3){
					return ree * 1.55;
				} else if(exercisenum >=1){
					return ree * 1.375;
				} else {
					return ree * 1.2;
				}
			}
		}
		return 0;
		
	}


}
