package com.basic.admin.common.security;

import cn.hutool.core.util.CharsetUtil;
import com.basic.admin.common.enums.BizCodeEnum;
import com.basic.admin.common.result.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint
{

    ObjectMapper objectMapper;

    public UnauthorizedEntryPoint(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException
    {
        int status = HttpStatus.UNAUTHORIZED.value();
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.getWriter().write(objectMapper.writeValueAsString(
                        JsonData.buildResult(BizCodeEnum.OPS_NOT_AUTHORITY)
                )
        );
    }
}
