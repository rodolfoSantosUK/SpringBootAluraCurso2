package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Token_Service {
  
	@Value("${forum.jwt.expiration}")
	private String expiration;
		
	@Value("${forum.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authentication ) {
	    Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
	    Date dataExpiracao = new Date( hoje.getTime() +  Long.parseLong(expiration));
	    
		return Jwts.builder().setIssuer("Issuer: API de forum da Alura") // Aplicação que gerou o token 
				.setSubject(logado.getNome()) // Quem é o usuario autenticado a qual pertence esse token 
				.setIssuedAt(new Date())
				.setExpiration(dataExpiracao)
				.signWith(SignatureAlgorithm.HS256, secret) 
				.compact()  // para compactar e transformar numa String
				;
	}

	public boolean isTokenValido(String token) {
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false ;
		}
		 
	}
	
}
