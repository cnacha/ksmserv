package com.mfu.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mfu.dao.common.UserDAO;
import com.mfu.dao.record.PatientDAO;
import com.mfu.entity.common.User;
import com.mfu.entity.record.Patient;
import com.mfu.util.SecurityUtil;

@Controller
public class UserController {

	@RequestMapping(value = "/admin/listUserWS", method = RequestMethod.GET)
	@ResponseBody
	public List<User> listUserWS(HttpServletRequest request) {

		UserDAO userService = new UserDAO();
		List<User> users = userService.getAllUser();
		userService.closeEntityManager();
		return users;
	}

	@RequestMapping(value = "/admin/saveUserWS", method = RequestMethod.POST)
	@ResponseBody
	public String saveUserWS(@RequestBody User user, HttpServletRequest request) {

		try {
			UserDAO userService = new UserDAO();
			userService.saveUser(user);
			userService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/admin/activateUserWS", method = RequestMethod.GET)
	@ResponseBody
	public String activateUserWS(HttpServletRequest request) {

		UserDAO userDAO = new UserDAO();
		try {
			User user = userDAO.findUserByKey(request.getParameter("userKey"));
			user.setStatus(1);
			userDAO.saveUser(user);
			userDAO.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}
	
	@RequestMapping(value = "/findUserByIdWS", method = RequestMethod.GET)
	@ResponseBody
	public User findUserByIdWS(HttpServletRequest request) {

		User user = null;
		UserDAO userService = new UserDAO();
		try {

			user = userService.findUserByKey(request.getParameter("userId"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		userService.closeEntityManager();
		return user;
	}
	
	@RequestMapping(value = "/admin/listHospitalstaffWS", method = RequestMethod.GET)
	@ResponseBody
	public List<User> listHospitalstaffWS(HttpServletRequest request) {

		UserDAO service = new UserDAO();
		List<User> users = service.findUserByRole(SecurityManager.ROLE_HOSPITALSTAFF);
		service.closeEntityManager();
		return users;
	}

	@RequestMapping(value = "/admin/deactivateUserWS", method = RequestMethod.GET)
	@ResponseBody
	public String deactivateUserWS(HttpServletRequest request) {

		UserDAO userDAO = new UserDAO();
		try {
			User user = userDAO.findUserByKey(request.getParameter("userKey"));
			user.setStatus(0);
			userDAO.saveUser(user);
			userDAO.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/admin/listActivateUserWS", method = RequestMethod.GET)
	@ResponseBody
	public List<User> listActivateUserWS(HttpServletRequest request) {

		UserDAO userService = new UserDAO();
		List<User> users = userService.listActivateUsers();
		userService.closeEntityManager();
		return users;
	}

	@RequestMapping(value = "/admin/listDeactivateUserWS", method = RequestMethod.GET)
	@ResponseBody
	public List<User> listDeactivateUserWS(HttpServletRequest request) {

		UserDAO service = new UserDAO();
		List<User> users = service.listDeactivateUsers();
		service.closeEntityManager();
		return users;
	}

	@RequestMapping(value = "/admin/updateUserWS", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserWS(HttpServletRequest request) {

		try {
			UserDAO userService = new UserDAO();
			User user = userService.findUserByKey(request.getParameter("userId"));
			user.setFirstname(request.getParameter("firstname"));
			user.setLastname(request.getParameter("lastname"));
			user.setLastname(request.getParameter("username"));
			userService.updateUser(user);
			userService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}
	
	@RequestMapping(value = "/updatePatinetWS", method = RequestMethod.POST)
	@ResponseBody
	public String updatePatinetWS(HttpServletRequest request) {

		try {
			UserDAO userService = new UserDAO();
			User user = userService.findUserByKey(request.getParameter("userId"));
			SecurityUtil securityService = new SecurityUtil();
			String passwordMD5 = securityService.passwordToMD5(request.getParameter("password"));
			
			if(!user.getPassword().equals(passwordMD5)){
				return "-1";
			}
			user.setPassword(securityService.passwordToMD5(request.getParameter("newpass")));
			userService.updateUser(user);
			userService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";
	}

	@RequestMapping(value = "/admin/deleteUserWS", method = RequestMethod.DELETE)
	@ResponseBody
	public String removeUser(HttpServletRequest request) {

		try {
			UserDAO userService = new UserDAO();
			userService.deleteUser(request.getParameter("userId"));
			userService.closeEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}

		return "1";

	}
	
	@RequestMapping(value = "/listMobileUserWS", method = RequestMethod.GET)
	@ResponseBody
	public List<User> listMobileUserWS(HttpServletRequest request) {

		UserDAO userService = new UserDAO();
		List<User> users = userService.listMobileUser();
		System.out.println("listMobileUserWS return size = "+users.size());
		userService.closeEntityManager();
		return users;
	}

}
