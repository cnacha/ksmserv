package com.mfu.dao.ha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.mfu.entity.ha.UserSymptom;

public class UserSymptomDAO {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public UserSymptomDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<UserSymptom> getAllUserSymptom() {
		List<UserSymptom> usersyntom = null;
		try {
			Query q = em.createQuery("select p from UserSymptom p");
			usersyntom = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersyntom;
	}

	public UserSymptom findUserSymptom(String patientId, String symptomName) {
		UserSymptom usersymptom = null;
		try {
			Query q = em
					.createQuery("SELECT p FROM UserSymptom p WHERE p.patientID = :pid AND p.symptomName = :sn");
			q.setParameter("pid", patientId);
			q.setParameter("sn", symptomName);
			List<UserSymptom> syntoms = q.getResultList();
			if(syntoms.size()>0)
				usersymptom = syntoms.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersymptom;
	}

	public UserSymptom findUserSymptomByKey(String key) {
		UserSymptom usersyntom = null;
		try {
			Query q = em
					.createQuery("select p from UserSymptom p where p.key = :key");
			q.setParameter("key", key);
			List<UserSymptom> syntoms = q.getResultList();
			usersyntom = syntoms.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersyntom;
	}

	public void saveUserSymptom(UserSymptom usersyntom) {
		try {
			em.persist(usersyntom);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateUserSymptom(UserSymptom usersyntom) {
		try {
			em.merge(usersyntom);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteUserSymptom(String key) {

		UserSymptom usersyntom = this.findUserSymptomByKey(key);
		if (usersyntom != null)
			em.remove(usersyntom);
	}

	public void closeEntityManager() {

		if (em != null && em.isOpen())
			em.close();
	}

	public List<UserSymptom> getSyntomByPatient(String patientId) {
		List<UserSymptom> s = null;
		try {
			Query q = em
					.createQuery("select p from UserSymptom p where p.patientID = :id");
			q.setParameter("id", patientId);
			s = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}
}
