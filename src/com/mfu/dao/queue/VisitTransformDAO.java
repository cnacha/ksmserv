package com.mfu.dao.queue;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.queue.Visit;
import com.mfu.entity.queue.VisitTransform;
import com.mfu.web.controller.QueueController;

public class VisitTransformDAO {
	
	private static final Logger log = Logger.getLogger(VisitTransformDAO.class.getName());

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public VisitTransformDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<VisitTransform> getAllVisitTransforms() {

		List<VisitTransform> visitTransforms = null;

		try {
			Query q = em.createQuery("select p from VisitTransform p");
			visitTransforms = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return visitTransforms;

	}

	public VisitTransform findVisitTransformByKey(String key) {

		VisitTransform visitTransform = null;

		try {
			Query q = em.createQuery("select u from VisitTransform u where u.key = :key");

			q.setParameter("key", key);
			visitTransform = (VisitTransform) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return visitTransform;

	}

	public void saveVisitTransform(VisitTransform visitTransform) {

		try {
			em.persist(visitTransform);
			log.info("saved visitTransform");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateVisitTransform(VisitTransform visitTransform) {

		try {
			em.merge(visitTransform);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteVisitTransform(String key) {

		VisitTransform visitTransform = this.findVisitTransformByKey(key);
		if (visitTransform != null)
			em.remove(visitTransform);
	}

	public void closeEntityManager() {

		if (em != null){
			em.close();
			log.info("closed entityManager...");
		}

	}

	public List<VisitTransform> findVisitTransformByStage(int stage) {

		List<VisitTransform> visitTransforms = null;

		try {
			Query q = em
					.createQuery(
							"select p from VisitTransform p WHERE p.currentStage = :stage ORDER BY p.sendToDiagRmsDateTime")
					.setParameter("stage", stage);
			visitTransforms = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return visitTransforms;

	}

	public VisitTransform findVisitTransformByHN(String HN) {

		VisitTransform visitTransform = null;

		try {
			Query q = em.createQuery(
					"select u from VisitTransform u where u.hospitalNumber = :HN ORDER BY u.sendToDiagRmsDateTime DESC");

			q.setParameter("HN", HN);
			List<VisitTransform> visitTransforms = q.getResultList();

			if (!visitTransforms.isEmpty()) {
				visitTransform = visitTransforms.get(0);
			}

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return visitTransform;
	}

	public VisitTransform findVisitTransformByVN(String VN) {

		VisitTransform visitTransform = null;

		try {
			Query q = em.createQuery(
					"select u from VisitTransform u where u.visitNumber = :vn ORDER BY u.sendToDiagRmsDateTime DESC");

			q.setParameter("vn", VN);
			List<VisitTransform> visitTransforms = q.getResultList();

			if (!visitTransforms.isEmpty()) {
				visitTransform = visitTransforms.get(0);
			}

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return visitTransform;

	}

}
