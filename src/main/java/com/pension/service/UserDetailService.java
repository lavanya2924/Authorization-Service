package com.pension.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pension.entity.LoginUserDetail;
import com.pension.exception.NotValidUserException;
import com.pension.repository.UserDetailRepository;
import com.pension.util.JwtUtil;

@Service
public class UserDetailService {
	
	private Logger log = LoggerFactory.getLogger(UserDetailService.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailRepository repository;

	public boolean verifyUser(LoginUserDetail user) {
		String name = user.getUserName();
		String password = user.getPassword();
		LoginUserDetail dbUser = repository.findByUserName(name);
		return dbUser != null && dbUser.getPassword().equals(password);
	}

	public boolean saveUser(LoginUserDetail user) {
		String name = user.getUserName();
		LoginUserDetail dbUser = repository.findByUserName(name);
		LoginUserDetail saved = null;
		if(dbUser == null && user.getPassword().length() > 3 &&  name.length() > 3) { 
			saved = repository.save(user);
			log.info("User is saved ...");
		}
		return saved != null;
	}

	public ResponseEntity<Map<String, String>> loginDetail(LoginUserDetail user) {
		String token = "";
		if (this.verifyUser(user)) {
			token = jwtUtil.generateToken(user.getUserName());
			log.info("User is verified ...");
		} else {
			throw new NotValidUserException("In valid Credentials");
		}
		log.info("Token Generated ...");
		return new ResponseEntity<>(new HashMap<>(Map.of("token", token)), HttpStatus.OK);
	}

}
