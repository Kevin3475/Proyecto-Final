package co.edu.uniquindio.poo.academiamusicuq.model;

import java.time.LocalDate;
import java.util.List;

public class Matricula {

    private int idMatricula;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechaInscripcion;
    private EstadoMatricula estadoMatricula;
    private List<ClaseGrupal> listClasesGrupales;
    private List<ReporteProgreso> listReporteProgreso;
    private List<Asistencia> listAsistencia;
    private boolean certificadoEmitido;


    public Matricula(int idMatricula,Estudiante estudiante,Curso curso,LocalDate fechaInscripcion,EstadoMatricula estadoMatricula,boolean certificadoEmitido){

        this.idMatricula = idMatricula;
        this.estudiante = estudiante;
        this.curso = curso;
        this.fechaInscripcion = fechaInscripcion;
        this.estadoMatricula = estadoMatricula;
        this.certificadoEmitido = certificadoEmitido;

    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoMatricula getEstadoMatricula() {
        return estadoMatricula;
    }

    public void setEstadoMatricula(EstadoMatricula estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    public List<ClaseGrupal> getListClasesGrupales() {
        return listClasesGrupales;
    }

    public void setListClasesGrupales(List<ClaseGrupal> listClasesGrupales) {
        this.listClasesGrupales = listClasesGrupales;
    }

    public List<ReporteProgreso> getListReporteProgreso() {
        return listReporteProgreso;
    }

    public void setListReporteProgreso(List<ReporteProgreso> listReporteProgreso) {
        this.listReporteProgreso = listReporteProgreso;
    }

    public List<Asistencia> getListAsistencia() {
        return listAsistencia;
    }

    public void setListAsistencia(List<Asistencia> listAsistencia) {
        this.listAsistencia = listAsistencia;
    }

    public boolean isCertificadoEmitido() {
        return certificadoEmitido;
    }

    public void setCertificadoEmitido(boolean certificadoEmitido) {
        this.certificadoEmitido = certificadoEmitido;
    }
}
