package co.edu.uniquindio.poo;

public class Entrenador extends Persona{

    private String especialidad;

    public Entrenador (String nombre,String fechaNacimiento,String nacionalidad,String especialidad){
        super(nombre,fechaNacimiento,nacionalidad);

        this.especialidad = especialidad;
    }

    public String getEspecialidad(){
        return especialidad;
    }
    public void setEspecialidad(String especialidad){
        this.especialidad = especialidad;
    }

}
