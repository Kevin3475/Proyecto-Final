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
    private List<Clase> listaClases;

    public ClaseController(Academia academia) {
        this.academia = academia;
        this.listaClases = new ArrayList<>();
        inicializarClasesDeEjemplo();
    }

    private void inicializarClasesDeEjemplo() {

    }

    // Metodos del crud
    public boolean registrarClase(Clase clase) {
        // Verificar si ya existe una clase con el mismo ID
        for (Clase c : listaClases) {
            if (c.getId() == clase.getId()) {
                return false;
            }
        }
        return listaClases.add(clase);
    }

    public List<Clase> obtenerTodasLasClases() {
        return new ArrayList<>(listaClases);
    }

    public boolean actualizarClase(int id, Clase claseActualizada) {
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getId() == id) {
                listaClases.set(i, claseActualizada);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarClase(int id) {
        return listaClases.removeIf(clase -> clase.getId() == id);
    }

    // Metodos de las Clases
    public List<ClaseGrupal> obtenerClasesGrupales() {
        return listaClases.stream()
                .filter(clase -> clase instanceof ClaseGrupal)
                .map(clase -> (ClaseGrupal) clase)
                .collect(Collectors.toList());
    }

    public List<ClaseIndividual> obtenerClasesIndividuales() {
        return listaClases.stream()
                .filter(clase -> clase instanceof ClaseIndividual)
                .map(clase -> (ClaseIndividual) clase)
                .collect(Collectors.toList());
    }

    // Metodos de los Estudiantes
    public boolean agregarEstudianteAClaseGrupal(ClaseGrupal clase, Estudiante estudiante) {
        return clase.agregarEstudiante(estudiante);
    }

    public boolean removerEstudianteDeClaseGrupal(ClaseGrupal clase, Estudiante estudiante) {
        return clase.getListEstudiantes().remove(estudiante);
    }

    // Metodos de Asistencia
    public boolean registrarAsistencia(Clase clase, Asistencia asistencia) {
        return clase.registrarAsistencia(clase.getProfesor(), asistencia);
    }

    public List<Asistencia> obtenerAsistenciasClase(Clase clase) {
        // En una implementación real, esto vendría de una base de datos
        return new ArrayList<>();
    }

    // Metodos de Evaluacion del Estudiante
    public ReporteProgreso evaluarProgresoEstudiante(Clase clase, Estudiante estudiante, float calificacion, String observaciones) {
        return clase.evaluarProgreso(estudiante, calificacion, observaciones);
    }

    public List<ReporteProgreso> obtenerEvaluacionesClase(Clase clase) {
        // En una implementación real, esto vendría de una base de datos
        return new ArrayList<>();
    }

    // Metodos de Consulta
    public List<Clase> obtenerClasesPorProfesor(String idProfesor) {
        return listaClases.stream()
                .filter(clase -> clase.getProfesor() != null && clase.getProfesor().getId().equals(idProfesor))
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorCurso(String idCurso) {
        return listaClases.stream()
                .filter(clase -> clase.getCurso() != null && clase.getCurso().getIdCurso().equals(idCurso))
                .collect(Collectors.toList());
    }

    public List<Clase> obtenerClasesPorAula(String idAula) {
        return listaClases.stream()
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