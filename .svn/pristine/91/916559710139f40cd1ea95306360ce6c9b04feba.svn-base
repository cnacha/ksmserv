package com.mfu.dao.survey;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.survey.News;
import com.mfu.entity.survey.Promotion;


public class PromotionDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;
	public PromotionDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<Promotion> getAllPromotion() {
		List<Promotion> promo = null;
		try {
			Query q = em.createQuery("select p from Promotion p");
			promo = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promo;
	}

	public Promotion findPromotionByKey(String key) {

		Promotion promo = null;
		try {

			Query q = em
					.createQuery("select p from Promotion p where p.key =:key");
			q.setParameter("key", key);
			List<Promotion> fom = q.getResultList();
			promo = fom.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promo;
	}

	public void deletePromotion(String key) {
		Promotion promo = this.findPromotionByKey(key);
		if (promo != null)
			em.remove(promo);
	}

	public Promotion savePromotion(Promotion promo) {

		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			 em.persist(promo);
			 transaction.commit();
			// em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promo;
	}

	public void updatePromotion(Promotion promo) {
		try {
			em.merge(promo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeEntityManager() {
		if (em != null)
			em.close();
	}
	
	public List<Promotion> getPromotionByTarget(String target) {
		List<Promotion> promo = null;
		try {
			Query q = em.createQuery("select p from Promotion p where p.target = :target");
			q.setParameter("target", target);
			promo = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promo;
	}
	
	public List<Promotion> getPromotionByTargetAndLevel(String target, int level) {
		List<Promotion> promotion = null;
		String Level = "";
		if(level == 0){
			Level = ("Normal");
		}else if(level == 1){
			Level = ("Risk");
		}else if(level == 2){
			Level = ("Disease");
		}
		try {
			Query q = em.createQuery("select p from Promotion p where p.target = :target AND p.level = :level");
			q.setParameter("target", target);
			q.setParameter("level", Level);
			promotion = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return promotion;
	}
}