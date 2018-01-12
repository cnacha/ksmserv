package com.mfu.dao.nutrition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.nutrition.Food;

public class FoodDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");
	private EntityManager em = null;

	public FoodDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<Food> getAllFood() {

		List<Food> res = null;

		try {
			Query q = em.createQuery("select p from Food p");
			res = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	private static final int MAX_SEARCH_RESULT = 5;
	
	public List<Food> findFood(String keyword) {

		List<Food> res = new ArrayList<Food>();

		List<Food> allFood = this.getAllFood();
		
		for(Food f: allFood){
			if(f.getName().indexOf(keyword)!=-1){
				res.add(f);
				if(res.size()>=MAX_SEARCH_RESULT)
					break;
			}
		}

		return res;

	}

	public Food findFoodByKey(String key) {

		Food res = null;
		try {
			Query q = em
					.createQuery("select p from Food p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			res = (Food) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public Food findFoodByCode(String code) {

		Food res = null;
		try {
			Query q = em
					.createQuery("select p from Food p where p.code = :code");

			q.setParameter("code", code);
			res = (Food) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public Food findFoodByFoodCode(String code) {

		Food res = null;

		try {
			Query q = em
					.createQuery("select p from Food p where p.code = :foodCode");

			q.setParameter("foodCode", code);
			res = (Food) q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

	public void updateFood(Food o) {

		try {
			em.merge(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertFood(Food o) {

		try {
			em.persist(o);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteFood(String key) {

		Food o = this.findFoodByKey(key);
		if (o != null)
			em.remove(o);
	}

	public void closeEntityManager() {

		if (em != null)
			em.close();

	}
}
