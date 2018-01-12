package com.mfu.dao.record;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.ActivityType;


public class ActivityTypeDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;
	public ActivityTypeDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<ActivityType> getAllActivityType() {

		List<ActivityType> res = null;

		try {
			Query q = em.createQuery("select p from ActivityType p order by p.typeCode");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public ActivityType findActivityTypeByKey(String key) {

		ActivityType res = null;

		try {
			Query q = em.createQuery("select p from ActivityType p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			 res = (ActivityType)q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public ActivityType findActivityTypeByCode(String code) {

		ActivityType res = null;
		Log.info("findActivityTypeByCode "+code);
		try {
			Query q = em.createQuery("select p from ActivityType p where p.typeCode = :code");

			q.setParameter("code", code);
			 res = (ActivityType)q.getSingleResult();
			 Log.info(" 		"+res);

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	

	public void updateActivityType(ActivityType o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void insertActivityType(ActivityType o) {

		try {
			em.persist(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteActivityType(String key) {

		ActivityType o = this.findActivityTypeByKey(key);
		if (o != null)
			em.remove(o);
	}
	
	public void closeEntityManager() {

		if (em != null)

			em.close();

	}

}
