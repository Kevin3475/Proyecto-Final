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


    //Crud del Estudiante

    public boolean  registrarEstudiante(Estudiante estudianteNuevo){
        for(Estudiante estudiante: listEstudiantes){
            if(estudiante.getId().equals(estudianteNuevo.getId())){
                return false;
            }
        }
        listEstudiantes.add(estudianteNuevo);
        return true;
    }


    public List<Estudiante> listarEstudiantes(){
        return listEstudiantes;
    }


    public boolean actualizarEstudiante(String id,Estudiante estudianteActualizado){

        for(Estudiante estudiante:listEstudiantes){
            if(estudiante.getId().equals(id)){

                estudiante.setNombre(estudianteActualizado.getNombre());
                estudiante.setEmail(estudianteActualizado.getEmail());
                estudiante.setTelefono(estudianteActualizado.getTelefono());
                estudiante.setNivel(estudianteActualizado.getNivel());

                return true;
            }
        }
        return false;
    }


    public boolean eliminarEstudiante(String id){

        for(Estudiante estudiante: listEstudiantes){
            if(estudiante.getId().equals(id)){
                listEstudiantes.remove(estudiante);

                return true;

            }
        }
        return false;
    }



    // Crud del Profesor


    public boolean registrarProfesor(Profesor profesorNuevo){

        for(Profesor profesor: listProfesores){
            if(profesor.getId().equals(profesorNuevo.getId())){
                return false;
            }
        }
        listProfesores.add(profesorNuevo);
        return true;
    }


    public List<Profesor> listarProfesores(){
        return listProfesores;
    }


    public boolean actualizarProfesor(String id, Profesor profesorActualizado){

        for(Profesor profesor: listProfesores){
            if(profesor.getId().equals(id)){

                profesor.setNombre(profesorActualizado.getNombre());
                profesor.setEmail(profesorActualizado.getEmail());
                profesor.setTelefono(profesorActualizado.getTelefono());
                profesor.setEspecialidad(profesorActualizado.getEspecialidad());
                return true;
            }
        }
        return false;
    }


    public boolean eliminarProfesor(String id){

        for(Profesor profesor: listProfesores){
            if(profesor.getId().equals(id)){

                listProfesores.remove(profesor);
                return true;
            }
        }
        return false;
    }



    // Crud del Curso


    public boolean registrarCurso(Curso cursoNuevo){

        for(Curso curso: listCursos){
            if(curso.getIdCurso().equals(cursoNuevo.getIdCurso())){
                return false;
            }
        }
        listCursos.add(cursoNuevo);
        return true;
    }


    public List<Curso> listarCursos(){
        return listCursos;
    }


    public boolean actualizarCuros(String idCurso,Curso cursoActualizado){

        for(Curso curso: listCursos){
            if(curso.getIdCurso().equals(idCurso)){

                curso.setInstrumento(cursoActualizado.getInstrumento());
                curso.setNivel(cursoActualizado.getNivel());
                curso.setCapacidad(cursoActualizado.getCapacidad());
                curso.setProfesor(cursoActualizado.getProfesor());

                return true;
            }
        }
        return false;
    }


    public boolean eliminarCurso(String idCurso){

        for(Curso curso: listCursos){
            if(curso.getIdCurso().equals(idCurso)){
                listCursos.remove(curso);
                return true;
            }
        }
        return false;
    }


    // Metodo crear y agregar curso

    public boolean crearCurso(String idCurso, Instrumento instrumento, String nivel, int capacidad, Profesor profesor) {

        if (idCurso == null || idCurso.trim().isEmpty() ||
                instrumento == null ||
                nivel == null || nivel.trim().isEmpty() ||
                capacidad <= 0 ||
                profesor == null) {
            return false;
        }

        for (Curso curso : listCursos) {
            if (curso.getIdCurso().equals(idCurso)) {
                return false;
            }
        }

        Curso nuevoCurso = new Curso(idCurso, instrumento, nivel, capacidad, profesor);
        listCursos.add(nuevoCurso);
        return true;
    }


    
    // Metodo asignar clase a el profesor

    public boolean asignarClaseAProfesor(String id, Clase clase) {

        if (id == null || id.trim().isEmpty() || clase == null) {
            return false;
        }


        for (Profesor profesor : listProfesores) {
            if (profesor.getId().equals(id)) {

                profesor.getListClases().add(clase);
                return true;
            }
        }

        return false;
    }















}









