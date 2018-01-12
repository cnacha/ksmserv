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

import com.mfu.dao.record.VitalSignDAO;
import com.mfu.entity.record.VitalSignRecord;
import com.mfu.web.model.WSResponse;

@Controller
public class VitalSignController {

	private static final Logger log = Logger
			.getLogger(VitalSignController.class.getName());

	@RequestMapping(value = "/recordVitalSignWS", method = RequestMethod.POST)
	@ResponseBody
	public String recordVitalSign(@RequestBody VitalSignRecord v) {
		try {
			VitalSignDAO dao = new VitalSignDAO();
			v.setUpdateDate(new Date());
			dao.insertVitalSign(v);
			HealthAnalysisController kc = new HealthAnalysisController();
			kc.classifySymptomCloudML(v);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());

		}
		return "-1";
	}

	@RequestMapping(value = "/getLatestVitalSignWS", method = RequestMethod.GET)
	@ResponseBody
	public VitalSignRecord getLastVitalSign(HttpServletRequest request) {
		try {
			VitalSignDAO dao = new VitalSignDAO();
			VitalSignRecord rec = dao.findLastVitalSignByHN(request
					.getParameter("codeHN"));
			return rec;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/recordVitalSignBatchWS", method = RequestMethod.POST)
	@ResponseBody
	public List<WSResponse> recordVitalSignBatch(
			@RequestBody VitalSignRecord[] vlist) {
		List<WSResponse> resList = new ArrayList<WSResponse>();

		String result;
		for (VitalSignRecord v : vlist) {
			result = this.recordVitalSign(v);
			resList.add(new WSResponse(v.getCodeHN(), Integer.parseInt(result)));
		}

		return resList;
	}
	/**
	 * @RequestMapping(value = "/listVitalSignWS", method = RequestMethod.GET)
	 * @ResponseBody public List<VitalSign> listVitalSignWS(HttpServletRequest
	 *               request) {
	 * 
	 *               VitalSignDAO VitalSignServ = new VitalSignDAO();
	 *               List<VitalSign> vitalSign =
	 *               VitalSignServ.getAllVitalSign();
	 *               VitalSignServ.closeEntityManager(); return vitalSign; }
	 * @RequestMapping(value = "/saveVitalSignWS", method = RequestMethod.POST)
	 * @ResponseBody public String saveVitalSignWS(@RequestBody VitalSign
	 *               vitalSign, HttpServletRequest request) {
	 * 
	 *               VitalSignDAO VitalSignServ = new VitalSignDAO(); try {
	 *               if(vitalSign.getKey() == null)
	 *               VitalSignServ.saveVitalSign(vitalSign); else
	 *               VitalSignServ.updateVitalSign(vitalSign); return "1"; }
	 *               catch (Exception e) { e.printStackTrace(); }finally{
	 *               VitalSignServ.closeEntityManager(); } return "-1"; }
	 * @RequestMapping(value = "/deleteVitalSignWS", method =
	 *                       RequestMethod.DELETE) public @ResponseBody String
	 *                       deleteVitalSignWS(HttpServletRequest request) {
	 * 
	 *                       try {
	 * 
	 *                       VitalSignDAO VitalSignServ = new VitalSignDAO();
	 *                       VitalSignServ
	 *                       .deleteVitalSign(request.getParameter("key"));
	 *                       VitalSignServ.closeEntityManager(); } catch
	 *                       (Exception e) { e.printStackTrace(); return "-1"; }
	 * 
	 *                       return "1"; }
	 * @RequestMapping(value = "/addVitalSignWS", method = RequestMethod.GET)
	 * @ResponseBody public String TestVitalSign() {
	 * 
	 *               try { VitalSignDAO vitalSignService = new VitalSignDAO();
	 *               VitalSign vitalSign = new VitalSign();
	 * 
	 * 
	 * 
	 *               /* vitalSign.setBodyHeight(120);
	 *               vitalSign.setBodyWeight(50); vitalSign.setBpDiastolic(62);
	 *               vitalSign.setBpSystolic(95); vitalSign.setPulseRate(65);
	 *               vitalSign.setRespitoryRate(76);
	 *               vitalSign.setTemperature(34);
	 *               vitalSign.setCodeHN("12345678"); vitalSign.setPatientId(
	 *               "ag5rc21zZXJ2LTE1OTMwOHIUCxIHUGF0aWVudBiAgICAgIDJCAw");
	 *               vitalSignService.saveVitalSign(vitalSign);
	 *               vitalSignService.closeEntityManager();
	 * 
	 * 
	 * 
	 *               vitalSign.setBodyHeight(120); vitalSign.setBodyWeight(50);
	 *               vitalSign.setBpDiastolic(92); vitalSign.setBpSystolic(144);
	 *               vitalSign.setPulseRate(189);
	 *               vitalSign.setRespitoryRate(76);
	 *               vitalSign.setTemperature(34);
	 *               vitalSign.setCodeHN("88993564"); vitalSign.setPatientId(
	 *               "ag5rc21zZXJ2LTE1OTMwOHIUCxIHUGF0aWVudBiAgICAgIDJCQw");
	 *               vitalSignService.saveVitalSign(vitalSign);
	 *               vitalSignService.closeEntityManager();
	 * 
	 *               vitalSign.setBodyHeight(120); vitalSign.setBodyWeight(50);
	 *               vitalSign.setBpDiastolic(84); vitalSign.setBpSystolic(122);
	 *               vitalSign.setPulseRate(120);
	 *               vitalSign.setRespitoryRate(76);
	 *               vitalSign.setTemperature(34);
	 *               vitalSign.setCodeHN("12115487"); vitalSign.setPatientId(
	 *               "ag5rc21zZXJ2LTE1OTMwOHIUCxIHUGF0aWVudBiAgICAgICJCww");
	 *               vitalSignService.saveVitalSign(vitalSign);
	 *               vitalSignService.closeEntityManager();
	 * 
	 * 
	 * 
	 *               } catch (Exception e) { e.printStackTrace(); return "-1"; }
	 * 
	 *               return "1"; }
	 **/

}
