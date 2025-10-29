package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class Academia {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Estudiante> listEstudiantes;
    private List<Curso> listCursos;
    private List<Aula> listAulas;
    private List<Profesor> listProfesores;
    private AdministradorAcademico administrador;

    public Academia (String nombre,String direccion,String telefono,AdministradorAcademico administrador){

        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.listEstudiantes = new ArrayList<>();
        this.listCursos = new ArrayList<>();
        this.listAulas = new ArrayList<>();
        this.listProfesores = new ArrayList<>();
        this.administrador = administrador;


    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(){
        this.nombre = nombre;
    }
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(){
        this.direccion = direccion;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(){
        this.telefono = telefono;
    }
    public List<Estudiante> getListEstudiantes(){
        return listEstudiantes;
    }
    public void setListEstudiantes(List<Estudiante> listEstudiantes){
        this.listEstudiantes = new ArrayList<>();
    }
    public List<Profesor> getListProfesores(){
        return listProfesores;
    }
    public void setListProfesores(List<Profesor> listProfesores){
        this.listProfesores = new ArrayList<>();
    }
    public List<Aula> getListAulas(){
        return listAulas;
    }
    public void setListAulas(List<Aula> listAulas){
        this.listAulas = new ArrayList<>();
    }
    public List<Curso> getListCursos(){
        return listCursos;
    }
    public void setListCursos(List<Curso> listCursos){
        this.listCursos = listCursos;
    }

    public AdministradorAcademico getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdministradorAcademico administrador) {
        this.administrador = administrador;
    }
}
