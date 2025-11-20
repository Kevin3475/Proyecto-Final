package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Clase;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseGrupal;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseIndividual;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Asistencia;
import co.edu.uniquindio.poo.academiamusicuq.model.ReporteProgreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClaseController {

    private Academia academia;

    public ClaseController(Academia academia) {
        this.academia = academia;
    }

    // Metodos del crud
    public boolean registrarClase(Clase clase) {
        boolean resultado = academia.registrarClase(clase);

        // Si es una clase grupal, agregarla también al profesor
        if (resultado && clase instanceof ClaseGrupal && clase.getProfesor() != null) {
            clase.getProfesor().getListClasesGrupales().add((ClaseGrupal) clase);
        }
        // Si es una clase individual, agregarla también al profesor
        else if (resultado && clase instanceof ClaseIndividual && clase.getProfesor() != null) {
            clase.getProfesor().getListClasesIndividuales().add((ClaseIndividual) clase);
        }

        return resultado;
    }

    public List<Clase> obtenerTodasLasClases() {
        return new ArrayList<>(academia.getListClases());
    }

    public boolean actualizarClase(int id, Clase claseActualizada) {
        return academia.actualizarClase(id, claseActualizada);
    }

    public boolean eliminarClase(int id) {
        return academia.eliminarClase(id);
    }

    // Metodos de las Clases
    public List<ClaseGrupal> obtenerClasesGrupales() {
        return academia.getListClases().stream()
                .filter(clase -> clase instanceof ClaseGrupal)
                .map(clase -> (ClaseGrupal) clase)
                .collect(Collectors.toList());
    }

    public List<ClaseIndividual> obtenerClasesIndividuales() {
        return academia.getListClases().stream()
                .filter(clase -> clase instanceof ClaseIndividual)
                .map(clase -> (ClaseIndividual) clase)
                .collect(Collectors.toList());
    }

    // Metodos de los Estudiantes
    public boolean agregarEstudianteAClaseGrupal(ClaseGrupal clase, Estudiante estudiante) {
        if (clase.agregarEstudiante(estudiante)) {
            estudiante.getListClasesGrupales().add(clase);
            return true;
        }
        return false;
    }

    public boolean removerEstudianteDeClaseGrupal(ClaseGrupal clase, Estudiante estudiante) {
        if (clase.getListEstudiantes().remove(estudiante)) {
            estudiante.getListClasesGrupales().remove(clase);
            return true;
        }
        return false;
    }

    // Metodos de Asistencia
    public boolean registrarAsistencia(Clase clase, Asistencia asistencia) {
        return clase.registrarAsistencia(clase.getProfesor(), asistencia);
    }

    public List<Asistencia> obtenerAsistenciasClase(Clase clase) {
        List<Asistencia> asistencias = new ArrayList<>();
        // Recopilar asistencias de todos los estudiantes de esta clase
        if (clase instanceof ClaseGrupal) {
            ClaseGrupal grupal = (ClaseGrupal) clase;
            for (Estudiante estudiante : grupal.getListEstudiantes()) {
                asistencias.addAll(estudiante.getListAsistencias().stream()
                        .filter(a -> a.getClase().equals(clase))
                        .collect(Collectors.toList()));
            }
        } else if (clase instanceof ClaseIndividual) {
            ClaseIndividual individual = (ClaseIndividual) clase;
            if (individual.getEstudiante() != null) {
                asistencias.addAll(individual.getEstudiante().getListAsistencias().stream()
                        .filter(a -> a.getClase().equals(clase))
                        .collect(Collectors.toList()));
            }
        }
        return asistencias;
    }

    // Metodos de Evaluacion del Estudiante
    public ReporteProgreso evaluarProgresoEstudiante(Clase clase, Estudiante estudiante, float calificacion, String observaciones) {
        return clase.evaluarProgreso(estudiante, calificacion, observaciones);
    }

    public List<ReporteProgreso> obtenerEvaluacionesClase(Clase clase) {
        List<ReporteProgreso> evaluaciones = new ArrayList<>();
        // Recopilar reportes de todos los estudiantes de esta clase
        if (clase instanceof ClaseGrupal) {
            ClaseGrupal grupal = (ClaseGrupal) clase;
            for (Estudiante estudiante : grupal.getListEstudiantes()) {
                evaluaciones.addAll(estudiante.getListReportesProgresos().stream()
                        .filter(r -> r.getCurso().equals(clase.getCurso()))
                        .collect(Collectors.toList()));
            }
        } else if (clase instanceof ClaseIndividual) {
            ClaseIndividual individual = (ClaseIndividual) clase;
            if (individual.getEstudiante() != null) {
                evaluaciones.addAll(individual.getEstudiante().getListReportesProgresos().stream()
                        .filter(r -> r.getCurso().equals(clase.getCurso()))
                        .collect(Collectors.toList()));
            }
        }
        return evaluaciones;
    }

    // Metodos de Consulta
    public List<Clase> obtenerClasesPorProfesor(String idProfesor) {
        return academia.getListClases().stream()
                .filter(clase -> clase.getProfesor() != null && clase.getProfesor().getId().equals(idProfesor))
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorCurso(String idCurso) {
        return academia.getListClases().stream()
                .filter(clase -> clase.getCurso() != null && clase.getCurso().getIdCurso().equals(idCurso))
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorAula(String idAula) {
        return academia.getListClases().stream()
                .filter(clase -> clase.getAula() != null && clase.getAula().getIdAula().equals(idAula))
                .collect(Collectors.toList());
    }

    // Metodos del Cupo
    public boolean verificarCupoDisponible(ClaseGrupal clase) {
        return clase.verificarCupo();
    }

    public int obtenerCupoDisponible(ClaseGrupal clase) {
        return clase.getCupoMaximo() - clase.getListEstudiantes().size();
    }
}