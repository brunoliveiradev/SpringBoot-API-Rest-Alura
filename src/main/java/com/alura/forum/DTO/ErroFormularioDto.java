package com.alura.forum.DTO;

public class ErroFormularioDto {

    private String campo;
    private String mensagemErro;

    public ErroFormularioDto(String campo, String mensagemErro) {
        this.campo = campo;
        this.mensagemErro = mensagemErro;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
