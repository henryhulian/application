package com.rest.game.business.service.usecase.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.game.business.common.Code;
import com.rest.game.business.entity.order.DepositOrder;
import com.rest.game.business.repository.order.DepositOrderRepository;
import com.rest.game.business.service.usecase.BalanceService;
import com.rest.game.business.service.usecase.CustomerSocketApiService;
import com.rest.server.entity.Session;
import com.rest.server.protocol.VideoGameDataPackage;


@Service
public class SocketApiServiceImpl implements CustomerSocketApiService{
	
	private static final Log log = LogFactory.getLog(SocketApiServiceImpl.class);
	
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	@Override
	public VideoGameDataPackage handleCommand(Session session,VideoGameDataPackage msg) {
		switch( msg.getCommand()){
			case CustomerSocketApiService.COMMAND_LOGIN:
				break;
			case CustomerSocketApiService.COMMAND_FIND_BALANCE:
				log.debug(CustomerSocketApiService.COMMAND_FIND_BALANCE+"-->"+msg);
				findBalance(session,msg);
				break;
			case CustomerSocketApiService.COMMAND_INSERT_BENCHMARK:
				log.debug(CustomerSocketApiService.COMMAND_INSERT_BENCHMARK+"-->"+msg);
				insertBenchmark(session,msg);
				break;
			default:
				log.error("Unknown command:-->"+msg);
				break;
		}
		return msg;
	}


	private VideoGameDataPackage findBalance( Session session , VideoGameDataPackage msg) {
		msg.getParameters().put(CustomerSocketApiService.PARM_CODE,Code.SUCCESS);
		msg.getParameters().put(CustomerSocketApiService.PARM_MSG, Code.getMessage(Code.SUCCESS));
		msg.getParameters().put(CustomerSocketApiService.PARM_BALANCE, balanceService.findBalance(session.getUserId()));
		return msg;
	}
	
	private void insertBenchmark(Session session, VideoGameDataPackage msg) {
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setUserId(1000L);
		depositOrder.setUserName("BENCHMARK");
		depositOrder.setStatus(0);
		depositOrder.setCreateTime(new Date());
		depositOrder.setAmount(new BigDecimal("100.00"));
		depositOrderRepository.save(depositOrder);
	}

}
