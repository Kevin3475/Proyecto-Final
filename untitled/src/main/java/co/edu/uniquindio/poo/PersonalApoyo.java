package co.edu.uniquindio.poo;

public class PersonalApoyo extends Persona {

    private String experienciaLaboral;

    public PersonalApoyo(String nombre,String fechaNacimiento,String nacionalidad,String experienciaLaboral){
        super (nombre,fechaNacimiento,nacionalidad);

        this.experienciaLaboral = experienciaLaboral;
    }

    public String getExperienciaLaboral(){
        return experienciaLaboral;
    }

    public void setExperienciaLaboral(String experienciaLaboral){
        this.experienciaLaboral = experienciaLaboral;
    }

}
