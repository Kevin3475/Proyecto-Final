package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nit;
    private String nombre;
    private List<Evento> listEventos;
    private List<Persona> listPersonas;

    public Empresa(String nit,String nombre){

        this.nit = nit;
        this.nombre = nombre;
        this.listEventos = new ArrayList<>();
        this.listPersonas = new ArrayList<>();


    }

    public String getNit(){
        return nit;
    }
    public void setNit(String nit){
        this.nit = nit;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(){
        this.nombre = nombre;
    }

    public List<Evento> getListEventos(){
        return listEventos;
    }
    public void setListEventos(List<Evento> listEventos){
        this.listEventos = listEventos;
    }

    public List<Persona> getListPersonas(){
        return listPersonas;
    }
    public void setListPersonas(List<Persona> listPersonas){
        this.listPersonas = listPersonas;
    }

}














