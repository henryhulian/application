package com.rest.game.business.repository.order;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rest.game.business.entity.order.DepositOrder;




public interface DepositOrderRepository extends CrudRepository<DepositOrder,String> {
	
	public List<DepositOrder> findDepositOrderByUserId( Long userId );
	
}

