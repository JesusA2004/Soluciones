package com.SolucionesParaPlagas.android.Modelo.Entidad.Pedido;

import java.util.List;

public class JsonPedido {

    private List<PedidoIndividual> value;
    private String nextLink;
    private int count;

    public List<PedidoIndividual> getValue() {
        return value;
    }

    public void setValue(List<PedidoIndividual> value) {
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
