package com.SolucionesParaPlagas.android.Modelo.Repositorio;

import java.util.List;
import java.util.ArrayList;

//Repositorio que guarda una lista de objetos

public abstract class RepositorioLista<Tipo>{

    private List<Tipo> listaDatos;

    protected RepositorioLista() {
        listaDatos = new ArrayList<>();
    }

    public List<Tipo> getDatos() {
        return listaDatos;
    }

    // Metodo para obtener el primer objeto de la lista
    public Tipo getDato() {
        if (!listaDatos.isEmpty()) {
            return listaDatos.get(0);
        }
        return null;
    }

    // Metodo para obtener un objeto por medio de su indice en la lista
    public Tipo getDato(int indice) {
        if (indice >= 0 && indice < listaDatos.size() && !listaDatos.isEmpty()) {
            return listaDatos.get(indice);
        }
        return null;
    }

    // Metodo para añadir un objeto al repositorio
    public void setDato(Tipo objeto) {
        listaDatos.add(objeto);
    }

    // Metodo para añadir una lista de datos al repositorio
    public void setDatos(List<Tipo> datos) {
        listaDatos.addAll(datos);
    }

    // Metodo para limpiar el repositorio
    public void clearList() {
        listaDatos.clear();
    }

    // Metodo para eliminar un objeto del repositorio
    public void clearDato(Tipo objeto) {
        listaDatos.remove(objeto);
    }

}

