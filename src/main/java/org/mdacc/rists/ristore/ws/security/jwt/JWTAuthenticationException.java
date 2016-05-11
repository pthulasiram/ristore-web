package org.mdacc.rists.ristore.ws.security.jwt;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class JWTAuthenticationException extends AuthenticationException {
	
	public JWTAuthenticationException(String msg) {
		super(msg);
	}

}
