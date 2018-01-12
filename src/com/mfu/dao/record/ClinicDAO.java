package com.mfu.dao.record;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.Clinic;


public class ClinicDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;
	public ClinicDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<Clinic> getAllClinic() {

		List<Clinic> res = null;

		try {
			Query q = em.createQuery("select p from Clinic p");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public Clinic findClinicByKey(String key) {

		Clinic res = null;

		try {
			Query q = em.createQuery("select p from Clinic p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			 res = (Clinic)q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public Clinic findClinicByClinicCode(String clinicCode) {

		Clinic res = null;

		try {
			Query q = em.createQuery("select p from Clinic p where p.clinicCode = :clinicCode");

			q.setParameter("clinicCode", clinicCode);
			res = (Clinic)q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public Clinic findClinicBySpecialty(String specialty) {

		Clinic res = null;

		try {
			Query q = em.createQuery("select p from Clinic p where p.specialty = :specialty");

			q.setParameter("specialty", specialty);
			res = (Clinic)q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public void updateClinic(Clinic o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void insertClinic(Clinic o) {

		try {
			em.persist(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteClinic(String key) {

		Clinic o = this.findClinicByKey(key);
		if (o != null)
			em.remove(o);
	}

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}
}
