package com.rest.server.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public static String getCookie(HttpServletRequest request, String cookieName){
		Cookie[] cookies = request.getCookies();
		
		if(cookies==null){
			return null;
		}
		
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(cookieName)){
				return cookie.getValue();
			}
		}
		
		return null;
	}
	
	public static String setCookie(HttpServletResponse response,String cookieName , String cookieValue , String path , boolean httpOnly , int expiry){
		
		Cookie cookie=new Cookie(cookieName, cookieValue);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
		
		return null;
	}
	
}
