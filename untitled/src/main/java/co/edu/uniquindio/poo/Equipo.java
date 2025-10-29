package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String nombre;
    private String pais;
    private List<Atleta> listAtletas;

    public Equipo(String nombre,String pais){

        this.nombre = nombre;
        this.pais = pais;
        this.listAtletas = new ArrayList<>();

    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getPais(){
        return pais;
    }
    public void setPais(String pais){
        this.pais = pais;
    }

    public List<Atleta> getListAtletas(){
        return listAtletas;
    }
    public void setListAtletas(List<Atleta> listAtletas){
        this.listAtletas = listAtletas;
    }

}
