package com.mfu.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.record.ClinicDAO;
import com.mfu.entity.record.Clinic;
import com.mfu.web.model.WSResponse;

@Controller
public class ClinicController {
	@RequestMapping("/listClinicWS")
	public @ResponseBody List<Clinic> listClinicWS(HttpServletRequest request) {
		ClinicDAO dao = new ClinicDAO();

		List<Clinic> list = null;
		try {
			list = dao.getAllClinic();
			dao.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@RequestMapping("/saveClinicWS")
	public @ResponseBody String saveClinicWS(@RequestBody Clinic clinic) {
		ClinicDAO dao = new ClinicDAO();
		try {
			if (clinic.getKey() == null) {
				Clinic foundClinic = dao.findClinicByClinicCode(clinic.getClinicCode());
				if(foundClinic!=null){
					clinic.setKey(foundClinic.getKey());
					dao.updateClinic(clinic);
					dao.closeEntityManager();
				} else {
					dao.insertClinic(clinic);
					dao.closeEntityManager();
				}
			} else {
				dao.updateClinic(clinic);
				dao.closeEntityManager();
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}
	
	@RequestMapping(value = "/insertClinicBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> insertClinicBatch(@RequestBody Clinic[] list, HttpServletRequest request){
		List<WSResponse> resList = new ArrayList<WSResponse>();
		String result;
		for (Clinic d : list) {
			
			result = this.saveClinicWS(d);
			resList.add(new WSResponse(d.getClinicCode()+" "+d.getName(), Integer.parseInt(result)));
		}

		return resList;
	}

	@RequestMapping("/deleteClinicWS")
	public @ResponseBody String deleteClinicWS(HttpServletRequest request) {
		ClinicDAO dao = new ClinicDAO();
		try {
			dao.deleteClinic(request.getParameter("key"));
			dao.closeEntityManager();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

}
