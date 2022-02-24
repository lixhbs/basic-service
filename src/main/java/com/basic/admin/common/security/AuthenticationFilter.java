package com.basic.admin.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.basic.admin.common.constants.ConstantKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter
{

    ObjectMapper objectMapper;
    RedisTemplate<String, Object> redisTemplate;

    public static final String[] COMMON_AUTHORITIES = {
//            "USER.PASSWORD.SAVE",
//            "BASE.FILE_UPLOAD"
            "AUTH.LOGIN.AFTER"
    };

    public AuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate)
    {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        String token = request.getHeader("authorization");
        if (StrUtil.isNotEmpty(token))
        {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token)
    {
        try
        {
            String key = String.format(ConstantKey.SYS_TOKEN_REDIS_PREFIX, token);
            // 包装类
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key)))
            {
                String stringValue = (String) redisTemplate.opsForValue().get(key);
                JSONObject jsonObject = JSONUtil.parseObj(stringValue);
                AuthSubject authSubject = JSONUtil.toBean(jsonObject, AuthSubject.class);
                //刷新TOKEN失效时间
                redisTemplate.expire(key, authSubject.getTimeout(), TimeUnit.MINUTES);
                String account = authSubject.getAccount();
                request.setAttribute("account", account);
                request.setAttribute("userId", authSubject.getUserId());
                authSubject.getApiRoles().addAll(Arrays.asList(COMMON_AUTHORITIES));
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authSubject.getApiRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                UsernamePasswordAuthenticationToken uauthenToken = new UsernamePasswordAuthenticationToken(account, null, simpleGrantedAuthorities);
                uauthenToken.setDetails(authSubject);
                return uauthenToken;
            }
        } catch (Exception e)
        {
            log.error("Token鉴权失败: " + token, e);
            return null;
        }

        return null;
    }

}

