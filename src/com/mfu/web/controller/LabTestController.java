package com.mfu.web.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import com.mfu.dao.record.LabTestResultDAO;
import com.mfu.entity.record.LabTestResult;
import com.mfu.web.model.WSResponse;

@Controller
public class LabTestController {

	private static final Logger log = Logger
			.getLogger(LabTestController.class.getName());

	@RequestMapping(value = "/recordLabTestResultWS", method = RequestMethod.POST)
	@ResponseBody
	public String recordLabTestResult(@RequestBody LabTestResult v) {
		LabTestResultDAO dao = new LabTestResultDAO();
		try {
			
			v.setUpdateDate(new Date());
			dao.insertLabTestResult(v);
			//HealthAnalysisController kc = new HealthAnalysisController();
			//kc.classifySymptomCloudML(v);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());

		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}

	@RequestMapping(value = "/getLatestLabTestWS", method = RequestMethod.GET)
	@ResponseBody
	public LabTestResult getLastRecord(HttpServletRequest request) {
		LabTestResultDAO dao = new LabTestResultDAO();
		try {
			LabTestResult rec = dao.findLastLabTestResultByName(
					request.getParameter("testName"), 
					request.getParameter("codeHN"));
			
			return rec;
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info("error occurs" + errors.toString());
		}finally{
			dao.closeEntityManager();
		}
		return null;
	}
	
	@RequestMapping(value = "/recordLabTestResultBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> recordLabTestResultBatch(
			@RequestBody LabTestResult[] vlist) {
		List<WSResponse> resList = new ArrayList<WSResponse>();

		String result;
		for (LabTestResult v : vlist) {
			result = this.recordLabTestResult(v);
			resList.add(new WSResponse(v.getCodeHN(), Integer.parseInt(result)));
		}

		return resList;
	}


}
