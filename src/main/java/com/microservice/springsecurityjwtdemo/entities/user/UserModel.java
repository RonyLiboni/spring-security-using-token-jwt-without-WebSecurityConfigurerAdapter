package com.microservice.springsecurityjwtdemo.entities.user;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.microservice.springsecurityjwtdemo.entities.user.dto.UserFormDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserModel implements UserDetails{
 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "varbinary(36)")
	private UUID userId;
	
	@Column(nullable = false, unique = true)
	@Schema(example = "Rony@gmail.com")
	private String username;
	
	@Column(nullable = false)
	@Schema(example = "123456")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<RoleModel> roles;
	
	@Column(nullable = true)
	private String passwordRecoveryToken;
	
	public UserModel (UserFormDto form, PasswordEncoder passwordEncoder) {
		this.username = form.getUsername();
		this.password = passwordEncoder.encode(form.getPassword());
		this.roles = List.of(new RoleModel(RoleName.ROLE_USER));
	}	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
