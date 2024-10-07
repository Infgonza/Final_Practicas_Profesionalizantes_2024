/*package config;

import java.security.Key;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@SuppressWarnings("deprecation")
@Component
public class JwtUtils {
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.time.expiration}")
	private String timeExpiration;
	
	//Generar token de acceso
	public String generateAccesToken(String nombreUsuario) {
		return Jwts.builder()
				.setSubject(nombreUsuario)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();		
				
	}
	
	//Validar el token de acceso
	public boolean isTokenValid(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
			return true;
		} catch (Exception e) {
			
			return false;
		}
	}
	
	//Obtener todos los claims del token
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}
	
	
	
	
	
	
	
	
	//Obtener firma del token
	public Key getSignatureKey() {
		byte[] KeyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(KeyBytes);
	}

}*/
