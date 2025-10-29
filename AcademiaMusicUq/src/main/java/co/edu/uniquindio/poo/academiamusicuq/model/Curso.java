package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String idCurso;
    private String nivel;
    private int capacidad;

    private List<Clase> listClases;
    private List<Estudiante> listEstudiantes;
    private Profesor profesor;
    private Instrumento instrumento;


    public Curso(String idCurso,Instrumento instrumento,String nivel,int capacidad,Profesor profesor){

        this.idCurso = idCurso;
        this.instrumento = instrumento;
        this.nivel = nivel;
        this.capacidad = capacidad;

        this.listClases = new ArrayList<>();
        this.listEstudiantes = new ArrayList<>();
        this.profesor = profesor;


    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }
}
