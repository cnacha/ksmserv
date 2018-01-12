package com.mfu.web.controller;

import java.util.ArrayList;
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
import com.mfu.dao.ha.ImpactFactorDAO;
import com.mfu.dao.ha.UserSymptomDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.ha.ImpactFactor;
import com.mfu.entity.ha.UserSymptom;
import com.mfu.entity.record.Patient;
import com.mfu.util.SecurityUtil;
import com.mfu.util.StringUtil;
import com.mfu.web.model.WSResponse;

@Controller
public class PatientController {
	
	private static final Logger log = Logger.getLogger(PatientController.class
			.getName());

	@RequestMapping(value = "/listPatientWS", method = RequestMethod.GET)
	@ResponseBody
	public List<Patient> listPatientWS(HttpServletRequest request) {

		PatientDAO patientService = new PatientDAO();
		List<Patient> patients = patientService.getAllPatient();
		patientService.closeEntityManager();
		return patients;
	}

	@RequestMapping(value = "/updateDeviceTokenKeyOnSpecificUser", method = RequestMethod.GET)
	@ResponseBody
	public String updateDeviceTokenKeyOnSpecificUser(HttpServletRequest request) {

		String userKey = request.getParameter("userKey");
		String tokenKey = request.getParameter("tokenKey");

		try {
			UserDAO dao = new UserDAO();
			User user = dao.findUserByKey(userKey);
			user.setDeviceTokenKey(tokenKey);
			dao.updateUser(user);
			dao.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/findPatientByIdWS", method = RequestMethod.GET)
	@ResponseBody
	public Patient findPatientByIdWS(HttpServletRequest request) {

		Patient patient = null;
		PatientDAO patientService = new PatientDAO();
		try {

			patient = patientService.findPatientByKey(request.getParameter("patientId"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		patientService.closeEntityManager();
		return patient;
	}



	@RequestMapping(value = "/savePatientWS", method = RequestMethod.POST)
	@ResponseBody
	public String savePatientWS(@RequestBody Patient patient, HttpServletRequest request) {

		PatientDAO patientService = new PatientDAO();
		try {
			if(!"".equals(patient.getCodeHN())){
				Patient foundPatient = patientService.findPatientsByHospitalNumber(patient.getCodeHN());
				if(foundPatient!=null){
					patient.setProfilePicture("profile-default.png");
					patient.setKey(foundPatient.getKey());
					patientService.updatePatient(patient);
				} else {
					patientService.savePatient(patient);
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally{
			patientService.closeEntityManager();
		}

		return "1";
	}
	
	@RequestMapping(value = "/insertPatientBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> insertPatientBatch(@RequestBody Patient[] list, HttpServletRequest request){
		List<WSResponse> resList = new ArrayList<WSResponse>();

		String result;
		for (Patient d : list) {
			result = this.savePatientWS(d, request);
			resList.add(new WSResponse(d.getFirstname()+" "+d.getLastname(), Integer.parseInt(result)));
		}

		return resList;
	}
		

	@RequestMapping(value = "/recordPatientProfileWS", method = RequestMethod.POST)
	@ResponseBody
	public String recordPatientProfileWS(@RequestBody Patient patient, HttpServletRequest request) {

		PatientDAO dao = new PatientDAO();
		try {
			
			if (patient.getCodeHN() != null && !"".equals(patient.getCodeHN())){
				Patient p = dao.findPatientsByHospitalNumber(patient.getCodeHN());
			
				p.setBirthdate(patient.getBirthdate());
				p.setGender(patient.getGender());
				p.setBloodGroup(patient.getBloodGroup());
				p.setBloodRH(patient.getBloodRH());
				p.setMaterialStatus(patient.getMaterialStatus());
				p.setNationality(patient.getNationality());
				p.setRace(patient.getRace());
				dao.updatePatient(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally {
			dao.closeEntityManager();
		}

		return "1";
	}
/**
	@RequestMapping(value = "/recordVitalSignWS", method = RequestMethod.POST)
	@ResponseBody
	public String recordVitalSignWS(@RequestBody VitalSign v, HttpServletRequest request) {

		PatientDAO dao = new PatientDAO();
		try {
			Patient p = dao.findPatientsByHospitalNumber(v.getCodeHN());
			if (p != null) {
				v.setPatientId(p.getKeyString());
				v.setUpdateDate(new Date());
				dao.insertVitalSign(v);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally {
			dao.closeEntityManager();
		}

		return "1";
	}

	
	
	@RequestMapping(value = "/getLatestVitalSignWS", method = RequestMethod.GET)
	public @ResponseBody VitalSign getLatestVitalSign(HttpServletRequest request) {
		String patientid = request.getParameter("patientid");
		String codeHN = request.getParameter("codeHN");
		VitalSign v = null;
		PatientDAO dao = new PatientDAO();
		try {
			if (patientid != null) {

				List<VitalSign> result = dao.getVitalSignByPatient(patientid);
				if (result != null && result.size() > 0)
					v = result.get(0);
			} else if (codeHN != null) {
				Patient p = dao.findPatientsByHospitalNumber(codeHN);
				List<VitalSign> result = dao.getVitalSignByPatient(p.getKeyString());
				if (result != null && result.size() > 0)
					v = result.get(0);
			}
			// System.out.println(v.getKey());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeEntityManager();
		}

		return v;
	}
**/
	@RequestMapping(value = "/searchPatientByHospitalNumberWS", method = RequestMethod.GET)
	public @ResponseBody Patient searchPatientByHospitalNumberWS(HttpServletRequest request) {
		log.info("searchPatientByHospitalNumberWS called");
		PatientDAO patientService = new PatientDAO();
		Patient patient = patientService.findPatientsByHospitalNumber(request.getParameter("hospitalNumber"));
		patientService.closeEntityManager();
		return patient;
	}
	
	@RequestMapping("/getSymptomByPatientWS")
	public @ResponseBody List<UserSymptom> getSymptomByPatient(HttpServletRequest request){
		UserSymptomDAO dao = new UserSymptomDAO();
		PatientDAO pdao = new PatientDAO();
		try {
			String patientKey = null;
			if(request.getParameter("codeHN")!=null && request.getParameter("key")==null){
				patientKey = pdao.findPatientsByHospitalNumber(request.getParameter("codeHN")).getKeyString();
				
			} else
				patientKey = request.getParameter("key");
			List<UserSymptom> res = dao.getSyntomByPatient(patientKey);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
			pdao.closeEntityManager();
		}
		return null;
	}


	@RequestMapping(value = "/checkPatientPasswordWS")
	public @ResponseBody String checkPatientPasswordWSWS(HttpServletRequest request) {

		SecurityUtil securityService = new SecurityUtil();
		if (securityService.passwordToMD5(request.getParameter("password"))
				.equals(request.getParameter("currentPassword"))) {
			return "1";
		}

		return "0";
	}

	@RequestMapping(value = "/deletePatientWS", method = RequestMethod.DELETE)
	public @ResponseBody String removePatient(HttpServletRequest request) {

		try {
			PatientDAO patientService = new PatientDAO();
			patientService.deletePatient(request.getParameter("patientId"));
			patientService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";

	}

	@RequestMapping(value = "/genTestPatient", method = RequestMethod.GET)
	public @ResponseBody String genTestPatient(HttpServletRequest request) {

		try {

			int num = Integer.parseInt(request.getParameter("num"));

			for (int i = 0; i < num; i++) {
				PatientDAO patientService = new PatientDAO();
				Patient patient = new Patient();
				patient.setContact(StringUtil.randomNumber(10));
				patient.setCitizenId(StringUtil.randomNumber(13));
				patient.setFirstname(StringUtil.randomString(10));
				patient.setLastname(StringUtil.randomString(10));
				patient.setCodeHN(StringUtil.randomNumber(8));

				patientService.savePatient(patient);
				patientService.closeEntityManager();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";

	}

}