package com.mfu.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mfu.dao.appointment.AppointmentDAO;
import com.mfu.dao.common.UserDAO;
import com.mfu.dao.nutrition.ConsumptionDAO;
import com.mfu.dao.record.DoctorDAO;
import com.mfu.entity.appointment.Appointment;
import com.mfu.entity.nutrition.Consumption;
import com.mfu.web.model.Dashboard;

@Controller
public class DashboardController {
	
	private static final Logger log = Logger.getLogger(DashboardController.class
			.getName());
	
	@RequestMapping("/getLifeStyleConsumptionWeeklyWS")
	public @ResponseBody String getLifeStyleConsumptionWeeklyWS(HttpServletRequest request) {
		
		NutritionController nc = new NutritionController();
		LifeStyleController lc = new LifeStyleController();
		List<Consumption> consumptionSummary = nc.getConsumptionWeeklyWS(request);
		HashMap<String, String[]> lifestyleSummary =  lc.getLifeStyleWeeklyWS(request);
		
		JSONObject result = new JSONObject();
		try {
			result.put("consumption", consumptionSummary);
			result.put("lifestyle", lifestyleSummary);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		log.info(result.toString());
		
		
		return result.toString();
	}

	@RequestMapping(value = "/dashboardData", method = RequestMethod.GET)
	public @ResponseBody Dashboard dashboardData() {

		Dashboard dashboard = new Dashboard();
		AppointmentDAO appointmentDAO = new AppointmentDAO();

		int totalPenddingAppointments = appointmentDAO.findAppointmentByStatus(Appointment.STATUS_PENDING).size();
		int totalApproveAppointments = appointmentDAO.findAppointmentByStatus(Appointment.STATUS_APPROVE).size();
		int totalCancelAppointments = appointmentDAO.findAppointmentByStatus(Appointment.STATUS_CANCEL).size();
		int totalConfirmAppointments = appointmentDAO.findAppointmentByStatus(Appointment.STATUS_CONFIRM).size();
		int totalVisitAppointments = appointmentDAO.findAppointmentByStatus(Appointment.STATUS_VISIT).size();
		
		int monday = 0;
		int tuesday = 0;
		int wendesday = 0;
		int thursday = 0;
		int friday = 0;
		int saturday = 0;
		int sunday = 0;
		
		List<Appointment> appList = appointmentDAO.getAllAppointment();
		appointmentDAO.closeEntityManager();
		for (Appointment appoint: appList) {
			String day = String.valueOf(appoint.getEndDateTime());
			String daySub = day.substring(0, 3);
			if(daySub.equalsIgnoreCase("mon")){
				monday++;
			}else if(daySub.equalsIgnoreCase("tue")){
				tuesday++;
			}else if(daySub.equalsIgnoreCase("wed")){
				wendesday++;
			}else if(daySub.equalsIgnoreCase("thu")){
				thursday++;
			}else if(daySub.equalsIgnoreCase("fri")){
				friday++;
			}else if(daySub.equalsIgnoreCase("sat")){
				saturday++;
			}else if(daySub.equalsIgnoreCase("sun")){
				sunday++;
			}
		}
		
		int[] weekday = {monday,tuesday,wendesday,thursday,friday,saturday,sunday};
		dashboard.setTotalAppointmentDayInWeek(weekday);
		

		dashboard.setTotalPenddingAppointments(totalPenddingAppointments);
		dashboard.setTotalApproveAppointments(totalApproveAppointments);
		dashboard.setTotalCancelAppointments(totalCancelAppointments);
		dashboard.setTotalConfirmAppointments(totalConfirmAppointments);
		dashboard.setTotalVisitAppointments(totalVisitAppointments);

		int totalAppointment = totalPenddingAppointments + totalApproveAppointments + totalCancelAppointments
				+ totalConfirmAppointments + totalVisitAppointments;
		dashboard.setTotalAppointment(totalAppointment);

		UserDAO userDAO = new UserDAO();
		dashboard.setTotalUsers(userDAO.findUserByRole("patient").size());
		userDAO.closeEntityManager();
		
		DoctorDAO doctorDAO = new DoctorDAO();
		dashboard.setTotalDoctors(doctorDAO.getAllDoctor().size());
		doctorDAO.closeEntityManager();

		int approvalPercentage = 0;
		if (totalAppointment != 0)
			approvalPercentage = ((totalAppointment - totalPenddingAppointments) / totalAppointment) * 100;
		int confirmedPercentage = 0;
		if (totalApproveAppointments + totalConfirmAppointments + totalVisitAppointments != 0)
			confirmedPercentage = ((totalConfirmAppointments + totalVisitAppointments)
					/ (totalApproveAppointments + totalConfirmAppointments + totalVisitAppointments)) * 100;
		int visitedPercentage = 0;
		if (totalConfirmAppointments + totalVisitAppointments != 0)
			visitedPercentage = ((totalVisitAppointments) / (totalConfirmAppointments + totalVisitAppointments)) * 100;

		dashboard.setApprovalPercentage(approvalPercentage);
		dashboard.setConfirmedPercentage(confirmedPercentage);
		dashboard.setVisitedPercentage(visitedPercentage);

		return dashboard;
	}

}
