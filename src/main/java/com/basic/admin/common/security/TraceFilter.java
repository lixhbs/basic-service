package com.basic.admin.common.security;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class TraceFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String requestId = request.getHeader("requestId");
        if (StrUtil.isNotEmpty(requestId))
        {
            request.setAttribute("requestId", requestId);
        } else
        {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            log.debug("empty request id, generate: {}", uuid);
            request.setAttribute("requestId", uuid);
//            requestId = uuid;
        }
        String referer = request.getHeader("referer");
//        if (StringUtils.isNotEmpty(referer)) {
//            try {
//                URL url = new URL(referer);
//                referer = url.getHost();
//            } catch (Exception e) {
//                log.warn("failed to resolve domain by referer: {}", referer);
//            }
//        }
        log.info("request id: {}, referer: {}, X-Real-Ip: {}, remote address: {}, request url: {}, method: {}, content length: {}, query String: {}",
                requestId, referer,
                request.getHeader("X-Real-Ip"), request.getRemoteAddr(), request.getRequestURL(),
                request.getMethod(), request.getContentLength(), request.getQueryString());
        filterChain.doFilter(request, response);
    }
}
