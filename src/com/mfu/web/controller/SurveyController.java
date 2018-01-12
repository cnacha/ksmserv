package com.mfu.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.ha.UserSymptomDAO;
import com.mfu.dao.survey.FilledFormDAO;
import com.mfu.dao.survey.FormDAO;
import com.mfu.dao.survey.NewsDAO;
import com.mfu.dao.survey.PromotionDAO;
import com.mfu.entity.ha.UserSymptom;
import com.mfu.entity.survey.Choice;
import com.mfu.entity.survey.FilledForm;
import com.mfu.entity.survey.FilledItem;
import com.mfu.entity.survey.News;
import com.mfu.entity.survey.Promotion;
import com.mfu.entity.survey.Question;
import com.mfu.entity.survey.SurveyForm;
import com.mfu.util.StringUtil;

@Controller
public class SurveyController {
	@RequestMapping("/listFormWS")
	public @ResponseBody List<SurveyForm> listFormWS(HttpServletRequest request) {
		FormDAO Formdao = new FormDAO();

		List<SurveyForm> FormList = null;
		try {
			FormList = Formdao.getAllForm();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Formdao.closeEntityManager();
		return FormList;
	}
	
	@RequestMapping("/listUnfilledFormWS")
	public @ResponseBody List<SurveyForm> listUnfilledFormWS(HttpServletRequest request) {
		FormDAO Formdao = new FormDAO();

		List<SurveyForm> result = new ArrayList<SurveyForm>();
		try {
			String userId = request.getParameter("userId");
			List<SurveyForm> allFormList = Formdao.getAllForm();
			List<FilledForm> fillList = this.getFilledFormByUserWS(request);
			for(SurveyForm svf: allFormList){
				boolean foundFilled = false;
				for(FilledForm ff: fillList )
					if(svf.getKeyString().equals(ff.getFormId()))
						foundFilled = true;
				if(!foundFilled)
					result.add(svf);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Formdao.closeEntityManager();
		return result;
	}
	
	@RequestMapping("/getSurveyFormWS")
	public @ResponseBody SurveyForm getSurveyFormWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		SurveyForm form = null;
		try {
			form = dao.findFormByKey(request.getParameter("formid"));
			List<Question> tmpList = dao.getQuestionByForm(form.getKeyString());
			List<Question> qList = new ArrayList<Question>();
			for(Question q: tmpList){
				if(q.getType()!=null && q.getType().equalsIgnoreCase(Question.TYPE_CHOICE))
					q.setAnswerChoices( dao.getChoiceByQuestion(q.getKeyString()));
				
				qList.add(q);
			}
			form.setQuestions(qList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return form;
		
	}
	
	@RequestMapping("/saveFormWS")
	public @ResponseBody String saveFormWS(@RequestBody SurveyForm form){
		FormDAO dao = new FormDAO();
		try {
			System.out.println("saving form: "+form.getName()+" "+form.getDescription()+" "+form.getVoucherDescript());
			if(form.getKey() == null)
				dao.insertForm(form);
			else
				dao.updateForm(form);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deleteFormWS")
	public @ResponseBody String deleteFormWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		try {
			dao.deleteForm(request.getParameter("key"));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/listQuestionByFormWS")
	public @ResponseBody List<Question> listQuestionByFormWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		try {
			List<Question> res = dao.getQuestionByForm(request.getParameter("key"));
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return null;
	}

	@RequestMapping("/saveQuestionWS")
	public @ResponseBody String saveQuestionWS(@RequestBody Question q){
		FormDAO dao = new FormDAO();
		try {
		//	System.out.println("saving form: "+form.getName()+" "+form.getDescription());
			if(q.getKey() == null)
				dao.insertQuestion(q);
			else
				dao.updateQuestion(q);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deleteQuestionWS")
	public @ResponseBody String deleteQuestionWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		try {
			dao.deleteQuestion(request.getParameter("key"));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/listChoiceByQuestionWS")
	public @ResponseBody List<Choice> listChoiceByQuestionWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		try {
			List<Choice> res = dao.getChoiceByQuestion(request.getParameter("key"));
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return null;
	}

	@RequestMapping("/saveChoiceWS")
	public @ResponseBody String saveChoiceWS(@RequestBody Choice o){
		FormDAO dao = new FormDAO();
		try {
		//	System.out.println("saving form: "+form.getName()+" "+form.getDescription());
			if(o.getKey() == null)
				dao.insertChoice(o);
			else
				dao.updateChoice(o);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deleteChoiceWS")
	public @ResponseBody String deleteChoiceWS(HttpServletRequest request){
		FormDAO dao = new FormDAO();
		try {
			dao.deleteChoice(request.getParameter("key"));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/saveFilledFormWS")
	public @ResponseBody String saveFilledFormWS(@RequestBody FilledForm form){
		FormDAO fdao = new FormDAO();
		SurveyForm des = fdao.findFormByKey(form.getFormId());
		
		FilledFormDAO dao = new FilledFormDAO();
		try {
			form.setVoucherCode(StringUtil.randomString(8).toUpperCase());
			form.setVoucherState(FilledForm.VOUCHER_STATE_UNUSED);
			form.setVoucherDes(des.getVoucherDescript());
			dao.saveFilledForm(form);
			while(form.getKey()==null){
				Thread.sleep(500);
				System.out.println("waiting for key...");
			}
			System.out.println("FilledForm saved: "+form.getKeyString());
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally{
			dao.closeEntityManager();
		}
		try{
			dao = new FilledFormDAO();
			if(form.getFilledItem()!=null){
				for(FilledItem i: form.getFilledItem()){
					i.setFilledFormId(form.getKeyString());
					dao.saveFilledItem(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally{
			dao.closeEntityManager();
		}
		return "1";
		
	}
	
	@RequestMapping("/getFilledFormByUserWS")
	public @ResponseBody List<FilledForm> getFilledFormByUserWS(HttpServletRequest request){
		FilledFormDAO dao = new FilledFormDAO();
		try {
			List<FilledForm> formList = dao.findFilledFormByUser(request.getParameter("userId"));
			return formList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			dao.closeEntityManager();
		}
		return null;
	}
	
	@RequestMapping("/listFilledFormWS")
	public @ResponseBody List<FilledForm> listFilledFormWS(HttpServletRequest request) {
		FilledFormDAO FilledFormdao = new FilledFormDAO();
		List<FilledForm> FilledFormList = null;
		try {
			FilledFormList = FilledFormdao.getAllFilledForm();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FilledFormdao.closeEntityManager();
		return FilledFormList;
	}
	
	@RequestMapping("/updateVoucherStateWS")
	public @ResponseBody String updateVoucherWS(@RequestBody FilledForm form) {
		FilledFormDAO dao = new FilledFormDAO();
		try {
			FilledForm filleOld = dao.findFilledFormByKey(form.getKeyString());

			if(filleOld.getVoucherState() != 1){
//				FilledForm filleUp = dao.findFilledFormByKey(filleOld.getKeyString());
//				filleUp.setFilledItem(filleOld.getFilledItem());
//				filleUp.setFormId(filleOld.getFormId());
//				filleUp.setUserId(filleOld.getUserId());
//				filleUp.setVoucherCode(filleOld.getVoucherCode());
				filleOld.setVoucherState(FilledForm.VOUCHER_STATE_USED);
				dao.updateFilledForm(filleOld);
				System.out.println("State: "+filleOld.getVoucherState());
			}
			else{
//				FilledForm filleUp = dao.findFilledFormByKey(filleOld.getKeyString());
//				filleUp.setFilledItem(filleOld.getFilledItem());
//				filleUp.setFormId(filleOld.getFormId());
//				filleUp.setUserId(filleOld.getUserId());
//				filleUp.setVoucherCode(filleOld.getVoucherCode());
				filleOld.setVoucherState(FilledForm.VOUCHER_STATE_UNUSED);
				dao.updateFilledForm(filleOld);
				System.out.println("State: "+filleOld.getVoucherState());
			}
			return "1";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/QRVoucherCodeWS")
	public @ResponseBody String QRVoucherCodeWS(@RequestBody FilledForm form) {
		FilledFormDAO dao = new FilledFormDAO();
		try {
			FilledForm filleOld = dao.findFilledFormByVoucherCode(form.getVoucherCode());
				if(filleOld != null){
					if(filleOld.getVoucherState() != 1){
						FilledForm filleUp = dao.findFilledFormByKey(filleOld.getKeyString());
						System.out.println("State: "+filleUp.getVoucherState());
						filleUp.setFilledItem(filleOld.getFilledItem());
						filleUp.setFormId(filleOld.getFormId());
						filleUp.setUserId(filleOld.getUserId());
						filleUp.setVoucherCode(filleOld.getVoucherCode());
						filleUp.setVoucherState(FilledForm.VOUCHER_STATE_USED);
						dao.updateFilledForm(filleUp);
						System.out.println("State: "+filleUp.getVoucherState());
					}
					else{
						return "-2";
					}
				}else{
					return "-1";
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dao.closeEntityManager();
		}
		return "0";
	}
	
	@RequestMapping("/getFilledFormByCode")
	public @ResponseBody FilledForm QRCodeWS(HttpServletRequest request) {
		FilledFormDAO dao = new FilledFormDAO();
		FilledForm filleOld = null;
		try {
			System.out.print(request.getParameter("voucher"));
			
			//filleOld = dao.findFilledFormByVoucherCode(request.getParameter("voucher"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dao.closeEntityManager();
		}
		return filleOld;
	}
	
	@RequestMapping("/listNewsWS")
	public @ResponseBody List<News> listNewsWS(HttpServletRequest request) {
		NewsDAO Newsdao = new NewsDAO();

		List<News> Newslist = null;
		try {
			Newslist = Newsdao.getAllNews();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Newsdao.closeEntityManager();
		return Newslist;
	}
	
	@RequestMapping("/saveNewsWS")
	public @ResponseBody String saveNewsWS(@RequestBody News news){
		NewsDAO dao = new NewsDAO();
		try {
			
			
			if(news.getKey() == null){
				news.setDate(new Date());
				dao.saveNews(news);
			}else{
				News renews = dao.findNewsByKey(news.getKeyString());
				renews.setCaption(news.getCaption());
				renews.setDescription(news.getDescription());
				renews.setTitle(news.getTitle());
				renews.setTarget(news.getTarget());
				renews.setLevel(news.getLevel());
				renews.setDate(new Date());
				dao.updateNews(renews);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deleteNewsWS")
	public @ResponseBody String deleteNewsWS(HttpServletRequest request){
		NewsDAO dao = new NewsDAO();
		try {
			dao.deleteNews(request.getParameter("key"));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/listPromotionWS")
	public @ResponseBody List<Promotion> listPromotionWS(HttpServletRequest request) {
		PromotionDAO Promodao = new PromotionDAO();

		List<Promotion> Promolist = null;
		try {
			Promolist = Promodao.getAllPromotion();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Promodao.closeEntityManager();
		return Promolist;
	}
	
	@RequestMapping("/savePromotionWS")
	public @ResponseBody String savePromotionWS(@RequestBody Promotion promo){
		PromotionDAO dao = new PromotionDAO();
		try {
			if(promo.getKey() == null){
				promo.setDate(new Date());
				dao.savePromotion(promo);
			}else{
				Promotion repromo = dao.findPromotionByKey(promo.getKeyString());
				repromo.setCaption(promo.getCaption());
				repromo.setDescription(promo.getDescription());
				repromo.setTitle(promo.getTitle());
				repromo.setTarget(promo.getTarget());
				repromo.setLevel(promo.getLevel());
				repromo.setDate(new Date());
				dao.updatePromotion(repromo);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/deletePromotionWS")
	public @ResponseBody String deletePromotionWS(HttpServletRequest request){
		PromotionDAO dao = new PromotionDAO();
		try {
			dao.deletePromotion(request.getParameter("key"));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return "-1";
	}
	
	@RequestMapping("/listNewsByTargetAndLevelWS")
	public @ResponseBody List<News> listNewsByTargetAndLevelWS(HttpServletRequest request){
		NewsDAO dao = new NewsDAO();
		try {
			List<News> res = dao.getNewsByTargetAndLevel(request.getParameter("target"), Integer.parseInt(request.getParameter("level")));
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return null;
	}
	
	@RequestMapping("/listPromotionByTargetAndLevelWS")
	public @ResponseBody List<Promotion> listPromotionByTargetWS(HttpServletRequest request){
		PromotionDAO dao = new PromotionDAO();
		try {
			List<Promotion> res = dao.getPromotionByTargetAndLevel(request.getParameter("target"), Integer.parseInt(request.getParameter("level")));
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dao.closeEntityManager();
		}
		return null;
	}
	
	
	@RequestMapping("/findNewsByKeyWS")
	public @ResponseBody News findNewsByKeyWS(HttpServletRequest request) {
		NewsDAO dao = new NewsDAO();
		News NewsContent = null;
		try {
			
			NewsContent  = dao.findNewsByKey(request.getParameter("key"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dao.closeEntityManager();
		}
		return NewsContent;
	}
	
	@RequestMapping("/findPromotionByKeyWS")
	public @ResponseBody Promotion findPromotionByKeyWS(HttpServletRequest request) {
		PromotionDAO dao = new PromotionDAO();
		Promotion promotion = null;
		try {
			
			promotion  = dao.findPromotionByKey(request.getParameter("key"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			dao.closeEntityManager();
		}
		return promotion;
	}
}