package com.mfu.dao.survey;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.survey.Choice;
import com.mfu.entity.survey.Question;
import com.mfu.entity.survey.SurveyForm;

public class FormDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;
	
	public FormDAO(){
		em = emfInstance.createEntityManager();
	} 
	public List<SurveyForm> getAllForm(){
		List<SurveyForm> form = null;
		try{
			Query q = em.createQuery("select p from SurveyForm p");
			form = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return form;
	}
	
	public SurveyForm findFormByKey(String key) {

		SurveyForm form = null;
		try {

			Query q = em.createQuery("select p from SurveyForm p where p.key =:key");
			q.setParameter("key", KeyFactory.stringToKey(key));
			List<SurveyForm> fom = q.getResultList();
			form = fom.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	public void deleteForm(String key) {
		SurveyForm form = this.findFormByKey(key);
		System.out.println("removing "+form.getKeyString());
		if (form != null)
			em.remove(form);
	}

	public void insertForm(SurveyForm form) {

		try {
			em.persist(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateForm(SurveyForm form) {

		try {
			em.merge(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****** 		QUESTION			******/
	
	public void insertQuestion(Question q) {

		try {
			em.persist(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateQuestion(Question q) {

		try {
			em.merge(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteQuestion(String key) {
		Question o = this.findQuestionByKey(key);
		System.out.println("removing "+o.getKeyString());
		if (o != null)
			em.remove(o);
	}
	
	public Question findQuestionByKey(String key) {

		Question res = null;
		try {

			Query q = em.createQuery("select p from Question p where p.key =:key");
			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (Question) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Question> getQuestionByForm(String formkey){
		List<Question> res = null;
		try{
			Query q = em.createQuery("SELECT p FROM Question p WHERE p.formKey=:formkey ORDER BY orderNo")
					.setParameter("formkey", formkey);
			
			res = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	/****** 		QUESTION			******/
	public void insertChoice(Choice c) {

		try {
			em.persist(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateChoice(Choice q) {

		try {
			em.merge(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteChoice(String key) {
		Choice o = this.findChoiceByKey(key);
		System.out.println("removing "+o.getKeyString());
		if (o != null)
			em.remove(o);
	}
	
	public Choice findChoiceByKey(String key) {

		Choice res = null;
		try {

			Query q = em.createQuery("select p from Choice p where p.key =:key");
			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (Choice) q.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Choice> getChoiceByQuestion(String qkey){
		List<Choice> res = null;
		try{
			Query q = em.createQuery("SELECT p FROM Choice p WHERE p.questionKey=:questionKey ORDER BY orderNo")
					.setParameter("questionKey", qkey);
			
			res = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public void closeEntityManager() {
		if (em != null)
			em.close();
	}
}