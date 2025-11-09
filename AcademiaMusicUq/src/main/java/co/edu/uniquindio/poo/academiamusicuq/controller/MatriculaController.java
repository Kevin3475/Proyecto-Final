package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Matricula;
import co.edu.uniquindio.poo.academiamusicuq.model.EstadoMatricula;
import co.edu.uniquindio.poo.academiamusicuq.model.Estudiante;
import co.edu.uniquindio.poo.academiamusicuq.model.Curso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatriculaController {

    private Academia academia;
    private List<Matricula> listaMatriculas;

    public MatriculaController(Academia academia) {
        this.academia = academia;
        this.listaMatriculas = new ArrayList<>();
        // En una implementación real, esto vendría de la academia
        inicializarMatriculasDeEjemplo();
    }

    private void inicializarMatriculasDeEjemplo() {
        // Obtener estudiantes y cursos de ejemplo
        List<Estudiante> estudiantes = academia.getListEstudiantes();
        List<Curso> cursos = academia.getListCursos();

        if (!estudiantes.isEmpty() && !cursos.isEmpty()) {
            // Crear algunas matrículas de ejemplo
            Matricula matricula1 = new Matricula(1, estudiantes.get(0), cursos.get(0),
                    LocalDate.now().minusMonths(2), EstadoMatricula.FINALIZADA, true);

            Matricula matricula2 = new Matricula(2, estudiantes.get(1), cursos.get(1),
                    LocalDate.now().minusMonths(1), EstadoMatricula.ACTIVA, false);

            Matricula matricula3 = new Matricula(3, estudiantes.get(2), cursos.get(2),
                    LocalDate.now(), EstadoMatricula.FINALIZADA, false);

            listaMatriculas.add(matricula1);
            listaMatriculas.add(matricula2);
            listaMatriculas.add(matricula3);
        }
    }

    // ===== MÉTODOS CRUD BÁSICOS =====
    public boolean registrarMatricula(Matricula matricula) {
        // Verificar si ya existe una matrícula con el mismo ID
        for (Matricula m : listaMatriculas) {
            if (m.getIdMatricula() == matricula.getIdMatricula()) {
                return false;
            }
        }
        return listaMatriculas.add(matricula);
    }

    public List<Matricula> obtenerTodasLasMatriculas() {
        return new ArrayList<>(listaMatriculas);
    }

    public boolean actualizarMatricula(int idMatricula, Matricula matriculaActualizada) {
        for (int i = 0; i < listaMatriculas.size(); i++) {
            if (listaMatriculas.get(i).getIdMatricula() == idMatricula) {
                listaMatriculas.set(i, matriculaActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarMatricula(int idMatricula) {
        return listaMatriculas.removeIf(matricula -> matricula.getIdMatricula() == idMatricula);
    }

    // ===== MÉTODOS DE GESTIÓN DE CERTIFICADOS =====
    public boolean emitirCertificado(int idMatricula) {
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getIdMatricula() == idMatricula) {
                if (matricula.puedeEmitirCertificado()) {
                    matricula.setCertificadoEmitido(true);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean revocarCertificado(int idMatricula) {
        for (Matricula matricula : listaMatriculas) {
            if (matricula.getIdMatricula() == idMatricula) {
                matricula.setCertificadoEmitido(false);
                return true;
            }
        }
        return false;
    }

    // ===== MÉTODOS DE CONSULTA =====
    public List<Matricula> obtenerMatriculasPorEstudiante(String idEstudiante) {
        return listaMatriculas.stream()
                .filter(matricula -> matricula.getEstudiante() != null &&
                        matricula.getEstudiante().getId().equals(idEstudiante))
                .collect(Collectors.toList());
    }

    public List<Matricula> obtenerMatriculasPorCurso(String idCurso) {
        return listaMatriculas.stream()
                .filter(matricula -> matricula.getCurso() != null &&
                        matricula.getCurso().getIdCurso().equals(idCurso))
                .collect(Collectors.toList());
    }

    public List<Matricula> obtenerMatriculasPorEstado(EstadoMatricula estado) {
        return listaMatriculas.stream()
                .filter(matricula -> matricula.getEstadoMatricula() == estado)
                .collect(Collectors.toList());
    }

    public List<Matricula> obtenerMatriculasParaCertificado() {
        return listaMatriculas.stream()
                .filter(Matricula::puedeEmitirCertificado)
                .collect(Collectors.toList());
    }

    public List<Matricula> obtenerMatriculasConCertificado() {
        return listaMatriculas.stream()
                .filter(Matricula::isCertificadoEmitido)
                .collect(Collectors.toList());
    }

    // ===== MÉTODOS DE VERIFICACIÓN =====
    public boolean verificarMatriculaActiva(Estudiante estudiante, Curso curso) {
        return listaMatriculas.stream()
                .anyMatch(matricula ->
                        matricula.getEstudiante().equals(estudiante) &&
                                matricula.getCurso().equals(curso) &&
                                matricula.getEstadoMatricula() == EstadoMatricula.ACTIVA);
    }

    public boolean puedeMatricularse(Estudiante estudiante, Curso curso) {
        // Verificar si el estudiante ya tiene una matrícula activa en este curso
        if (verificarMatriculaActiva(estudiante, curso)) {
            return false;
        }

        // Verificar nivel del estudiante (si el curso tiene requisitos)
        if (curso != null && estudiante != null) {
            return curso.verificarNivelEstudiante(estudiante);
        }

        return true;
    }

    // ===== MÉTODOS DE ESTADÍSTICAS =====
    public int contarMatriculasActivas() {
        return (int) listaMatriculas.stream()
                .filter(matricula -> matricula.getEstadoMatricula() == EstadoMatricula.ACTIVA)
                .count();
    }

    public int contarMatriculasFinalizadas() {
        return (int) listaMatriculas.stream()
                .filter(matricula -> matricula.getEstadoMatricula() == EstadoMatricula.FINALIZADA)
                .count();
    }

    public int contarCertificadosEmitidos() {
        return (int) listaMatriculas.stream()
                .filter(Matricula::isCertificadoEmitido)
                .count();
    }
}