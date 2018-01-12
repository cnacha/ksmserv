package com.mfu.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.search.query.ExpressionParser.negation_return;
import com.mfu.dao.common.UserDAO;
import com.mfu.dao.queue.QueueEstimatedTimeDAO;
import com.mfu.dao.record.ClinicDAO;
import com.mfu.dao.record.DoctorDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.nutrition.Food;
import com.mfu.entity.queue.QueueEstimatedTime;
import com.mfu.entity.record.Clinic;
import com.mfu.entity.record.Doctor;
import com.mfu.entity.record.Patient;
import com.mfu.util.SecurityUtil;
import com.mfu.util.StringUtil;

@Controller
public class InitialController {

	private static final Logger log = Logger.getLogger(InitialController.class
			.getName());

	@RequestMapping(value = "/admin/installPreset", method = RequestMethod.GET)
	@ResponseBody
	public String userTester() {

		try {
			UserDAO userService = new UserDAO();
			User user = new User();
			user.setFirstname("Firstname");
			user.setLastname("Lastname");
			user.setPassword("user");
			user.setUsername("user");
			user.setRole("HospitalStaff");
			user.setStatus(1);
			userService.saveUser(user);
			userService.closeEntityManager();

			UserDAO userService1 = new UserDAO();
			User user1 = new User();
			user1.setFirstname("Nacha");
			user1.setLastname("Chondamrongkul");
			user1.setPassword("admin");
			user1.setUsername("admin");
			user1.setRole("Admin");
			user1.setStatus(1);
			userService1.saveUser(user1);
			userService1.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/initialClinic", method = RequestMethod.GET)
	@ResponseBody
	public String initialClinic() {

		try {
			// add clinic
			ClinicDAO clinicDAO1 = new ClinicDAO();
			Clinic clinic1 = new Clinic();
			clinic1.setClinicCode("001");
			clinic1.setLocation("SA 127");
			clinic1.setName("Dental Center");
			clinic1.setSpecialty("Orthodontics");
			clinic1.setMessage("Smile is everything");
			clinicDAO1.insertClinic(clinic1);
			clinicDAO1.closeEntityManager();

			ClinicDAO clinicDAO2 = new ClinicDAO();
			Clinic clinic2 = new Clinic();
			clinic2.setClinicCode("002");
			clinic2.setLocation("D3 401");
			clinic2.setName("Cosmetic Surgery Center");
			clinic2.setSpecialty("Nose surgery");
			clinic2.setMessage("We love pointy nose");
			clinicDAO2.insertClinic(clinic2);
			clinicDAO2.closeEntityManager();

			ClinicDAO clinicDAO3 = new ClinicDAO();
			Clinic clinic3 = new Clinic();
			clinic3.setClinicCode("003");
			clinic3.setLocation("UI 100");
			clinic3.setName("Beauty Center");
			clinic3.setSpecialty("Skincare");
			clinic3.setMessage("For beauty skin");
			clinicDAO3.insertClinic(clinic3);
			clinicDAO3.closeEntityManager();

			ClinicDAO clinicDAO4 = new ClinicDAO();
			Clinic clinic4 = new Clinic();
			clinic4.setClinicCode("004");
			clinic4.setLocation("F 251");
			clinic4.setName("Beauty Center");
			clinic4.setSpecialty("Anti age");
			clinic4.setMessage("Forever young");
			clinicDAO4.insertClinic(clinic4);
			clinicDAO4.closeEntityManager();

			ClinicDAO clinicDAO5 = new ClinicDAO();
			Clinic clinic5 = new Clinic();
			clinic5.setClinicCode("005");
			clinic5.setLocation("CD 003");
			clinic5.setName("Cancer Center");
			clinic5.setSpecialty("Lung cancer");
			clinic5.setMessage("Smoking for what!?");
			clinicDAO5.insertClinic(clinic5);
			clinicDAO5.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/initialFood", method = RequestMethod.GET)
	@ResponseBody
	public String initialFood() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File("WEB-INF/food.json")), "UTF8"));
			
			StringBuffer jsonSB = new StringBuffer();
			String strLine;
			List<Food> foodList = new ArrayList<Food>();
		
			while ((strLine = br.readLine()) != null) {
				jsonSB.append(strLine);
				log.info("reading line: " + strLine);
				foodList.add(mapper.readValue(strLine,Food.class));
			}
			br.close();
			//Food[] foods = mapper.readValue(jsonSB.toString().getBytes(), Food[].class);
			NutritionController nc = new NutritionController();
			for (Food o : foodList) {
				nc.saveFoodWS(o);
			}
			return "1";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "-1";
	}

