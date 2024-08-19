package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import java.util.ArrayList;
import java.util.List;

public abstract class Repositorio<Tipo> {

    private List<Tipo> listaDatos;

    protected Repositorio() {
        listaDatos = new ArrayList<>();
    }

    public List<Tipo> getDatos() {
        return listaDatos;
    }

    public Tipo getDato() {
        if (!listaDatos.isEmpty()) {
            return listaDatos.get(0);
        }
        return null;
    }

    public void setDato(Tipo objeto) {
        listaDatos.add(objeto);
    }

    public void setDatos(List<Tipo> datos) {
        listaDatos.addAll(datos);
    }

    public void clearList() {
        listaDatos.clear();
    }

}
