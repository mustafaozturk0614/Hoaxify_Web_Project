package com.project.hoaxify.entity;

import com.project.hoaxify.annotaion.UniqeUserName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")

public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "{hoaxify.constraints.username.NotNull.message}") @Size(min = 4, max = 64)
	@UniqeUserName
	//	@JsonView(Views.Base.class)
	private String username;
	@NotNull @Size(min = 2, max = 64)
	//	@JsonView(Views.Base.class)
	private String displayName;

	@Email
	private String email;
	@NotNull
	@Size(min = 8, max = 255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constrain.password.Pattern.message}")

	private String password;

	//	@JsonView(Views.Base.class) // response dönerken sadece bu verileri aldık

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Hoax> hoaxes;
	private String image;
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
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


