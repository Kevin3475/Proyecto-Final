package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Aula {

    private String idAula;
    private String nombre;
    private int capacidad;
    private boolean disponible;
    private List<Clase> listClases;


    public Aula(String idAula,String nombre,int capacidad,boolean disponible){

        this.idAula = idAula;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.disponible = disponible;
        this.listClases = new ArrayList<>();

    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean getDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible){
        this.disponible = disponible;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }


}
















































