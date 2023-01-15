package com.example.LoginDemo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.LoginDemo.auth.services.LoginService;
import com.example.LoginDemo.user.entities.Users;
import com.example.LoginDemo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {


    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
        }
        if (!(header == null)) {
            final String token = header.split(" ")[1].trim();
            DecodedJWT decoded = null;
            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC512(LoginService.JWT_SECRET)).build();
                decoded = verifier.verify(token);
            } catch (JWTVerificationException ex) {
                filterChain.doFilter(request, response);
            }

            Optional<Users> userDetails = userRepository.findById(decoded.getClaim("id").asLong());
            if (!userDetails.isPresent() || !userDetails.get().isActive()) {
                filterChain.doFilter(request, response);

            }
            Users user = userDetails.get();
            user.setPassword(null);
            user.setActive(false);
            user.setPassword(null);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.get(), null, List.of());
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }
}

