package com.mfu.dao.record;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.LabTestResult;

public class LabTestResultDAO {
	private static final Logger log = Logger
			.getLogger(LabTestResultDAO.class.getName());
	
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;

	public LabTestResultDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<LabTestResult> getAllLabTestResult() {

		List<LabTestResult> res = null;

		try {
			Query q = em.createQuery("select p from LabTestResult p");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public LabTestResult findLabTestResultByKey(String key) {

		LabTestResult res = null;

		try {
			Query q = em
					.createQuery("select p from LabTestResult p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (LabTestResult) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public LabTestResult findLastLabTestResultByName(String testname,
			String codeHN) {
		LabTestResult res = null;
		try {
			Query q = em
					.createQuery("select p from LabTestResult p where p.codeHN = :codeHN and p.testName = :testName order by p.updateDate desc");

			q.setParameter("codeHN", codeHN);
			q.setParameter("testName", testname);
			List<LabTestResult> resList = q.getResultList();
			log.info("findLastLabTestResultByName result "+resList.size());
			if(resList!=null && resList.size()>0)
				res = resList.get(0);

		} catch (NoResultException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info("error occurs" + errors.toString());
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info("error occurs" + errors.toString());
		}
		return res;

	}

	public void updateLabTestResult(LabTestResult o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertLabTestResult(LabTestResult o) {

		try {
			log.info("insert lab test result...");
			log.info(o.getCodeHN());
			em.persist(o);
			log.info(o.getKeyString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteLabTestResult(String key) {

		LabTestResult o = this.findLabTestResultByKey(key);
		if (o != null)
			em.remove(o);
	}

	public void closeEntityManager() {

		if (em != null)
			em.close();

	}
}
