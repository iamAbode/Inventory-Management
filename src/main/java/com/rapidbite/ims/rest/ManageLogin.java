package com.rapidbite.ims.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rapidbite.ims.dao.UserRepo;
import com.rapidbite.ims.entity.User;

@RestController
public class ManageLogin
{

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/api/login/{email}/{password}")	
	public String findById(@PathVariable String email, @PathVariable String password, HttpServletRequest request) {
		Optional<User> result = userRepo.findByEmail(email);
		
		User user = null;
		String res = "";
		if(result.isPresent()) {
			user = result.get();
			if(user.getPassword().equals(password)) {
				res = "ok";
				HttpSession session = request.getSession();
				session.setAttribute("user_id", user.getUserId());
				session.setAttribute("firstname", user.getFirstname());
				session.setAttribute("lastname", user.getLastname());
				session.setAttribute("email", user.getEmail());
				session.setAttribute("role", user.getRole());
				session.setAttribute("dept", user.getDepartment());
			}
			else {
				res = "Invalid Password";
			}
		}
		else {
			res = "Invalid Login Credentials";
		}
		return res;
	}
	
	@GetMapping("/api/check")
	public String checkSession(HttpServletRequest request) {
		//request.getSession().invalidate();	
		return "ok";
	}
	
	@GetMapping("/api/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();	
		return "ok";
	}
	
	
}
