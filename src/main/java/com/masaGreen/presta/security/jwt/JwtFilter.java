package com.masaGreen.presta.security.jwt;

import com.masaGreen.presta.security.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import java.util.Arrays;



@Slf4j

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtService jwtService;
    
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;
    
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
     public JwtFilter(HandlerExceptionResolver handlerExceptionResolver){
        this.handlerExceptionResolver = handlerExceptionResolver;
     }
    
    Claims claims;
    @Override
    protected void doFilterInternal( HttpServletRequest request,  HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{

            String bearerToken = getJwtToken(request);

            if( bearerToken != null && jwtService.validateToken(bearerToken) && SecurityContextHolder.getContext().getAuthentication() == null ){
                String subject= jwtService.getIdNumberFromJWT(bearerToken);
                request.setAttribute("idNumber",subject);
                
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(subject);
                claims = jwtService.extractAllClaims(bearerToken);
                
                
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
               
            }
            filterChain.doFilter(request,response);
        }catch ( MalformedJwtException | IllegalArgumentException | ExpiredJwtException | UnsupportedJwtException | SignatureException ex){
            log.error("error parsing jwt token {}",ex.getMessage());

            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
        
    }

    
    private String getJwtToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken !=null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7).trim();
        }
        return null;
    }
}
