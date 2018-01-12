package com.mfu.dao.survey;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.survey.News;

public class NewsDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;
	public NewsDAO() {
		em = emfInstance.createEntityManager();
	}
	
	public List<News> getAllNews() {
		List<News> news = null;
		try {
			Query q = em.createQuery("select p from News p");
			news = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public News findNewsByKey(String key) {

		News news = null;
		try {

			Query q = em
					.createQuery("select p from News p where p.key =:key");
			q.setParameter("key", key);
			List<News> fom = q.getResultList();
			news = fom.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public void deleteNews(String key) {
		News news = this.findNewsByKey(key);
		if (news != null)
			em.remove(news);
	}

	public News saveNews(News news) {

		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			 em.persist(news);
			 transaction.commit();
			// em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public void updateNews(News news) {
		try {
			em.merge(news);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<News> getNewsByTarget(String target) {
		List<News> news = null;
		try {
			Query q = em.createQuery("select p from News p where p.target = :target");
			q.setParameter("target", target);
			news = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}
	
	public List<News> getNewsByTargetAndLevel(String target, int level) {
		List<News> news = null;
		String Level = "";
		if(level == 0){
			Level = ("Normal");
		}else if(level == 1){
			Level = ("Risk");
		}else if(level == 2){
			Level = ("Disease");
		}
		try {
			Query q = em.createQuery("select p from News p where p.target = :target AND p.level = :level");
			q.setParameter("target", target);
			q.setParameter("level", Level);
			news = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public void closeEntityManager() {
		if (em != null)
			em.close();
	}
	
}
