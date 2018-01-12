package com.mfu.dao.ha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.ha.ImpactFactor;

public class ImpactFactorDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public ImpactFactorDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<ImpactFactor> getAllImpactFactor() {
		List<ImpactFactor> impactfactors = null;
		try {
			Query q = em.createQuery("select p from ImpactFactor p");
			impactfactors = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return impactfactors;
	}
	
	public ImpactFactor findImpactFactorByKey(String key) {
		ImpactFactor impactfactor = null;
		try {
			Query q = em.createQuery("select p from ImpactFactor p where p.key = :key");
			q.setParameter("key", key);
			List<ImpactFactor> impactfactors = q.getResultList();
			impactfactor = impactfactors.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impactfactor;
	}
	
	public void saveImpactFactor(ImpactFactor impactfactor) {
		try {
			em.persist(impactfactor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateImpactFactor(ImpactFactor impactfactor) {
		try {
			em.merge(impactfactor);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteImpactFactor(String key) {
		ImpactFactor impactfactor = this.findImpactFactorByKey(key);
		if (impactfactor != null)
			em.remove(impactfactor);
	}
	
	public List<ImpactFactor> getSyntomTamplateBySymptomID(String symtomID) {
		List<ImpactFactor> im = null;
		try {
			Query q = em.createQuery("select p from ImpactFactor p where p.symtomID = :id order by p.impactName");

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
