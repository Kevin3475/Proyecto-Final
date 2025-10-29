package co.edu.uniquindio.poo.academiamusicuq.model;

public class Profesor extends Persona implements IAgendable{

    private String especialidad;

    public Profesor(String id,String nombre,String apellido,String documento,String email,String telefono,String especialidad){
        super(id,nombre,apellido,documento,email,telefono);

        this.especialidad = especialidad;

    }

    public String getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

}











































