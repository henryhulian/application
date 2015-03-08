package com.rest.game.business.entity.user;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Wallet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1129648589052943971L;

	@Id
	private Long userId;
	
	private BigDecimal balance;
	
	@Version
	private Long version=0L;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
