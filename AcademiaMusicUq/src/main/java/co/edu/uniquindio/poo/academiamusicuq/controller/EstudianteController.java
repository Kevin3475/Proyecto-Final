package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Curso;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseGrupal;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseIndividual;
import co.edu.uniquindio.poo.academiamusicuq.model.Matricula;
import co.edu.uniquindio.poo.academiamusicuq.model.EstadoMatricula;
import co.edu.uniquindio.poo.academiamusicuq.model.ReporteProgreso;

import java.util.List;

public class EstudianteController {

    private Academia academia;

    public EstudianteController(Academia academia) {
        this.academia = academia;
    }

    // Metodos del Crud
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

    // Metodos de Gestion
    public boolean inscribirEstudianteEnCurso(Estudiante estudiante, Curso curso) {
        // Verificar que el estudiante tenga el nivel requerido
        if (curso.verificarNivelEstudiante(estudiante)) {
            // Crear matrícula con los 6 parámetros requeridos
            Matricula matricula = new Matricula(
                    estudiante.getListMatriculas().size() + 1, // idMatricula
                    estudiante, // estudiante
                    curso, // curso
                    java.time.LocalDate.now(), // fechaInscripcion
                    EstadoMatricula.ACTIVA, // estadoMatricula - por defecto ACTIVA
                    false // certificadoEmitido - por defecto false
            );
            return estudiante.inscribirMatricula(matricula);
        }
        return false;
    }

    public boolean inscribirEnClaseGrupal(Estudiante estudiante, ClaseGrupal clase) {
        if (clase.verificarCupo() && !clase.getListEstudiantes().contains(estudiante)) {
            boolean resultadoClase = clase.agregarEstudiante(estudiante);
            boolean resultadoEstudiante = estudiante.inscribirClaseGrupal(clase);
            return resultadoClase && resultadoEstudiante;
        }
        return false;
    }

    public boolean agregarClaseIndividual(Estudiante estudiante, ClaseIndividual clase) {
        // Para clases individuales, asignar el estudiante a la clase
        if (clase.getEstudiante() == null) {
            clase.setEstudiante(estudiante);
            boolean resultadoEstudiante = estudiante.agregarClaseIndividual(clase);
            boolean resultadoProfesor = clase.getProfesor().getListClasesIndividuales().add(clase);
            return resultadoEstudiante && resultadoProfesor;
        }
        return false;
    }

    // Metodos de Consulta
    public String consultarHorarioEstudiante(Estudiante estudiante) {
        return estudiante.consultarHorario();
    }

    // Metodos del Reporte
    public ReporteProgreso generarReporteProgreso(Estudiante estudiante) {
        return estudiante.generarReporteProgreso();
    }
}