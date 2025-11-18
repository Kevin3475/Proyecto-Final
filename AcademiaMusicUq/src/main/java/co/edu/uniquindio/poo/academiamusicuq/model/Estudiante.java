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

    // getters and setters

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

    // Metodos Estudiante

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

    // Metodo reporteProgreso

    @Override
    public ReporteProgreso generarReporteProgreso(){
        return new ReporteProgreso(
                listReportesProgresos.size() + 1,
                this,
                null,
                null,
                java.time.LocalDate.now(),
                "Sin observaciones",
                0.0f,
                false
        );
    }

    //Metodo ConsultarHorario

    public String consultarHorario() {
        StringBuilder sb = new StringBuilder();
        sb.append("Horario del estudiante: ")
                .append(getNombre())
                .append(" ")
                .append(getApellido())
                .append("\n")
                .append("------------------------------------------\n");


        if (listClasesGrupales.isEmpty() && listClasesIndividuales.isEmpty()) {
            sb.append("No hay clases asignadas.\n");
            return sb.toString();
        }


        for (ClaseGrupal clase : listClasesGrupales) {
            BloqueHorario bh = clase.getHorario();
            sb.append("Clase grupal: ").append(clase.getCurso().getNombreCurso())
                    .append(" | Profesor: ").append(clase.getProfesor().getNombre())
                    .append(" | Día: ").append(bh.getDia())
                    .append(" | ").append(bh.getHoraInicio()).append(" - ").append(bh.getHoraFin())
                    .append("\n");
        }


        for (ClaseIndividual clase : listClasesIndividuales) {
            BloqueHorario bh = clase.getHorario();
            sb.append("Clase individual: ").append(clase.getCurso().getNombreCurso())
                    .append(" | Profesor: ").append(clase.getProfesor().getNombre())
                    .append(" | Día: ").append(bh.getDia())
                    .append(" | ").append(bh.getHoraInicio()).append(" - ").append(bh.getHoraFin())
                    .append("\n");
        }

        return sb.toString();
    }


    //Metodo InscribirCurso

    public boolean inscribirCurso() {

        if (listMatriculas == null || listMatriculas.isEmpty()) {
            return false;
        }

        Matricula matricula = listMatriculas.get(listMatriculas.size() - 1);

        for (Matricula m : listMatriculas) {
            if (m.getCurso().equals(matricula.getCurso())) {
                return false;
            }
        }

        listMatriculas.add(matricula);
        return true;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido() + " (" + getEmail() + ")";
    }

}