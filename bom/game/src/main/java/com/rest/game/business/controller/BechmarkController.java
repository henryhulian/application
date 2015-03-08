package com.rest.game.business.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.game.business.entity.order.DepositOrder;
import com.rest.game.business.repository.order.DepositOrderRepository;
import com.rest.game.business.service.usecase.BalanceService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bechmark")
@Api(value = "/bechmark", description = "压力测试")
public class BechmarkController {
	
	private static final Log log = LogFactory.getLog(BechmarkController.class);


	@Autowired
	private DepositOrderRepository depositOrderRepository;
	
	
	@Autowired
	private BalanceService balanceService;
	
	@RequestMapping(value = "/depositOrder", method = RequestMethod.GET)
	@ApiOperation(value="创建存款订单")
	public ResponseEntity<Void> regist(
			HttpServletRequest request) {
		
		DepositOrder depositOrder = new DepositOrder();
		depositOrder.setUserId(1000L);
		depositOrder.setUserName("BENCHMARK");
		depositOrder.setStatus(0);
		depositOrder.setCreateTime(new Date());
		depositOrder.setAmount(new BigDecimal("100.00"));
		
		depositOrderRepository.save(depositOrder);

		return  ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/depositOrderAuto", method = RequestMethod.GET)
	@ApiOperation(value="创建存款订单")
	public ResponseEntity<Void> depositAuCreat(  @RequestParam  Integer count) {
		
		long timeStart = System.currentTimeMillis();
		log.debug("insert "+count+" start:"+timeStart);
		for( int i=0 ;i < count ; i++){
			DepositOrder depositOrder = new DepositOrder();
			depositOrder.setUserId(1000L);
			depositOrder.setUserName("BENCHMARK");
			depositOrder.setStatus(0);
			depositOrder.setCreateTime(new Date());
			depositOrder.setAmount(new BigDecimal("100.00"));
			depositOrderRepository.save(depositOrder);
		}
		long timeEnd = System.currentTimeMillis();
		log.debug("insert "+count+" end:"+timeEnd);
		log.debug("cost:"+(timeEnd-timeStart));
		return  ResponseEntity.ok().build();
	}
	
	
	/*@RequestMapping(value = "/depositOrderList", method = RequestMethod.GET)
	@ApiOperation(value="存款订单count")
	public List<DepositOrder> list() {
		
		List<DepositOrder> depositOrders = depositOrderRepository.findDepositOrder();
		Collections.reverse(depositOrders);	
		
		return  depositOrders;
		
	}*/

	@RequestMapping(value = "/depositOrderCount", method = RequestMethod.GET)
	@ApiOperation(value="存款订单count")
	public Long count() {
		return  depositOrderRepository.count();
	}

	@RequestMapping(value = "/simpleRequest", method = RequestMethod.GET)
	@ApiOperation(value="简单GET测试")
	public String simpleRequest() {
		return  "OK";
	}
	
}
