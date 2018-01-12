package com.mfu.dao.ha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.ha.LifeFactor;

public class LifeFactorDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public LifeFactorDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<LifeFactor> getAllLifeFactor() {
		List<LifeFactor> impactfactors = null;
		try {
			Query q = em.createQuery("select p from LifeFactor p");
			impactfactors = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return impactfactors;
	}
	
	public LifeFactor findLifeFactorByKey(String key) {
		LifeFactor impactfactor = null;
		try {
			Query q = em.createQuery("select p from LifeFactor p where p.key = :key");
			q.setParameter("key", key);
			List<LifeFactor> impactfactors = q.getResultList();
			impactfactor = impactfactors.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impactfactor;
	}
	
	public void saveLifeFactor(LifeFactor fa) {
		try {
			em.persist(fa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateLifeFactor(LifeFactor fa) {
		try {
			em.merge(fa);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteLifeFactor(String key) {
		LifeFactor fa = this.findLifeFactorByKey(key);
		if (fa != null)
			em.remove(fa);
	}
	
	public List<LifeFactor> getLifeFactorBySymptomID(String symtomID) {
		List<LifeFactor> im = null;
		try {
			Query q = em.createQuery("select p from LifeFactor p where p.symtomID = :id order by p.factorName");

			q.setParameter("id", symtomID);
			im = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return im;
	}
	
	public void closeEntityManager() {
		
		if (em != null)
			em.close();
	}
}
