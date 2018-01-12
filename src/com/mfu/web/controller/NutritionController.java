package com.mfu.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.common.UserDAO;
import com.mfu.dao.nutrition.ConsumptionDAO;
import com.mfu.dao.nutrition.FoodDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.nutrition.Consumption;
import com.mfu.entity.nutrition.Food;
import com.mfu.web.model.WSResponse;

@Controller
public class NutritionController {
	
	private static final Logger log = Logger.getLogger(NutritionController.class
			.getName());
	
	@RequestMapping("/listFoodWS")
	public @ResponseBody List<Food> listFoodWS(HttpServletRequest request) {
		FoodDAO dao = new FoodDAO();

		List<Food> list = null;
		try {
			list = dao.getAllFood();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return list;
	}
	
	@RequestMapping("/searchFoodWS")
	public @ResponseBody List<Food> searchFoodWS(HttpServletRequest request) {
		FoodDAO dao = new FoodDAO();

		List<Food> list = null;
		String keyword = request.getParameter("keyword");
		log.info("searching keyword: "+keyword);
		try {
			if(keyword !=null && !keyword.equals(""))
				list = dao.findFood(request.getParameter("keyword"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return list;
	}
	
	@RequestMapping("/getFoodsByCodeWS")
	public @ResponseBody List<Food> getFoodsByCodeWS(@RequestBody String[] keys) {
		FoodDAO dao = new FoodDAO();

		List<Food> list = new ArrayList<Food>();
		try {
			Food f = null;
			for(String key: keys){
				f = dao.findFoodByCode(key);
				if(f!=null)
					list.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return list;
	}

	@RequestMapping("/saveFoodWS")
	public @ResponseBody String saveFoodWS(@RequestBody Food o) {
		FoodDAO dao = new FoodDAO();
		try {
			if (o.getKey() == null) {
				Food foundFood = dao.findFoodByFoodCode(o.getCode());
				if(foundFood!=null){
					o.setKey(foundFood.getKey());
					dao.updateFood(o);
					
				} else {
					dao.insertFood(o);
				}
			} else {
				dao.updateFood(o);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping(value = "/insertFoodBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> insertFoodBatch(@RequestBody Food[] list, HttpServletRequest request){
		List<WSResponse> resList = new ArrayList<WSResponse>();
		String result;
		for (Food d : list) {
			
			result = this.saveFoodWS(d);
			resList.add(new WSResponse(d.getCode()+" "+d.getName(), Integer.parseInt(result)));
		}

		return resList;
	}

	@RequestMapping("/deleteFoodWS")
	public @ResponseBody String deleteFoodWS(HttpServletRequest request) {
		FoodDAO dao = new FoodDAO();
		try {
			dao.deleteFood(request.getParameter("key"));
			dao.closeEntityManager();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}
	
	@RequestMapping("/getConsumptionSummaryWS")
	public @ResponseBody Consumption getConsumptionSummary(HttpServletRequest request) {
		ConsumptionDAO dao = new ConsumptionDAO();

		Consumption sum = new Consumption();
		String patientKey = request.getParameter("patientKey");
		String dateStr = request.getParameter("date");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date findDate = formatter.parse(dateStr);
			List<Consumption> list = dao.findConsumptionByMeal(patientKey, findDate, null);
			sum.setConsumptionTime(findDate);
			for(Consumption c: list){
				sum.setCalories(c.getCalories()+sum.getCalories());
				sum.setFiber(c.getFiber()+sum.getFiber());
				sum.setProtein(c.getProtein()+sum.getProtein());
				sum.setFat(c.getFat()+sum.getFat());
				sum.setCarbohydrates(c.getCarbohydrates()+sum.getCarbohydrates());
				sum.setSugars(c.getSugars()+sum.getSugars());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return sum;
	}
	
	@RequestMapping("/getConsumptionWeeklyWS")
	public @ResponseBody List<Consumption> getConsumptionWeeklyWS(HttpServletRequest request) {
		ConsumptionDAO dao = new ConsumptionDAO();
		UserDAO uDAO = new UserDAO();
		
		String patientKey = request.getParameter("patientKey");
		if(patientKey == null)
			patientKey = (String)request.getAttribute("patientKey");
		if(patientKey == null){
			User user = uDAO.findUserByCodeHN(request.getParameter("codeHN"));
			if(user!=null)
				patientKey = user.getKeyString();
			else 
				return null;
		}
			
		
		List<Consumption> cList = new ArrayList<Consumption>();
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
			Date findDate = dateOnly.parse(dateOnly.format(cal.getTime()));
			long nextDate;
			Consumption sum;
			for(int i=0; i<7; i++){
				sum = new Consumption();
				sum.setMealType("summary");
				List<Consumption> list = dao.findConsumptionByMeal(patientKey, findDate, null);
				sum.setPatientKey(patientKey);
				sum.setConsumptionTime(findDate);
				for(Consumption c: list){
					sum.setCalories(c.getCalories()+sum.getCalories());
					sum.setFiber(c.getFiber()+sum.getFiber());
					sum.setProtein(c.getProtein()+sum.getProtein());
					sum.setFat(c.getFat()+sum.getFat());
					sum.setCarbohydrates(c.getCarbohydrates()+sum.getCarbohydrates());
					sum.setSugars(c.getSugars()+sum.getSugars());
				}
				log.info("calories limit "+ sum.getCaloriesLimit());
				//System.out.println("		"+sum.getConsumptionTime());
				cList.add(sum);
				nextDate = findDate.getTime()-(24 * 60 * 60 * 1000);
				findDate = new Date(nextDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return cList;
	}
	
	
	
	@RequestMapping("/listConsumptionWS")
	public @ResponseBody List<Consumption> listConsumptionWS(HttpServletRequest request) {
		ConsumptionDAO dao = new ConsumptionDAO();

		List<Consumption> list = null;
		String patientKey = request.getParameter("patientKey");
		String dateStr = request.getParameter("date");
		String mealType = request.getParameter("mealType");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date findDate = formatter.parse(dateStr);
			list = dao.findConsumptionByMeal(patientKey, findDate, mealType);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		
		return list;
	}
	
	@RequestMapping("/recordConsumptionWS")
	public @ResponseBody String recordConsumptionWS(@RequestBody Consumption o) {
		ConsumptionDAO dao = new ConsumptionDAO();
		try {
			if (o.getKey() == null) {
					dao.insertConsumption(o);
			} else {
				dao.updateConsumption(o);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deleteConsumptionWS")
	public @ResponseBody String deleteConsumptionWS(HttpServletRequest request) {
		ConsumptionDAO dao = new ConsumptionDAO();
		try {
			dao.deleteConsumption(request.getParameter("key"));
			dao.closeEntityManager();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
