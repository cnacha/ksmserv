package com.mfu.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mfu.dao.common.UserDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.record.Patient;
import com.mfu.util.StringUtil;

@Controller
public class SecurityController {

	private SecurityManager securityManager = new SecurityManager();
	
	private static final String PREFIX_TEMPERARY_CODEHN = "998";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = null;

		// Check whether the current user have authority to access dashboard or
		// not.
		if (securityManager.validateAuthority(request)) {
			User user = (User) request.getSession().getAttribute(
					SecurityManager.USER_DATA_SESSION_ATTRIBUTE);

			// Check whether the current user is administrator or hospital
			// staff.
			if (user!= null && user.getRole().equals(SecurityManager.ROLE_ADMIN)) {
				model = new ModelAndView("pages/admin-hospital.jsp");
			} else if (user!= null && user.getRole()
					.equals(SecurityManager.ROLE_HOSPITALSTAFF)) {
				model = new ModelAndView("dashboard.jsp");
			} else {
				model = new ModelAndView("login.jsp");
				User loginBean = new User();
				model.addObject("loginBean", loginBean);
			}

		} else {
			model = new ModelAndView("login.jsp");
			User loginBean = new User();
			model.addObject("loginBean", loginBean);
		}

		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView executeLogin(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loginBean") User loginBean) {
		ModelAndView model = null;
		try {

			UserDAO userService = new UserDAO();
			User isValidUser = userService.authenticate(
					loginBean.getUsername(), loginBean.getPassword());
			if (isValidUser != null
					&& isValidUser.getRole().equals(
							SecurityManager.ROLE_HOSPITALSTAFF)) {
				System.out.println("Hospital Staff Login Successful");
				request.getSession().setAttribute(
						SecurityManager.USER_DATA_SESSION_ATTRIBUTE,
						isValidUser);
				request.getSession().setAttribute("userId",
						isValidUser.getKeyString());
				securityManager.initAuthorityWithoutPersistKey(request,
						isValidUser);
				// request.setAttribute("adminData", isValidUser);
				model = new ModelAndView("dashboard.jsp");
			} else if (isValidUser != null
					&& isValidUser.getRole().equals(SecurityManager.ROLE_ADMIN)) {
				System.out.println("Admin Staff Login Successful");
				request.getSession().setAttribute(
						SecurityManager.USER_DATA_SESSION_ATTRIBUTE,
						isValidUser);
				securityManager.initAuthorityWithoutPersistKey(request,
						isValidUser);
				// request.setAttribute("adminData", isValidUser);
				model = new ModelAndView("pages/admin-hospital.jsp");
			} else {
				model = new ModelAndView("login.jsp");
				model.addObject("loginBean", loginBean);
				request.setAttribute("message", "Invalid credentials!!");
			}

			userService.closeEntityManager();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView displayLogout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("loggedInUser", null);
		ModelAndView model = new ModelAndView("login.jsp");
		User loginBean = new User();
		model.addObject("loginBean", loginBean);
		model.addObject("AloginBean", loginBean);

		securityManager.destroyAuthority(request);

		return model;
	}

	@RequestMapping(value = "/loginPatientWS", method = RequestMethod.POST)
	@ResponseBody
	public User executeLoginWS(HttpServletRequest request,
			HttpServletResponse response, @RequestBody User loginBean) {
		System.out.println("login patient: " + loginBean.getUsername() + " "
				+ loginBean.getPassword());
		try {

			UserDAO userService = new UserDAO();
			User isValidUser = userService.authenticate(
					loginBean.getUsername(), loginBean.getPassword());
			userService.closeEntityManager();
			if (isValidUser != null
					&& isValidUser.getRole().equals(
							SecurityManager.ROLE_PATIENT)) {
				System.out.println("Patient Login Successful");
				securityManager.initAuthority(request, isValidUser);
				// request.getSession().setAttribute(SecurityManager.USER_DATA_SESSION_ATTRIBUTE,
				// isValidUser);
				// request.getSession().setAttribute("loggedInUser",
				// isValidUser);
				// request.setAttribute("adminData", isValidUser);
				return isValidUser;
			} else {

				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/registerPatientUserWS", method = RequestMethod.POST)
	@ResponseBody
	public String registerPatientUserWS(@RequestBody User user,
			HttpServletRequest request) {
		System.out.println("registerPatientUserWS called " + user.getPassword());

		UserDAO userService = new UserDAO();
		try {
			// Check Duplicate user
			if (userService.checkDuplicateUsername(user.getUsername())) {
				return "-3";
			}
			if(userService.findUserByCodeHN(user.getCodeHN())!=null){
				return "-4";
			}
			// Check HN
			PatientDAO pDAO = new PatientDAO();

			Patient p = null;
			if (user.getCodeHN() != null && !"".equals(user.getCodeHN())) {
				p = pDAO.findPatientsByHospitalNumber(user.getCodeHN());
				pDAO.closeEntityManager();
			}

			if (p == null) {

				// create temperary patient record
				PatientDAO patientDAO2 = new PatientDAO();
				Patient tmpPatient = new Patient();
				tmpPatient.setCodeHN(PREFIX_TEMPERARY_CODEHN + StringUtil.randomNumber(7));
				tmpPatient.setFirstname(user.getFirstname());
				tmpPatient.setLastname(user.getLastname());
				patientDAO2.savePatient(tmpPatient);
				patientDAO2.closeEntityManager();

				// save user
				user.setRole(SecurityManager.ROLE_PATIENT);
				user.setStatus(1);
				user.setCodeHN(tmpPatient.getCodeHN());
				userService.saveUser(user);
				userService.closeEntityManager();

				return "1";

			} else {
				// auto activate user when HN is existed and correct
				user.setRole(SecurityManager.ROLE_PATIENT);
				user.setFirstname(p.getFirstname());
				user.setLastname(p.getLastname());
				user.setCodeHN(p.getCodeHN());
				user.setStatus(1);
				userService.saveUser(user);
				userService.closeEntityManager();

				return "1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

	}

	public static ModelAndView getLoginPage() {
		ModelAndView mv = new ModelAndView("login.jsp");
		User loginBean = new User();
		mv.addObject("loginBean", loginBean);

		return mv;
	}
}
