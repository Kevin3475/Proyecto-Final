package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalDate;

public class ReporteProgreso {

    private int idReporte;
    private Estudiante estudiante;
    private Curso curso;
    private Profesor profesor;
    private LocalDate fecha;
    private String observaciones;
    private float calificacion;
    private boolean aprobado;

    public ReporteProgreso(int idReporte, Estudiante estudiante,Curso curso,Profesor profesor, LocalDate fecha, String observaciones,float calificacion,boolean aprobado){

        this.idReporte = idReporte;
        this.estudiante = estudiante;
        this.curso = curso;
        this.profesor = profesor;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.calificacion = calificacion;
        this.aprobado = aprobado;

    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
}
