package com.rest.game.business.service.usecase;

import java.math.BigDecimal;

public interface BalanceService {

	int increaseBalance( Long userId , BigDecimal amount );
	int decreaseBalance( Long userId , BigDecimal amount );
	BigDecimal findBalance( Long userId );
}
