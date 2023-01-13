package cl.bci.bciexercise.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final static String bearer = "Bearer ";

    //@Value("${application.security.jwt.secret}")
    private String secret = "s3cret";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Pasa por AuthorizationFilter");
        log.info("request.getServletPath() {}", request.getServletPath());
        if (request.getServletPath().contains("/login")){
            filterChain.doFilter(request, response);
        }else{
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(!ObjectUtils.isEmpty(authHeader) && authHeader.startsWith(bearer)){
                try {
                    String token = authHeader.substring(bearer.length());
                    Algorithm alg  = Algorithm.HMAC256(secret.getBytes());
                    JWTVerifier verificator = JWT.require(alg).build();
                    DecodedJWT decoder = verificator.verify(token);
                    String email = decoder.getSubject();

                    Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
                    decoder.getClaim("roles").asList(String.class).forEach(rol -> {
                        roles.add(new SimpleGrantedAuthority(rol));
                    });

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, null, roles);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    filterChain.doFilter(request, response);

                }catch (Exception e){
                    log.info("error en AuthorizationFilter {}", e.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    Map<String , String > error = new HashMap<>();
                    error.put("message-error", e.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), error);

                }

            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}

