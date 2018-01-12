package com.mfu.dao.nutrition;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.nutrition.Consumption;

public class ConsumptionDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;

	public ConsumptionDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<Consumption> getAllConsumption() {

		List<Consumption> res = null;

		try {
			Query q = em.createQuery("select p from Consumption p");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public Consumption findConsumptionByKey(String key) {

		Consumption res = null;
		try {
			Query q = em
					.createQuery("select p from Consumption p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (Consumption) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public List<Consumption> findConsumptionByMeal(String patientKey,Date findDate, String mealType) {

		List<Consumption> res = null;
		
		// reset time
		findDate.setHours(0);
		findDate.setMinutes(0);
		findDate.setSeconds(0);
		
		Date nextDate = new Date(findDate.getTime()+(24 * 60 * 60 * 1000));
		//System.out.println(findDate.toString());
		//System.out.println(nextDate.toString());
		try {
			String queryStr = "SELECT p FROM Consumption p WHERE p.consumptionTime >= :findDate AND  p.consumptionTime < :nextDate AND p.patientKey=:patientKey";
			if(mealType!=null && !mealType.equals("")){
				queryStr+= " AND p.mealType=:mealType";
			}
			Query q = em.createQuery(queryStr);
			q.setParameter("mealType", mealType);
			q.setParameter("patientKey", patientKey);
			q.setParameter("findDate", findDate);
			q.setParameter("nextDate", nextDate);
			
			res = q.getResultList();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public void updateConsumption(Consumption o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertConsumption(Consumption o) {

		try {
			em.persist(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteConsumption(String key) {

		Consumption o = this.findConsumptionByKey(key);
		if (o != null)
			em.remove(o);
	}

	public void closeEntityManager() {

		if (em != null)
			em.close();

	}
}
