	package com.swamy.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.entity.Role;
import com.swamy.entity.User;
import com.swamy.payload.LoginDTO;
import com.swamy.payload.SignUpDTO;
import com.swamy.repository.RoleRepository;
import com.swamy.repository.UserRepository;
import com.swamy.service.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	@Autowired
	private IAuthService authService;

	@PostMapping(value = { "/signin", "/login" })
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

		return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);
	}

	@PostMapping(value = { "/signup", "/register" })
	public ResponseEntity<String> register(@RequestBody SignUpDTO signUpDTO) {

		return new ResponseEntity<>(authService.register(signUpDTO), HttpStatus.CREATED);
	}
}
