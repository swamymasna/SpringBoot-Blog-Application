package com.swamy.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.entity.Role;
import com.swamy.entity.User;
import com.swamy.exception.BlogApiException;
import com.swamy.payload.LoginDTO;
import com.swamy.payload.SignUpDTO;
import com.swamy.repository.RoleRepository;
import com.swamy.repository.UserRepository;
import com.swamy.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(LoginDTO loginDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User Logged In Successfully..!";
	}

	@Override
	public String register(SignUpDTO signUpDTO) {

		// check if user is exists or not
		if (userRepository.existsByUsername(signUpDTO.getUsername())) {
			throw new BlogApiException("Username is already Exists..!");
		}

		// check if email is exists or not
		if (userRepository.existsByEmail(signUpDTO.getEmail())) {
			throw new BlogApiException("Email is already Exists..!");
		}

		User user = new User();
		user.setName(signUpDTO.getName());
		user.setUsername(signUpDTO.getUsername());
		user.setEmail(signUpDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		roles.add(role);

		user.setRoles(roles);

		userRepository.save(user);

		return "User Registered Successfully..!";
	}

}
