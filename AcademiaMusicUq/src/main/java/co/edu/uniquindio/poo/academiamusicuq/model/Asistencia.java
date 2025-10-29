package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalDate;

public class Asistencia {

    private LocalDate fecha;
    private boolean presente;

    public Asistencia(LocalDate fecha, boolean presente){

        this.fecha = fecha;
        this.presente = presente;
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
}
