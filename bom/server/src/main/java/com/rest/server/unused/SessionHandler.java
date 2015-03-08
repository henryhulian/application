package com.rest.server.unused;
/*package com.springrest.restserver.handler;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.springrest.restserver.domain.order.DepositOrder;
import com.springrest.restserver.repository.order.DepositOrderRepository;
import com.springrest.restserver.service.BalanceService;

public class SessionHandler extends IoHandlerAdapter {
	
	private static final Log log = LogFactory.getLog(SessionHandler.class);
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.info("the new session is connecting");
	}


	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		try {
			
			DepositOrder depositOrder = new DepositOrder();
			depositOrder.setUserId(1000L);
			depositOrder.setUserName("BENCHMARK");
			depositOrder.setStatus(0);
			depositOrder.setCreateTime(new Date());
			depositOrder.setAmount(new BigDecimal("100.00"));
			depositOrderRepository.save(depositOrder);
			
			session.write(message.toString()); // 发送回客服端（测试用）

		} catch (Exception e) {
			log.error("ERROR",e);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		log.info("Socket Client:" + session.getRemoteAddress() + " close connection!");
		session.close(false);
	}
}
*/