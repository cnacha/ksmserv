package com.mfu.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.appointment.AppointmentDAO;
import com.mfu.dao.common.UserDAO;
import com.mfu.dao.record.DoctorDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.entity.appointment.Appointment;
import com.mfu.entity.common.User;
import com.mfu.entity.record.Doctor;
import com.mfu.entity.record.Patient;
import com.mfu.util.CalendarAPI;
import com.mfu.util.PushService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppointmentController {

	@RequestMapping(value = "/listAllAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listAllAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.getAllAppointment();
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/findAppointmentByDateAndStatusWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> findAppointmentByDateAndStatusWS(HttpServletRequest request) {

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		System.out.println("status = " + status);

		// Convert String to Date
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		List<Appointment> appointments = null;

		try {
			Date sDate = format.parse(startDate);
			Date eDate = format.parse(endDate);

			AppointmentDAO appointmentService = new AppointmentDAO();
			if (status.equals("-1")) {
				appointments = appointmentService.findAppointmentByDateAndStatus(sDate, eDate);
			} else {
				appointments = appointmentService.findAppointmentByDateAndStatus(sDate, eDate,
						Integer.parseInt(status));
			}

			System.out.println("Appointment report size = " + appointments.size());
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;
	}

	@RequestMapping(value = "/listAppointmentByDoctorAndDateIdWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listAppointmentByDoctorAndDateIdWS(HttpServletRequest request) {
		System.out.println(request.getParameter("doctorId") + " " + request.getParameter("date"));

		AppointmentDAO appointmentService = new AppointmentDAO();

		List<Appointment> datas = new ArrayList<Appointment>();

		List<Appointment> appointments = appointmentService.findAppointmentByDoctorId(request.getParameter("doctorId"));

		String date = request.getParameter("date");

		for (Appointment appointment : appointments) {

			String preferDate = "";
			if (appointment.getPreferChoice() == 1) {
				preferDate = appointment.getPreferDate1();
			} else {
				preferDate = appointment.getPreferDate2();
			}
			System.out.println(preferDate + " == " + date);
			if (preferDate.equals(date) && (appointment.getStatus() == 1 || appointment.getStatus() == -1)) {

				datas.add(appointment);
			}
		}

		appointmentService.closeEntityManager();

		return datas;
	}

	@RequestMapping(value = "/listPendingAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listPendingAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByStatus(Appointment.STATUS_PENDING);
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listVisitedAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listVisitedAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByStatus(Appointment.STATUS_VISIT);
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listConfirmAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listConfirmAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByStatus(Appointment.STATUS_CONFIRM);
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listApproveAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listApproveAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByStatus(Appointment.STATUS_APPROVE);
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listCancelAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listCancelAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByStatus(Appointment.STATUS_CANCEL);
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listAppointmentByDoctorIdWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listAppointmentByDoctorIdWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByDoctorId(request.getParameter("doctorId"));
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/listAvailableTimeByDoctorIAndDateWS", method = RequestMethod.GET)
	public @ResponseBody List<String> listAvailableTimeByDoctorIAndDateWS(HttpServletRequest request) {
		AppointmentDAO appointmentService = new AppointmentDAO();
		List<Appointment> appointments = appointmentService.findAppointmentByDoctorId(request.getParameter("doctorId"));
		appointmentService.closeEntityManager();

		List<String> times = new ArrayList<>(Arrays.asList("08:00 - 08:30", "08:30 - 09:00", "09:00 - 09:30",
				"09:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00", "11:00 - 11:30", "11:30 - 12:00", "13:00 - 13:30",
				"13:30 - 14:00", "14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30", "15:30 - 16:00"));
		List<String> dataTimes = new ArrayList<String>();
		List<String> availableTimes = new ArrayList<>();

		for (Appointment appointment : appointments) {
			System.out.println("In loop");
			if (appointment.getStatus() == Appointment.STATUS_APPROVE
					|| appointment.getStatus() == Appointment.STATUS_CONFIRM) {
				String chosenDate = "";
				String chosenTime = "";
				if (appointment.getPreferChoice() == 1) {
					chosenDate = appointment.getPreferDate1();
					chosenTime = appointment.getPreferTime1();

				} else if (appointment.getPreferChoice() == 2) {
					chosenDate = appointment.getPreferDate2();
					chosenTime = appointment.getPreferTime2();
				}
				System.out.println(chosenDate + " " + request.getParameter("selectedDate"));
				if (chosenDate.equals(request.getParameter("selectedDate"))) {
					dataTimes.add(chosenTime);
				}
			}
			times.removeAll(dataTimes);
			availableTimes = new ArrayList<>(times);
		}

		if (availableTimes.isEmpty())
			return times;
		else
			return availableTimes;
	}

	@RequestMapping(value = "/findLasted5AppointmentByPatientWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> findLasted5AppointmentByPatientWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		System.out.println(request.getParameter("patientId"));
		List<Appointment> appointments = appointmentService
				.findTop5AppointmentByPatientKey(request.getParameter("patientId"));
		appointmentService.closeEntityManager();

		return appointments;
	}

	@RequestMapping(value = "/findAppointmentByPatientWS", method = RequestMethod.GET)
	public @ResponseBody List<Appointment> listAppointmentByPatientIdWS(HttpServletRequest request) {

		List<Appointment> appointments = null;
		AppointmentDAO appointmentService = new AppointmentDAO();
		PatientDAO pservice = new PatientDAO();
		Patient p = pservice.findPatientsByHospitalNumber(request.getParameter("codeHN"));
		if (request.getParameter("fromDate") != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date fromDate = formatter.parse(request.getParameter("fromDate"));

				appointments = appointmentService.findAppointmentByFromDate(p.getKeyString(), fromDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (request.getParameter("status") != null) {
			int status = Integer.parseInt(request.getParameter("status"));
			appointments = appointmentService.findAppointmentByStatus(p.getKeyString(), status);
		} else {
			appointments = appointmentService.findAppointmentByPatientId(p.getKeyString());
		}

		appointmentService.closeEntityManager();

		return appointments;
	}

	// Make an patient's appointment
	@RequestMapping(value = "/makeAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String makeAppointmentWS(@RequestBody Appointment appointment, HttpServletRequest request) {
		System.out.println(
				"makeAppointmentWS called " + " " + appointment.getPreferChoice() + " " + appointment.getStatus());

		AppointmentDAO appointmentService = new AppointmentDAO();
		try {
			if (appointment.getStatus() == 1 || appointment.getStatus() == -1) {
				appointment.fetchStartEndDateTime();

			}
			if (appointment.getCodeHN() != null && appointment.getPatientId() == null) {
				PatientDAO pservice = new PatientDAO();
				Patient p = pservice.findPatientsByHospitalNumber(appointment.getCodeHN());
				appointment.setPatientId(p.getKeyString());
				pservice.closeEntityManager();
			}
			if (appointment.getDoctorId() == null) {
				// assign doctor by speciality
				DoctorDAO dservice = new DoctorDAO();
				List<Doctor> doctors = dservice.findDoctorBySpecialty(appointment.getSpecialty());
				dservice.closeEntityManager();
				// find available doctor by purposed time choices
				for (Doctor doctor : doctors) {
					appointment.setPreferChoice(1);
					appointment.fetchStartEndDateTime();
					List<Appointment> result = appointmentService.findAppointmentByDoctorAndTime(doctor.getKeyString(),
							appointment.getStartDateTime(), appointment.getEndDateTime());
					System.out.println(" found " + result.size() + "appointment of " + doctor.getKeyString());
					if (result == null || result.size() == 0) {
						String appointmentDayInWeek = appointment.getStartDateTime().toString().substring(0, 3);
						if (isDoctorWorkingDay(doctor, appointmentDayInWeek)) {
							appointment.setDoctorId(doctor.getKeyString());
							break;
						}

					} else if (appointment.getPreferDate2() != null) {
						appointment.setPreferChoice(2);
						appointment.fetchStartEndDateTime();
						result = appointmentService.findAppointmentByDoctorAndTime(doctor.getKeyString(),
								appointment.getStartDateTime(), appointment.getEndDateTime());
						// System.out.println(" 2 found "+result.size()+"
						// appointment of "+doctor.getKeyString());
						if (result == null || result.size() == 0) {
							String appointmentDayInWeek = appointment.getStartDateTime().toString().substring(0, 3);
							if (isDoctorWorkingDay(doctor, appointmentDayInWeek)) {
								appointment.setDoctorId(doctor.getKeyString());
								break;
							}
						}
					}
				}

				// Don't have any doctor available at patient requested time.
				if (appointment.getDoctorId() == null)
					return "-2";
			} else {
				String appointmentDayInWeek = appointment.getStartDateTime().toString().substring(0, 3);
				Doctor doctor = new DoctorDAO().findDoctorByKey(appointment.getDoctorId());
				System.out.println("make appointmentDayInWeek: " + appointmentDayInWeek + " doc: "
						+ doctor.getSaturday() + " " + isDoctorWorkingDay(doctor, appointmentDayInWeek));
				if (!isDoctorWorkingDay(doctor, appointmentDayInWeek)) {
					return "-2";
				}
			}

			appointmentService.insertAppointment(appointment);
			appointmentService.closeEntityManager();

			while (appointment.getKeyString() == null) {
				// Wait until save successfully.
			}

			// Call calendar API when add leave day
			if (appointment.getStatus() == Appointment.STATUS_LEAVE) {
				DoctorDAO dservice = new DoctorDAO();
				Doctor doctor = dservice.findDoctorByKey(appointment.getDoctorId());
				dservice.closeEntityManager();

				CalendarAPI calendarAPI = new CalendarAPI();
				calendarAPI.insertEventOnCalendar(doctor, appointment);
			} else if (appointment.getStatus() == Appointment.STATUS_APPROVE) {

				// Prepare Doctor information
				DoctorDAO doctorService = new DoctorDAO();
				Doctor doctor = doctorService.findDoctorByKey(appointment.getDoctorId());
				doctorService.closeEntityManager();

				// Prepare Appointment information
				AppointmentDAO appointmentService2 = new AppointmentDAO();

				Appointment appointment2 = null;
				while (appointment2 == null) {
					appointment2 = appointmentService2.findAppointmentByKey(appointment.getKeyString());
				}
				appointmentService2.closeEntityManager();

				System.out.println("Begin insert event...." + appointment2.getStatus());
				CalendarAPI calendarAPI = new CalendarAPI();
				calendarAPI.insertEventOnCalendar(doctor, appointment2);
				System.out.println("End insert event....");

				sendNotifactionOnAppointmentStatusChange(appointment.getStatus(), appointment);

			}

		} catch (Exception e) {
			if (appointmentService != null)
				appointmentService.closeEntityManager();
			e.printStackTrace();
			return "-1";
		}
		return "1";
	}

	private boolean isDoctorWorkingDay(Doctor doctor, String dayInWeek) {
		boolean isWorkDay = false;

		switch (dayInWeek) {
		case "Sun":
			if (doctor.getSunday() == 1) {
				isWorkDay = true;
			}
			break;
		case "Mon":
			if (doctor.getMonday() == 1) {
				isWorkDay = true;
			}
			break;

		case "Tue":
			if (doctor.getTuesday() == 1) {
				isWorkDay = true;
			}
			break;

		case "Wed":
			if (doctor.getWednesday() == 1) {
				isWorkDay = true;
			}
			break;

		case "Thu":
			if (doctor.getThursday() == 1) {
				isWorkDay = true;
			}
			break;
		case "Fri":
			if (doctor.getFriday() == 1) {
				isWorkDay = true;
			}
			break;
		case "Sat":
			if (doctor.getSaturday() == 1) {
				isWorkDay = true;
			}
			break;

		default:
			System.out.println("Somethings wrong on isDoctorWorkingDay()");
			break;
		}

		return isWorkDay;
	}

	@RequestMapping(value = "/saveAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String saveAppointmentWS(@RequestBody Appointment appointment, HttpServletRequest request) {
		System.out.println("saveAppointmentWS called " + appointment.getKeyString() + " "
				+ appointment.getPreferChoice() + " " + appointment.getStatus());

		AppointmentDAO appointmentService = new AppointmentDAO();
		Appointment app = appointmentService.findAppointmentByKey(appointment.getKeyString());

		try {
			if (appointment.getStatus() == Appointment.STATUS_APPROVE
					|| appointment.getStatus() == Appointment.STATUS_VISIT
					|| appointment.getStatus() == Appointment.STATUS_PENDING
					|| appointment.getStatus() == Appointment.STATUS_REFUSE) {
				appointment.fetchStartEndDateTime();
				// check available time/date
				if (!appointmentService.checkAppointmentValid(appointment))
					return "-1";

				String appointmentDayInWeek = appointment.getStartDateTime().toString().substring(0, 3);
				if (!isDoctorWorkingDay(app.getDoctor(), appointmentDayInWeek)) {
					return "-1";
				}
				System.out.println("appointmentDayInWeek: " + appointmentDayInWeek);

			}

			appointmentService.updateAppointment(appointment);
			appointmentService.closeEntityManager();

			// Call calendar API only APPROVE_STATUS or CANCELLATION_STATUS
			if (appointment.getStatus() == Appointment.STATUS_APPROVE) {

				// Prepare Doctor information
				DoctorDAO doctorService = new DoctorDAO();
				Doctor doctor = doctorService.findDoctorByKey(appointment.getDoctorId());
				doctorService.closeEntityManager();

				// Prepare Appointment information
				AppointmentDAO appointmentService2 = new AppointmentDAO();
				Appointment appointment2 = appointmentService2.findAppointmentByKey(appointment.getKeyString());
				appointmentService2.closeEntityManager();

				System.out.println("Begin insert event....");
				CalendarAPI calendarAPI = new CalendarAPI();
				calendarAPI.insertEventOnCalendar(doctor, appointment2);
				System.out.println("End insert event....");

				// Send push notification on approve appointment
				sendNotifactionOnAppointmentStatusChange(appointment.getStatus(), appointment);

			} else if (appointment.getStatus() == Appointment.STATUS_CANCEL) {
				System.out.println("Begin delete event...." + appointment.getEventId());
				CalendarAPI calendarAPI = new CalendarAPI();
				calendarAPI.deleteEventOnCalendar(appointment.getEventId());
				System.out.println("End delete event....");

				// Send push notification on cancel appointment
				sendNotifactionOnAppointmentStatusChange(appointment.getStatus(), appointment);
			} else if (appointment.getStatus() == Appointment.STATUS_REFUSE) {
				// Send push notification on refuse appointment
				sendNotifactionOnAppointmentStatusChange(appointment.getStatus(), appointment);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/cancelAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String cancelAppointmentWS(@RequestBody Appointment appointment, HttpServletRequest request) {
		System.out.println(
				"cancelAppointmentWS called " + " " + appointment.getPreferChoice() + " " + appointment.getStatus());

		try {

			AppointmentDAO appointmentService = new AppointmentDAO();

			Appointment appoint = appointmentService.findAppointmentByKey(appointment.getKeyString());
			appoint.setStatus(Appointment.STATUS_CANCEL);

			System.out.println("Begin delete event...." + appoint.getEventId());
			CalendarAPI calendarAPI = new CalendarAPI();
			calendarAPI.deleteEventOnCalendar(appoint.getEventId());
			System.out.println("End delete event....");

			appointmentService.updateAppointment(appoint);
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/confirmAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String confirmAppointmentWS(@RequestBody Appointment appointment, HttpServletRequest request) {
		System.out.println(
				"confirmAppointmentWS called " + " " + appointment.getPreferChoice() + " " + appointment.getStatus());

		try {

			AppointmentDAO appointmentService = new AppointmentDAO();
			Appointment a = appointmentService.findAppointmentByKey(appointment.getKeyString());
			a.setStatus(Appointment.STATUS_CONFIRM);

			appointmentService.updateAppointment(a);
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/confirmVisitAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String confirmVisitAppointmentWS(HttpServletRequest request) {
		System.out.println("confirmVisitAppointmentWS called " + request.getParameter("status"));

		try {

			AppointmentDAO appointmentService = new AppointmentDAO();
			Appointment a = appointmentService.findAppointmentByKey(request.getParameter("key"));
			a.setStatus(Appointment.STATUS_VISIT);

			appointmentService.updateAppointment(a);
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}
	
	@RequestMapping(value = "/markVisitAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String markVisitAppointmentWS(@RequestBody Appointment appointment, HttpServletRequest request) {
		System.out.println("markVisitAppointmentWS called "+ " "+ appointment.getKeyString());
				
		try {
			
			AppointmentDAO appointmentService = new AppointmentDAO();
			Appointment a = appointmentService.findAppointmentByKey(appointment.getKeyString());
			a.setStatus(Appointment.STATUS_VISIT);
			appointmentService.updateAppointment(a);
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/findAppointmentWS", method = RequestMethod.GET)
	public @ResponseBody Appointment findAppointmentWS(HttpServletRequest request) {
		System.out.println("... passing key " + request.getParameter("appointmentId"));

		AppointmentDAO appointmentService = new AppointmentDAO();
		Appointment appointment = appointmentService.findAppointmentByKey(request.getParameter("appointmentId"));
		appointmentService.closeEntityManager();

		return appointment;
	}

	@RequestMapping(value = "/deleteAppointmentWS", method = RequestMethod.DELETE)
	public @ResponseBody String removeAppointment(HttpServletRequest request) {

		try {
			AppointmentDAO appointmentService = new AppointmentDAO();

			Appointment appoint = appointmentService.findAppointmentByKey(request.getParameter("appointmentId"));

			// Call Google Calendar API
			System.out.println("Begin delete event...." + appoint.getEventId());
			CalendarAPI calendarAPI = new CalendarAPI();
			calendarAPI.deleteEventOnCalendar(appoint.getEventId());
			System.out.println("End delete event....");

			appointmentService.deleteAppointment(request.getParameter("appointmentId"));
			appointmentService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return "1";

	}

	@RequestMapping(value = "/updateAppointmentWS", method = RequestMethod.POST)
	public @ResponseBody String updateAppointmentWS(HttpServletRequest request) {

		AppointmentDAO appointmentService = new AppointmentDAO();
		try {

			int appointmentStatus = Integer.parseInt(request.getParameter("appointmentStatus"));

			Appointment appointment = appointmentService.findAppointmentByKey(request.getParameter("appointmentId"));
			System.out.println(appointment.getPreferChoice());
			if (appointment.getPreferChoice() == 1) {
				if (request.getParameter("preferDate1") != null) {
					appointment.setPreferDate1(request.getParameter("preferDate1"));
					appointment.setPreferTime1(request.getParameter("preferTime1"));
				} else {
					System.out.println("PreferDate1 is null");
				}
			} else {
				if (request.getParameter("preferDate2") != null) {
					appointment.setPreferDate1(request.getParameter("preferDate2"));
					appointment.setPreferTime1(request.getParameter("preferTime2"));
				} else {
					System.out.println("PreferDate2 is null");
				}
			}

			if (appointment.getStatus() != appointmentStatus) {

				sendNotifactionOnAppointmentStatusChange(appointmentStatus, appointment);
			}

			appointment.setStatus(appointmentStatus);

			appointmentService.insertAppointment(appointment);

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		appointmentService.closeEntityManager();

		return "1";
	}

	/**
	 * @param appointmentStatus
	 * @param appointment
	 */
	private void sendNotifactionOnAppointmentStatusChange(int appointmentStatus, Appointment appointment) {
		UserDAO userDAO = new UserDAO();

		PatientDAO patientDAO = new PatientDAO();
		Patient patient = patientDAO.findPatientByKey(appointment.getPatientId());
		patientDAO.closeEntityManager();
		System.out.println("sendNotifactionOnAppointmentStatusChange to " + patient.getCodeHN());
		User user = userDAO.findUserByCodeHN(patient.getCodeHN());
		userDAO.closeEntityManager();

		String message = null;
		if (user != null) {
			if (appointmentStatus == Appointment.STATUS_APPROVE) {
				message = "Your appointment on " + appointment.getStartDateTime().toString().substring(0, 10)
						+ " has been approved. Please click confirm if your free to go";
			} else if (appointmentStatus == Appointment.STATUS_REFUSE) {
				message = "Your appointment has been rejected. Please reschedule!!!";
			} else if (appointmentStatus == Appointment.STATUS_CANCEL) {
				message = "Your appointment has been cancelled. Sorry for the inconvenience!!!";
			}
		} else {
			System.out.println("This people does not register on smartqueue application.");
		}

		// Call FCM service and give the device token key also a warning
		// message.
		boolean isSendNotificationSuccess = false;
		try {
			PushService fcm = new PushService();
			if (message != null && user.getDeviceTokenKey() != null)
				isSendNotificationSuccess = fcm.send(user.getDeviceTokenKey(), message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// If FCM have been send the push notification.
		if (isSendNotificationSuccess) {
			System.out.println("Send Notification Success");
		} else {
			System.out.println("Send Notification failed or not send");
		}
	}

	@RequestMapping(value = "/resetAppointment", method = RequestMethod.GET)
	public @ResponseBody String resetAppointment(HttpServletRequest request) {
		String key = request.getParameter("key");
		int status = Integer.parseInt(request.getParameter("status"));
		AppointmentDAO appointmentService = new AppointmentDAO();
		Appointment ap = appointmentService.findAppointmentByKey(key);
		ap.setStatus(status);
		appointmentService.updateAppointment(ap);
		appointmentService.closeEntityManager();
		return "1";

	}

	@RequestMapping(value = "/resetAllAppointmentStatusToPendding", method = RequestMethod.GET)
	public @ResponseBody String resetAllAppointmentStatusToPendding() {
		try {
			System.out.println("Begin reset ....");
			AppointmentDAO appointmentService = new AppointmentDAO();
			List<Appointment> appointments = appointmentService.getAllAppointment();
			appointmentService.closeEntityManager();

			for (Appointment appointment : appointments) {
				AppointmentDAO dao = new AppointmentDAO();
				appointment.setEventId("");
				appointment.setStatus(Appointment.STATUS_PENDING);
				dao.updateAppointment(appointment);
				dao.closeEntityManager();
			}

			System.out.println("End reset ....");

		} catch (Exception e) {
			e.printStackTrace();

			return "-1";
		}

		return "1";
	}

}
