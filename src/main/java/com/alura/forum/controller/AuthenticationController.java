package com.alura.forum.controller;

import com.alura.forum.DTO.request.LoginFormDto;
import com.alura.forum.config.security.TokenService;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    //Esta classe por algum motivo não vem configurada por padrão para ser injetada, precisa do @Bean no SecurityConfig
    //Precisa extender o método em uma classe que extende o WebSecurityConfigurerAdapter
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginFormDto form) {
        //authManager recebe no seu método essa classe abaixo e foi criado o método dentro do form, para receber os dados
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();

        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            //Geração do Token pela biblioteca jjwt abstraído no service
            String token = tokenService.generateToken(authentication);

            System.out.println(token);
            return ResponseEntity.ok().build();

        } catch (AuthenticationException e) {
            //caso dê erro de autenticação esta é a exception, e retornara um erro 400 - bad request
            return ResponseEntity.badRequest().build();
        }
    }

}
