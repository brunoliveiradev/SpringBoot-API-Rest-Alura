package com.alura.forum.config.security;

import com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Classe de configuração
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Configurações de autenticação: controle de acessos, login
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());

    }

    // Necessário sobrescrever e implementar @Bean para que seja possivel fazer a injeção da classe em controllers
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Configurações de autorização: urls, perfis de acessos
    // Configuração para a aplicação seguir o modelo REST onde não manterá estado = STATELESS
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,  "/topicos").permitAll()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                //  este endpoint abaixo devolve informações sensíveis, apenas para fins de teste será permitAll
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                // apenas quem for ADMIN conseguirá realizar ações do método DELETE
                .antMatchers(HttpMethod.DELETE, "/topicos/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Filtra autenticação via token, antes do filtro 'UsernamePasswordAuthenticationFilter' que já vêm por padrão
                .addFilterBefore(new AuthenticationByTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configurações de recursos estáticos: arquivos csv, JavaScript, imagens, outros..
    // Ficará aqui as configurações para habilitar o Swagger
    @Override
    public void configure(WebSecurity web) throws Exception {
        //Endereços que o swagger chamam
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

}
