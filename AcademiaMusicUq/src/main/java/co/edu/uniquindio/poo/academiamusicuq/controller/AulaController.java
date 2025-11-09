package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Aula;
import co.edu.uniquindio.poo.academiamusicuq.model.BloqueHorario;
import co.edu.uniquindio.poo.academiamusicuq.model.Clase;

import java.util.List;

public class AulaController {

    private Academia academia;

    public AulaController(Academia academia) {
        this.academia = academia;
    }

    // ===== MÉTODOS CRUD BÁSICOS =====
    public boolean registrarAula(Aula aula) {
        return academia.registrarAula(aula);
    }

    public List<Aula> obtenerAulas() {
        return academia.listarAulas();
    }

    public boolean actualizarAula(String idAula, Aula aula) {
        return academia.actualizarAula(idAula, aula);
    }

    public boolean eliminarAula(String idAula) {
        return academia.eliminarAula(idAula);
    }

    // ===== MÉTODOS DE GESTIÓN DE HORARIOS =====
    public boolean agregarHorarioAAula(Aula aula, BloqueHorario horario) {
        // Verificar disponibilidad primero
        if (!aula.estaDisponible(horario)) {
            return false;
        }

        return aula.getListHorarios().add(horario);
    }

    public boolean removerHorarioDeAula(Aula aula, BloqueHorario horario) {
        return aula.getListHorarios().remove(horario);
    }

    // ===== MÉTODOS DE CONSULTA =====
    public boolean verificarDisponibilidadAula(Aula aula, BloqueHorario horario) {
        return aula.estaDisponible(horario);
    }

    public List<BloqueHorario> obtenerHorariosAula(Aula aula) {
        return aula.getListHorarios();
    }

    public List<Clase> obtenerClasesAula(Aula aula) {
        return aula.getListClases();
    }

    // ===== MÉTODOS DE FILTRADO =====
    public List<Aula> obtenerAulasDisponibles() {
        return academia.listarAulas().stream()
                .filter(Aula::isDisponible)
                .toList();
    }

    public List<Aula> obtenerAulasPorCapacidad(int capacidadMinima) {
        return academia.listarAulas().stream()
                .filter(aula -> aula.getCapacidad() >= capacidadMinima)
                .toList();
    }
}