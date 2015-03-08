package com.rest.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rest.server.entity.Session;



public interface SessionRepository extends JpaRepository<Session,String> {
	
	@Query(" SELECT model FROM Session model WHERE model.userId=:userId")
	public List<Session> findSessionByUserId( @Param("userId") Long userId );
	
}

