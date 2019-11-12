package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AutenticacaoViaTokenFilter  extends  OncePerRequestFilter {
	
	private Token_Service tokenService;
	 
	public AutenticacaoViaTokenFilter(Token_Service tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token =  recuperarToken(request);
		System.out.println("Recuperando token na classe *Filter: " + token);
		boolean valido = tokenService.isTokenValido(token);
		System.out.println("Validando token no *Filter: " + valido);
		
		if(valido) {
			autenticarCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarCliente(String token) {
		
		Long idUsuario =  tokenService.getIdUsuario(token);
		
	//	UsernamePasswordAuthenticationToken authentication = new 
	//			UsernamePasswordAuthenticationToken(usuario, null, usuario.get);
		
	//	SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
	}

	private String recuperarToken(HttpServletRequest request) {
	
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ") ) {
			return null;
		}
		
		return token.substring(7, token.length());
		
		
	}

}
