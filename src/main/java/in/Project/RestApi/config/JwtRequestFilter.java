package in.Project.RestApi.config;

import in.Project.RestApi.service.CustomUserDetailsService;
import in.Project.RestApi.service.TokenBlackListService;
import in.Project.RestApi.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenBlackListService tokenBlackListService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String email = null;

        // ✅ Skip JWT validation for /signout
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/signout")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            if (jwtToken != null && tokenBlackListService.isTokenBlackList(jwtToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            try {
                email = jwtTokenUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Unable to get JWT token");
            } catch (ExpiredJwtException ex) {
                // ✅ Instead of blocking, log the error and allow logout
                System.out.println("JWT token has expired: " + ex.getMessage());
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
