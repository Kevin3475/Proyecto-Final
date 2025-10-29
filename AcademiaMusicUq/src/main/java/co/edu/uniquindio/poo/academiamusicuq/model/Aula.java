package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Aula {

    private String idAula;
    private int capacidad;
    private String tipo;
    private List<Clase> listClases;
    private List<Horario> listHorarios;

    public Aula(String idAula,int capacidad,String tipo){

        this.idAula = idAula;
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.listClases = new ArrayList<>();
        this.listHorarios = new ArrayList<>();
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }

    public List<Horario> getListHorarios(){
        return listHorarios;
    }

    public void setListHorarios(List<Horario> listHorarios){
        this.listHorarios = listHorarios;
    }
}
















































