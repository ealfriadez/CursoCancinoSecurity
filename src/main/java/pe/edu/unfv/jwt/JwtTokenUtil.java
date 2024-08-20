package pe.edu.unfv.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import pe.edu.unfv.model.UsuariosModel;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;// 24 horas

	public String getSubject(String token) {

		return parseClaims(token).getSubject();
	}

	private String jwtSecret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJpZCI6IjVjOWYzYWI2NzY2Mjg2NDYyNDY0YTczNCIsIm5hbWUiOiJSYW5keSIsImF2YXRhciI6Ii8vd3d3LmdyYXZhdGFyLmNvbS9hdmF0YXIvMTNhN2MyYzdkOGVkNTNkMDc2MzRkOGNlZWVkZjM0NTEcz0yMDAmcj1wZyZkPW1tIiwiaWF0IjoxNTU0NTIxNjk1LCJleHAiOjE1NTQ1MjUyOTV9SxRurShXSSI3SE11z6nme9EoaD29TDBFr8Qwngkg";

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
		return (Key) Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	@SuppressWarnings("deprecation")
	public String generarToken(UsuariosModel usuario) {
		
		return Jwts.builder()
				.subject(String.format("%s,%s", usuario.getId(), usuario.getCorreo()))
				.issuer("elio@net")
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS256, this.jwtSecret)
				.compact();
	}
	
	@SuppressWarnings("deprecation")
	public boolean validateAccessToken(String token) {
		
		try {
            ((JwtParser) Jwts.parser().setSigningKey(this.jwtSecret)).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
        	System.out.println("JWT expirado"+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
        	System.out.println("Token es null, está vacío o contiene espacios "+ ex.getMessage());
        } catch (MalformedJwtException ex) {
        	System.out.println("JWT es inválido"+ ex);
        } catch (UnsupportedJwtException ex) {
        	System.out.println("JWT no soportado"+ ex);
        } catch (SignatureException ex) {
        	System.out.println("Validación de firma errónea");
        }
         
        return false;
	}
}
