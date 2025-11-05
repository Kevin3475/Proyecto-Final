package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona implements IAgendable,IEvaluable{

    private String especialidad;
    private Instrumento instrumento;
    private boolean activo;
    private List<Clase> listClases;
    private List<ComentarioFormativo> listComentariosFormativos;
    private  List<BloqueHorario> listBloqueHorarios;

    public Profesor(String id,String nombre,String apellido,String email,String telefono,String especialidad,Instrumento instrumento,boolean activo){
        super(id,nombre,apellido,email,telefono);

        this.especialidad = especialidad;
        this.instrumento = instrumento;
        this.activo = activo;
        this.listClases = new ArrayList<>();
        this.listComentariosFormativos = new ArrayList<>();
        this.listBloqueHorarios = new ArrayList<>();

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

    public Instrumento getInstrumento(){
        return instrumento;
    }
    public void setInstrumento(Instrumento instrumento){
        this.instrumento = instrumento;
    }

    public List<ComentarioFormativo> getListComentariosFormativos() {
        return listComentariosFormativos;
    }

    public void setListComentariosFormativos(List<ComentarioFormativo> listComentariosFormativos) {
        this.listComentariosFormativos = listComentariosFormativos;
    }

    public List<BloqueHorario> getListBloqueHorarios() {
        return listBloqueHorarios;
    }

    public void setListBloqueHorarios(List<BloqueHorario> listBloqueHorarios) {
        this.listBloqueHorarios = listBloqueHorarios;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public boolean asignarHorario(BloqueHorario nuevoBloque){
        for(BloqueHorario existente: listBloqueHorarios){
            if(existente.conflictoCon(nuevoBloque)){
                return false;
            }
        }
        listBloqueHorarios.add(nuevoBloque);
        return true;
    }
    public boolean agregarClase(Clase clase){
        return listClases.add(clase);
    }
    public ComentarioFormativo generarComentario(Estudiante estudiante, Curso curso, String contenido){
        ComentarioFormativo comentario = new ComentarioFormativo(listComentariosFormativos.size()+1,contenido,java.time.LocalDate.now(),estudiante,curso);
        listComentariosFormativos.add(comentario);
        return comentario;
    }
}











































