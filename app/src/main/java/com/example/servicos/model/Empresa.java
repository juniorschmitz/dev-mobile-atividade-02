package com.example.servicos.model;

public class Empresa {
    private String razao_social;
    private String nome_fantasia;
    private String natureza_juridica;
    private String capital_social;
    private String data_inicio;
    private String porte;
    private String tipo;

    public String getRazao_social() {
        return razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public String getNatureza_juridica() {
        return natureza_juridica;
    }

    public String getCapital_social() {
        return capital_social;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public String getPorte() {
        return porte;
    }

    public String getTipo() {
        return tipo;
    }

    public String apresentacaoFormatada() {
        return this.razao_social.concat("\n")
                .concat(this.nome_fantasia)
                .concat("\n")
                .concat(this.natureza_juridica);
    }
}
