package com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Profile("dev")
@Configuration
@EnableWebSecurity
public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter {

    // Configuração para a aplicação seguir o modelo REST onde não manterá estado = STATELESS
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,  "/**").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //Configurações de recursos estáticos: arquivos csv, JavaScript, imagens, outros..
    // Ficará aqui as configurações para habilitar o Swagger
    @Override
    public void configure(WebSecurity web) throws Exception {
        //Endereços que o swagger chamam
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}
