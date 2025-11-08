package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalDate;

public class Asistencia {

    private int idAsistencia;
    private Estudiante estudiante;
    private Clase clase;
    private LocalDate fecha;
    private boolean presente;

    public Asistencia(int idAsistencia,Estudiante estudiante,Clase clase,LocalDate fecha, boolean presente){

        this.idAsistencia = idAsistencia;
        this.estudiante = estudiante;
        this.clase = clase;
        this.fecha = fecha;
        this.presente = presente;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public LocalDate getFecha(){
        return fecha;
    }
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public boolean getPresente(){
        return presente;
    }
    public void setPresente(boolean presente){
        this.presente = presente;
    }


    public boolean registrarAsistencia() {
        if (estudiante == null || clase == null || clase.getProfesor() == null) {
            return false;
        }

        estudiante.getListAsistencias().add(this);

        clase.getProfesor().getListAsistencias().add(this);

        return true;
    }





}
