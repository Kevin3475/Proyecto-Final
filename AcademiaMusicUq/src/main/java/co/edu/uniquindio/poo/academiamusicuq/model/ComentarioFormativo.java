package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ComentarioFormativo {

    private int idComentario;
    private String contenido;
    private LocalDate fecha;
    private Estudiante estudiante;
    private Curso curso;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public ComentarioFormativo(int idComentario,String contenido,LocalDate fecha,Estudiante estudiante,Curso curso){

        this.idComentario = idComentario;
        this.contenido = contenido;
        this.fecha = fecha;
        this.estudiante = estudiante;
        this.curso = curso;

    }


    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "Comentario #" + idComentario + " - " + fecha.format(dateFormatter);
    }

    // los metodos los puse en AdministradorAcademico
}