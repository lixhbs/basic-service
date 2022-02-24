package com.basic.admin.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    static final String[] AUTH_WHITELIST = {
            "/api/admin/user/login",
            //"/api/sys/user/leapLogin"
            "/**"
    };

    UnauthorizedEntryPoint unauthorizedEntryPoint;
    ObjectMapper objectMapper;
    RedisTemplate<String, Object> redisTemplate;
    JsonAccessDeniedHandler jsonAccessDeniedHandler;

    @Autowired
    public SecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint,
                          ObjectMapper objectMapper,
                          RedisTemplate<String, Object> redisTemplate,
                          JsonAccessDeniedHandler jsonAccessDeniedHandler
    )
    {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.jsonAccessDeniedHandler = jsonAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .accessDeniedHandler(jsonAccessDeniedHandler)
                .and().addFilter(new AuthenticationFilter(authenticationManager(), objectMapper, redisTemplate));
        httpSecurity.headers().cacheControl();
    }

    @Bean
    UserDetailsService getUserDetailsService()
    {
        return new InMemoryUserDetailsManager();
    }

}
