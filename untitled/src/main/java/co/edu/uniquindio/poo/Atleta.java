package co.edu.uniquindio.poo;

public class Atleta extends Persona {

    private String experienciaDeporte;
    private Equipo equipo;

    public Atleta(String nombre,String fechaNacimiento,String nacionalidad,String experienciaDeporte,Equipo equipo){
        super(nombre,fechaNacimiento,nacionalidad);

        this.experienciaDeporte = experienciaDeporte;
        this.equipo = equipo = equipo;
    }

    public String getExperienciaDeporte(){
        return experienciaDeporte;
    }
    public void setExperienciaDeporte(String experienciaDeporte){
        this.experienciaDeporte = experienciaDeporte;
    }

    public Equipo getEquipo(){
        return equipo;
    }
    public void setEquipo(Equipo equipo){
        this.equipo = equipo;
    }


}
