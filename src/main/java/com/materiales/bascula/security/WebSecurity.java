package com.materiales.bascula.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.materiales.bascula.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurity {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	@NonNull 
	DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(new AntPathRequestMatcher("/login.html"), new AntPathRequestMatcher("/api/**"),
								new AntPathRequestMatcher("/css/**"), new AntPathRequestMatcher("/images/**"), new AntPathRequestMatcher("/js/**"))
						.permitAll()
						.anyRequest().authenticated())
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin((form) -> form
						.loginPage("/login.html")
						.defaultSuccessUrl("/", true)
						.permitAll())
				.logout((logout) -> logout
						.logoutUrl("/logout.html")
						.permitAll())
				.rememberMe((
						rememberMe) -> rememberMe
								.tokenValiditySeconds(7 * 24 * 60 * 60)
								.key("WkcaOSLvTCXj57Q5")
								.tokenRepository(persistentTokenRepository()));
		return http.build();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
		tokenRepo.setDataSource(dataSource);
		return tokenRepo;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}