package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalTime;

public class BloqueHorario {

    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public BloqueHorario(String dia,LocalTime horaInicio,LocalTime horaFin){

        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;

    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }


    // Meetodo conflictoCon


    public boolean conflictoCon(BloqueHorario otro) {

        if (!this.dia.equals(otro.dia)) {
            return false;
        }

        return this.horaInicio.isBefore(otro.horaFin) && otro.horaInicio.isBefore(this.horaFin);
    }



}


















