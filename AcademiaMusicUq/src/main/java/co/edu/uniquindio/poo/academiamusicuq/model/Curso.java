package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String idCurso;
    private String nombreCurso;
    private Nivel nivel;
    private int capacidad;
    private List<Clase> listClases;
    private List<Estudiante> listEstudiantes;
    private Profesor profesor;
    private Instrumento instrumento;


    public Curso(String idCurso,String nombreCurso,Instrumento instrumento,Nivel nivel,int capacidad,Profesor profesor){

        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
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

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
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

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}



