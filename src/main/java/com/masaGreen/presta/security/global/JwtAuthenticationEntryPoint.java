package com.masaGreen.presta.security.global;

import com.masaGreen.presta.ExceptionsHandling.ExceptionObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
//covers exceptions inside the filter even before request reaches servlet
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(
                HttpStatus.UNAUTHORIZED.value(),
                authException.getMessage(),
                new Date());
        response.sendError(HttpStatus.UNAUTHORIZED.value(),authException.getMessage());
        // response.sendError(HttpStatus.UNAUTHORIZED.value(),exceptionObject.toString());
    }
}
