package com.rest.server.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.rest.server.protocol.amf.AmfChannelInitializer;
import com.rest.server.protocol.websocket.WebSocketChannelInitializer;

@Configuration
@Profile("SocketServer")
public class NettyConfig {
	
	private static final Log log = LogFactory.getLog(NettyConfig.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private AmfChannelInitializer amfChannelInitializer;
	
	@Autowired
	private WebSocketChannelInitializer webSocketChannelInitializer;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ServerBootstrap bootstrap() {

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup(), workerGroup())
				.channel(NioServerSocketChannel.class)
				.childHandler(amfChannelInitializer);
		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for ( ChannelOption option : keySet) {
			bootstrap.option(option, tcpChannelOptions.get(option));
		}
		InetSocketAddress address = tcpPort();
		bootstrap.bind(address);
		log.info("netty started at "+address.getAddress()+":"+address.getPort());
		return bootstrap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public ServerBootstrap wsbootstrap() {

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup(), workerGroup())
				.channel(NioServerSocketChannel.class)
				.childHandler(webSocketChannelInitializer);
		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (ChannelOption option : keySet) {
			bootstrap.option(option, tcpChannelOptions.get(option));
		}
		InetSocketAddress address = wstcpPort();
		bootstrap.bind(address);
		log.info("netty started at "+address.getAddress()+":"+address.getPort());
		return bootstrap;
	}

	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(env.getProperty("io.core",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
	}

	@Bean(destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(env.getProperty("io.worker.tcp.max",Integer.class,Runtime.getRuntime().availableProcessors() + 1));
	}

	@Bean
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(env.getProperty("tcp.socket.port",Integer.class,12727));
	}
	
	@Bean
	public InetSocketAddress wstcpPort() {
		return new InetSocketAddress(env.getProperty("ws.tcp.socket.port",Integer.class,12728));
	}

	@Bean
	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, true);
		options.put(ChannelOption.SO_BACKLOG, 128);
		return options;
	}

}
