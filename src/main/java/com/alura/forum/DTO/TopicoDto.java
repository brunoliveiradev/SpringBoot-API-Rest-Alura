package com.alura.forum.DTO;

import com.alura.forum.model.Topico;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    //Transformando listas de Topico para lista de TopicoDTO
    public static List<TopicoDto> converter(List<Topico> topicos) {
        return topicos.stream()
                .map(TopicoDto::new) // chama o construtor que recebe o proprio topico como parametro
                .collect(Collectors.toList());
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
