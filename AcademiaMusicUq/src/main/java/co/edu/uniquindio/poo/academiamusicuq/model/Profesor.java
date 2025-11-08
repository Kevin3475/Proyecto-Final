package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona implements IAgendable,IEvaluable{

    private String especialidad;
    private Instrumento instrumento;
    private boolean activo;
    private List<Clase> listClases;
    private List<ClaseGrupal> listClasesGrupales;
    private List<ClaseIndividual> listClasesIndividuales;
    private List<ComentarioFormativo> listComentariosFormativos;
    private List<BloqueHorario> listBloqueHorarios;
    private List<Asistencia> listAsistencias;

    public Profesor(String id,String nombre,String apellido,String email,String telefono,String especialidad,Instrumento instrumento,boolean activo){
        super(id,nombre,apellido,email,telefono);

        this.especialidad = especialidad;
        this.instrumento = instrumento;
        this.activo = activo;
        this.listClases = new ArrayList<>();
        this.listClasesGrupales = new ArrayList<>();
        this.listClasesIndividuales = new ArrayList<>();
        this.listComentariosFormativos = new ArrayList<>();
        this.listBloqueHorarios = new ArrayList<>();
        this.listAsistencias = new ArrayList<>();


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

    public List<Asistencia> getListAsistencias() {
        return listAsistencias;
    }

    public void setListAsistencias(List<Asistencia> listAsistencias) {
        this.listAsistencias = listAsistencias;
    }

    public List<ClaseGrupal> getListClasesGrupales() {
        return listClasesGrupales;
    }

    public void setListClasesGrupales(List<ClaseGrupal> listClasesGrupales) {
        this.listClasesGrupales = listClasesGrupales;
    }

    public List<ClaseIndividual> getListClasesIndividuales() {
        return listClasesIndividuales;
    }

    public void setListClasesIndividuales(List<ClaseIndividual> listClasesIndividuales) {
        this.listClasesIndividuales = listClasesIndividuales;
    }

    // metodo de la interface IAgendable


    @Override
    public boolean asignarHorario(BloqueHorario nuevoBloque){
        for(BloqueHorario existente: listBloqueHorarios){
            if(existente.conflictoCon(nuevoBloque)){
                return false;
            }
        }
        listBloqueHorarios.add(nuevoBloque);
        return true;
    }


    // metodo agregarClase

    public boolean agregarClase(Clase clase){
        return listClases.add(clase);
    }

    // metodo generarComentario

    public ComentarioFormativo generarComentario(Estudiante estudiante, Curso curso, String contenido){
        ComentarioFormativo comentario = new ComentarioFormativo(listComentariosFormativos.size()+1,contenido,java.time.LocalDate.now(),estudiante,curso);
        listComentariosFormativos.add(comentario);
        return comentario;
    }



    // metodo crearClaseGrupal

    public boolean crearClaseGrupal(ClaseGrupal clase) {

        if (clase == null) {
            return false;
        }

        for (ClaseGrupal existente : listClasesGrupales) {
            if (existente.getHorario().conflictoCon(clase.getHorario())) {
                return false;
            }
        }

        listClasesGrupales.add(clase);
        return true;
    }



    // Metodo registrarAsistencia

    public boolean registrarAsistencia(Asistencia asistencia) {

        if (asistencia == null) {
            return false;
        }

        Clase claseAsociada = asistencia.getClase();


        boolean claseEncontrada = false;
        for (Clase clase : listClases) {
            if (clase.equals(claseAsociada)) {
                claseEncontrada = true;
                break;
            }
        }

        if (!claseEncontrada) {
            return false;
        }

        listAsistencias.add(asistencia);
        return true;
    }


    // Metodo de la interface IEvaluable

    @Override

    public ReporteProgreso valorarProgreso(Estudiante estudiante, Curso curso, float calificacion, String observaciones) {
        if (estudiante == null || curso == null) {
            return null;
        }

        boolean aprobado = calificacion >= 3.0;

        ReporteProgreso reporte = new ReporteProgreso(
                estudiante.getListReportesProgresos().size() + 1,
                estudiante,
                curso,
                this,
                java.time.LocalDate.now(),
                observaciones,
                calificacion,
                aprobado
        );

        estudiante.getListReportesProgresos().add(reporte);

        return reporte;
    }



}











































