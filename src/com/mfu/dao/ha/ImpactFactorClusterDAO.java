package com.mfu.dao.ha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;



import com.mfu.entity.ha.ImpactFactorCluster;

public class ImpactFactorClusterDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public ImpactFactorClusterDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<ImpactFactorCluster> getAllClustering() {
		List<ImpactFactorCluster> clusterings = null;
		try {
			Query q = em.createQuery("select p from Clustering p");
			clusterings = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clusterings;
	}

	public ImpactFactorCluster findClusteringByKey(String key) {
		ImpactFactorCluster clustering = null;
		try {
			Query q = em.createQuery("select p from Clustering p where p.key = :key");
			q.setParameter("key", key);
			List<ImpactFactorCluster> clusterings = q.getResultList();
			clustering = clusterings.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clustering;
	}

	public void saveClustering(ImpactFactorCluster clustering) {
		try {
			em.persist(clustering);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateClustering(ImpactFactorCluster clustering) {
		try {
			em.merge(clustering);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteClustering(String key) {

		ImpactFactorCluster clustering = this.findClusteringByKey(key);
		if (clustering != null)
			em.remove(clustering);
	}

	public void closeEntityManager() {

		if (em != null)
			em.close();
	}
}