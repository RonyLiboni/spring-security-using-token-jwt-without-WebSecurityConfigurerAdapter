package com.microservice.springsecurityjwtdemo.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class RoleModel implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleName roleName;

	@Override
	public String getAuthority() {
		return roleName.toString();
	}

	@Override
	public String toString() {
		return roleName.toString();
	}
	
	
}