package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona implements IAgendable{

    private String especialidad;
    private List<Clase> listClases;

    public Profesor(String id,String nombre,String apellido,String documento,String email,String telefono,String especialidad){
        super(id,nombre,apellido,documento,email,telefono);

        this.especialidad = especialidad;
        this.listClases = new ArrayList<>();

    }

    public String getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

    public List<Clase> getListClases() {
        return listClases;
    }

    public void setListClases(List<Clase> listClases) {
        this.listClases = listClases;
    }
}











































