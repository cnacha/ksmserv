package com.mfu.dao.queue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.mortbay.log.Log;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.appointment.Appointment;
import com.mfu.entity.queue.QueueEstimatedTime;

public class QueueEstimatedTimeDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;

	public QueueEstimatedTimeDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<QueueEstimatedTime> getAllQueueEstimatedTime() {

		List<QueueEstimatedTime> res = null;

		try {
			Query q = em.createQuery("select p from QueueEstimatedTime p");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public QueueEstimatedTime findQueueEstimatedTimeByKey(String key) {

		QueueEstimatedTime res = null;

		try {
			Query q = em.createQuery("select p from QueueEstimatedTime p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (QueueEstimatedTime) q.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public QueueEstimatedTime findQueueEstimatedTimeByCurrentTimeANDStage(Date current, int stage) {
		
		System.out.println("Current Date = "+current);
		QueueEstimatedTime res = null;
		
		List<QueueEstimatedTime> result1 = null;
		List<QueueEstimatedTime> result2 = null;

		try {
			result1 = em.createQuery(
					"select p from QueueEstimatedTime p where p.startDateTime <= :current AND p.currentStage = :stage")
					.setParameter("current", current).setParameter("stage", stage).getResultList();
	
			
			result2 = em.createQuery(
					"select p from QueueEstimatedTime p where p.endDateTime >= :current AND p.currentStage = :stage")
					.setParameter("current", current).setParameter("stage", stage).getResultList();
				
			
			System.out.println("Result from query size: "+result1.size()+" && "+result2.size());
			
			
			for (QueueEstimatedTime a : result1) {
				boolean isMatch = false;
				for (QueueEstimatedTime b : result2) {
					if (b.getKeyString().equals(a.getKeyString())) {
						isMatch = true;
						break;
					}
				}
				if (isMatch)
					res = a;
			}
			

		} catch (Exception e) {
			Log.info(e.getMessage());
			Log.info(e.getCause().toString());
			e.printStackTrace();
		}

		return res;

	}

	public void updateQueueEstimatedTime(QueueEstimatedTime o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertQueueEstimatedTime(QueueEstimatedTime o) {

		try {
			em.persist(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteQueueEstimatedTime(String key) {

		QueueEstimatedTime o = this.findQueueEstimatedTimeByKey(key);
		if (o != null)
			em.remove(o);
	}

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}
}
