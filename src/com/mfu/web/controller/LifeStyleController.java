package com.mfu.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.mfu.dao.nutrition.ConsumptionDAO;
import com.mfu.dao.record.ActivityTypeDAO;
import com.mfu.dao.record.ClinicDAO;
import com.mfu.dao.record.LifeStyleDAO;
import com.mfu.entity.nutrition.Consumption;
import com.mfu.entity.record.ActivityType;
import com.mfu.entity.record.Clinic;
import com.mfu.entity.record.LifeStyleRecord;
import com.mfu.web.model.WSResponse;

@Controller
public class LifeStyleController {

	private static final Logger log = Logger
			.getLogger(LifeStyleController.class.getName());

	@RequestMapping(value = "/recordLifeStyleWS", method = RequestMethod.POST)
	@ResponseBody
	public String recordLifestyle(@RequestBody LifeStyleRecord v) {
		try {
			log.info("recordLifestyle called: "+v.getUpdateDate());
			if(v.getUpdateDate() == null){
				v.setUpdateDate(new Date());
			}
			LifeStyleDAO dao = new LifeStyleDAO();
			LifeStyleRecord rec = dao.findRecordByDate(v.getCodeHN(), new Date(v.getUpdateDate().getTime()));
			//log.info("found "+rec.getUpdateDate());
			if(rec!=null){
				HashMap<String, Double> passinMeasureMap = v.getMeasures();
				HashMap<String, Double> savedMeasureMap = rec.getMeasures();
				savedMeasureMap.putAll(passinMeasureMap);
				rec.setMeasures(savedMeasureMap);
				rec.setUpdateDate(v.getUpdateDate());
				dao.saveRecord(rec);
				
			} else 
				dao.saveRecord(v);
				
			
			//HealthAnalysisController kc = new HealthAnalysisController();
			//kc.classifySymptomCloudML(v);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());

		}
		return "-1";
	}
	
	@RequestMapping(value = "/geLifeStyleByDateWS", method = RequestMethod.GET)
	@ResponseBody
	public LifeStyleRecord geLifeStyleByDateWS(HttpServletRequest request) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date rdate = formatter.parse(request.getParameter("rdate"));
			
			LifeStyleDAO dao = new LifeStyleDAO();
			LifeStyleRecord rec = dao.findRecordByDate(request.getParameter("codeHN"), rdate);
			rec.setMeasures(LifeStyleDAO.resolveCodeName(rec.getMeasures()));
			return rec;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getLifeStyleWeeklyWS")
	public @ResponseBody HashMap<String, String[]> getLifeStyleWeeklyWS(HttpServletRequest request) {
		LifeStyleDAO dao = new LifeStyleDAO();

		
		String codeHN = request.getParameter("codeHN");
		List<LifeStyleRecord> cList = new ArrayList<LifeStyleRecord>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -4);
			SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
			Date findDate = dateOnly.parse(dateOnly.format(cal.getTime()));
			long nextDate;
			LifeStyleRecord rec;
			HashMap<String, Double> summary = new HashMap<String, Double>();
			HashMap<String, Double> allMeasures = new HashMap<String, Double>();
			List<String> dateList = new ArrayList<String>();
			
			for(int i=0; i<5; i++){
				rec =  dao.findRecordByDate(codeHN, findDate);
				dateList.add(dateOnly.format(findDate));
				if(rec==null){
					rec = new LifeStyleRecord();
					rec.setUpdateDate(findDate);
				} else
					allMeasures.putAll(rec.getMeasures());
				cList.add(rec);
				findDate = new Date(findDate.getTime()+(24 * 60 * 60 * 1000));
			}
			
			
			
			
			Iterator it = allMeasures.entrySet().iterator();
			HashMap<String, String[]> result = new HashMap<String, String[]>();
			// query execise value
			 List<String> valueArray = new ArrayList<String>();
			for(LifeStyleRecord r: cList){
				valueArray.add(""+r.getHour(LifeStyleRecord.ID_PREFIX_ACTIVITY_EXERCISE));
			}
			result.put("exercise", valueArray.toArray(new String[valueArray.size()]));
			
			// query work value
			valueArray.clear();
			for(LifeStyleRecord r: cList){
				valueArray.add(""+r.getHour(LifeStyleRecord.ID_PREFIX_ACTIVITY_WORK));
			}
			result.put("work", valueArray.toArray(new String[valueArray.size()]));
			
			// query sleep value
			valueArray.clear();
			for(LifeStyleRecord r: cList){
				valueArray.add(""+r.getHour(LifeStyleRecord.ID_PREFIX_ACTIVITY_SLEEP));
			}
			result.put("sleep", valueArray.toArray(new String[valueArray.size()]));
			
//		    while (it.hasNext()) {
//		        Map.Entry pair = (Map.Entry)it.next();
//		       
//		        List<String> valueArray = new ArrayList<String>();
//		        for(LifeStyleRecord r: cList){
//		        	if(r.getMeasures()!=null){
//		        		Double value = r.getMeasures().get(pair.getKey());
//		        		if(value !=null )
//		        			valueArray.add(""+value);
//		        		else
//		        			valueArray.add(new String("0.0"));
//		        	}else
//		        		valueArray.add(new String("0.0"));
//
//		        }
//		        //System.out.println( + " = " + valueArray.toString());
//		        result.put((String)pair.getKey(), valueArray.toArray(new String[valueArray.size()]));
//		        it.remove(); // avoids a ConcurrentModificationException
//		       
//		    }
		    //result = LifeStyleDAO.resolveCodeName(result);
		    result.put("rdate", dateList.toArray(new String[dateList.size()]));
		    return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	

	@RequestMapping(value = "/getLatestLifeStyleWS", method = RequestMethod.GET)
	@ResponseBody
	public LifeStyleRecord getLastRecord(HttpServletRequest request) {
		try {
			LifeStyleDAO dao = new LifeStyleDAO();
			LifeStyleRecord rec = dao.findLastRecordByHN(request
					.getParameter("codeHN"));
			return rec;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/listActivityTypeWS")
	public @ResponseBody List<ActivityType> listActivityTypeWS(HttpServletRequest request) {
		ActivityTypeDAO dao = new ActivityTypeDAO();

		List<ActivityType> list = null;
		try {
			list = dao.getAllActivityType();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return list;
	}
	
	@RequestMapping("/saveActivityTypeBatchWS")
	public @ResponseBody ActivityType[] saveActivityTypeWS(@RequestBody ActivityType[] list) {
		

		try {
			for(ActivityType o: list){
				ActivityTypeDAO dao = new ActivityTypeDAO();
				dao.insertActivityType(o);
				dao.closeEntityManager();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
