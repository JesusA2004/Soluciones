package com.SolucionesParaPlagas.android.Modelo.Entidad.Cliente;

import java.util.List;

public class JsonCliente {

    private List<ClienteIndividual> value;
    private String nextLink;
    private int count;

    public List<ClienteIndividual> getValue() {
        return value;
    }

    public void setValue(List<ClienteIndividual> value) {
        this.value = value;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
