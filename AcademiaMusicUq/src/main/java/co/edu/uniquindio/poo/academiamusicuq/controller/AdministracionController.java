package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.ReporteProgreso;
import co.edu.uniquindio.poo.academiamusicuq.model.ComentarioFormativo;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Matricula;
import co.edu.uniquindio.poo.academiamusicuq.model.EstadoMatricula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministracionController {

    private Academia academia;
    private List<ComentarioFormativo> listaComentarios;

    public AdministracionController(Academia academia) {
        this.academia = academia;
        this.listaComentarios = new ArrayList<>();
    }


    public List<ReporteProgreso> generarReporte(List<Estudiante> estudiantes, String tipoReporte, LocalDate fechaInicio, LocalDate fechaFin) {
        List<ReporteProgreso> reportes = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getListReportesProgresos() != null) {
                for (ReporteProgreso reporte : estudiante.getListReportesProgresos()) {
                    if ((fechaInicio == null || !reporte.getFecha().isBefore(fechaInicio)) &&
                            (fechaFin == null || !reporte.getFecha().isAfter(fechaFin))) {

                        switch (tipoReporte) {
                            case "Todos los reportes":
                                reportes.add(reporte);
                                break;
                            case "Reportes aprobados":
                                if (reporte.isAprobado()) {
                                    reportes.add(reporte);
                                }
                                break;
                            case "Reportes reprobados":
                                if (!reporte.isAprobado()) {
                                    reportes.add(reporte);
                                }
                                break;
                            default:
                                reportes.add(reporte);
                                break;
                        }
                    }
                }
            }
        }

        return reportes;
    }

    public boolean agregarComentarioFormativo(ComentarioFormativo comentario) {
        return listaComentarios.add(comentario);
    }

    public List<ComentarioFormativo> listarComentariosEstudiante(Estudiante estudiante) {
        return listaComentarios.stream()
                .filter(comentario -> comentario.getEstudiante().equals(estudiante))
                .collect(Collectors.toList());
    }

    public List<ComentarioFormativo> listarTodosLosComentarios() {
        return new ArrayList<>(listaComentarios);
    }

    public boolean eliminarComentario(int idComentario) {
        return listaComentarios.removeIf(comentario -> comentario.getIdComentario() == idComentario);
    }


    public int contarTotalMatriculas() {
        return 0;
    }

    public int contarMatriculasActivas() {
        return 0;
    }

    public int contarMatriculasFinalizadas() {
        return 0;
    }

    public int contarCertificadosEmitidos() {
        return 0;
    }


    public int obtenerTotalEstudiantes() {
        return academia.getListEstudiantes().size();
    }

    public int obtenerTotalProfesores() {
        return academia.getListProfesores().size();
    }

    public int obtenerTotalCursos() {
        return academia.getListCursos().size();
    }

    public int obtenerTotalAulas() {
        return academia.getListAulas().size();
    }


    public double calcularPromedioCalificaciones() {
        List<ReporteProgreso> todosLosReportes = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListReportesProgresos() != null) {
                todosLosReportes.addAll(estudiante.getListReportesProgresos());
            }
        }

        if (todosLosReportes.isEmpty()) {
            return 0.0;
        }

        double suma = todosLosReportes.stream()
                .mapToDouble(ReporteProgreso::getCalificacion)
                .sum();

        return suma / todosLosReportes.size();
    }

    public double calcularTasaAprobacion() {
        List<ReporteProgreso> todosLosReportes = new ArrayList<>();

        for (Estudiante estudiante : academia.getListEstudiantes()) {
            if (estudiante.getListReportesProgresos() != null) {
                todosLosReportes.addAll(estudiante.getListReportesProgresos());
            }
        }

        if (todosLosReportes.isEmpty()) {
            return 0.0;
        }

        long aprobados = todosLosReportes.stream()
                .filter(ReporteProgreso::isAprobado)
                .count();

        return (double) aprobados / todosLosReportes.size() * 100;
    }

    public boolean actualizarInformacionAcademia(String nombre, String direccion, String telefono) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            academia.setNombre(nombre);
        }
        if (direccion != null && !direccion.trim().isEmpty()) {
            academia.setDireccion(direccion);
        }
        if (telefono != null && !telefono.trim().isEmpty()) {
            academia.setTelefono(telefono);
        }
        return true;
    }

    public String obtenerResumenSistema() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("=== RESUMEN DEL SISTEMA ===\n\n");
        resumen.append("Estudiantes: ").append(obtenerTotalEstudiantes()).append("\n");
        resumen.append("Profesores: ").append(obtenerTotalProfesores()).append("\n");
        resumen.append("Cursos: ").append(obtenerTotalCursos()).append("\n");
        resumen.append("Aulas: ").append(obtenerTotalAulas()).append("\n");
        resumen.append("Matrículas: ").append(contarTotalMatriculas()).append("\n");
        resumen.append("Promedio Calificaciones: ").append(String.format("%.2f", calcularPromedioCalificaciones())).append("\n");
        resumen.append("Tasa de Aprobación: ").append(String.format("%.1f", calcularTasaAprobacion())).append("%\n");

        return resumen.toString();
    }
}