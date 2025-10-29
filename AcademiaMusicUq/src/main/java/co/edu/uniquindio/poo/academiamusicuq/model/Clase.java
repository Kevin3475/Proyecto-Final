package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Clase {

    private int id;
    private Aula aula;
    private Profesor profesor;
    private BloqueHorario horario;
    private TipoClase tipoClase;



    public Clase(int id,Aula aula,TipoClase tipoClase,Profesor profesor,BloqueHorario horario){

        this.id = id;
        this.aula = aula;
        this.tipoClase = tipoClase;
        this.profesor = profesor;
        this.horario = horario;


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
}
