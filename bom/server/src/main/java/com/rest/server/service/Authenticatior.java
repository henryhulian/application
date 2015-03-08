package com.rest.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Authenticatior {
	int login(String userName, String password,HttpServletRequest request, HttpServletResponse response);
	int logout(HttpServletRequest request, HttpServletResponse response);
}
