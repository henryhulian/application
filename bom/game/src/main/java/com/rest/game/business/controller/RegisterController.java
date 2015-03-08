package com.rest.game.business.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.game.business.entity.user.User;
import com.rest.game.business.service.domain.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/register")
@Api(value = "/register", description = "注册")
public class RegisterController {
	
	private static final Log log = LogFactory.getLog(RegisterController.class);

	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	@ApiOperation(value="注册")
	public ResponseEntity<Void> regist(
			@ApiParam(defaultValue = "test001", required = true, value = "用户名") @RequestParam String userName,
			@ApiParam(defaultValue = "111111", required = true, value = "密码") @RequestParam String password
			) {
		
		User user = userService.findUserByUserName(userName);
		if(user!=null){
			 return ResponseEntity.status(201).build();
		}
		
		user = userService.createUserAndAuthorization(userName, password);
		
		log.trace("Create:"+user.getUserName());

		return  ResponseEntity.status(200).build();
	}

}
