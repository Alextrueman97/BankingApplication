package com.bankingApp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.service.UserAccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAccount(
			 @RequestParam("username") String username,
	            @RequestParam("email") String email,
	            @RequestParam("password") String password,
	            @RequestParam("confirmPassword") String confirmPassword,
	            @RequestParam("firstName") String firstName,
	            @RequestParam("lastName") String lastName) {
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }
		if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
		UserAccount ua = new UserAccount(username, email, password, firstName, lastName);
        try {
            UserAccount savedInfo = userAccountService.register(ua);
            return ResponseEntity.ok().body("Registration successful");
        } catch(DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Account Not Created Please try again!");
        }
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(
			@RequestParam("username")String username,
			@RequestParam("password")String password,
			HttpServletRequest request, Model model){
		UserAccount user = this.userAccountService.login(username, password);
		if(user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			return ResponseEntity.ok().body("user logged in");
		}
		else {
			return ResponseEntity.badRequest().body("Username or password incorrect! Please try again!");
		}
		
	}
	
	@GetMapping("/account")
	public ResponseEntity<UserAccount> acountinformation(HttpSession session) {
		UserAccount user = (UserAccount) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}else {
			return ResponseEntity.ok(user);
		}
	}

}
