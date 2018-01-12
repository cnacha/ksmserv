package com.mfu.dao.queue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.mfu.entity.queue.Visit;

public class VisitDAO {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public VisitDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<Visit> getAllVisits() {

		List<Visit> visits = null;

		try {
			Query q = em.createQuery("select p from Visit p");
			visits = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return visits;

	}

	public Visit findVisitByKey(String key) {

		Visit vist = null;

		try {
			Query q = em.createQuery("select u from Visit u where u.key = :key");

			q.setParameter("key", key);
			vist = (Visit) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return vist;

	}

	

	

	public void saveVisit(Visit visit) {

		try {
			em.persist(visit);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateUser(Visit visit) {

		try {
			em.merge(visit);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteVisit(String key) {

		Visit visit = this.findVisitByKey(key);
		if (visit != null)
			em.remove(visit);
	}

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}

	
}
