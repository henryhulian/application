package com.rest.server.service;

import io.netty.util.AttributeKey;
import com.rest.server.entity.Session;
import com.rest.server.protocol.VideoGameDataPackage;


public interface SocketApiService {
	
	public static final String TOKEN="token";
	@SuppressWarnings("deprecation")
	public static final AttributeKey<Session> TOKEN_KEY = new AttributeKey<Session>(TOKEN);
	
	public static final String COMMAND_INSERT_BENCHMARK="insertBenchmark";
	
	public static final String COMMAND_LOGIN="login";
	public static final String COMMAND_FIND_BALANCE="findBalance";
	
	public static final String PARM_CODE="code";
	public static final String PARM_MSG="msg";
	public static final String PARM_BALANCE="balance";
	
	VideoGameDataPackage handleCommand( Session session , VideoGameDataPackage msg );
}
