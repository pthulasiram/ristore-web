package org.mdacc.rists.ristore.ws.security.jwt;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
    
	private String SecretKey = "494847a9c8a147bf82f4ca6da59efe61";
    private JWSVerifier verifier;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authentication;
        try {
			JWSVerifier verifier = new MACVerifier(SecretKey);
			boolean isVerified = jwtToken.getSignedToken().verify(verifier);
			if (isVerified) {
				jwtToken.setAuthenticated(true);
			} else {
				throw new JWTAuthenticationException("authentication failed");
			}
			return jwtToken;
		} catch (JOSEException e) {
			throw new JWTAuthenticationException("authentication failed");
		}
    }

    @Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
    
    
}