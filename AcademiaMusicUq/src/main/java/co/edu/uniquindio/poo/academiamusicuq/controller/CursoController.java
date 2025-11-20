package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Curso;
import co.edu.uniquindio.poo.academiamusicuq.model.Clase;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Profesor;

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

    // Metodos de Gestion
    public boolean agregarClaseACurso(Curso curso, Clase clase) {
        if (curso != null && clase != null) {
            return curso.agregarClase(clase);
        }
        return false;
    }

    public boolean removerClaseDeCurso(Curso curso, Clase clase) {
        if (curso != null && clase != null) {
            return curso.getListClases().remove(clase);
        }
        return false;
    }

    public boolean agregarEstudianteACurso(Curso curso, Estudiante estudiante) {
        if (curso != null && estudiante != null) {
            // Verificar que el estudiante tenga el nivel adecuado
            if (curso.verificarNivelEstudiante(estudiante)) {
                // Verificar que no exceda la capacidad
                if (curso.getListEstudiantes().size() < curso.getCapacidad()) {
                    // Verificar que el estudiante no esté ya inscrito
                    if (!curso.getListEstudiantes().contains(estudiante)) {
                        return curso.getListEstudiantes().add(estudiante);
                    }
                }
            }
        }
        return false;
    }

    public boolean removerEstudianteDeCurso(Curso curso, Estudiante estudiante) {
        if (curso != null && estudiante != null) {
            return curso.getListEstudiantes().remove(estudiante);
        }
        return false;
    }

    // Metodos de Consulta
    public boolean verificarNivelEstudiante(Curso curso, Estudiante estudiante) {
        if (curso != null && estudiante != null) {
            return curso.verificarNivelEstudiante(estudiante);
        }
        return false;
    }

    public List<Estudiante> obtenerEstudiantesInscritos(Curso curso) {
        if (curso != null) {
            return curso.getListEstudiantes();
        }
        return null;
    }

    public List<Clase> obtenerClasesCurso(Curso curso) {
        if (curso != null) {
            return curso.getListClases();
        }
        return null;
    }

    // Metodos de Profesores
    public List<Profesor> obtenerProfesoresDisponibles() {
        return academia.getListProfesores();
    }
}