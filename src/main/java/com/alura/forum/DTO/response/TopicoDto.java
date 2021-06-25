package com.alura.forum.DTO.response;

import com.alura.forum.model.Topico;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TopicoDto implements Serializable {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }

    //Transformando page de Topico para 'page' de TopicoDTO
    public static Page<TopicoDto> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDto::new); // chama o construtor que recebe o proprio topico como parametro
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
