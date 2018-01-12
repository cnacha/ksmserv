package com.mfu.dao.record;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.Doctor;

public class DoctorDAO {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public DoctorDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<Doctor> getAllDoctor() {

		List<Doctor> doctors = null;

		try {
			Query q = em.createQuery("select p from Doctor p");
			doctors = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctors;

	}

	public Doctor findDoctorByKey(String key) {

		Doctor doctor = null;

		try {
			Query q = em.createQuery("select p from Doctor p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			List<Doctor> doctors = q.getResultList();
			doctor = doctors.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctor;

	}
	public Doctor findDoctorByDoctorCode(String code) {

		Doctor res = null;

		try {
			Query q = em.createQuery("select p from Doctor p where p.doctorCode = :doctorCode");

			q.setParameter("doctorCode", code);
			res = (Doctor)q.getSingleResult();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}
	
	public Doctor findDoctorByKeyString(String key) {

		Doctor doctor = null;

		try {
			Query q = em.createQuery("select p from Doctor p where p.key = :key");

			q.setParameter("key", key);
			List<Doctor> doctors = q.getResultList();
			doctor = doctors.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctor;

	}

	public void saveDoctor(Doctor doctor) {

		try {
			em.persist(doctor);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteDoctor(String key) {

		Doctor doctor = this.findDoctorByKey(key);
		if (doctor != null)
			em.remove(doctor);
	}

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}

	public List<Doctor> findDoctorBySpecialty(String parameter) {

		List<Doctor> doctors = null;
		try {
			Query q = em.createQuery("select p from Doctor p where p.specialty = :specialty");

			q.setParameter("specialty", parameter);
			doctors = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctors;
	}

}