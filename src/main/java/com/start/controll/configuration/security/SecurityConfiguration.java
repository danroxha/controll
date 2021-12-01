package com.start.controll.configuration.security;

import com.start.controll.services.UserPrincipalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserPrincipalDetailsService userPrincipalDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userPrincipalDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().authorizeRequests()
          .antMatchers("/dashboard/daily/**").hasAnyRole("ADMIN", "SCRUM_MASTER")
          .antMatchers("/dashboard/turma/json**").hasAnyRole("ADMIN", "SCRUM_MASTER")
          .antMatchers("/dashboard/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.GET, "/").permitAll()
          .anyRequest().authenticated()
          .and()
          .formLogin().loginPage("/login").permitAll()
          .and().
          logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/");
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/public/**");
  }
}
