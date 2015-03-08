package com.rest.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes={@Index(name="userId",columnList="userId")})
public class Session  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3600879727090351823L;
	
	public static final int STATUS_LOGIN=1;
	public static final int STATUS_LOGOUT=2;

	public static final String NAME="session";
	
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private String userName;
	
	private Long userId;

	private String sessionIp="";
	
	private String sessionSign="";
	
	private Integer status=STATUS_LOGIN;
	
	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	private Timestamp lassAccessTime=new Timestamp(System.currentTimeMillis());
	
	
	public String getSessionIp() {
		return sessionIp;
	}

	public void setSessionIp(String sessionIp) {
		this.sessionIp = sessionIp;
	}

	public String getSessionSign() {
		return sessionSign;
	}

	public void setSessionSign(String sessionSign) {
		this.sessionSign = sessionSign;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLassAccessTime() {
		return lassAccessTime;
	}

	public void setLassAccessTime(Timestamp lassAccessTime) {
		this.lassAccessTime = lassAccessTime;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "Session [id=" + id + ", userName=" + userName + ", userId="
				+ userId + ", sessionIp=" + sessionIp + ", sessionSign="
				+ sessionSign + ", createTime=" + createTime
				+ ", lassAccessTime=" + lassAccessTime + "]";
	}
}
