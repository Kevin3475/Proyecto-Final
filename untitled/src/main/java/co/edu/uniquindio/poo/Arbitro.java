package co.edu.uniquindio.poo;

public class Arbitro extends Persona{

    private String nivelExperiencia;

    public Arbitro(String nombre,String fechaNacimiento,String nacionalidad,String nivelExperiencia){
        super(nombre,fechaNacimiento,nacionalidad);

        this.nivelExperiencia = nivelExperiencia;
    }

    public String getNivelExperiencia(){
        return nivelExperiencia;
    }
    public void setNivelExperiencia(String nivelExperiencia){
        this.nivelExperiencia = nivelExperiencia;
    }

}
