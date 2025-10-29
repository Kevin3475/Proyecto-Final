package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Horario {

    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private List<Asistencia> listAsistencias;

    public Horario(String dia,LocalTime horaInicio,LocalTime horafin){

        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.listAsistencias = new ArrayList<>();

    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public List<Asistencia> getListAsistencias() {
        return listAsistencias;
    }

    public void setListAsistencias(List<Asistencia> listAsistencias) {
        this.listAsistencias = listAsistencias;
    }
}



















