package com.microservice.springsecurityjwtdemo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.springsecurityjwtdemo.entities.user.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
	Optional<UserModel> findByUsername(String username);
	
	boolean existsByUsername(String username);
	boolean existsByPasswordRecoveryToken(String passwordRecoveryToken);
	
	Optional<UserModel> findByPasswordRecoveryToken(String passwordRecoveryToken);
}
