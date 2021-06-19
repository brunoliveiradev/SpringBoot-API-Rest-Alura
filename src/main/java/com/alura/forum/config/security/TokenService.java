package com.alura.forum.config.security;

import com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    //Gerando o token para ser consumido no controller
    public String generateToken(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API do fórum")    //quem é a aplicação quem fez a geração do token
                .setSubject(usuarioLogado.getId().toString())  // quem é o usuário autenticado que o token pertence
                .setIssuedAt(hoje) //data de geração do token
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS512, secret)  //qual algoritmo usar, e qual a key
                .compact();
    }

    // Retorna se é valido ou não
    public Boolean isTokenValid(String token) {
        //Recebe o token, faz um parse descriptografando, baseado na key,
        // e o metodo devolve o claims, se valido, retorna o objeto, caso contrario joga uma exception
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Recupera as informações baseado no token
    public Long getIdUsuario(String token) {
        // primeiro faz o parse do token, pegando o body
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        // Retorna o id do usuario como tipo Long, ao invés de string
        return Long.parseLong(claims.getSubject());
    }
}
