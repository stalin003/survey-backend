package com.example.survey.config;

import com.example.survey.filters.AuthenticationFilter;
import com.example.survey.filters.AuthorizationFilter;
import com.example.survey.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(
                        "/api/user/**",
                        "/api/login",
                        "/api/token/refresh"
//                        "/api/question/**",
//                        "/api/option/**",
//                        "/api/AQ/**"
                ).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/question/**").hasAnyRole("USER", "OFFICER")
                .antMatchers(HttpMethod.POST, "/api/AQ/**").hasRole("USER")
                .antMatchers(
                        "/api/question/**",
                        "/api/option/**",
                        "/api/AQ/**").hasRole("OFFICER")
                .anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}
