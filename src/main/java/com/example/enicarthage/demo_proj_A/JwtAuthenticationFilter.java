package com.example.enicarthage.demo_proj_A;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private static final String PROFESSEUR_ID_CLAIM = "professeurId";

    private final byte[] jwtSecretBytes;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(String jwtSecret, UserDetailsService userDetailsService) {
        this.jwtSecretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader(AUTH_HEADER);

            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = authHeader.substring(BEARER_PREFIX.length());
            Claims claims = parseJwt(jwt);

            String username = claims.getSubject();
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(request, username, claims);
            }

        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            logger.warn("Invalid JWT token: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid token");
            return;
        } catch (Exception e) {
            logger.error("JWT authentication failed", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecretBytes))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private void authenticateUser(HttpServletRequest request, String username, Claims claims) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Store professeurId in request attribute
        Long professeurId = claims.get("userId", Long.class);
        if (professeurId != null) {
            request.setAttribute(PROFESSEUR_ID_CLAIM, professeurId);
        }
    }
}