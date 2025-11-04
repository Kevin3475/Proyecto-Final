package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class AdministradorAcademico extends Persona{


    private boolean activo;
    private List<Profesor> listProfesores;
    private List<Estudiante> listEstudiantes;
    private List<Curso> listCursos;
    private List<Aula> listAulas;
    private List<ReporteProgreso> listReportesProgresos;
    private List<ComentarioFormativo> listComentariosFormativos;

    public AdministradorAcademico(String id,String nombre,String apellido,String documento,String email,String telefono,boolean activo){
        super(id,nombre,apellido,email,telefono);

        this.activo = activo;
        this.listProfesores = new ArrayList<>();
        this.listEstudiantes = new ArrayList<>();
        this.listCursos = new ArrayList<>();
        this.listAulas = new ArrayList<>();
        this.listReportesProgresos = new ArrayList<>();
        this.listComentariosFormativos = new ArrayList<>();


    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Profesor> getListProfesores() {
        return listProfesores;
    }

    public void setListProfesores(List<Profesor> listProfesores) {
        this.listProfesores = listProfesores;
    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public List<Curso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }

    public List<Aula> getListAulas() {
        return listAulas;
    }

    public void setListAulas(List<Aula> listAulas) {
        this.listAulas = listAulas;
    }

    public List<ReporteProgreso> getListReportesProgresos() {
        return listReportesProgresos;
    }

    public void setListReportesProgresos(List<ReporteProgreso> listReportesProgresos) {
        this.listReportesProgresos = listReportesProgresos;
    }

    public List<ComentarioFormativo> getListComentariosFormativos() {
        return listComentariosFormativos;
    }

    public void setListComentariosFormativos(List<ComentarioFormativo> listComentariosFormativos) {
        this.listComentariosFormativos = listComentariosFormativos;
    }
}
