package com.bankingApp.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.service.UserAccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/userAccount")
@CrossOrigin(origins = "http://localhost:3000")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private HttpServletRequest request;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAccount(@RequestBody UserAccount userAccount) {
		if (userAccount.getUsername().isEmpty() || userAccount.getEmail().isEmpty() || userAccount.getPassword().isEmpty() ||  userAccount.getFirstName().isEmpty() || userAccount.getLastName().isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }
		UserAccount ua = new UserAccount(userAccount.getUsername(), userAccount.getEmail(), userAccount.getPassword(), userAccount.getFirstName(), userAccount.getLastName());
        try {
            UserAccount savedInfo = userAccountService.register(ua);
            return ResponseEntity.ok().body("Registration successful");
        } catch(DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Account Not Created Please try again!");
        }
	}
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody Map<String, String> loginRequest,
	                                      HttpServletRequest request){
	    String username = loginRequest.get("username");
	    String password = loginRequest.get("password");
	    UserAccount user = this.userAccountService.login(username, password);
	    if(user != null) {
	        HttpSession session = request.getSession(true);
	        session.setAttribute("user", user);
	        return ResponseEntity.ok().body(true); // User is logged in
	    }
	    else {
	        return ResponseEntity.ok().body(false); // User is not logged in
	    }
	}
	
	@GetMapping("/account")
	public ResponseEntity<UserAccount> acountinformation() {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		UserAccount user = (UserAccount) session.getAttribute("user");
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}else {
			return ResponseEntity.ok(user);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return ResponseEntity.ok().build();
	}
}
