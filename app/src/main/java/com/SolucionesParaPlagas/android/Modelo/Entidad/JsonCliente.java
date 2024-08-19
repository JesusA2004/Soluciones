package com.SolucionesParaPlagas.android.Modelo.Entidad;

import java.util.List;

public class JsonCliente {

    private List<Cliente> value;
    private String nextLink;
    private int count;

    public List<Cliente> getValue() {
        return value;
    }

    public void setValue(List<Cliente> value) {
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
