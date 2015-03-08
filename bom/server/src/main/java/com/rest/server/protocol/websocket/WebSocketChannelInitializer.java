package com.rest.server.protocol.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.server.handler.NettySecurityFilter;
import com.rest.server.handler.NettyServerHandler;


@Component
public class WebSocketChannelInitializer extends
		ChannelInitializer<SocketChannel> {

	@Autowired
	private AmfWebSocketDecoder amfDecoder;

	@Autowired
	private AmfWebSocketEncoder amfEncoder;

	@Autowired
	private NettyServerHandler serverHandler;

	@Autowired
	private NettySecurityFilter securityFilter;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(65536));
		pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
		pipeline.addLast("decoder", amfDecoder);
		pipeline.addLast("filter", securityFilter);
		pipeline.addLast("idleStateHandler", new IdleStateHandler(3, 3, 0));
		pipeline.addLast("handler", serverHandler);
		pipeline.addLast("encoder", amfEncoder);
	}

}
