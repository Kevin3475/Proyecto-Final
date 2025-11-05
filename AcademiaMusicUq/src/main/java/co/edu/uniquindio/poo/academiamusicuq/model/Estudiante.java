package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements IReportable{

    private Nivel nivel;
    private List<Matricula> listMatriculas;
    private List<ClaseGrupal> listClasesGrupales;
    private List<ClaseIndividual> listClasesIndividuales;
    private List<ReporteProgreso> listReportesProgresos;
    private List<Asistencia> listAsistencias;
    private boolean activo;


    public Estudiante (String id,String nombre,String apellido,String email,String telefono,Nivel nivel,boolean activo){
        super(id,nombre,apellido,email,telefono);

        this.nivel = nivel;
        this.listMatriculas = new ArrayList<>();
        this.listClasesGrupales = new ArrayList<>();
        this.listClasesIndividuales = new ArrayList<>();
        this.listReportesProgresos = new ArrayList<>();
        this.listAsistencias = new ArrayList<>();
        this.activo = activo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Matricula> getListMatriculas() {
        return listMatriculas;
    }

    public void setListMatriculas(List<Matricula> listMatriculas) {
        this.listMatriculas = listMatriculas;
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

    public List<ReporteProgreso> getListReportesProgresos() {
        return listReportesProgresos;
    }

    public void setListReportesProgresos(List<ReporteProgreso> listReportesProgresos) {
        this.listReportesProgresos = listReportesProgresos;
    }

    public List<Asistencia> getListAsistencias() {
        return listAsistencias;
    }

    public void setListAsistencias(List<Asistencia> listAsistencias) {
        this.listAsistencias = listAsistencias;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean inscribirMatricula(Matricula matricula){
        return listMatriculas.add(matricula);
    }
    public boolean inscribirClaseGrupal(ClaseGrupal clase){
        return listClasesGrupales.add(clase);
    }
    public boolean agregarClaseIndividual(ClaseIndividual clase){
        return listClasesIndividuales.add(clase);
    }
    public void registrarAsistencia(Asistencia asistencia){
        listAsistencias.add(asistencia);
    }
    @Override
    public ReporteProgreso generarReporteProgreso(){
        return new ReporteProgreso();
    }

}
