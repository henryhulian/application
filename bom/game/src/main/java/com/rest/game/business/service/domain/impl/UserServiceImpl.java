package com.rest.game.business.service.domain.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.game.business.common.Role;
import com.rest.game.business.entity.user.User;
import com.rest.game.business.entity.user.Wallet;
import com.rest.game.business.repository.user.UserRepository;
import com.rest.game.business.repository.user.WalletRepository;
import com.rest.game.business.service.domain.UserService;
import com.rest.server.entity.Session;
import com.rest.server.service.Authorization;
import com.rest.server.service.SessionService;
import com.rest.server.util.DigestUtil;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private Authorization authorization;
	
	@Autowired
	private WalletRepository walletRepository;


	@Override
	public User findCurrentUserByToken(String token) {
		return findCurrentyUser(token);
	}
	
	public User findCurrentyUser(String token) {

		Session session = sessionService.findSessionByToken(token);
		
		User user = userRepository.findOne(session.getUserId());

		return user;
	}

	@Override
	public User createUserAndAuthorization(String userName, String password) {
		
		//创建会员
		User user = new User();
		user.setUserName(userName);
		user.setPassword(DigestUtil.sha256_base64(password));
		userRepository.save(user);
		
		//创建钱包
		Wallet wallet = new Wallet();
		wallet.setUserId(user.getId());
		wallet.setBalance(new BigDecimal("0.00"));
		walletRepository.save(wallet);
		
		//授权
		authorization.authorization(user.getId(), new HashSet<>(Arrays.asList(Role.USER)));
		
		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findUserByUserName(userName);
	}

}
