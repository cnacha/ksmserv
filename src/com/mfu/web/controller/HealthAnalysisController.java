package com.mfu.web.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.ml.v1.CloudMachineLearningEngineScopes;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mfu.dao.common.UserDAO;
import com.mfu.dao.ha.ImpacFactorClusterDAO;
import com.mfu.dao.ha.ImpactFactorDAO;
import com.mfu.dao.ha.LifeFactorDAO;
import com.mfu.dao.ha.SymptomDAO;
import com.mfu.dao.ha.UserSymptomDAO;
import com.mfu.dao.nutrition.ConsumptionDAO;
import com.mfu.dao.record.LabTestResultDAO;
import com.mfu.dao.record.LifeStyleDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.dao.record.VitalSignDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.ha.ImpacFactorCluster;
import com.mfu.entity.ha.ImpactFactor;
import com.mfu.entity.ha.LifeFactor;
import com.mfu.entity.ha.Symptom;
import com.mfu.entity.ha.SymptomCluster;
import com.mfu.entity.ha.TrainingDataSet;
import com.mfu.entity.ha.UserSymptom;
import com.mfu.entity.nutrition.Consumption;
import com.mfu.entity.record.LabTestResult;
import com.mfu.entity.record.LifeStyleRecord;
import com.mfu.entity.record.Patient;
import com.mfu.entity.record.VitalSignRecord;
import com.mfu.web.model.PredictionResponse;
import com.mfu.web.model.PredictionResult;
import com.mfu.web.model.RiskEvaluationResult;

import kmt.Cc;
import kmt.Cluster;
import kmt.KMeanEngine;
import kmt.Point;
import kmt.Pp;
import kmt.km;

@Controller
public class HealthAnalysisController {

	private static final String BUCKET_NAME = "ksmserv-154723.appspot.com";
	private static final String PREDICTWS_URL_PREFIX = "https://ml.googleapis.com/v1/projects/ksmserv-154723/models/";
	private static final String PREDICTWS_URL_SUFFIX = ":predict";
	private static final int BUFFER_SIZE = 2 * 1024 * 1024;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(new RetryParams.Builder()
					.initialRetryDelayMillis(10).retryMaxAttempts(10)
					.totalRetryPeriodMillis(15000).build());

	// ----------------------------------- SYMTOM CONTROLLER
	// ------------------------------------------------
	private static final Logger log = Logger
			.getLogger(HealthAnalysisController.class.getName());

	@RequestMapping(value = "/listSymtomWS", method = RequestMethod.GET)
	@ResponseBody
	public List<Symptom> listSymtomWS(HttpServletRequest request) {

		SymptomDAO SymtomServ = new SymptomDAO();
		List<Symptom> symtom = SymtomServ.getAllSymptom();
		SymtomServ.closeEntityManager();
		return symtom;
	}

	@RequestMapping(value = "/saveSymtomWS", method = RequestMethod.POST)
	@ResponseBody
	public String saveSymtomWS(@RequestBody Symptom symptom,
			HttpServletRequest request) {

		SymptomDAO SymtomServ = new SymptomDAO();
		try {
			if (symptom.getKey() == null)
				SymtomServ.saveSyntom(symptom);
			else
				SymtomServ.updateSyntom(symptom);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SymtomServ.closeEntityManager();
		}
		return "-1";
	}

