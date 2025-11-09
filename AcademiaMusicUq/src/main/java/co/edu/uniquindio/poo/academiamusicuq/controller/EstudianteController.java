package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Curso;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseGrupal;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseIndividual;

import java.util.List;

public class EstudianteController {

    private Academia academia;

    public EstudianteController(Academia academia) {
        this.academia = academia;
    }

    // ===== MÉTODOS CRUD BÁSICOS =====
    public boolean registrarEstudiante(Estudiante estudiante) {
        return academia.registrarEstudiante(estudiante);
    }

    public List<Estudiante> obtenerEstudiantes() {
        return academia.listarEstudiantes();
    }

    public boolean actualizarEstudiante(String id, Estudiante estudiante) {
        return academia.actualizarEstudiante(id, estudiante);
    }

    public boolean eliminarEstudiante(String id) {
        return academia.eliminarEstudiante(id);
    }

    // ===== MÉTODOS DE GESTIÓN ACADÉMICA =====
    public boolean inscribirEstudianteEnCurso(Estudiante estudiante, Curso curso) {
        // Verificar que el estudiante tenga el nivel requerido
        if (curso.verificarNivelEstudiante(estudiante)) {
            // Lógica para inscribir estudiante en curso
            // Esto se implementará cuando tengamos el sistema de matrículas
            return true;
        }
        return false;
    }

    public boolean inscribirEnClaseGrupal(Estudiante estudiante, ClaseGrupal clase) {
        if (clase.verificarCupo()) {
            return estudiante.inscribirClaseGrupal(clase) && clase.agregarEstudiante(estudiante);
        }
        return false;
    }

    public boolean agregarClaseIndividual(Estudiante estudiante, ClaseIndividual clase) {
        return estudiante.agregarClaseIndividual(clase);
    }

    // ===== MÉTODOS DE CONSULTA =====
    public String consultarHorarioEstudiante(Estudiante estudiante) {
        return estudiante.consultarHorario();
    }

    // ===== MÉTODOS DE PROGRESO =====
    public void generarReporteProgreso(Estudiante estudiante) {
        estudiante.generarReporteProgreso();
    }
}