package com.mfu.dao.survey;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.survey.*;

public class FillesItemDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;

	public FillesItemDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<FilledItem> getAllFilledItem(){
		List<FilledItem> FilledItem = null;
		try{
			Query q = em.createQuery("select p from FilledItem p");
			FilledItem = q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return FilledItem;
	}
	
	public FilledItem findFilledItemByKey(String key) {

		FilledItem FilledItem = null;
		try {

			Query q = em.createQuery("select p from FilledItem p where p.key =:key");
			q.setParameter("key", key);
			List<FilledItem> Filles = q.getResultList();
			FilledItem = Filles.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FilledItem;
	}
	
	public void deleteFilledItem(String key) {
		FilledItem fill = this.findFilledItemByKey(key);
		if (fill != null)
			em.remove(fill);
	}

	public void saveFilledItem(FilledItem FilledItem) {
		try {
			em.persist(FilledItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeEntityManager() {
		if (em != null)
			em.close();
	}
	
	public List<FilledItem> findFilledItemByQuestionKey(String key) {
		List<FilledItem> result = em.createQuery("SELECT p FROM FilledItem p WHERE p.questionId=:param1")
				.setParameter("param1", key).getResultList();
			return result;
	}
	
	public List<FilledItem> findFilledItemByAnswer(String answer) {
		List<FilledItem> result = em.createQuery("SELECT p FROM FilledItem p WHERE p.answerString=:param1")
				.setParameter("param1", answer).getResultList();
			return result;
	}
	
	
	public List<FilledItem> findFilledItemByPatientKey(String key, int set) {
		Query q = em.createQuery("select p from FilledItem p where p.userkey=:param1 AND p.set=:param2");
		q.setParameter("param1", key);
		q.setParameter("param2", set);
		List<FilledItem> result = q.getResultList();
			return result;
	}
}