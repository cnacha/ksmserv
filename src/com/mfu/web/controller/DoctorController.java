package com.mfu.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.appointment.AppointmentDAO;
import com.mfu.dao.record.ClinicDAO;
import com.mfu.dao.record.DoctorDAO;
import com.mfu.entity.appointment.Appointment;
import com.mfu.entity.record.Clinic;
import com.mfu.entity.record.Doctor;
import com.mfu.web.model.WSResponse;

@Controller
public class DoctorController {

	private final static Logger logger = Logger.getLogger(DoctorController.class.getName());

	@RequestMapping(value = "/listDoctorWS", method = RequestMethod.GET)
	@ResponseBody
	public List<Doctor> listDoctorWS(HttpServletRequest request) {
		logger.info("listDoctorWS is called");

		DoctorDAO doctorService = new DoctorDAO();
		List<Doctor> doctors = doctorService.getAllDoctor();
		doctorService.closeEntityManager();

		return doctors;
	}

	@RequestMapping(value = "/listSpecialityWS", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listSpeciality() {
		DoctorDAO dao = new DoctorDAO();
		List<Doctor> clist = dao.getAllDoctor();
		List<String> spList = new ArrayList<String>();
		for (Doctor c : clist) {
			if (!spList.contains(c.getSpecialty()))
				spList.add(c.getSpecialty());
		}
		return spList;
	}

	@RequestMapping(value = "/listDoctorBySpecialtyWS", method = RequestMethod.GET)
	@ResponseBody
	public List<Doctor> listDoctorBySpecialtyWS(HttpServletRequest request) {

		DoctorDAO doctorService = new DoctorDAO();
		List<Doctor> doctors = doctorService.findDoctorBySpecialty(request.getParameter("specialty"));
		doctorService.closeEntityManager();

		return doctors;
	}

	@RequestMapping(value = "/listAvaliableTimeByDoctorOnSpecificDate", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listAvaliableTimeByDoctorOnSpecificDate(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		// Input

		String preferDate = request.getParameter("preferDate");
		int options = Integer.parseInt(request.getParameter("options"));

		List<String> times = new ArrayList<String>();

		String time[] = { "8:00 - 8:30", "8:30 - 9:00", "9:00 - 9:30", "9:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00",
				"11:00 - 11:30", "11:30 - 12:00", "12:00 - 12:30", "12:30 - 13:00", "13:00 - 13:30", "13:30 - 14:00",
				"14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30", "15:30 - 16:00" };

		List<Appointment> appointments = appointmentService.findAppointmentByDoctorId(request.getParameter("doctorId"));
		appointmentService.closeEntityManager();
		// Process
		for (int i = 0; i < time.length; i++) {

			boolean avaliable = true;
			for (int j = 0; j < appointments.size(); j++) {
				Appointment app = appointments.get(j);

				switch (options) {
				case 1:
					if (time[i].equals(app.getPreferTime1()) && preferDate.equals(app.getPreferDate1())
							&& app.getStatus() == Appointment.STATUS_APPROVE) {
						avaliable = false;
					}
					break;
				case 2:
					if (time[i].equals(app.getPreferTime2()) && preferDate.equals(app.getPreferDate2())
							&& app.getStatus() == Appointment.STATUS_APPROVE) {
						avaliable = false;
					}
					break;
				}

			}

			if (avaliable) {
				times.add(time[i]);
			}

		}

		return times;
	}

	@RequestMapping(value = "/listDoctorBySpecialtyAndPreferDateAndPreferTimeWS", method = RequestMethod.GET)
	@ResponseBody
	public List<Doctor> listDoctorBySpecialtyAndPreferDateAndPreferTimeWS(HttpServletRequest request) {

		DoctorDAO doctorService = new DoctorDAO();
		List<Doctor> doctors = doctorService.findDoctorBySpecialty(request.getParameter("specialty"));
		doctorService.closeEntityManager();

		String preferDate = request.getParameter("preferDate");
		String preferTime = request.getParameter("preferTime");
		int preferChoice = Integer.parseInt(request.getParameter("preferChoice"));

		for (int i = 0; i < doctors.size(); i++) {

			AppointmentDAO appointmentService = new AppointmentDAO();
			List<Appointment> appointments = appointmentService
					.findAppointmentByDoctorId(doctors.get(i).getKeyString());
			appointmentService.closeEntityManager();
			for (int j = 0; j < appointments.size(); j++) {

				if (preferChoice == 1) {
					if (preferDate.equals(appointments.get(j).getPreferDate1())
							&& preferTime.equals(appointments.get(j).getPreferTime1())
							&& appointments.get(j).getStatus() == Appointment.STATUS_APPROVE) {
						doctors.remove(i);
					}
				} else {
					if (preferDate.equals(appointments.get(j).getPreferDate2())
							&& preferTime.equals(appointments.get(j).getPreferTime2())
							&& appointments.get(j).getStatus() == Appointment.STATUS_APPROVE) {
						doctors.remove(i);
					}
				}
			}
		}

		return doctors;
	}

	@RequestMapping(value = "/saveDoctorWS", method = RequestMethod.POST)
	@ResponseBody
	public String saveDoctorWS(@RequestBody Doctor doctor, HttpServletRequest request) {

		DoctorDAO doctorService = new DoctorDAO();
		try {
			Doctor foundDoctor = doctorService.findDoctorByDoctorCode(doctor.getDoctorCode());
			if(foundDoctor!=null){
				doctor.setKey(foundDoctor.getKey());
			}
			doctorService.saveDoctor(doctor);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}finally{
			doctorService.closeEntityManager();
		}

		return "1";
	}
	
	@RequestMapping(value = "/insertDoctorBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> insertDoctorBatch(@RequestBody Doctor[] doctors, HttpServletRequest request){
		List<WSResponse> resList = new ArrayList<WSResponse>();

		String result;
		for (Doctor d : doctors) {
			// set default working days to everyday
			d.setMonday(1);
			d.setTuesday(1);
			d.setWednesday(1);
			d.setThursday(1);
			d.setFriday(1);
			d.setSaturday(1);
			d.setSunday(1);
			result = this.saveDoctorWS(d, request);
			resList.add(new WSResponse(d.getFirstname()+" "+d.getLastname(), Integer.parseInt(result)));
		}

		return resList;
	}
	

	@RequestMapping(value = "/updateDoctorWS", method = RequestMethod.POST)
	@ResponseBody
	public String updateDoctorWS(@RequestBody Doctor doctor, HttpServletRequest request) {

		try {

			updateAppointmentSpacialtyByDoctorId(doctor.getKeyString(), doctor.getSpecialty(), request);
			DoctorDAO doctorService = new DoctorDAO();
			doctorService.saveDoctor(doctor);
			doctorService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	private void updateAppointmentSpacialtyByDoctorId(String doctorId, String specialty, HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		try {
			List<Appointment> appointments = appointmentService.findAppointmentByDoctorId(doctorId);
			appointmentService.closeEntityManager();
			for (Appointment appointment : appointments) {

				AppointmentDAO appointmentService1 = new AppointmentDAO();
				appointment.setSpecialty(specialty);
				appointmentService1.updateAppointment(appointment);
				appointmentService1.closeEntityManager();
			}

			appointmentService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/findDoctorById", method = RequestMethod.GET)
	@ResponseBody
	public Doctor findDoctorById(HttpServletRequest request) {

		DoctorDAO doctorService = new DoctorDAO();
		Doctor doc = doctorService.findDoctorByKey(request.getParameter("doctorId"));
		doctorService.closeEntityManager();
		return doc;
	}

	@RequestMapping(value = "/findDoctorByKeyString", method = RequestMethod.GET)
	@ResponseBody
	public Doctor findDoctorByKeyString(HttpServletRequest request) {

		System.out.println("find doctor :" + request.getParameter("doctorId"));
		DoctorDAO doctorService = new DoctorDAO();
		Doctor doc = doctorService.findDoctorByKeyString(request.getParameter("doctorId"));
		doctorService.closeEntityManager();
		return doc;
	}

	@RequestMapping(value = "/deleteDoctorWS", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteDoctorWS(HttpServletRequest request) {

		DoctorDAO doctorService = new DoctorDAO();
		try {

			doctorService.deleteDoctor(request.getParameter("doctorId"));
			doctorService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";

	}
	
}
