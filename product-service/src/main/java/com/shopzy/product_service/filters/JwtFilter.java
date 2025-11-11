package com.shopzy.product_service.filters;

import com.shopzy.product_service.utils.JwtUtils;
import com.shopzy.user_service.dto.JwtResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtFilter
        extends OncePerRequestFilter
{
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = request.getHeader("Authorization");
        if(Objects.isNull(token) || !token.startsWith("Bearer ")) {
            log.error("INVALID AUTH TOKEN");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing Authorization header");
            return;
        }
        String jwtToken = token.split("Bearer ")[1];
        try {
            JwtResponse jwtResponse = jwtUtils.validateJwt(jwtToken);
            log.info("Validated JWT Token for UserId: {}", jwtResponse.getId());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwtResponse, null));
            filterChain.doFilter(request, response);
            log.info("Response Received for UserId: {}", jwtResponse.getId());
        } catch (Exception e) {
            log.error("FAILED TO VERIFY TOKEN: ", e);
        }
    }
}
