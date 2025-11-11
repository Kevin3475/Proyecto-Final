package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Curso;
import co.edu.uniquindio.poo.academiamusicuq.model.Clase;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;

import java.util.List;

public class CursoController {

    private Academia academia;

    public CursoController(Academia academia) {
        this.academia = academia;
    }

    // Metodos del Crud
    public boolean registrarCurso(Curso curso) {
        return academia.registrarCurso(curso);
    }

    public List<Curso> obtenerCursos() {
        return academia.listarCursos();
    }

    public boolean actualizarCurso(String idCurso, Curso curso) {
        return academia.actualizarCurso(idCurso, curso);
    }

    public boolean eliminarCurso(String idCurso) {
        return academia.eliminarCurso(idCurso);
    }

    // Metodos de Clase
    public boolean agregarClaseACurso(Curso curso, Clase clase) {
        return curso.agregarClase(clase);
    }

    public boolean removerClaseDeCurso(Curso curso, Clase clase) {
        return curso.getListClases().remove(clase);
    }

    // Metodos del Estudiante
    public boolean agregarEstudianteACurso(Curso curso, Estudiante estudiante) {
        // Verificar nivel primero
        if (!curso.verificarNivelEstudiante(estudiante)) {
            return false;
        }

        // Verificar capacidad
        if (curso.getListEstudiantes().size() >= curso.getCapacidad()) {
            return false;
        }

        return curso.getListEstudiantes().add(estudiante);
    }

    public boolean removerEstudianteDeCurso(Curso curso, Estudiante estudiante) {
        return curso.getListEstudiantes().remove(estudiante);
    }

    // Metodos de Consulta
    public boolean verificarNivelEstudiante(Curso curso, Estudiante estudiante) {
        return curso.verificarNivelEstudiante(estudiante);
    }

    public List<Clase> obtenerClasesCurso(Curso curso) {
        return curso.getListClases();
    }

    public List<Estudiante> obtenerEstudiantesCurso(Curso curso) {
        return curso.getListEstudiantes();
    }
}