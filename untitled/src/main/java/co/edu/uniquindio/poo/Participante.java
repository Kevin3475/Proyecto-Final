package co.edu.uniquindio.poo;

public class Participante {

    private int edad;
    private String pais;
    private Persona persona;
    private RolParticipante rolParticipante;

    public Participante(int edad,String pais,Persona persona,RolParticipante rolParticipante){

        this.edad = edad;
        this.pais = pais;
        this.persona = persona;
        this.rolParticipante = rolParticipante;

    }

    public int getEdad(){
        return edad;
    }
    public void setEdad(int edad){
        this.edad = edad;
    }

    public String getPais(){
        return pais;
    }
    public void setPais(String pais){
        this.pais = pais;
    }

    public Persona getPersona(){
        return persona;
    }
    public void setPersona(Persona persona){
        this.persona = persona;
    }

    public RolParticipante getRolParticipante(){
        return rolParticipante;
    }
    public void setRolParticipante(RolParticipante rolParticipante){
        this.rolParticipante = rolParticipante;
    }


}
