package com.mfu.dao.queue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.mfu.entity.queue.QueueMonitor;

public class QueueMonitorDAO {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public QueueMonitorDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<QueueMonitor> getAllQueueMonitors() {

		List<QueueMonitor> queueMonitors = null;

		try {
			Query q = em.createQuery("select p from QueueMonitor p");
			queueMonitors = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return queueMonitors;

	}

	public QueueMonitor findQueueMonitorByKey(String key) {

		QueueMonitor queueMonitor = null;

		try {
			Query q = em.createQuery("select u from QueueMonitor u where u.key = :key");

			q.setParameter("key", key);
			queueMonitor = (QueueMonitor) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return queueMonitor;

	}

	

	

	public void saveQueueMonitor(QueueMonitor queueMonitor) {

		try {
			em.persist(queueMonitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateUser(QueueMonitor queueMonitor) {

		try {
			em.merge(queueMonitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteQueueMonitor(String key) {

		QueueMonitor queueMonitor = this.findQueueMonitorByKey(key);
		if (queueMonitor != null)
			em.remove(queueMonitor);
	}

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}

	
}
