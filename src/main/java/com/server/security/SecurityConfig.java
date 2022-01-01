package com.server.security;

import com.server.filter.CustomAuthenticationFilter;
import com.server.filter.CustomAuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
                authenticationManagerBean());
        // customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // authorize
        // http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.GET,
        // "/api/categories").permitAll();
        // http.authorizeRequests().antMatchers("/api/category/**").permitAll();
        // http.authorizeRequests().antMatchers("/api/products",
        // "/api/product/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.GET,
        // "/api/role/**").permitAll();
        // http.authorizeRequests().antMatchers("/api/cart/**").permitAll();
        // http.authorizeRequests().antMatchers("/api/user/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.GET,
        // "/api/users/**").hasAnyAuthority("BUYER");
        // http.authorizeRequests().antMatchers(HttpMethod.POST,
        // "/api/user/save/**").hasAnyAuthority("SELLER");
        http.authorizeRequests().antMatchers("/api/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // TODO Auto-generated method stub
        return super.authenticationManagerBean();
    }

    // configiration cors global
    // @Bean
    // CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
    // "HEAD"));
    // configuration.setAllowCredentials(true);
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

}
