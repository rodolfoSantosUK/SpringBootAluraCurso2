package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.Token_Service;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.Login_Form;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired /* Essa classe nao é injetada pelo Spring automaticamente
	porém SecurityConfigurations através do WebSecurityConfigurerAdapter           
	permite a sobreescrita e injeção  */
	private AuthenticationManager authenticationManager ; 
	
	@Autowired
	private Token_Service tokenService ; 
	
	
	@PostMapping 
	public ResponseEntity<TokenDto> autenticando(@RequestBody @Valid Login_Form loginForm  ) {
	 
		UsernamePasswordAuthenticationToken dadosLogin =  loginForm.converter();
		
		try {
			Authentication authentication =  authenticationManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
			
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	   
	}
	
	
	
}
