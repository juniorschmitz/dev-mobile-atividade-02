package com.example.servicos.model;

public class Logradouro {
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String complement;
    private String ibge;

    public String getCep() {
        return cep;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public String getComplement() {
        return complement;
    }

    public String getIbge() {
        return ibge;
    }

    public String apresentacaoFormatada() {
        return this.street.concat("\n")
                .concat(this.neighborhood)
                .concat("\n")
                .concat(this.city);
    }
}
