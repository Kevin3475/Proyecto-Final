package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AsistenciaController {

    private Academia academia;

    public AsistenciaController(Academia academia) {
        this.academia = academia;
    }

    public List<Asistencia> buscarAsistencias(Estudiante estudiante, Curso curso, Profesor profesor,
                                              String estado, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> resultado = new ArrayList<>();


        for (Estudiante est : academia.getListEstudiantes()) {
            if (est.getListAsistencias() != null) {
                for (Asistencia asistencia : est.getListAsistencias()) {


                    boolean cumpleFiltros = true;


                    if (estudiante != null && !asistencia.getEstudiante().equals(estudiante)) {
                        cumpleFiltros = false;
                    }


                    if (curso != null && (asistencia.getClase() == null ||
                            asistencia.getClase().getCurso() == null ||
                            !asistencia.getClase().getCurso().equals(curso))) {
                        cumpleFiltros = false;
                    }


                    if (profesor != null && (asistencia.getClase() == null ||
                            asistencia.getClase().getProfesor() == null ||
                            !asistencia.getClase().getProfesor().equals(profesor))) {
                        cumpleFiltros = false;
                    }


                    if (estado != null && !estado.equals("Todos")) {
                        boolean estadoBuscado = estado.equals("Presente");
                        if (asistencia.getPresente() != estadoBuscado) {
                            cumpleFiltros = false;
                        }
                    }

                    if (fechaInicio != null && asistencia.getFecha().isBefore(fechaInicio)) {
                        cumpleFiltros = false;
                    }
                    if (fechaFin != null && asistencia.getFecha().isAfter(fechaFin)) {
                        cumpleFiltros = false;
                    }

                    if (cumpleFiltros) {
                        resultado.add(asistencia);
                    }
                }
            }
        }

        return resultado;
    }

    public int contarAsistenciasEstudiante(Estudiante estudiante, LocalDate fechaInicio, LocalDate fechaFin) {
        if (estudiante.getListAsistencias() == null) return 0;

        return (int) estudiante.getListAsistencias().stream()
                .filter(asistencia -> (fechaInicio == null || !asistencia.getFecha().isBefore(fechaInicio)) &&
                        (fechaFin == null || !asistencia.getFecha().isAfter(fechaFin)) &&
                        asistencia.getPresente())
                .count();
    }

    public int contarTotalClasesEstudiante(Estudiante estudiante, LocalDate fechaInicio, LocalDate fechaFin) {
        if (estudiante.getListAsistencias() == null) return 0;

        return (int) estudiante.getListAsistencias().stream()
                .filter(asistencia -> (fechaInicio == null || !asistencia.getFecha().isBefore(fechaInicio)) &&
                        (fechaFin == null || !asistencia.getFecha().isAfter(fechaFin)))
                .count();
    }

    public double calcularPorcentajeAsistencia(Estudiante estudiante, LocalDate fechaInicio, LocalDate fechaFin) {
        int totalClases = contarTotalClasesEstudiante(estudiante, fechaInicio, fechaFin);
        if (totalClases == 0) return 0.0;

        int asistencias = contarAsistenciasEstudiante(estudiante, fechaInicio, fechaFin);
        return (double) asistencias / totalClases * 100;
    }

    public List<Estudiante> obtenerEstudiantesConBajaAsistencia(double porcentajeMinimo, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Estudiante> estudiantesBajaAsistencia = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            double porcentaje = calcularPorcentajeAsistencia(estudiante, fechaInicio, fechaFin);
            if (porcentaje < porcentajeMinimo) {
                estudiantesBajaAsistencia.add(estudiante);
            }
        }

        return estudiantesBajaAsistencia;
    }
}
