package co.edu.uniquindio.poo.academiamusicuq.model;

public class ClaseIndividual extends Clase{

    private String objetivoPersonal;
    private Estudiante estudiante;

    public ClaseIndividual(int id,String fecha,Aula aula,String tipo,Profesor profesor,BloqueHorario horario,String objetivoPersonal,Estudiante estudiante){
        super(id,fecha,aula,tipo,profesor,horario);

        this.objetivoPersonal = objetivoPersonal;
        this.estudiante = estudiante;


    }

    public String getObjetivoPersonal() {
        return objetivoPersonal;
    }

    public void setObjetivoPersonal(String objetivoPersonal) {
        this.objetivoPersonal = objetivoPersonal;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
