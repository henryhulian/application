package com.rest.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rest.server.protocol.VideoGameDataPackage;
import com.rest.server.service.SocketApiService;

@Component
@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<VideoGameDataPackage> {
	
	private static final Log log = LogFactory.getLog(NettyServerHandler.class);
	
	@Autowired
	private SocketApiService socketApiService;
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, VideoGameDataPackage msg)
			throws Exception {
		
		Channel channel =  ctx.channel();
		
		log.trace(msg);
		socketApiService.handleCommand(channel.attr(SocketApiService.TOKEN_KEY).get(),msg);
		
		channel.writeAndFlush(msg);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		
		Channel channel =  ctx.channel();
		
		if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
            	
            	if( StringUtils.isEmpty(channel.attr(SocketApiService.TOKEN_KEY).get()) ){
            		 log.info("Client "+ctx.channel().remoteAddress()+" is READER_IDLE and no token, close channel......");
            		 ctx.close();
            	}
               
            } else if (e.state() == IdleState.WRITER_IDLE) {
            	
            	if(StringUtils.isEmpty(channel.attr(SocketApiService.TOKEN_KEY).get()) ){
	           		 log.info("Client "+ctx.channel().remoteAddress()+" is WRITER_IDLE and no token, close channel......");
	           		 ctx.close();
            	}
            }
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("Client "+ctx.channel().remoteAddress()+" is connected!");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("Client "+ctx.channel().remoteAddress()+" is disconnected!");
		super.channelInactive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		 try {
		        log.error("ERROR: Unhandled exception: " + cause.getMessage()
		                + ". Closing channel " + ctx.channel().remoteAddress(),cause);
		        ctx.channel().close();
		    } catch (Exception ex) {
		    	log.error("ERROR trying to close socket because we got an unhandled exception",cause);
		    }
	}

}
