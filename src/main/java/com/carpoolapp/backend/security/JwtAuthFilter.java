package com.carpoolapp.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    //Service is the expert in Jwt knows how to extract, validate, generate token
    private final JwtService jwtService;
    //Loads user frm DB after authentication
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");

        //checks if request contains Bearer or not
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        //Token actually starts from index 8 till 7 it is Bearer_
        String token = authHeader.substring(7);
        if(!jwtService.isTokenValid(token)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        //Asking service to Extracts email because JWT contains Payload in which with email more info is present we need only email from that token
        String email = jwtService.extractEmail(token);

        //SecurityContextHolder is Spring's memory: if token contains email and user already is not authenticated
        if(email != null && SecurityContextHolder.getContext()
                .getAuthentication() == null){
            //Now authFilter calling userDetails to loadUser from db
            UserDetails userDetails = userDetailsService.
                    loadUserByUsername(email);
            //Now user has been authenticated it contains userDetails, roles-> AUthentication Object
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
            //adds request info IP address and session info
            authToken.setDetails(new WebAuthenticationDetailsSource().
                    buildDetails(request));
            //Now Spring memory got the context who the user and to which user the request belongs
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
