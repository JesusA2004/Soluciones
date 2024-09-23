package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import java.util.List;
import java.util.ArrayList;

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

    public Tipo getDato(int index) {
        if (index >= 0 && index < listaDatos.size() && !listaDatos.isEmpty()) {
            return listaDatos.get(index);
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

    public void clearDato() {
        listaDatos.remove(1);
    }

}
