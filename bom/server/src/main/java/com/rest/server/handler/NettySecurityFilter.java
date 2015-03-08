package com.rest.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rest.server.entity.Session;
import com.rest.server.protocol.VideoGameDataPackage;
import com.rest.server.service.SessionService;
import com.rest.server.service.SocketApiService;

@Component
@Sharable
public class NettySecurityFilter extends MessageToMessageDecoder<VideoGameDataPackage>{
	
	
	@Autowired
	private SessionService sessionService;
	
	private static final Log log = LogFactory.getLog(NettySecurityFilter.class);
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, VideoGameDataPackage msg,
			List<Object> out) throws Exception {
		
		Channel channel =  ctx.channel();
		
		log.trace("Receive data:"+msg);
		
		if( msg.getCommand().equals(SocketApiService.COMMAND_LOGIN)){
			
			String token=(String)msg.getParameters().get(SocketApiService.TOKEN);
			
			// if cann't find token return
			if( StringUtils.isEmpty(token)){
				log.warn(msg);
				log.warn("Login Command , Token is empty , client:"+channel.remoteAddress());
				channel.close();
				return;
			}
			
			Session session = sessionService.findSessionByToken(token);
			
			if( session == null ){
				log.warn("Session not valid for token:"+token);
				channel.close();
				return;
			}
			
            //check session status
            if( session.getStatus() == null || session.getStatus() != Session.STATUS_LOGIN ){
           	 log.warn("Session status is not login "+session.getStatus()+" for user:"+session.getUserName());
           	 channel.close();
             return;
           }
			
			channel.attr(SocketApiService.TOKEN_KEY).set(session);
			
			out.add(msg);
			return;
		}else if( msg.getCommand().equals(SocketApiService.COMMAND_INSERT_BENCHMARK) ){
			channel.attr(SocketApiService.TOKEN_KEY).set(new Session());
			out.add(msg);
			return;
		}
		
		
		Session session = channel.attr(SocketApiService.TOKEN_KEY).get();
		
		 //detect token
        // if cann't find token return
		/*if( StringUtils.isEmpty(token)){
			log.warn("Token key is empty , client:"+channel.remoteAddress());
			 channel.close();
			 return;
		}*/
		
        //find session information
        //if cann't find session return
        Session sessionNew = sessionService.findSessionByToken(session.getSessionSign());
        if( sessionNew == null ){
        	log.warn("Session null not valid for token:"+session.getSessionSign());
        	 channel.close();
        	 return;
        }
    	
        //check ip
        if( sessionNew.getSessionIp()!=null && !channel.remoteAddress().toString().contains((String)sessionNew.getSessionIp())){
        	 log.warn("Session ip:"+sessionNew.getSessionIp()+" not match client ip:"+channel.remoteAddress().toString()+" for user:"+sessionNew.getUserName());
        	 channel.close();
        	 return;
        }
         
       
        //Verify user access
        /*RolesAllowed rolesAnnotation = method.getMethodAnnotation(RolesAllowed.class);
        Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
        
        if( !authorization.isUserAllowed(session.getUserId(), rolesSet)){
        	 log.trace("User:"+session.getUserName()+" do not has in role:"+rolesSet);
        	 channel.close();
        	 return;
        }*/
        
        channel.attr(SocketApiService.TOKEN_KEY).set(sessionNew);
		
		out.add(msg);
	}

}
