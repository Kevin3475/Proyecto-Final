package co.edu.uniquindio.poo.academiamusicuq.model;

public class ClaseIndividual extends Clase{

    private String objetivoPersonal;
    private Estudiante estudiante;

    public ClaseIndividual(int id, Aula aula, TipoClase tipoClase, Profesor profesor, BloqueHorario horario, Curso curso,String objetivoPersonal,Estudiante estudiante){
        super(id,aula,tipoClase,profesor,horario,curso);

        this.objetivoPersonal = objetivoPersonal;
        this.estudiante = estudiante;


    }

    public String getObjetivoPersonal() {
        return objetivoPersonal;
    }

    public void setObjetivoPersonal(String objetivoPersonal) {
        this.objetivoPersonal = objetivoPersonal;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }


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


    // metodo para solicitar una clase Individual


    public boolean solicitarClaseIndividual(Estudiante estudiante, Profesor profesor, BloqueHorario horario) {


        if (estudiante == null || profesor == null || horario == null) {
            return false;
        }

        if (!profesor.isActivo()) {
            return false;
        }

        for (BloqueHorario bloque : profesor.getListBloqueHorarios()) {
            if (bloque.conflictoCon(horario)) {
                return false;
            }
        }

        this.setEstudiante(estudiante);
        this.setProfesor(profesor);
        this.setHorario(horario);

        profesor.getListClasesIndividuales().add(this);

        return true;
    }
}
