package com.basic.admin.common.security;

import cn.hutool.core.util.CharsetUtil;
import com.basic.admin.common.enums.BizCodeEnum;
import com.basic.admin.common.result.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler
{
    ObjectMapper objectMapper;

    public JsonAccessDeniedHandler(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException
    {
        int status = HttpStatus.FORBIDDEN.value();
        httpServletResponse.setStatus(status);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(CharsetUtil.UTF_8);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                        JsonData.buildResult(BizCodeEnum.OPS_NOT_AUTHORITY)
                )
        );
    }
}
