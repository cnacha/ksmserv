package com.mfu.dao.ha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.ha.ImpacFactorCluster;
import com.mfu.entity.ha.Symptom;
import com.mfu.entity.ha.SymptomCluster;

public class SymptomDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public SymptomDAO() {
		em = emfInstance.createEntityManager();
	}
	public List<Symptom> getAllSymptom() {
		List<Symptom> syntoms = null;
		try {
			Query q = em.createQuery("select p from Symptom p");
			syntoms = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return syntoms;
	}

	public Symptom findSyntomByKey(String key) {
		Symptom syntom = null;
		try {
			Query q = em.createQuery("select p from Symptom p where p.key = :key");
			q.setParameter("key", key);
			List<Symptom> syntoms = q.getResultList();
			syntom = syntoms.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return syntom;
	}

	public void saveSyntom(Symptom syntom) {
		try {
			em.persist(syntom);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSyntom(Symptom syntom) {
		try {
			em.merge(syntom);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveSymtomCluster(SymptomCluster syntom) {
		try {
			em.persist(syntom);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateSymtomCluster(SymptomCluster syntom) {
		try {
			em.merge(syntom);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteSyntom(String key) {

		Symptom syntom = this.findSyntomByKey(key);
		if (syntom != null)
			em.remove(syntom);
	}

	public List<Symptom> getSyntomByPatient(String patientId) {
		List<Symptom> s = null;
		try {
			Query q = em.createQuery("select p from Symptom p where p.patientId = :id order by p.updateDate DESC");

			q.setParameter("id", patientId);
			s = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}
	
	public List<SymptomCluster> findClusteringBySymptom(String skey) {
		List<SymptomCluster> clusterings = null;
		try {
			Query q = em.createQuery("select p from SymptomCluster p where p.symptomKey = :key");
			q.setParameter("key", skey);
			clusterings = q.getResultList();
			if(clusterings.size()==0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusterings;
	}
	
	public void closeEntityManager() {

		if (em != null)
			em.close();
	}
}