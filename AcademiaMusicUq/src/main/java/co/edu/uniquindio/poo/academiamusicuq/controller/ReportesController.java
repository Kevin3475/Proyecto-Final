package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesController {

    private Academia academia;

    public ReportesController(Academia academia) {
        this.academia = academia;
    }

    // ===== MÉTODOS DE REPORTES DE PROGRESO =====
    public List<ReporteProgreso> generarReporteProgresoGeneral() {
        List<ReporteProgreso> todosLosReportes = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListReportesProgresos() != null) {
                todosLosReportes.addAll(estudiante.getListReportesProgresos());
            }
        }

        return todosLosReportes;
    }

    public List<ReporteProgreso> generarReportePorEstudiante(Estudiante estudiante) {
        if (estudiante.getListReportesProgresos() != null) {
            return new ArrayList<>(estudiante.getListReportesProgresos());
        }
        return new ArrayList<>();
    }

    public List<ReporteProgreso> generarReportePorCurso(Curso curso) {
        List<ReporteProgreso> reportesDelCurso = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListReportesProgresos() != null) {
                for (ReporteProgreso reporte : estudiante.getListReportesProgresos()) {
                    if (reporte.getCurso() != null && reporte.getCurso().equals(curso)) {
                        reportesDelCurso.add(reporte);
                    }
                }
            }
        }

        return reportesDelCurso;
    }

    public List<ReporteProgreso> generarReportePorProfesor(Profesor profesor) {
        List<ReporteProgreso> reportesDelProfesor = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListReportesProgresos() != null) {
                for (ReporteProgreso reporte : estudiante.getListReportesProgresos()) {
                    if (reporte.getProfesor() != null && reporte.getProfesor().equals(profesor)) {
                        reportesDelProfesor.add(reporte);
                    }
                }
            }
        }

        return reportesDelProfesor;
    }

    // ===== MÉTODOS DE REPORTES DE ASISTENCIA =====
    public List<Asistencia> generarReporteAsistencias(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> asistenciasFiltradas = new ArrayList<>();

        // Recopilar asistencias de todos los estudiantes
        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListAsistencias() != null) {
                for (Asistencia asistencia : estudiante.getListAsistencias()) {
                    if ((fechaInicio == null || !asistencia.getFecha().isBefore(fechaInicio)) &&
                            (fechaFin == null || !asistencia.getFecha().isAfter(fechaFin))) {
                        asistenciasFiltradas.add(asistencia);
                    }
                }
            }
        }

        return asistenciasFiltradas;
    }

    public List<Asistencia> generarReporteAsistenciasPorEstudiante(Estudiante estudiante, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> asistenciasEstudiante = new ArrayList<>();

        if (estudiante.getListAsistencias() != null) {
            for (Asistencia asistencia : estudiante.getListAsistencias()) {
                if ((fechaInicio == null || !asistencia.getFecha().isBefore(fechaInicio)) &&
                        (fechaFin == null || !asistencia.getFecha().isAfter(fechaFin))) {
                    asistenciasEstudiante.add(asistencia);
                }
            }
        }

        return asistenciasEstudiante;
    }

    // ===== MÉTODOS DE REPORTES DE MATRÍCULAS =====
    public List<Matricula> generarReporteMatriculas() {
        List<Matricula> todasLasMatriculas = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListMatriculas() != null) {
                todasLasMatriculas.addAll(estudiante.getListMatriculas());
            }
        }

        return todasLasMatriculas;
    }

    public List<Matricula> generarReporteMatriculasPorEstado(EstadoMatricula estado) {
        List<Matricula> matriculasFiltradas = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListMatriculas() != null) {
                for (Matricula matricula : estudiante.getListMatriculas()) {
                    if (matricula.getEstadoMatricula() == estado) {
                        matriculasFiltradas.add(matricula);
                    }
                }
            }
        }

        return matriculasFiltradas;
    }

    // ===== MÉTODOS DE ESTADÍSTICAS =====
    public int contarTotalEstudiantes() {
        return academia.getListEstudiantes().size();
    }

    public int contarTotalProfesores() {
        return academia.getListProfesores().size();
    }

    public int contarTotalCursos() {
        return academia.getListCursos().size();
    }

    public int contarTotalAulas() {
        return academia.getListAulas().size();
    }

    public double calcularPromedioCalificaciones() {
        List<ReporteProgreso> todosLosReportes = generarReporteProgresoGeneral();

        if (todosLosReportes.isEmpty()) {
            return 0.0;
        }

        double suma = todosLosReportes.stream()
                .mapToDouble(ReporteProgreso::getCalificacion)
                .sum();

        return suma / todosLosReportes.size();
    }

    public double calcularTasaAprobacion() {
        List<ReporteProgreso> todosLosReportes = generarReporteProgresoGeneral();

        if (todosLosReportes.isEmpty()) {
            return 0.0;
        }

        long aprobados = todosLosReportes.stream()
                .filter(ReporteProgreso::isAprobado)
                .count();

        return (double) aprobados / todosLosReportes.size() * 100;
    }

    public double calcularTasaAsistencia() {
        List<Asistencia> todasLasAsistencias = generarReporteAsistencias(null, null);

        if (todasLasAsistencias.isEmpty()) {
            return 0.0;
        }

        long presentes = todasLasAsistencias.stream()
                .filter(Asistencia::getPresente)
                .count();

        return (double) presentes / todasLasAsistencias.size() * 100;
    }

    // ===== MÉTODOS DE ANÁLISIS AVANZADO =====
    public List<Object[]> generarReporteEstudiantesPorNivel() {
        List<Object[]> resultado = new ArrayList<>();

        long basicos = academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.BASICO)
                .count();
        long intermedios = academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.INTERMEDIO)
                .count();
        long avanzados = academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.AVANZADO)
                .count();

        resultado.add(new Object[]{"Básico", basicos});
        resultado.add(new Object[]{"Intermedio", intermedios});
        resultado.add(new Object[]{"Avanzado", avanzados});

        return resultado;
    }

    public List<Object[]> generarReporteCursosPopulares() {
        List<Object[]> resultado = new ArrayList<>();

        for (Curso curso : academia.getListCursos()) {
            int cantidadEstudiantes = curso.getListEstudiantes().size();
            resultado.add(new Object[]{
                    curso.getNombreCurso(),
                    curso.getInstrumento().toString(),
                    cantidadEstudiantes
            });
        }

        // Ordenar por popularidad
        resultado.sort((a, b) -> Integer.compare((Integer)b[2], (Integer)a[2]));

        return resultado;
    }

    public List<Object[]> generarReporteRendimientoPorInstrumento() {
        List<Object[]> resultado = new ArrayList<>();

        for (Instrumento instrumento : Instrumento.values()) {
            List<ReporteProgreso> reportesInstrumento = generarReporteProgresoGeneral().stream()
                    .filter(r -> r.getCurso() != null && r.getCurso().getInstrumento() == instrumento)
                    .collect(Collectors.toList());

            if (!reportesInstrumento.isEmpty()) {
                double promedio = reportesInstrumento.stream()
                        .mapToDouble(ReporteProgreso::getCalificacion)
                        .average()
                        .orElse(0.0);

                long aprobados = reportesInstrumento.stream()
                        .filter(ReporteProgreso::isAprobado)
                        .count();

                double tasaAprobacion = (double) aprobados / reportesInstrumento.size() * 100;

                resultado.add(new Object[]{
                        instrumento.toString(),
                        reportesInstrumento.size(),
                        String.format("%.2f", promedio),
                        String.format("%.1f%%", tasaAprobacion)
                });
            }
        }

        return resultado;
    }

    // ===== MÉTODOS DE EXPORTACIÓN =====
    public String generarReporteTextoCompleto() {
        StringBuilder reporte = new StringBuilder();

        reporte.append("=== REPORTE COMPLETO ACADEMIA DE MÚSICA ===\n\n");

        // Estadísticas generales
        reporte.append("ESTADÍSTICAS GENERALES:\n");
        reporte.append("=======================\n");
        reporte.append("Total Estudiantes: ").append(contarTotalEstudiantes()).append("\n");
        reporte.append("Total Profesores: ").append(contarTotalProfesores()).append("\n");
        reporte.append("Total Cursos: ").append(contarTotalCursos()).append("\n");
        reporte.append("Total Aulas: ").append(contarTotalAulas()).append("\n");
        reporte.append("Promedio Calificaciones: ").append(String.format("%.2f", calcularPromedioCalificaciones())).append("\n");
        reporte.append("Tasa de Aprobación: ").append(String.format("%.1f%%", calcularTasaAprobacion())).append("\n");
        reporte.append("Tasa de Asistencia: ").append(String.format("%.1f%%", calcularTasaAsistencia())).append("\n\n");

        // Cursos más populares
        reporte.append("CURSOS MÁS POPULARES:\n");
        reporte.append("====================\n");
        List<Object[]> cursosPopulares = generarReporteCursosPopulares();
        for (Object[] curso : cursosPopulares) {
            reporte.append("- ").append(curso[0]).append(" (").append(curso[1]).append("): ").append(curso[2]).append(" estudiantes\n");
        }

        return reporte.toString();
    }
}