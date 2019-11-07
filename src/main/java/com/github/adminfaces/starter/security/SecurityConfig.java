package com.github.adminfaces.starter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}
	   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// form login
		http.headers().frameOptions().sameOrigin()
		.and()
		.csrf().disable()
		.authorizeRequests()
				.antMatchers("/cadastroUsuario.xhtml", "/login.xhtml", "/resources/**", "/redefinirSenha.xhtml",
						"/javax.faces.resource/**").permitAll()
				.antMatchers("/index.xhtml","/listaEventos.xhtml", "/cadastroEvento.xhtml").authenticated()
				.antMatchers("/listaUsuarios.xhtml").hasAnyRole("SINDICO")
				.antMatchers("/listaUnidadeMoradia.xhtml").hasAnyRole("SINDICO")
				.and()
				.formLogin().loginPage("/login.xhtml")
				.defaultSuccessUrl("/index.xhtml")
				.failureUrl("/login.xhtml?authfailed=true").permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login.xhtml")
				.logoutUrl("/j_spring_security_logout")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID");
				 

	}

}