	@RequestMapping(value = "/initialDoc", method = RequestMethod.GET)
	@ResponseBody
	public String initialDoc() {

		try {

			// add doctor
			DoctorDAO docDAO = new DoctorDAO();
			Doctor doc1 = new Doctor();
			doc1.setEmail("5731305076@lamduan.mfu.ac.th");
			doc1.setFirstname("Suepsakun");
			doc1.setLastname("Chang");
			doc1.setMonday(1);
			doc1.setTuesday(1);
			doc1.setWednesday(1);
			doc1.setThursday(1);
			doc1.setFriday(1);
			doc1.setSaturday(0);
			doc1.setSunday(1);
			ClinicDAO dao = new ClinicDAO();
			Clinic cli = dao.findClinicByClinicCode("001");
			System.out.println("1. " + cli.getSpecialty());
			doc1.setSpecialty(cli.getSpecialty());
			dao.closeEntityManager();
			docDAO.saveDoctor(doc1);
			docDAO.closeEntityManager();

			DoctorDAO docDAO2 = new DoctorDAO();
			Doctor doc2 = new Doctor();
			doc2.setEmail("5731305088@lamduan.mfu.ac.th");
			doc2.setFirstname("Apiwat");
			doc2.setLastname("Dave");
			doc2.setMonday(1);
			doc2.setTuesday(1);
			doc2.setWednesday(1);
			doc2.setThursday(1);
			doc2.setFriday(1);
			doc2.setSaturday(1);
			doc2.setSunday(0);
			ClinicDAO dao2 = new ClinicDAO();
			Clinic cli2 = dao2.findClinicByClinicCode("002");
			System.out.println("2. " + cli2.getSpecialty());
			doc2.setSpecialty(cli2.getSpecialty());
			dao2.closeEntityManager();
			docDAO2.saveDoctor(doc2);
			docDAO2.closeEntityManager();

			DoctorDAO docDAO3 = new DoctorDAO();
			Doctor doc3 = new Doctor();
			doc3.setEmail("5731305100@lamduan.mfu.ac.th");
			doc3.setFirstname("Pimnara");
			doc3.setLastname("Mint");
			doc3.setMonday(0);
			doc3.setTuesday(1);
			doc3.setWednesday(1);
			doc3.setThursday(1);
			doc3.setFriday(1);
			doc3.setSaturday(1);
			doc3.setSunday(0);
			ClinicDAO dao3 = new ClinicDAO();
			Clinic cli3 = dao3.findClinicByClinicCode("003");
			System.out.println("3. " + cli3.getSpecialty());
			doc3.setSpecialty(cli3.getSpecialty());
			dao3.closeEntityManager();
			docDAO3.saveDoctor(doc3);
			docDAO3.closeEntityManager();

			DoctorDAO docDAO4 = new DoctorDAO();
			Doctor doc4 = new Doctor();
			doc4.setEmail("5731305065@lamduan.mfu.ac.th");
			doc4.setFirstname("Vorawan");
			doc4.setLastname("Aom");
			doc4.setMonday(1);
			doc4.setTuesday(1);
			doc4.setWednesday(1);
			doc4.setThursday(0);
			doc4.setFriday(1);
			doc4.setSaturday(1);
			doc4.setSunday(1);
			ClinicDAO dao4 = new ClinicDAO();
			Clinic cli4 = dao4.findClinicByClinicCode("003");
			System.out.println("4. " + cli4.getSpecialty());
			doc4.setSpecialty(cli4.getSpecialty());
			dao4.closeEntityManager();
			docDAO4.saveDoctor(doc4);
			docDAO4.closeEntityManager();

			DoctorDAO docDAO5 = new DoctorDAO();
			Doctor doc5 = new Doctor();
			doc5.setEmail("5731305086@lamduan.mfu.ac.th");
			doc5.setFirstname("Anuwat");
			doc5.setLastname("John");
			doc5.setMonday(0);
			doc5.setTuesday(0);
			doc5.setWednesday(1);
			doc5.setThursday(1);
			doc5.setFriday(1);
			doc5.setSaturday(1);
			doc5.setSunday(1);
			ClinicDAO dao5 = new ClinicDAO();
			Clinic cli5 = dao5.findClinicByClinicCode("004");
			System.out.println("5. " + cli5.getSpecialty());
			doc5.setSpecialty(cli5.getSpecialty());
			dao5.closeEntityManager();
			docDAO5.saveDoctor(doc5);
			docDAO5.closeEntityManager();

			DoctorDAO docDAO6 = new DoctorDAO();
			Doctor doc6 = new Doctor();
			doc6.setEmail("5731305017@lamduan.mfu.ac.th");
			doc6.setFirstname("Chonthida");
			doc6.setLastname("Da");
			doc6.setMonday(1);
			doc6.setTuesday(1);
			doc6.setWednesday(1);
			doc6.setThursday(1);
			doc6.setFriday(0);
			doc6.setSaturday(0);
			doc6.setSunday(1);
			ClinicDAO dao6 = new ClinicDAO();
			Clinic cli6 = dao6.findClinicByClinicCode("005");
			System.out.println("6. " + cli6.getSpecialty());
			doc6.setSpecialty(cli6.getSpecialty());
			dao6.closeEntityManager();
			docDAO6.saveDoctor(doc6);
			docDAO6.closeEntityManager();

			DoctorDAO docDAO7 = new DoctorDAO();
			Doctor doc7 = new Doctor();
			doc7.setEmail("5731305031@lamduan.mfu.ac.th");
			doc7.setFirstname("Nattamon");
			doc7.setLastname("Fluek");
			doc7.setMonday(1);
			doc7.setTuesday(1);
			doc7.setWednesday(1);
			doc7.setThursday(1);
			doc7.setFriday(1);
			doc7.setSaturday(1);
			doc7.setSunday(1);
			ClinicDAO dao7 = new ClinicDAO();
			Clinic cli7 = dao7.findClinicByClinicCode("001");
			System.out.println("7. " + cli7.getSpecialty());
			doc7.setSpecialty(cli7.getSpecialty());
			dao7.closeEntityManager();
			docDAO7.saveDoctor(doc7);
			docDAO7.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/initialEstimatedTime", method = RequestMethod.GET)
	@ResponseBody
	public String initialEstiamtedTime() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");

		try {
			// Add estimated time
			QueueEstimatedTimeDAO estimatedTimeDAO = new QueueEstimatedTimeDAO();

			String str_startDate = "2017-01-01 1:00";
			String str_endDate = "2017-01-01 1:59";

			QueueEstimatedTime estimatedTime = new QueueEstimatedTime();
			estimatedTime.setCurrentStage(1);
			estimatedTime.setEstimatedTime(20);
			estimatedTime.setStartDateTime(simpleDateFormat
					.parse(str_startDate));
			estimatedTime.setEndDateTime(simpleDateFormat.parse(str_endDate));

			estimatedTimeDAO.insertQueueEstimatedTime(estimatedTime);
			estimatedTimeDAO.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO2 = new QueueEstimatedTimeDAO();

			String str_startDate2 = "2017-01-01 2:00";
			String str_endDate2 = "2017-01-01 2:59";

			QueueEstimatedTime estimatedTime2 = new QueueEstimatedTime();
			estimatedTime2.setCurrentStage(1);
			estimatedTime2.setEstimatedTime(14);
			estimatedTime2.setStartDateTime(simpleDateFormat
					.parse(str_startDate2));
			estimatedTime2.setEndDateTime(simpleDateFormat.parse(str_endDate2));

			estimatedTimeDAO2.insertQueueEstimatedTime(estimatedTime2);
			estimatedTimeDAO2.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO3 = new QueueEstimatedTimeDAO();
			String str_startDate3 = "2017-01-01 3:00";
			String str_endDate3 = "2017-01-01 3:59";
			QueueEstimatedTime estimatedTime3 = new QueueEstimatedTime();
			estimatedTime3.setCurrentStage(1);
			estimatedTime3.setEstimatedTime(19);
			estimatedTime3.setStartDateTime(simpleDateFormat
					.parse(str_startDate3));
			estimatedTime3.setEndDateTime(simpleDateFormat.parse(str_endDate3));
			estimatedTimeDAO3.insertQueueEstimatedTime(estimatedTime3);
			estimatedTimeDAO3.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO4 = new QueueEstimatedTimeDAO();
			String str_startDate4 = "2017-01-01 4:00";
			String str_endDate4 = "2017-01-01 4:59";
			QueueEstimatedTime estimatedTime4 = new QueueEstimatedTime();
			estimatedTime4.setCurrentStage(1);
			estimatedTime4.setEstimatedTime(14);
			estimatedTime4.setStartDateTime(simpleDateFormat
					.parse(str_startDate4));
			estimatedTime4.setEndDateTime(simpleDateFormat.parse(str_endDate4));
			estimatedTimeDAO4.insertQueueEstimatedTime(estimatedTime4);
			estimatedTimeDAO4.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO5 = new QueueEstimatedTimeDAO();
			String str_startDate5 = "2017-01-01 5:00";
			String str_endDate5 = "2017-01-01 5:59";
			QueueEstimatedTime estimatedTime5 = new QueueEstimatedTime();
			estimatedTime5.setCurrentStage(1);
			estimatedTime5.setEstimatedTime(14);
			estimatedTime5.setStartDateTime(simpleDateFormat
					.parse(str_startDate5));
			estimatedTime5.setEndDateTime(simpleDateFormat.parse(str_endDate5));
			estimatedTimeDAO5.insertQueueEstimatedTime(estimatedTime5);
			estimatedTimeDAO5.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO6 = new QueueEstimatedTimeDAO();
			String str_startDate6 = "2017-01-01 6:00";
			String str_endDate6 = "2017-01-01 6:59";
			QueueEstimatedTime estimatedTime6 = new QueueEstimatedTime();
			estimatedTime6.setCurrentStage(1);
			estimatedTime6.setEstimatedTime(24);
			estimatedTime6.setStartDateTime(simpleDateFormat
					.parse(str_startDate6));
			estimatedTime6.setEndDateTime(simpleDateFormat.parse(str_endDate6));
			estimatedTimeDAO6.insertQueueEstimatedTime(estimatedTime6);
			estimatedTimeDAO6.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO7 = new QueueEstimatedTimeDAO();
			String str_startDate7 = "2017-01-01 7:00";
			String str_endDate7 = "2017-01-01 7:59";
			QueueEstimatedTime estimatedTime7 = new QueueEstimatedTime();
			estimatedTime7.setCurrentStage(1);
			estimatedTime7.setEstimatedTime(17);
			estimatedTime7.setStartDateTime(simpleDateFormat
					.parse(str_startDate7));
			estimatedTime7.setEndDateTime(simpleDateFormat.parse(str_endDate7));
			estimatedTimeDAO7.insertQueueEstimatedTime(estimatedTime7);
			estimatedTimeDAO7.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO8 = new QueueEstimatedTimeDAO();
			String str_startDate8 = "2017-01-01 8:00";
			String str_endDate8 = "2017-01-01 8:59";
			QueueEstimatedTime estimatedTime8 = new QueueEstimatedTime();
			estimatedTime8.setCurrentStage(1);
			estimatedTime8.setEstimatedTime(36);
			estimatedTime8.setStartDateTime(simpleDateFormat
					.parse(str_startDate8));
			estimatedTime8.setEndDateTime(simpleDateFormat.parse(str_endDate8));
			estimatedTimeDAO8.insertQueueEstimatedTime(estimatedTime8);
			estimatedTimeDAO8.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO9 = new QueueEstimatedTimeDAO();
			String str_startDate9 = "2017-01-01 9:00";
			String str_endDate9 = "2017-01-01 9:59";
			QueueEstimatedTime estimatedTime9 = new QueueEstimatedTime();
			estimatedTime9.setCurrentStage(1);
			estimatedTime9.setEstimatedTime(42);
			estimatedTime9.setStartDateTime(simpleDateFormat
					.parse(str_startDate9));
			estimatedTime9.setEndDateTime(simpleDateFormat.parse(str_endDate9));
			estimatedTimeDAO9.insertQueueEstimatedTime(estimatedTime9);
			estimatedTimeDAO9.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO10 = new QueueEstimatedTimeDAO();
			String str_startDate10 = "2017-01-01 10:00";
			String str_endDate10 = "2017-01-01 10:59";
			QueueEstimatedTime estimatedTime10 = new QueueEstimatedTime();
			estimatedTime10.setCurrentStage(1);
			estimatedTime10.setEstimatedTime(31);
			estimatedTime10.setStartDateTime(simpleDateFormat
					.parse(str_startDate10));
			estimatedTime10.setEndDateTime(simpleDateFormat
					.parse(str_endDate10));
			estimatedTimeDAO10.insertQueueEstimatedTime(estimatedTime10);
			estimatedTimeDAO10.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO11 = new QueueEstimatedTimeDAO();
			String str_startDate11 = "2017-01-01 11:00";
			String str_endDate11 = "2017-01-01 11:59";
			QueueEstimatedTime estimatedTime11 = new QueueEstimatedTime();
			estimatedTime11.setCurrentStage(1);
			estimatedTime11.setEstimatedTime(29);
			estimatedTime11.setStartDateTime(simpleDateFormat
					.parse(str_startDate11));
			estimatedTime11.setEndDateTime(simpleDateFormat
					.parse(str_endDate11));
			estimatedTimeDAO11.insertQueueEstimatedTime(estimatedTime11);
			estimatedTimeDAO11.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO12 = new QueueEstimatedTimeDAO();
			String str_startDate12 = "2017-01-01 12:00";
			String str_endDate12 = "2017-01-01 12:59";
			QueueEstimatedTime estimatedTime12 = new QueueEstimatedTime();
			estimatedTime12.setCurrentStage(1);
			estimatedTime12.setEstimatedTime(45);
			estimatedTime12.setStartDateTime(simpleDateFormat
					.parse(str_startDate12));
			estimatedTime12.setEndDateTime(simpleDateFormat
					.parse(str_endDate12));
			estimatedTimeDAO12.insertQueueEstimatedTime(estimatedTime12);
			estimatedTimeDAO12.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO13 = new QueueEstimatedTimeDAO();
			String str_startDate13 = "2017-01-01 13:00";
			String str_endDate13 = "2017-01-01 13:59";
			QueueEstimatedTime estimatedTime13 = new QueueEstimatedTime();
			estimatedTime13.setCurrentStage(1);
			estimatedTime13.setEstimatedTime(39);
			estimatedTime13.setStartDateTime(simpleDateFormat
					.parse(str_startDate13));
			estimatedTime13.setEndDateTime(simpleDateFormat
					.parse(str_endDate13));
			estimatedTimeDAO13.insertQueueEstimatedTime(estimatedTime13);
			estimatedTimeDAO13.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO14 = new QueueEstimatedTimeDAO();
			String str_startDate14 = "2017-01-01 14:00";
			String str_endDate14 = "2017-01-01 14:59";
			QueueEstimatedTime estimatedTime14 = new QueueEstimatedTime();
			estimatedTime14.setCurrentStage(1);
			estimatedTime14.setEstimatedTime(28);
			estimatedTime14.setStartDateTime(simpleDateFormat
					.parse(str_startDate14));
			estimatedTime14.setEndDateTime(simpleDateFormat
					.parse(str_endDate14));
			estimatedTimeDAO14.insertQueueEstimatedTime(estimatedTime14);
			estimatedTimeDAO14.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO15 = new QueueEstimatedTimeDAO();
			String str_startDate15 = "2017-01-01 15:00";
			String str_endDate15 = "2017-01-01 15:59";
			QueueEstimatedTime estimatedTime15 = new QueueEstimatedTime();
			estimatedTime15.setCurrentStage(1);
			estimatedTime15.setEstimatedTime(33);
			estimatedTime15.setStartDateTime(simpleDateFormat
					.parse(str_startDate15));
			estimatedTime15.setEndDateTime(simpleDateFormat
					.parse(str_endDate15));
			estimatedTimeDAO15.insertQueueEstimatedTime(estimatedTime15);
			estimatedTimeDAO15.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO16 = new QueueEstimatedTimeDAO();
			String str_startDate16 = "2017-01-01 16:00";
			String str_endDate16 = "2017-01-01 16:59";
			QueueEstimatedTime estimatedTime16 = new QueueEstimatedTime();
			estimatedTime16.setCurrentStage(1);
			estimatedTime16.setEstimatedTime(48);
			estimatedTime16.setStartDateTime(simpleDateFormat
					.parse(str_startDate16));
			estimatedTime16.setEndDateTime(simpleDateFormat
					.parse(str_endDate16));
			estimatedTimeDAO16.insertQueueEstimatedTime(estimatedTime16);
			estimatedTimeDAO16.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO17 = new QueueEstimatedTimeDAO();
			String str_startDate17 = "2017-01-01 17:00";
			String str_endDate17 = "2017-01-01 17:59";
			QueueEstimatedTime estimatedTime17 = new QueueEstimatedTime();
			estimatedTime17.setCurrentStage(1);
			estimatedTime17.setEstimatedTime(30);
			estimatedTime17.setStartDateTime(simpleDateFormat
					.parse(str_startDate17));
			estimatedTime17.setEndDateTime(simpleDateFormat
					.parse(str_endDate17));
			estimatedTimeDAO17.insertQueueEstimatedTime(estimatedTime17);
			estimatedTimeDAO17.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO18 = new QueueEstimatedTimeDAO();
			String str_startDate18 = "2017-01-01 18:00";
			String str_endDate18 = "2017-01-01 18:59";
			QueueEstimatedTime estimatedTime18 = new QueueEstimatedTime();
			estimatedTime18.setCurrentStage(1);
			estimatedTime18.setEstimatedTime(17);
			estimatedTime18.setStartDateTime(simpleDateFormat
					.parse(str_startDate18));
			estimatedTime18.setEndDateTime(simpleDateFormat
					.parse(str_endDate18));
			estimatedTimeDAO18.insertQueueEstimatedTime(estimatedTime18);
			estimatedTimeDAO18.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO19 = new QueueEstimatedTimeDAO();
			String str_startDate19 = "2017-01-01 19:00";
			String str_endDate19 = "2017-01-01 19:59";
			QueueEstimatedTime estimatedTime19 = new QueueEstimatedTime();
			estimatedTime19.setCurrentStage(1);
			estimatedTime19.setEstimatedTime(22);
			estimatedTime19.setStartDateTime(simpleDateFormat
					.parse(str_startDate19));
			estimatedTime19.setEndDateTime(simpleDateFormat
					.parse(str_endDate19));
			estimatedTimeDAO19.insertQueueEstimatedTime(estimatedTime19);
			estimatedTimeDAO19.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO20 = new QueueEstimatedTimeDAO();
			String str_startDate20 = "2017-01-01 20:00";
			String str_endDate20 = "2017-01-01 20:59";
			QueueEstimatedTime estimatedTime20 = new QueueEstimatedTime();
			estimatedTime20.setCurrentStage(1);
			estimatedTime20.setEstimatedTime(11);
			estimatedTime20.setStartDateTime(simpleDateFormat
					.parse(str_startDate20));
			estimatedTime20.setEndDateTime(simpleDateFormat
					.parse(str_endDate20));
			estimatedTimeDAO20.insertQueueEstimatedTime(estimatedTime20);
			estimatedTimeDAO20.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO21 = new QueueEstimatedTimeDAO();
			String str_startDate21 = "2017-01-01 21:00";
			String str_endDate21 = "2017-01-01 21:59";
			QueueEstimatedTime estimatedTime21 = new QueueEstimatedTime();
			estimatedTime21.setCurrentStage(1);
			estimatedTime21.setEstimatedTime(13);
			estimatedTime21.setStartDateTime(simpleDateFormat
					.parse(str_startDate21));
			estimatedTime21.setEndDateTime(simpleDateFormat
					.parse(str_endDate21));
			estimatedTimeDAO21.insertQueueEstimatedTime(estimatedTime21);
			estimatedTimeDAO21.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO22 = new QueueEstimatedTimeDAO();
			String str_startDate22 = "2017-01-01 22:00";
			String str_endDate22 = "2017-01-01 22:59";
			QueueEstimatedTime estimatedTime22 = new QueueEstimatedTime();
			estimatedTime22.setCurrentStage(1);
			estimatedTime22.setEstimatedTime(10);
			estimatedTime22.setStartDateTime(simpleDateFormat
					.parse(str_startDate22));
			estimatedTime22.setEndDateTime(simpleDateFormat
					.parse(str_endDate22));
			estimatedTimeDAO22.insertQueueEstimatedTime(estimatedTime22);
			estimatedTimeDAO22.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO23 = new QueueEstimatedTimeDAO();
			String str_startDate23 = "2017-01-01 23:00";
			String str_endDate23 = "2017-01-01 23:59";
			QueueEstimatedTime estimatedTime23 = new QueueEstimatedTime();
			estimatedTime23.setCurrentStage(1);
			estimatedTime23.setEstimatedTime(9);
			estimatedTime23.setStartDateTime(simpleDateFormat
					.parse(str_startDate23));
			estimatedTime23.setEndDateTime(simpleDateFormat
					.parse(str_endDate23));
			estimatedTimeDAO23.insertQueueEstimatedTime(estimatedTime23);
			estimatedTimeDAO23.closeEntityManager();

			QueueEstimatedTimeDAO estimatedTimeDAO0 = new QueueEstimatedTimeDAO();
			String str_startDate0 = "2017-01-01 0:00";
			String str_endDate0 = "2017-01-01 0:59";
			QueueEstimatedTime estimatedTime0 = new QueueEstimatedTime();
			estimatedTime0.setCurrentStage(1);
			estimatedTime0.setEstimatedTime(15);
			estimatedTime0.setStartDateTime(simpleDateFormat
					.parse(str_startDate0));
			estimatedTime0.setEndDateTime(simpleDateFormat.parse(str_endDate0));
			estimatedTimeDAO0.insertQueueEstimatedTime(estimatedTime0);
			estimatedTimeDAO0.closeEntityManager();

			// stage3
			QueueEstimatedTimeDAO estimatedDAO = new QueueEstimatedTimeDAO();
			String st_startDate = "2017-01-01 1:00";
			String st_endDate = "2017-01-01 1:59";
			QueueEstimatedTime time = new QueueEstimatedTime();
			time.setCurrentStage(3);
			time.setEstimatedTime(20);
			time.setStartDateTime(simpleDateFormat.parse(st_startDate));
			time.setEndDateTime(simpleDateFormat.parse(st_endDate));
			estimatedDAO.insertQueueEstimatedTime(time);
			estimatedDAO.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO2 = new QueueEstimatedTimeDAO();
			String st_startDate2 = "2017-01-01 2:00";
			String st_endDate2 = "2017-01-01 2:59";
			QueueEstimatedTime time2 = new QueueEstimatedTime();
			time2.setCurrentStage(3);
			time2.setEstimatedTime(14);
			time2.setStartDateTime(simpleDateFormat.parse(st_startDate2));
			time2.setEndDateTime(simpleDateFormat.parse(st_endDate2));
			estimatedDAO2.insertQueueEstimatedTime(time2);
			estimatedDAO2.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO3 = new QueueEstimatedTimeDAO();
			String st_startDate3 = "2017-01-01 3:00";
			String st_endDate3 = "2017-01-01 3:59";
			QueueEstimatedTime time3 = new QueueEstimatedTime();
			time3.setCurrentStage(3);
			time3.setEstimatedTime(19);
			time3.setStartDateTime(simpleDateFormat.parse(st_startDate3));
			time3.setEndDateTime(simpleDateFormat.parse(st_endDate3));
			estimatedDAO3.insertQueueEstimatedTime(time3);
			estimatedDAO3.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO4 = new QueueEstimatedTimeDAO();
			String st_startDate4 = "2017-01-01 4:00";
			String st_endDate4 = "2017-01-01 4:59";
			QueueEstimatedTime time4 = new QueueEstimatedTime();
			time4.setCurrentStage(3);
			time4.setEstimatedTime(14);
			time4.setStartDateTime(simpleDateFormat.parse(st_startDate4));
			time4.setEndDateTime(simpleDateFormat.parse(st_endDate4));
			estimatedDAO4.insertQueueEstimatedTime(time4);
			estimatedDAO4.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO5 = new QueueEstimatedTimeDAO();
			String st_startDate5 = "2017-01-01 5:00";
			String st_endDate5 = "2017-01-01 5:59";
			QueueEstimatedTime time5 = new QueueEstimatedTime();
			time5.setCurrentStage(3);
			time5.setEstimatedTime(14);
			time5.setStartDateTime(simpleDateFormat.parse(st_startDate5));
			time5.setEndDateTime(simpleDateFormat.parse(st_endDate5));
			estimatedDAO5.insertQueueEstimatedTime(time5);
			estimatedDAO5.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO6 = new QueueEstimatedTimeDAO();
			String st_startDate6 = "2017-01-01 6:00";
			String st_endDate6 = "2017-01-01 6:59";
			QueueEstimatedTime time6 = new QueueEstimatedTime();
			time6.setCurrentStage(3);
			time6.setEstimatedTime(24);
			time6.setStartDateTime(simpleDateFormat.parse(st_startDate6));
			time6.setEndDateTime(simpleDateFormat.parse(st_endDate6));
			estimatedDAO6.insertQueueEstimatedTime(time6);
			estimatedDAO6.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO7 = new QueueEstimatedTimeDAO();
			String st_startDate7 = "2017-01-01 7:00";
			String st_endDate7 = "2017-01-01 7:59";
			QueueEstimatedTime time7 = new QueueEstimatedTime();
			time7.setCurrentStage(3);
			time7.setEstimatedTime(17);
			time7.setStartDateTime(simpleDateFormat.parse(st_startDate7));
			time7.setEndDateTime(simpleDateFormat.parse(st_endDate7));
			estimatedDAO7.insertQueueEstimatedTime(time7);
			estimatedDAO7.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO8 = new QueueEstimatedTimeDAO();
			String st_startDate8 = "2017-01-01 8:00";
			String st_endDate8 = "2017-01-01 8:59";
			QueueEstimatedTime time8 = new QueueEstimatedTime();
			time8.setCurrentStage(3);
			time8.setEstimatedTime(36);
			time8.setStartDateTime(simpleDateFormat.parse(st_startDate8));
			time8.setEndDateTime(simpleDateFormat.parse(st_endDate8));
			estimatedDAO8.insertQueueEstimatedTime(time8);
			estimatedDAO8.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO9 = new QueueEstimatedTimeDAO();
			String st_startDate9 = "2017-01-01 9:00";
			String st_endDate9 = "2017-01-01 9:59";
			QueueEstimatedTime time9 = new QueueEstimatedTime();
			time9.setCurrentStage(3);
			time9.setEstimatedTime(42);
			time9.setStartDateTime(simpleDateFormat.parse(st_startDate9));
			time9.setEndDateTime(simpleDateFormat.parse(st_endDate9));
			estimatedDAO9.insertQueueEstimatedTime(time9);
			estimatedDAO9.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO10 = new QueueEstimatedTimeDAO();
			String st_startDate10 = "2017-01-01 10:00";
			String st_endDate10 = "2017-01-01 10:59";
			QueueEstimatedTime time10 = new QueueEstimatedTime();
			time10.setCurrentStage(3);
			time10.setEstimatedTime(31);
			time10.setStartDateTime(simpleDateFormat.parse(st_startDate10));
			time10.setEndDateTime(simpleDateFormat.parse(st_endDate10));
			estimatedDAO10.insertQueueEstimatedTime(time10);
			estimatedDAO10.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO11 = new QueueEstimatedTimeDAO();
			String st_startDate11 = "2017-01-01 11:00";
			String st_endDate11 = "2017-01-01 11:59";
			QueueEstimatedTime time11 = new QueueEstimatedTime();
			time11.setCurrentStage(3);
			time11.setEstimatedTime(29);
			time11.setStartDateTime(simpleDateFormat.parse(st_startDate11));
			time11.setEndDateTime(simpleDateFormat.parse(st_endDate11));
			estimatedDAO11.insertQueueEstimatedTime(time11);
			estimatedDAO11.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO12 = new QueueEstimatedTimeDAO();
			String st_startDate12 = "2017-01-01 12:00";
			String st_endDate12 = "2017-01-01 12:59";
			QueueEstimatedTime time12 = new QueueEstimatedTime();
			time12.setCurrentStage(3);
			time12.setEstimatedTime(45);
			time12.setStartDateTime(simpleDateFormat.parse(st_startDate12));
			time12.setEndDateTime(simpleDateFormat.parse(st_endDate12));
			estimatedDAO12.insertQueueEstimatedTime(time12);
			estimatedDAO12.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO13 = new QueueEstimatedTimeDAO();
			String st_startDate13 = "2017-01-01 13:00";
			String st_endDate13 = "2017-01-01 13:59";
			QueueEstimatedTime time13 = new QueueEstimatedTime();
			time13.setCurrentStage(3);
			time13.setEstimatedTime(39);
			time13.setStartDateTime(simpleDateFormat.parse(st_startDate13));
			time13.setEndDateTime(simpleDateFormat.parse(st_endDate13));
			estimatedDAO13.insertQueueEstimatedTime(time13);
			estimatedDAO13.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO14 = new QueueEstimatedTimeDAO();
			String st_startDate14 = "2017-01-01 14:00";
			String st_endDate14 = "2017-01-01 14:59";
			QueueEstimatedTime time14 = new QueueEstimatedTime();
			time14.setCurrentStage(3);
			time14.setEstimatedTime(28);
			time14.setStartDateTime(simpleDateFormat.parse(st_startDate14));
			time14.setEndDateTime(simpleDateFormat.parse(st_endDate14));
			estimatedDAO14.insertQueueEstimatedTime(time14);
			estimatedDAO14.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO15 = new QueueEstimatedTimeDAO();
			String st_startDate15 = "2017-01-01 15:00";
			String st_endDate15 = "2017-01-01 15:59";
			QueueEstimatedTime time15 = new QueueEstimatedTime();
			time15.setCurrentStage(3);
			time15.setEstimatedTime(33);
			time15.setStartDateTime(simpleDateFormat.parse(st_startDate15));
			time15.setEndDateTime(simpleDateFormat.parse(st_endDate15));
			estimatedDAO15.insertQueueEstimatedTime(time15);
			estimatedDAO15.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO16 = new QueueEstimatedTimeDAO();
			String st_startDate16 = "2017-01-01 16:00";
			String st_endDate16 = "2017-01-01 16:59";
			QueueEstimatedTime time16 = new QueueEstimatedTime();
			time16.setCurrentStage(3);
			time16.setEstimatedTime(48);
			time16.setStartDateTime(simpleDateFormat.parse(st_startDate16));
			time16.setEndDateTime(simpleDateFormat.parse(st_endDate16));
			estimatedDAO16.insertQueueEstimatedTime(time16);
			estimatedDAO16.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO17 = new QueueEstimatedTimeDAO();
			String st_startDate17 = "2017-01-01 17:00";
			String st_endDate17 = "2017-01-01 17:59";
			QueueEstimatedTime time17 = new QueueEstimatedTime();
			time17.setCurrentStage(3);
			time17.setEstimatedTime(30);
			time17.setStartDateTime(simpleDateFormat.parse(st_startDate17));
			time17.setEndDateTime(simpleDateFormat.parse(st_endDate17));
			estimatedDAO17.insertQueueEstimatedTime(time17);
			estimatedDAO17.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO18 = new QueueEstimatedTimeDAO();
			String st_startDate18 = "2017-01-01 18:00";
			String st_endDate18 = "2017-01-01 18:59";
			QueueEstimatedTime time18 = new QueueEstimatedTime();
			time18.setCurrentStage(3);
			time18.setEstimatedTime(17);
			time18.setStartDateTime(simpleDateFormat.parse(st_startDate18));
			time18.setEndDateTime(simpleDateFormat.parse(st_endDate18));
			estimatedDAO18.insertQueueEstimatedTime(time18);
			estimatedDAO18.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO19 = new QueueEstimatedTimeDAO();
			String st_startDate19 = "2017-01-01 19:00";
			String st_endDate19 = "2017-01-01 19:59";
			QueueEstimatedTime time19 = new QueueEstimatedTime();
			time19.setCurrentStage(3);
			time19.setEstimatedTime(22);
			time19.setStartDateTime(simpleDateFormat.parse(st_startDate19));
			time19.setEndDateTime(simpleDateFormat.parse(st_endDate19));
			estimatedDAO19.insertQueueEstimatedTime(time19);
			estimatedDAO19.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO20 = new QueueEstimatedTimeDAO();
			String st_startDate20 = "2017-01-01 20:00";
			String st_endDate20 = "2017-01-01 20:59";
			QueueEstimatedTime time20 = new QueueEstimatedTime();
			time20.setCurrentStage(3);
			time20.setEstimatedTime(11);
			time20.setStartDateTime(simpleDateFormat.parse(st_startDate20));
			time20.setEndDateTime(simpleDateFormat.parse(st_endDate20));
			estimatedDAO20.insertQueueEstimatedTime(time20);
			estimatedDAO20.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO21 = new QueueEstimatedTimeDAO();
			String st_startDate21 = "2017-01-01 21:00";
			String st_endDate21 = "2017-01-01 21:59";
			QueueEstimatedTime time21 = new QueueEstimatedTime();
			time21.setCurrentStage(3);
			time21.setEstimatedTime(13);
			time21.setStartDateTime(simpleDateFormat.parse(st_startDate21));
			time21.setEndDateTime(simpleDateFormat.parse(st_endDate21));
			estimatedDAO21.insertQueueEstimatedTime(time21);
			estimatedDAO21.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO22 = new QueueEstimatedTimeDAO();
			String st_startDate22 = "2017-01-01 22:00";
			String st_endDate22 = "2017-01-01 22:59";
			QueueEstimatedTime time22 = new QueueEstimatedTime();
			time22.setCurrentStage(3);
			time22.setEstimatedTime(10);
			time22.setStartDateTime(simpleDateFormat.parse(st_startDate22));
			time22.setEndDateTime(simpleDateFormat.parse(st_endDate22));
			estimatedDAO22.insertQueueEstimatedTime(time22);
			estimatedDAO22.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO23 = new QueueEstimatedTimeDAO();
			String st_startDate23 = "2017-01-01 23:00";
			String st_endDate23 = "2017-01-01 23:59";
			QueueEstimatedTime time23 = new QueueEstimatedTime();
			time23.setCurrentStage(3);
			time23.setEstimatedTime(9);
			time23.setStartDateTime(simpleDateFormat.parse(st_startDate23));
			time23.setEndDateTime(simpleDateFormat.parse(st_endDate23));
			estimatedDAO23.insertQueueEstimatedTime(time23);
			estimatedDAO23.closeEntityManager();

			QueueEstimatedTimeDAO estimatedDAO0 = new QueueEstimatedTimeDAO();
			String st_startDate0 = "2017-01-01 0:00";
			String st_endDate0 = "2017-01-01 0:59";
			QueueEstimatedTime time0 = new QueueEstimatedTime();
			time0.setCurrentStage(3);
			time0.setEstimatedTime(15);
			time0.setStartDateTime(simpleDateFormat.parse(st_startDate0));
			time0.setEndDateTime(simpleDateFormat.parse(st_endDate0));
			estimatedDAO0.insertQueueEstimatedTime(time0);
			estimatedDAO0.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/initialPatientWithUser", method = RequestMethod.GET)
	@ResponseBody
	public String initialPatient() {

		try {
			// add patient and create user account for them
			PatientDAO patientDAO = new PatientDAO();
			Patient patient1 = new Patient();
			patient1.setCodeHN("129896");
			patient1.setCitizenId("1659900234562");
			patient1.setFirstname("���");
			patient1.setLastname("A");
			patient1.setContact(StringUtil.randomNumber(10));
			patientDAO.savePatient(patient1);
			patientDAO.closeEntityManager();

			UserDAO userDAO = new UserDAO();
			User user = new User();
			user.setCitizenId("1659900234562");
			user.setCodeHN("129896");
			user.setRole("Patient");
			user.setFirstname("User");
			user.setLastname("A");
			user.setUsername("patientA");
			user.setPassword("patientA");
			user.setStatus(1);
			userDAO.saveUser(user);
			userDAO.closeEntityManager();

			PatientDAO patientDAO2 = new PatientDAO();
			Patient patient2 = new Patient();
			patient2.setCodeHN("129897");
			patient2.setCitizenId("1659900234563");
			patient2.setFirstname("User");
			patient2.setLastname("B");
			patient2.setContact(StringUtil.randomNumber(10));
			patientDAO2.savePatient(patient2);
			patientDAO2.closeEntityManager();

			UserDAO userDAO2 = new UserDAO();
			User user2 = new User();
			user2.setCitizenId("1659900234563");
			user2.setCodeHN("129897");
			user2.setRole("Patient");
			user2.setFirstname("User");
			user2.setLastname("B");
			user2.setUsername("patientB");
			user2.setPassword("patientB");
			user2.setStatus(1);
			userDAO2.saveUser(user2);
			userDAO2.closeEntityManager();

			PatientDAO patientDAO3 = new PatientDAO();
			Patient patient3 = new Patient();
			patient3.setCodeHN("129898");
			patient3.setCitizenId("1659900234564");
			patient3.setFirstname("User");
			patient3.setLastname("C");
			patient3.setContact(StringUtil.randomNumber(10));
			patientDAO3.savePatient(patient3);
			patientDAO3.closeEntityManager();

			UserDAO userDAO3 = new UserDAO();
			User user3 = new User();
			user3.setCitizenId("1659900234564");
			user3.setCodeHN("129898");
			user3.setRole("Patient");
			user3.setFirstname("User");
			user3.setLastname("C");
			user3.setUsername("patientC");
			user3.setPassword("patientC");
			user3.setStatus(1);
			userDAO3.saveUser(user3);
			userDAO3.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

}