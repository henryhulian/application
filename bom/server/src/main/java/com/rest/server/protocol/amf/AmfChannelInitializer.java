package com.rest.server.protocol.amf;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.server.handler.NettySecurityFilter;
import com.rest.server.handler.NettyServerHandler;

@Component
public class AmfChannelInitializer extends ChannelInitializer<SocketChannel> {

	 	@Autowired
	    private AmfDecoder amfDecoder;

	 	@Autowired
	    private AmfEncoder amfEncoder;
	    
	    @Autowired
	    private NettyServerHandler serverHandler;
	    
	    @Autowired
	    private NettySecurityFilter securityFilter;


	    @Override
	    protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipeline = ch.pipeline();
	        pipeline.addLast("framerDecode",new LengthFieldBasedFrameDecoder(10240, 0, 4, 0 , 4));
	        pipeline.addLast("decoder", amfDecoder);
	        pipeline.addLast("filter", securityFilter);
	        pipeline.addLast("idleStateHandler", new IdleStateHandler(3, 3, 0));
	        pipeline.addLast("handler", serverHandler);
	        pipeline.addLast("encoder", amfEncoder);
	    }


		public AmfDecoder getAmfDecoder() {
			return amfDecoder;
		}


		public void setAmfDecoder(AmfDecoder amfDecoder) {
			this.amfDecoder = amfDecoder;
		}


		public AmfEncoder getAmfEncoder() {
			return amfEncoder;
		}


		public void setAmfEncoder(AmfEncoder amfEncoder) {
			this.amfEncoder = amfEncoder;
		}


		public NettyServerHandler getServerHandler() {
			return serverHandler;
		}


		public void setServerHandler(NettyServerHandler serverHandler) {
			this.serverHandler = serverHandler;
		}

		public NettySecurityFilter getSecurityFilter() {
			return securityFilter;
		}

		public void setSecurityFilter(NettySecurityFilter securityFilter) {
			this.securityFilter = securityFilter;
		}

}
