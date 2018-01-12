package com.mfu.dao.record;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.mfu.entity.record.Patient;

public class PatientDAO {

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EntityManager em = null;

	public PatientDAO() {
		em = emfInstance.createEntityManager();
	}

	public List<Patient> getAllPatient() {

		List<Patient> patients = null;

		try {
			Query q = em.createQuery("select p from Patient p");
			patients = q.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return patients;

	}

	public Patient findPatientByKey(String key) {

		Patient patient = null;

		try {
			Query q = em.createQuery("select p from Patient p where p.key = :key");

			q.setParameter("key", KeyFactory.stringToKey(key));
			List<Patient> patients = q.getResultList();
			patient = patients.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return patient;

	}

	public void savePatient(Patient patient) {

		try {

			em.persist(patient);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updatePatient(Patient patient) {

		try {

			em.merge(patient);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void deletePatient(String key) {

		Patient patient = this.findPatientByKey(key);
		if (patient != null)
			em.remove(patient);
	}
	

	public void closeEntityManager() {

		if (em != null)

			em.close();

	}
	
	public Patient findPatientsByHospitalNumber(String hospitalNumber) {
		Patient p = null;
		try {
			Query q = em.createQuery("select p from Patient p where p.codeHN = :hospitalNumber");

			q.setParameter("hospitalNumber", hospitalNumber);
			p = (Patient) q.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}
	
	public Patient findPatientsByCitizenId(String citizenId) {
		Patient p = null;
		try {
			Query q = em.createQuery("select p from Patient p where p.citizenId = :citizenId");

			q.setParameter("citizenId", citizenId);
			p = (Patient) q.getSingleResult();

		} catch (Exception e) {
			//e.printStackTrace();
		}

		return p;
	}
	
	
/*
	public Patient patientLogin(String username, String password) {

		password = SecurityService.passwordToMD5(password);

		Patient patient = null;
		List<Patient> patients = null;
		try {
			Query q = em.createQuery("select p from Patient p where p.username = :username and p.password = :password");

			q.setParameter("username", username);
			q.setParameter("password", password);

			patients = q.getResultList();
			patient = patients.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return patient;
	}

	public List<Patient> findActivatePatients() {
		List<Patient> patients = null;
		try {
			Query q = em.createQuery("select p from Patient p where p.status = '1'");
			patients = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return patients;
	}

	public List<Patient> findDeactivatePatients() {
		List<Patient> patients = null;
		try {
			Query q = em.createQuery("select p from Patient p where p.status = '0'");
			patients = q.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return patients;
	}
*/
}
