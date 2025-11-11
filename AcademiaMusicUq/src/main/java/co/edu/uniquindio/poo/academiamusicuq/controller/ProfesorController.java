package co.edu.uniquindio.poo.academiamusicuq.controller;

import co.edu.uniquindio.poo.academiamusicuq.model.Academia;
import co.edu.uniquindio.poo.academiamusicuq.model.Profesor;
import co.edu.uniquindio.poo.academiamusicuq.model.Clase;
import co.edu.uniquindio.poo.academiamusicuq.model.ClaseGrupal;
import co.edu.uniquindio.poo.academiamusicuq.model.BloqueHorario;
import co.edu.uniquindio.poo.academiamusicuq.model.ComentarioFormativo;

import java.util.List;

public class ProfesorController {

    private Academia academia;

    public ProfesorController(Academia academia) {
        this.academia = academia;
    }

    // Metodos del Crud
    public boolean registrarProfesor(Profesor profesor) {
        return academia.registrarProfesor(profesor);
    }

    public List<Profesor> obtenerProfesores() {
        return academia.listarProfesores();
    }

    public boolean actualizarProfesor(String id, Profesor profesor) {
        return academia.actualizarProfesor(id, profesor);
    }

    public boolean eliminarProfesor(String id) {
        return academia.eliminarProfesor(id);
    }

    // Metodos de Clase
    public boolean asignarHorarioProfesor(Profesor profesor, BloqueHorario horario) {
        return profesor.asignarHorario(horario);
    }

    public boolean agregarClaseAProfesor(Profesor profesor, Clase clase) {
        return profesor.agregarClase(clase);
    }

    public boolean crearClaseGrupalProfesor(Profesor profesor, ClaseGrupal clase) {
        return profesor.crearClaseGrupal(clase);
    }

    // Metodos de Consulta
    public List<Clase> obtenerClasesProfesor(Profesor profesor) {
        return profesor.getListClases();
    }

    public List<ComentarioFormativo> obtenerComentariosProfesor(Profesor profesor) {
        return profesor.getListComentariosFormativos();
    }
}