package com.rest.game.business.service.domain;

import com.rest.game.business.entity.user.User;


public interface UserService {
	
	User findCurrentUserByToken( String token );
	
	User findUserByUserName( String userName );
	
	User createUserAndAuthorization(String userName , String password);
	
}
