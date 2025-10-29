package co.edu.uniquindio.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private String nombre;
    private LocalDate fechaInicio;
    private String ubicacion;
    private String deporte;
    private List<Equipo> listEquipos;
    private TipoEvento tipoEvento;

    public Evento(String nombre,LocalDate fechaInicio,String ubicacion,String deporte,TipoEvento tipoEvento){

        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.ubicacion = ubicacion;
        this.deporte = deporte;
        this.listEquipos = new ArrayList<>();
        this.tipoEvento = tipoEvento;



    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public String getUbicacion(){
        return ubicacion;
    }
    public void setUbicacion(String ubicacion){
        this.ubicacion = ubicacion;
    }

    public String getDeporte(){
        return deporte;
    }
    public void setDeporte(String deporte){
        this.deporte = deporte;
    }

    public List<Equipo> getListEquipos(){
        return listEquipos;
    }
    public void setListEquipos(List<Equipo> listEquipos){
        this.listEquipos = listEquipos;
    }

    public TipoEvento getTipoEvento(){
        return tipoEvento;
    }
    public void setTipoEvento(TipoEvento tipoEvento){
        this.tipoEvento = tipoEvento;
    }



}
