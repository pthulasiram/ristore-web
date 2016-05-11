package org.mdacc.rists.ristore.ws.security.jwt;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.nimbusds.jwt.SignedJWT;


public class JWTAuthenticationFilter extends GenericFilterBean {
    
	AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("JwtFilter.doFilter");
		System.out.println("JwtFilter.authenticationManager"
				+ authenticationManager);

		HttpServletRequest req = (HttpServletRequest) request;
		String stringToken = req.getHeader("Authorization");
        if (stringToken == null) {
            throw new InsufficientAuthenticationException("Authorization header not found");
        }
		// remove schema from token
        String authorizationSchema = "Bearer";
        if (stringToken.indexOf(authorizationSchema) == -1) {
            throw new InsufficientAuthenticationException("Authorization schema not found");
        }
        stringToken = stringToken.substring(authorizationSchema.length()).trim();
		System.out.println("JwtFilter.doFilter.stringToken:" + stringToken);

		if (stringToken != null && stringToken != "") {
			try {
				SignedJWT sjwt = SignedJWT.parse(stringToken);
				JWTToken token = new JWTToken(sjwt);
				Authentication auth = authenticationManager
						.authenticate((Authentication) token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (AuthenticationException e) {
				e.printStackTrace();
			}
		}

		chain.doFilter(request, response);
	}
}