	@RequestMapping(value = "/deleteSymtomWS", method = RequestMethod.DELETE)
	public @ResponseBody String deleteSymtomWS(HttpServletRequest request) {

		try {

			SymptomDAO SymtomServ = new SymptomDAO();
			SymtomServ.deleteSyntom(request.getParameter("key"));
			SymtomServ.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	// ----------------------------------- IMPACFACTOR CONTROLLER
	// ------------------------------------------------

	@RequestMapping(value = "/listImpactFactorBySymptomIdWS", method = RequestMethod.GET)
	@ResponseBody
	public List<ImpactFactor> findImpactFactorByIdWS(HttpServletRequest request) {

		List<ImpactFactor> impactfactor = null;
		ImpactFactorDAO ImpactService = new ImpactFactorDAO();
		try {

			impactfactor = ImpactService.getSyntomTamplateBySymptomID(request
					.getParameter("symtomID"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		ImpactService.closeEntityManager();
		return impactfactor;
	}

	@RequestMapping(value = "/saveImpactFactorWS", method = RequestMethod.POST)
	@ResponseBody
	public String saveImpactFactorWS(@RequestBody ImpactFactor impactfactor,
			HttpServletRequest request) {

		ImpactFactorDAO ImpactService = new ImpactFactorDAO();
		try {
			if (impactfactor.getKey() == null)
				ImpactService.saveImpactFactor(impactfactor);
			else
				ImpactService.updateImpactFactor(impactfactor);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ImpactService.closeEntityManager();
		}
		return "-1";
	}

	@RequestMapping(value = "/deleteImpactFactorWS", method = RequestMethod.DELETE)
	public @ResponseBody String removeImpactFactor(HttpServletRequest request) {

		try {
			ImpactFactorDAO ImpactService = new ImpactFactorDAO();
			ImpactService.deleteImpactFactor(request.getParameter("key"));
			ImpactService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}
	
	@RequestMapping(value = "/listLifeFactorBySymptomIdWS", method = RequestMethod.GET)
	@ResponseBody
	public List<LifeFactor> findLifeFactorByIdWS(HttpServletRequest request) {

		List<LifeFactor> fa = null;
		LifeFactorDAO dao = new LifeFactorDAO();
		try {

			fa = dao.getLifeFactorBySymptomID(
					request.getParameter("symtomID"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		dao.closeEntityManager();
		return fa;
	}

	@RequestMapping(value = "/saveLifeFactorWS", method = RequestMethod.POST)
	@ResponseBody
	public String saveLifeFactorWS(@RequestBody LifeFactor fa,
			HttpServletRequest request) {

		LifeFactorDAO dao = new LifeFactorDAO();
		try {
			if (fa.getKey() == null)
				dao.saveLifeFactor(fa);
			else
				dao.updateLifeFactor(fa);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.closeEntityManager();
		}
		return "-1";
	}

	@RequestMapping(value = "/deleteLifeFactorWS", method = RequestMethod.DELETE)
	public @ResponseBody String removeLifeFactor(HttpServletRequest request) {

		try {
			LifeFactorDAO dao = new LifeFactorDAO();
			dao.deleteLifeFactor(request.getParameter("key"));
			dao.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}
	
	@RequestMapping(value = "/evaluateLifeStyleWS")
	public @ResponseBody RiskEvaluationResult evaluateLifeStyleWS(HttpServletRequest request) throws Exception{
		String codeHN = request.getParameter("codeHN");
		
		PatientDAO pDAO = new PatientDAO();
		LifeStyleDAO lifestyleDAO = new LifeStyleDAO();
		VitalSignDAO vDAO = new VitalSignDAO();
		UserDAO userDAO = new UserDAO();
		String symptomId = null;
		List<LifeStyleRecord> lsList = null;
		List<Consumption> csList = null;
		VitalSignRecord vRec = null;
		try {
			symptomId = request.getParameter("symptomID");
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -6);
			SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
			Date findDate = dateOnly.parse(dateOnly.format(cal.getTime()));
			
			
			lsList = new ArrayList<LifeStyleRecord>();
			
			for(int i=0; i<7; i++){
				log.info("	findate for lifestyle "+findDate);
				findDate = new Date(findDate.getTime()+(24 * 60 * 60 * 1000));
				LifeStyleRecord lifeStyle = lifestyleDAO.findRecordByDate(codeHN, findDate);
				
				if(lifeStyle == null)
					lifeStyle = new LifeStyleRecord();
				
				//log.info(findDate+" "+lifeStyle.getMeasureValue("202walking"));
				
				lsList.add(lifeStyle);

			}
			
			User user = userDAO.findUserByCodeHN(codeHN);
			request.setAttribute("patientKey", user.getKeyString());
			NutritionController nuControl = new NutritionController();
			csList = nuControl.getConsumptionWeeklyWS(request);
			
			vRec = vDAO.findLastVitalSignByHN(codeHN);
			
			return this.evaluateRiskPotentialTrend(symptomId, vRec, lsList, csList);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info("error occurs" + errors.toString());
			e.printStackTrace();
		}finally{
			pDAO.closeEntityManager();
			userDAO.closeEntityManager();
		}

		return null;
		
	}

	private Point convertFileLineToPoint(String s) throws Exception {
		StringTokenizer st = new StringTokenizer(s, ",");

		List<Double> coordinateList = new ArrayList<Double>();
		while (st.hasMoreTokens()) {
			coordinateList.add(Double.parseDouble(st.nextToken()));
		}
		Point p = new Point(coordinateList.toArray(new Double[coordinateList
				.size()]));
		return p;
	}

	private Point[] convertInputStreamToPoints(InputStream ins)
			throws Exception {
		List<Point> points = new ArrayList<Point>();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String strLine;

		// Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			// Print the content on the console
			log.info("reading file: " + strLine);
			points.add(convertFileLineToPoint(strLine));
		}
		return points.toArray(new Point[points.size()]);
	}

	@RequestMapping(value = "/learnWS", method = RequestMethod.POST)
	public String Learn(@RequestBody TrainingDataSet dataset,
			HttpServletRequest request) {
		log.info("LearningController " + dataset.getSymptomKey());
		try {

			if (!"".equals(dataset.getInitialFilePath())
					&& !"".equals(dataset.getSamplingFilePath())) {
				// get initial file from cloud storage
				log.info("start reading initial file: "
						+ dataset.getInitialFilePath());
				GcsInputChannel readChannel = gcsService
						.openPrefetchingReadChannel(new GcsFilename(
								BUCKET_NAME, dataset.getInitialFilePath()),
								Long.valueOf(0).longValue(), BUFFER_SIZE);
				InputStream ins = Channels.newInputStream(readChannel);
				dataset.setInitialPoint(convertInputStreamToPoints(ins));

				log.info("start reading sampling file: "
						+ dataset.getSamplingFilePath());
				// get training file from cloud storage
				readChannel = gcsService.openPrefetchingReadChannel(
						new GcsFilename(BUCKET_NAME, dataset
								.getSamplingFilePath()), Long.valueOf(0)
								.longValue(), BUFFER_SIZE);
				ins = Channels.newInputStream(readChannel);
				dataset.setSamplingPoint(convertInputStreamToPoints(ins));
			}

			// km kmeans = new km();
			KMeanEngine kmeans = new KMeanEngine();
			kmeans.init(dataset.getInitialPoint(), dataset.getSamplingPoint());
			// kmeans.init(learn.getX(),learn.getY());
			List<Point> lastCentroids = kmeans.learn();

			// ImpacFactorClusterDAO imold = new ImpacFactorClusterDAO();
			SymptomDAO symDAO = new SymptomDAO();

			List<SymptomCluster> clusterList = null;
			try {
				// List<ImpacFactorCluster> clusterList =
				// imold.findClusteringByImpactFactorID(dataset.getKeyString());
				clusterList = symDAO.findClusteringBySymptom(dataset
						.getSymptomKey());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				symDAO.closeEntityManager();
			}
			SymptomDAO writeOperator;
			if (clusterList != null && clusterList.size() > 0) {
				for (SymptomCluster cluster : clusterList) {
					writeOperator = new SymptomDAO();
					cluster.setCentroid(lastCentroids.get(cluster.getLevel())
							.getCoordinate());
					writeOperator.updateSymtomCluster(cluster);
					writeOperator.closeEntityManager();
					/**
					 * imoldWriteOperator = new ImpacFactorClusterDAO();
					 * cluster.
					 * setCentroidX(lastCentroids.get(cluster.getLevel())
					 * .getX());
					 * cluster.setCentroidY(lastCentroids.get(cluster.getLevel
					 * ()).getY());
					 * imoldWriteOperator.updateClustering(cluster);
					 * imoldWriteOperator.closeEntityManager();
					 **/
				}
			} else {
				for (int i = 0; i < lastCentroids.size(); i++) {
					writeOperator = new SymptomDAO();
					SymptomCluster cluster = new SymptomCluster();
					cluster.setLevel(i);
					cluster.setCentroid(lastCentroids.get(i).getCoordinate());
					cluster.setSymptomKey(dataset.getSymptomKey());
					writeOperator.saveSymtomCluster(cluster);
					writeOperator.closeEntityManager();
					/**
					 * imoldWriteOperator = new ImpacFactorClusterDAO();
					 * ImpacFactorCluster cluster = new ImpacFactorCluster();
					 * cluster.setImpactFactorID(dataset.getKeyString());
					 * cluster.setImName(dataset.impactName);
					 * cluster.setSymptomName(dataset.symptomName);
					 * cluster.setCentroidX(lastCentroids.get(i).getX());
					 * cluster.setCentroidY(lastCentroids.get(i).getY());
					 * cluster.setLevel(i);
					 * imoldWriteOperator.saveClustering(cluster);
					 * imoldWriteOperator.closeEntityManager();
					 **/
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("error: " + e.getMessage());
			return "-1";
		}

		return "1";
	}

	public void classifySymptomCloudML(VitalSignRecord rec) {
		SymptomDAO sDAO = new SymptomDAO();
		ImpactFactorDAO iDAO = new ImpactFactorDAO();
		UserSymptomDAO usDAO;
		PatientDAO pDAO = new PatientDAO();
		LabTestResultDAO lDAO = new LabTestResultDAO();
		try {
			Patient patient = pDAO
					.findPatientsByHospitalNumber(rec.getCodeHN());
			if(patient == null){
				log.info("can not find patient "+rec.getCodeHN());
				return;
			}
			List<Symptom> stList = sDAO.getAllSymptom();
			for (Symptom s : stList) {
				usDAO = new UserSymptomDAO();
				// get impact factor
				List<ImpactFactor> factorList = iDAO
						.getSyntomTamplateBySymptomID(s.getKeyString());
				String factorName;
				String strippedFactorName;
				JsonObject requestRecord = new JsonObject();
				Map<String, String> testInfo =  new HashMap<String, String>();
				for (ImpactFactor factor : factorList) {
					factorName = factor.getimpactName();
					strippedFactorName = factorName.substring(factorName
							.indexOf(".") + 1);
					log.info("	getting prop: " + strippedFactorName);
					// getting data from patient's PROFILE
					if (factorName.startsWith("profile")) {
						String methodName = "get"
								+ strippedFactorName.substring(0, 1)
										.toUpperCase()
								+ strippedFactorName.substring(1);
						log.info("		getting profile: " + methodName +" return:" + patient.getClass().getMethod(methodName).getReturnType());
						
						Object valueObj = patient.getClass().getMethod(methodName).invoke(patient);
						if(valueObj instanceof String)
							requestRecord.addProperty(strippedFactorName, String.valueOf(valueObj));
						else if ( patient.getClass().getMethod(methodName).getReturnType().equals(Integer.TYPE)  ){
							requestRecord.addProperty(strippedFactorName, Integer.valueOf(""+valueObj));
						}
						
						// getting data from patient's VITALSIGN
					} else if (factorName.startsWith("vitalsign")) {
						requestRecord.addProperty(strippedFactorName,
								rec.getMeasureValue(strippedFactorName));
						
						// getting data from patient's LABTEST	
					} else if (factorName.startsWith("labtest")) {
						LabTestResult labtest = lDAO.findLastLabTestResultByName(strippedFactorName, rec.getCodeHN());
						if(labtest!=null){
							requestRecord.addProperty(strippedFactorName, labtest.getResultValue());
						}
					}
					
					// add to test information
					testInfo.put(factor.getImpactLabel(), requestRecord.get(strippedFactorName).getAsString());

				}
				
				// call web service on cloudML
				String wsurl = PREDICTWS_URL_PREFIX +s.getModelName()+ PREDICTWS_URL_SUFFIX;
				JsonArray recList = new JsonArray();
				recList.add(requestRecord);
				JsonObject instance = new JsonObject();
				instance.add("instances", recList);
				log.info("prediction request: "+instance.toString());
				NetHttpTransport netTransport = new NetHttpTransport();
				HttpRequestFactory requestFactory = netTransport
						.createRequestFactory(GoogleCredential.getApplicationDefault().createScoped(Collections.singleton(CloudMachineLearningEngineScopes.CLOUD_PLATFORM)));
				HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(wsurl),ByteArrayContent.fromString("application/json", instance.toString()));
				request.setParser(new JacksonFactory().createJsonObjectParser());
				log.info("prediction response: "+request.execute().parseAsString());
				PredictionResponse predres = request.execute().parseAs(PredictionResponse.class);
				if(predres!=null && predres.getPredictions().size()>0){
					boolean proability; 
					for(PredictionResult pr: predres.getPredictions()){
						log.info("prediction result: "+pr.getTargetResult());
						for(double d: pr.getProbabilities()){
							log.info(" probability: "+d);
						}
					}
					
					
					// save user symptom
					UserSymptom usersymp = usDAO.findUserSymptom(
						patient.getKeyString(), s.getSymptomName());
					log.info("finding symptom user: "+patient.getKeyString()+"#"+s.getSymptomName()+"#"+usersymp);
					usDAO.closeEntityManager();
					UserSymptomDAO writerOpt = new UserSymptomDAO();
					Gson gson = new Gson();
					if (usersymp == null) {
	
						usersymp = new UserSymptom();
						usersymp.setPatientID(patient.getKeyString());
						usersymp.setSymptomlevel( new Double( predres.getPredictions().get(0).getTargetResult()).intValue());
						
						usersymp.setSymptomKey(s.getKeyString());
						usersymp.setSymptomName(s.getSymptomName());
						usersymp.setTestInformation(gson.toJson(testInfo));
					/*	if(predres.getPredictions().get(0).getTargetResult() == 0){
							usersymp.setProbability(0);
						} else{
					*/		
							usersymp.setProbability(predres.getPredictions().get(0).getProbabilities()[1]);
					//	}
						writerOpt.saveUserSymptom(usersymp);
						writerOpt.closeEntityManager();
						log.info("saving user symptom");
					} else {
						usersymp.setSymptomlevel(new Double( predres.getPredictions().get(0).getTargetResult()).intValue());
						usersymp.setTestInformation(gson.toJson(testInfo));
						double prevProbability = usersymp.getProbability();
						/*	if(predres.getPredictions().get(0).getTargetResult() == 0){
							usersymp.setProbability(0);
						} else{
					*/	
							usersymp.setProbability(predres.getPredictions().get(0).getProbabilities()[1]);
					//	}
						usersymp.setProbablityVariant(usersymp.getProbability()-prevProbability);
						writerOpt.updateUserSymptom(usersymp);
						writerOpt.closeEntityManager();
						log.info("updating user symptom");
					}
					
						
					
				}
				
			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info("error occurs" + errors.toString());
		} finally {
			sDAO.closeEntityManager();
			iDAO.closeEntityManager();
			pDAO.closeEntityManager();
			lDAO.closeEntityManager();
		}
	}
	
	public RiskEvaluationResult  evaluateRiskPotentialTrend(String symptomId, VitalSignRecord vitalsignRecord, List<LifeStyleRecord> lifeRecordList, List<Consumption> consumptionRecordList) throws Exception{
		LifeFactorDAO lifefactorDAO = new LifeFactorDAO();
		List<LifeFactor> factorList = lifefactorDAO.getLifeFactorBySymptomID(symptomId);
		
		if(factorList.size()==0)
			return null;
		
		// point for evaluation in euclidean distance
		List<Double> evalPoint = new ArrayList<Double>();
		HashMap<String,Double> measuresValue = new HashMap<String,Double>();
		List<Double> goodPoint = new ArrayList<Double>(); 
		List<Double> badPoint = new ArrayList<Double>();
		for(LifeFactor factor: factorList){
			Double value = new Double(0);
			// LIFESTYLE VARIABLE
			if(factor.getFactorName().startsWith("lifestyle")){
				
				// Exercise Hour
				if(factor.getFactorName().indexOf("exerciseHour")!= -1){
					value = new Double(LifeStyleRecord.calculateExerciseHour(lifeRecordList));
				
				// Work Hour
				}else if(factor.getFactorName().indexOf("workHour")!= -1){
					value = new Double(LifeStyleRecord.calculateWorkHour(lifeRecordList));
					
				// Sleep Hour
				}else if(factor.getFactorName().indexOf("sleepHour")!= -1){
					value = new Double(LifeStyleRecord.calculateSleepHour(lifeRecordList));

				// Exercise Number	
				} else if(factor.getFactorName().indexOf("exerciseNumber")!= -1){
					value = new Double(LifeStyleRecord.calculateExerciseNumber(lifeRecordList));

				}
				log.info("lifestyle "+factor.getFactorName()+" "+value);
				
			// CONSUMPTION VARIABLE	
			} else if(factor.getFactorName().startsWith("consumption")){
				
				// Calories Left
				if(factor.getFactorName().indexOf("caloriesLeft")!= -1){
					double totalCaloriesLeft = 0;
					double totalCaloriesLeftPercent = 0;
					for(Consumption c: consumptionRecordList){
						//skip date without record
						log.info(" running through consumption "+c.getConsumptionTime()+" "+c.getCalories());
						if(c.getCalories()==0)
							continue;
						totalCaloriesLeft += (c.getCalories() - c.getCaloriesLimit());
						log.info("		calories left on"+c.getConsumptionTime()+" "+c.getCalories()+" - "+c.getCaloriesLimit());
						totalCaloriesLeftPercent += (totalCaloriesLeft / c.getCaloriesLimit()) * 100;
					}
					log.info("totalCaloriesLeft "+totalCaloriesLeft);
					totalCaloriesLeftPercent = Math.round(totalCaloriesLeftPercent / consumptionRecordList.size());
					value = new Double(totalCaloriesLeftPercent);
				}
			}
			Double showValue = value;
			if(factor.getTargetGoodValue()!=0)
				showValue = new Double(Math.round((value / factor.getTargetGoodValue())*100));
			
			measuresValue.put(factor.getFactorLabel(), showValue);
			evalPoint.add(value);
			goodPoint.add(factor.getTargetGoodValue());
			badPoint.add(factor.getTargetBadValue());
			
		}
		
		// convert to coordinate point for calculation
		Point evalP = new Point(evalPoint.toArray(new Double[evalPoint.size()]));
		Point goodP = new Point(goodPoint.toArray(new Double[goodPoint.size()]));
		Point badP = new Point(badPoint.toArray(new Double[badPoint.size()]));
		log.info("eval point "+evalP.toString());
		log.info("good point "+goodP.toString());
		log.info("bad point "+badP.toString());
		
		// compare euclidean distance between good and bad
		log.info("distance to goodP "+Point.distance(evalP, goodP));
		log.info("distance to badP "+Point.distance(evalP, badP));
		double distanceToGood = Point.distance(evalP, goodP);
		double distanceToBad = Point.distance(evalP, badP);
		if(distanceToGood < distanceToBad){
			RiskEvaluationResult result = new RiskEvaluationResult();
			result.setResult(1);
			result.setMeasureValues(measuresValue);
			result.setDistanceToGood(distanceToGood);
			result.setDistanceToBad(distanceToBad);
			return result;
		}else{
			RiskEvaluationResult result = new RiskEvaluationResult();
			result.setResult(0);
			result.setMeasureValues(measuresValue);
			result.setDistanceToGood(distanceToGood);
			result.setDistanceToBad(distanceToBad);
			return result;
		}
	}
	
/**
	public void classifySymptomKmean(VitalSignRecord rec) {
		SymptomDAO sDAO = new SymptomDAO();
		ImpactFactorDAO iDAO = new ImpactFactorDAO();
		UserSymptomDAO usDAO = new UserSymptomDAO();
		PatientDAO pDAO = new PatientDAO();
		try {
			Patient patient = pDAO
					.findPatientsByHospitalNumber(rec.getCodeHN());
			List<Symptom> stList = sDAO.getAllSymptom();
			for (Symptom s : stList) {
				// load symptom clusters
				List<SymptomCluster> sClusterList = sDAO
						.findClusteringBySymptom(s.getKeyString());
				List<Cluster> cList = new ArrayList<Cluster>();
				int i = 0;
				for (SymptomCluster scluster : sClusterList) {
					cList.add(scluster.convert(scluster.getLevel()));
					i++;
				}
				// get impact factor
				List<ImpactFactor> factorList = iDAO
						.getSyntomTamplateBySymptomID(s.getKeyString());

				// setup coordinate
				ArrayList<Double> coordinate = new ArrayList<Double>();
				String factorName;
				String strippedFactorName;
				for (ImpactFactor factor : factorList) {
					factorName = factor.getimpactName();
					strippedFactorName = factorName.substring(factorName
							.indexOf(".") + 1);
					log.info("	getting prop: " + strippedFactorName);
					if (factorName.startsWith("profile")) {
						String methodName = "get"
								+ strippedFactorName.substring(0, 1)
										.toUpperCase()
								+ strippedFactorName.substring(1);
						log.info("		getting profile: " + methodName);
						try {
							// Object value =
							// patient.getClass().getMethod(methodName).invoke(patient);
							coordinate.add(Double.parseDouble(""
									+ patient.getClass().getMethod(methodName)
											.invoke(patient)));

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (factorName.startsWith("vitalsign")) {
						coordinate.add(rec.getMeasureValue(strippedFactorName));
					}

				}
				// find cluster based on user vital sign record
				log.info("coordinate.size(): " + coordinate.size());
				double[] coor = new double[coordinate.size()];
				for (int j = 0; j < coordinate.size(); j++) {
					coor[j] = coordinate.get(j).doubleValue();
				}

				Point userConditionPoint = new Point(coor);
				log.info("classifying Point: " + userConditionPoint.toString());
				Cluster foundCluster = userConditionPoint.findCluster(cList);

				UserSymptom usersymp = usDAO.findUserSymptom(
						patient.getKeyString(), s.getSymptomName());
				usDAO.closeEntityManager();
				UserSymptomDAO writerOpt = new UserSymptomDAO();
				if (usersymp == null) {

					usersymp = new UserSymptom();
					usersymp.setPatientID(patient.getKeyString());
					usersymp.setSymptomlevel(foundCluster.getId());
					usersymp.setSymptomName(s.getSymptomName());
					writerOpt.saveUserSymptom(usersymp);
					writerOpt.closeEntityManager();
					log.info("saving user symptom");
				} else {
					usersymp.setSymptomlevel(foundCluster.getId());
					writerOpt.updateUserSymptom(usersymp);
					writerOpt.closeEntityManager();
					log.info("updating user symptom");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sDAO.closeEntityManager();
			iDAO.closeEntityManager();
			usDAO.closeEntityManager();
			pDAO.closeEntityManager();
		}
	}
**/
	@RequestMapping(value = "/classiflySymptomWS", method = RequestMethod.GET)
	@ResponseBody
	public void classifySymptomWS(HttpServletRequest request) {
		VitalSignDAO vDAO = new VitalSignDAO();
		VitalSignRecord rec = vDAO.findLastVitalSignByHN(request
				.getParameter("codeHN"));
		log.info(" found rec " + rec.getMeasures());
		//classifySymptomKmean(rec);
		classifySymptomCloudML(rec);

		/**
		 * UserSymptomDAO usedao = new UserSymptomDAO(); ImpacFactorClusterDAO
		 * imdao = new ImpacFactorClusterDAO(); List<ImpacFactorCluster> im =
		 * null; PatientDAO pdao = new PatientDAO(); Patient patient = new
		 * Patient(); List<VitalSign> vital = null; try { patient =
		 * pdao.findPatientsByHospitalNumber(request .getParameter("HNcode"));
		 * im = imdao.TakeAllClusteringBySymptom(request
		 * .getParameter("SymptomName"));
		 * 
		 * vital = pdao.getVitalSignByPatient(patient.getKeyString()); double
		 * vitalValue[] = new double[3]; for (int i = 0; i < vital.size(); i++)
		 * { vitalValue[i] = vital.get(i).getPulseRate(); vitalValue[i + 1] =
		 * vital.get(i).getBpDiastolic(); vitalValue[i + 2] =
		 * vital.get(i).getBpSystolic(); } int clusterResult[] = new
		 * int[vitalValue.length]; int count = 0; List<Cc> clusters = new
		 * ArrayList<Cc>(); for (int i = 0; i < im.size(); i++) { Cc cluster =
		 * new Cc(i); Pp centroid =
		 * Pp.setXaxisAndYaxisD(im.get(i).getCentroidX(), im
		 * .get(i).getCentroidY()); cluster.setCentroid(centroid);
		 * clusters.add(cluster); if (im.get(i).getLevel() == 2) { Date date =
		 * new Date(); double age = ((patient.getBirthdate().getYear() - date
		 * .getYear()) * (-1)); double impact = vitalValue[count]; for (int j =
		 * 0; j < 1; j++) { Pp p = Pp.setXaxisAndYaxisD(age, impact);
		 * log.info("POINT: " + p.toString()); Cc c = p.findCluster(clusters);
		 * log.info("		result is Cluster " + c.getId() % 3);
		 * clusterResult[count] = c.getId() % 3; } count++; clusters.clear(); }
		 * 
		 * }
		 * 
		 * int resultClassifly = (int) (clusterResult[0] + clusterResult[1] +
		 * clusterResult[2]) / 3; String resultLevel; if (resultClassifly == 2)
		 * { resultLevel = "Disease"; } else if (resultClassifly == 1) {
		 * resultLevel = "Risk"; } else { resultLevel = "Normal"; }
		 * log.info("\n--> " + patient.getFirstname() + " " +
		 * patient.getLastname() + " <-- " + "you are in " + resultLevel +
		 * " Group of " + im.get(0).getSymptomName() + "!!");
		 * 
		 * UserSymptom usersymp = new UserSymptom();
		 * usersymp.setPatientID(patient.getKeyString());
		 * usersymp.setSymptomlevel(resultClassifly);
		 * usersymp.setSymptomName(im.get(0).getSymptomName());
		 * usedao.saveUserSymptom(usersymp);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * usedao.closeEntityManager(); pdao.closeEntityManager();
		 * imdao.closeEntityManager();
		 **/
	}

}