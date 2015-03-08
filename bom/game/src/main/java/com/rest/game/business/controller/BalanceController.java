package com.rest.game.business.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.game.business.common.Code;
import com.rest.game.business.common.Role;
import com.rest.game.business.service.domain.UserService;
import com.rest.game.business.service.usecase.BalanceService;
import com.rest.server.entity.Session;
import com.rest.server.service.SessionService;
import com.rest.server.util.CookieUtil;
import com.rest.server.util.TokenUtil;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/balance")
@Api(value = "/balance", description = "余额")
public class BalanceController {
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value="余额查询")
	@RolesAllowed(Role.USER)
	public Code findBalance(
			HttpServletRequest request) {
		
		Session session = sessionService.findSessionByToken(CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE));
		
		Code code = new Code(Code.SUCCESS);
		code.setMessage(balanceService.findBalance(session.getUserId()).toString());
		
		return  code;
	}
}
