package com.rest.server.unused;
/*package com.springrest.restserver.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.springrest.restserver.handler.SessionHandler;

@Configuration
public class MinaConfig {

	private static final Log log = LogFactory.getLog(MinaConfig.class);

	@Autowired
	private Environment env;
	
	@Bean
	public IoHandler sessionHandler() {
		return new SessionHandler();
	}

	@Bean
	public NioSocketAcceptor acceptor() {
		NioSocketAcceptor acceptor = new NioSocketAcceptor(env.getProperty(
				"io.core", Integer.class, new Integer(Runtime.getRuntime()
						.availableProcessors() + 1)));
		
		// Logging
		if( env.getProperty("mina.socket.loging.filter",Boolean.class,false) == true ){
			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		}
		
		// Protocol codec
		//TextLineCodecFactory
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),new LineDelimiter(";"),new LineDelimiter(";"))));
		
		//PrefixedStringCodec
		//acceptor.getFilterChain().addLast(
		//		"codec",
		//		new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
		
		// Thread pool
		acceptor.getFilterChain().addLast("threadPool",
				new ExecutorFilter(	env.getProperty("io.core",Integer.class,Runtime.getRuntime().availableProcessors() + 1)
									,env.getProperty("io.worker.max",Integer.class,Runtime.getRuntime().availableProcessors() + 1)));
		
		// Set the read data buffer size
		acceptor.getSessionConfig().setReadBufferSize(2048);
		
		// The read / write channel 10 seconds without operation enters the idle state
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// Set business handler
		acceptor.setHandler(sessionHandler());
		
		try {
			acceptor.bind(new InetSocketAddress(env.getProperty(
					"mina.socket.port", Integer.class)));
			log.info("Socket Server Started...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return acceptor;
	}

}
*/