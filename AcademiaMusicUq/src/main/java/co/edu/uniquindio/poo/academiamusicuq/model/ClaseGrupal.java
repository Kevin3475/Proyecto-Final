package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class ClaseGrupal extends Clase{

    private int cupoMaximo;
    private List<Estudiante> listEstudiantes;

    public ClaseGrupal(int id, Aula aula, TipoClase tipoClase, Profesor profesor, BloqueHorario horario, Curso curso,int cupoMaximo){
        super(id,aula,tipoClase,profesor,horario,curso);

        this.cupoMaximo = cupoMaximo;
        this.listEstudiantes = new ArrayList<>();

    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    // metodos abstractos de el padre

    @Override
    public boolean registrarAsistencia(Profesor profesor, Asistencia asistencia) {
        if (asistencia == null || profesor == null) {
            return false;
        }

        if (!this.profesor.equals(profesor)) {
            return false;
        }

        profesor.getListAsistencias().add(asistencia);
        return true;
    }

    @Override
    public ReporteProgreso evaluarProgreso(Estudiante estudiante, float calificacion, String observaciones) {
        boolean aprobado = calificacion >= 3.0;
        ReporteProgreso reporte = new ReporteProgreso(
                estudiante.getListReportesProgresos().size() + 1,
                estudiante,
                this.curso,
                this.profesor,
                java.time.LocalDate.now(),
                observaciones,
                calificacion,
                aprobado
        );
        estudiante.getListReportesProgresos().add(reporte);
        return reporte;
    }


    // metodo para agregar un estudiante a la clase grupal

    public boolean agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            return false;
        }

        if (listEstudiantes.contains(estudiante)) {
            return false;
        }

        listEstudiantes.add(estudiante);
        return true;
    }


    // metodo para verificar que halla cupo

    public boolean verificarCupo() {
        return listEstudiantes.size() < cupoMaximo;
    }











}
