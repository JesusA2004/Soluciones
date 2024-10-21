package com.SolucionesParaPlagas.android.Modelo.Repositorio;

//Repositorio que guarda un solo objeto

public class Repositorio <Tipo>{

    private Tipo objeto;

    protected Repositorio(){
        // Constructor protejido para evitar instanciación de clases externas
    }

    public Tipo getObjeto(){
        return objeto;
    }

    public void setObjeto(Tipo objeto){
        this.objeto = objeto;
    }

    public void clearObjeto(){
        objeto = null;
    }

    public boolean esNulo(){
        if(objeto == null){
            return true;
        }
        return false;
    }

}
