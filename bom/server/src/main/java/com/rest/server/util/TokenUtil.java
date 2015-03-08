package com.rest.server.util;

import java.util.UUID;

public class TokenUtil {
	
	public static final String TOKEN_COOKIE_NMAE="TOKEN";
	public static final int TOKEN_COOKIE_EXPIRE_TIME=1000*60*60*6;
	
	public static String createRandomToken(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
