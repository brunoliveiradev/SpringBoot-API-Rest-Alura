package com.alura.forum.config.security;


import com.alura.forum.model.Usuario;
import com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    //Como não é possível fazer injeção de dependências é preciso receber via construtor
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AuthenticationByTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        //Validar o token se está correto
        Boolean valido = tokenService.isTokenValid(token);
        // Autenticar o usuário
        if (valido) {
            autenticarClient(token);
        }
        filterChain.doFilter(request, response);
    }

    // Método que irá através do SecurityContextHolder autenticar.
    private void autenticarClient(String token) {
        //Dentro do token temos o id do usuario
        Long idUsuario = tokenService.getIdUsuario(token);
        // Localizando as informações pelo id
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        // Criar uma classe Authentication, recebendo quem, e qual perfil
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
        // setAuthentication recebe as informações de quem será autenticado via classe Authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        //Nome do cabeçalho que desejamos recuperar
        String token = request.getHeader("Authorization");
        // Valida se o cabeçalho está com alguma informação incorreta
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        // retorna o token, pegando a partir do espaço posterior ao Bearer até o final da string
        return token.substring(7, token.length());
    }
}
