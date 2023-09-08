package com.swimmingcommunityapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.swimmingcommunityapp.exception.ErrorCode;
import com.swimmingcommunityapp.response.ErrorResponse;
import com.swimmingcommunityapp.response.Response;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_PERMISSION);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(ErrorCode.INVALID_PERMISSION.getHttpStatus().value());

        try (OutputStream outputStream = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, Response.error("ERORR",errorResponse));
            outputStream.flush();
        }
    }
}
