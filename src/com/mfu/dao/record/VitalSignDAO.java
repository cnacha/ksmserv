package com.mfu.dao.record;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.mfu.entity.record.VitalSignRecord;


public class VitalSignDAO {
	private static String ENTITY_NAME = "VitalSignRecord";
	
	private DatastoreService ds;
	public VitalSignDAO() {
		ds = DatastoreServiceFactory.getDatastoreService();
	}
	

	public VitalSignRecord findLastVitalSignByHN(String key) {
		// get latest recorded date
		Filter codeHNFilter = new FilterPredicate("codeHN", FilterOperator.EQUAL, key);
		Query q = new Query(ENTITY_NAME).setFilter(codeHNFilter).addSort("updateDate", SortDirection.DESCENDING);
		List<Entity> result = ds.prepare(q).asList(FetchOptions.Builder.withDefaults());
		if(result!=null && result.size()>0)
			return convertEntityToVitalSign(result.get(0));
		else
			return null;
		
	}
	
	public VitalSignRecord convertEntityToVitalSign(Entity entity){
		
		if(entity!=null){
			
			Map<String, Object> propMap = entity.getProperties();
			Iterator propSet = propMap.keySet().iterator();
			HashMap<String, Double> measuresMap = new HashMap<String, Double>();
			String propName;
			Double propValue;
			while(propSet.hasNext()){
				propName = (String)propSet.next();
			//	System.out.println("prop "+propName);
				if(propName.equals("codeHN") || propName.equals("updateDate"))
					continue;
				propValue = (Double)propMap.get(propName);
				measuresMap.put(propName, propValue);
			}
			VitalSignRecord rec = new VitalSignRecord();
			rec.setCodeHN((String)entity.getProperty("codeHN"));
			rec.setUpdateDate((Date)entity.getProperty("updateDate"));
			rec.setMeasures(measuresMap);
			return rec;
		} else
			return null;
	}
	
	public void insertVitalSign(VitalSignRecord v) {
		Entity e = new Entity(ENTITY_NAME);
		e.setProperty("codeHN", v.getCodeHN());
		e.setProperty("updateDate", v.getUpdateDate());
		HashMap<String, Double> map = v.getMeasures();
		for(String key : map.keySet()) {
			e.setProperty(key, map.get(key));
		}
		ds.put(e);
	}
	

}
