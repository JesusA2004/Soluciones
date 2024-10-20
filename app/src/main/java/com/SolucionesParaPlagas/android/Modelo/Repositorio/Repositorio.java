package com.SolucionesParaPlagas.android.Modelo.Repositorio;

public class Repositorio <Tipo>{

    private Tipo objeto;

    protected Repositorio(){

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

    public boolean objetoNulo(){
        if(objeto == null){
            return true;
        }
        return false;
    }

}
