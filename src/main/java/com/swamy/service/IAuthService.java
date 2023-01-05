package com.swamy.service;

import com.swamy.payload.LoginDTO;
import com.swamy.payload.SignUpDTO;

public interface IAuthService {

	public String login(LoginDTO loginDTO);
	
	public String register(SignUpDTO signUpDTO);
}

