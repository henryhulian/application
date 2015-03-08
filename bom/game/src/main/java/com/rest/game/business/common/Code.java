package com.rest.game.business.common;

import java.util.HashMap;
import java.util.Map;

public class Code {
	
	public static int SUCCESS=200;
	
	public static int ERROR_USER_NOT_EXISED=1000;
	public static int ERROR_PASSWORD_OR_USERNAME_NOT_MATCH=1001;
	public static int CHECK_CODE_NOT_MATCH=1002;
	
	private static Map<Integer, String> messages = new HashMap<Integer, String>();
	static{
		messages.put(SUCCESS, "SUCCESS");
		messages.put(ERROR_USER_NOT_EXISED, "ERROR_USER_NOT_EXISED");
		messages.put(ERROR_PASSWORD_OR_USERNAME_NOT_MATCH, "ERROR_PASSWORD_OR_USERNAME_NOT_MATCH");
		messages.put(CHECK_CODE_NOT_MATCH, "CHECK_CODE_NOT_MATCH");
	}
	
	public static String getMessage( Integer code ){
		return messages.get(code);
	}
	
	private Integer code;
	private String message;
	
	public Code( Integer code ) {
		this.setCode(code);
		this.message=Code.getMessage(code);
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
