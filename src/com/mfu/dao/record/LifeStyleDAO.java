package com.mfu.dao.record;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.mfu.entity.nutrition.Consumption;
import com.mfu.entity.record.ActivityType;
import com.mfu.entity.record.LifeStyleRecord;

public class LifeStyleDAO {
	private static String ENTITY_NAME = "LifeStyleRecord";
	
	private DatastoreService ds;
	public LifeStyleDAO() {
		ds = DatastoreServiceFactory.getDatastoreService();
	}
	

	public LifeStyleRecord findLastRecordByHN(String codeHN) {
		// get latest recorded date
		Filter codeHNFilter = new FilterPredicate("codeHN", FilterOperator.EQUAL, codeHN);
		Query q = new Query(ENTITY_NAME).setFilter(codeHNFilter).addSort("updateDate", SortDirection.DESCENDING);
		List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(result!=null && result.size()>0)
			return convertEntityToLifeStyle(result.get(0));
		else
			return null;
		
	}
	
	public LifeStyleRecord findRecordByDate(String codeHN, Date findDate){
		
		// reset time
	
		findDate.setHours(0);
		findDate.setMinutes(0);
		findDate.setSeconds(0);
		
		Date nextDate = new Date(findDate.getTime()+(24 * 60 * 60 * 1000));
	//	System.out.println(findDate.toString());
	//	System.out.println(nextDate.toString());
		Filter codeHNFilter = new FilterPredicate("codeHN", FilterOperator.EQUAL, codeHN);
		Filter fromDateFilter = new FilterPredicate("updateDate", FilterOperator.GREATER_THAN_OR_EQUAL, findDate);
		Filter toDateFilter = new FilterPredicate("updateDate", FilterOperator.LESS_THAN, nextDate);
		Filter combinedFilters =
			    CompositeFilterOperator.and(fromDateFilter, toDateFilter,codeHNFilter);
		Query q = new Query(ENTITY_NAME).setFilter(combinedFilters);
		List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(result!=null && result.size()>0)
			return convertEntityToLifeStyle(result.get(0));
		else
			return null;

	}
	
	public LifeStyleRecord convertEntityToLifeStyle(Entity entity){
		
		if(entity!=null){
			
			Map<String, Object> propMap = entity.getProperties();
			Iterator propSet = propMap.keySet().iterator();
			HashMap<String, Double> measuresMap = new HashMap<String, Double>();
			String propName;
			Double propValue;
			ActivityTypeDAO actDAO = new ActivityTypeDAO();
			ActivityType actType;
			while(propSet.hasNext()){
				propName = (String)propSet.next();
				//System.out.println("prop "+propName);
				if(propName.equals("codeHN") || propName.equals("updateDate"))
					continue;
				
				propValue = (Double)propMap.get(propName);
				//resolve type description
				// actType = actDAO.findActivityTypeByCode(propName);
				// if(actType!=null)
				//	 propName = actType.getDescription();
				measuresMap.put(propName, propValue);
			}
			actDAO.closeEntityManager();
			LifeStyleRecord rec = new LifeStyleRecord();
			rec.setKeyString(KeyFactory.keyToString(entity.getKey()));
			rec.setCodeHN((String)entity.getProperty("codeHN"));
			rec.setUpdateDate((Date)entity.getProperty("updateDate"));
			rec.setMeasures(measuresMap);
			return rec;
		} else
			return null;
	}
	
	public static HashMap resolveCodeName(HashMap propMap){
		ActivityTypeDAO actDAO = new ActivityTypeDAO();
		Iterator propSet = propMap.keySet().iterator();
		ActivityType actType;
		HashMap resultMap = new HashMap();
		String propName;
		Object propValue;
		while(propSet.hasNext()){
			propName = (String)propSet.next();
			propValue = propMap.get(propName);
			//resolve type description
			actType = actDAO.findActivityTypeByCode(propName);
			 if(actType!=null)
				 propName = actType.getDescription();
			 
			 resultMap.put(propName, propValue);
		}
		return resultMap;
	}
	
	public void saveRecord(LifeStyleRecord v) {
		Entity e;
		if(v.getKeyString()!=null){
			try {
				e = ds.get(KeyFactory.stringToKey(v.getKeyString()));
			} catch (EntityNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				e = new Entity(ENTITY_NAME); 
			}
		} else{
			e = new Entity(ENTITY_NAME);
		}
		e.setProperty("codeHN", v.getCodeHN());
		e.setProperty("updateDate", v.getUpdateDate());
		HashMap<String, Double> map = v.getMeasures();
		for(String key : map.keySet()) {
			e.setProperty(key, map.get(key));
		}
		ds.put(e);
	}
	

}
