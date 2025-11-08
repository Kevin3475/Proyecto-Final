package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Clase {

    protected int id;
    protected Aula aula;
    protected Profesor profesor;
    protected BloqueHorario horario;
    protected TipoClase tipoClase;
    protected Curso curso;


    public Clase(int id, Aula aula, TipoClase tipoClase, Profesor profesor, BloqueHorario horario, Curso curso) {

        this.id = id;
        this.aula = aula;
        this.tipoClase = tipoClase;
        this.profesor = profesor;
        this.horario = horario;
        this.curso = curso;


    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BloqueHorario getHorario() {
        return horario;
    }

    public void setHorario(BloqueHorario horario) {
        this.horario = horario;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public TipoClase getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(TipoClase tipoClase) {
        this.tipoClase = tipoClase;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }


    // Metodos abstractos para las hijas

    public abstract boolean registrarAsistencia(Profesor profesor,Asistencia asistencia);

    public abstract ReporteProgreso evaluarProgreso(Estudiante estudiante, float calificacion, String observaciones);



}
