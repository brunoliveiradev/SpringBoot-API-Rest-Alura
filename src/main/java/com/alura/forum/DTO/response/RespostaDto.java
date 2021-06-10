package com.alura.forum.DTO.response;

import com.alura.forum.model.Resposta;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RespostaDto implements Serializable {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String autor;

    public RespostaDto(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.autor = resposta.getAutor().getNome();
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getAutor() {
        return autor;
    }
}
