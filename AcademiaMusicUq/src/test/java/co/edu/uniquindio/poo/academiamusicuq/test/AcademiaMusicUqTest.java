package co.edu.uniquindio.poo.academiamusicuq.test;

import co.edu.uniquindio.poo.academiamusicuq.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AcademiaMusicUqTest {

    private Academia academia;
    private AdministradorAcademico admin;
    private Profesor profesor1, profesor2;
    private Estudiante estudiante1, estudiante2;
    private Curso curso1, curso2;
    private Aula aula1, aula2;
    private BloqueHorario bloque1, bloque2;

    @BeforeEach
    public void setUp() {
        admin = new AdministradorAcademico("A1", "Admin", "Uno", "admin@uq.edu", "3001234567", true);

        profesor1 = new Profesor("P1", "Juan", "Perez", "juan@uq.edu", "3001111111", "Piano", Instrumento.PIANO, true);
        profesor2 = new Profesor("P2", "Maria", "Gomez", "maria@uq.edu", "3002222222", "Guitarra", Instrumento.GUITARRA, true);

        estudiante1 = new Estudiante("E1", "Carlos", "Lopez", "carlos@uq.edu", "3003333333", Nivel.BASICO, true);
        estudiante2 = new Estudiante("E2", "Ana", "Martinez", "ana@uq.edu", "3004444444", Nivel.INTERMEDIO, true);

        aula1 = new Aula("A1", "Aula 101", 10, true);
        aula2 = new Aula("A2", "Aula 102", 5, true);

        bloque1 = new BloqueHorario("Lunes", LocalTime.of(10, 0), LocalTime.of(12, 0));
        bloque2 = new BloqueHorario("Martes", LocalTime.of(14, 0), LocalTime.of(16, 0));

        curso1 = new Curso("C1", "Piano Inicial", Instrumento.PIANO, Nivel.BASICO, 5, profesor1);
        curso2 = new Curso("C2", "Guitarra Intermedio", Instrumento.GUITARRA, Nivel.INTERMEDIO, 5, profesor2);

        academia = new Academia("Academia MusicUQ", "Calle 1", "5551234", admin);
    }

    // Test clase Estudiante

    @Test
    public void testRegistrarEstudiante() {
        assertTrue(academia.registrarEstudiante(estudiante1));
        assertTrue(academia.registrarEstudiante(estudiante2));
        assertFalse(academia.registrarEstudiante(estudiante1));
        List<Estudiante> estudiantes = academia.listarEstudiantes();
        assertEquals(2, estudiantes.size());
    }

    @Test
    public void testActualizarEstudiante() {
        academia.registrarEstudiante(estudiante1);
        Estudiante actualizado = new Estudiante("E1", "CarlosMod", "Lopez", "carlosmod@uq.edu", "3000000000", Nivel.INTERMEDIO, true);
        assertTrue(academia.actualizarEstudiante("E1", actualizado));
        assertEquals("CarlosMod", academia.listarEstudiantes().get(0).getNombre());
    }

    @Test
    public void testEliminarEstudiante() {
        academia.registrarEstudiante(estudiante1);
        assertTrue(academia.eliminarEstudiante("E1"));
        assertFalse(academia.eliminarEstudiante("E1"));
    }

    // Test clase Profesor

    @Test
    public void testRegistrarProfesor() {
        assertTrue(academia.registrarProfesor(profesor1));
        assertTrue(academia.registrarProfesor(profesor2));
        assertFalse(academia.registrarProfesor(profesor1));
        assertEquals(2, academia.listarProfesores().size());
    }

    @Test
    public void testActualizarProfesor() {
        academia.registrarProfesor(profesor1);
        Profesor actualizado = new Profesor("P1", "JuanMod", "Perez", "juanmod@uq.edu", "3009999999", "Piano", Instrumento.PIANO, true);
        assertTrue(academia.actualizarProfesor("P1", actualizado));
        assertEquals("JuanMod", academia.listarProfesores().get(0).getNombre());
    }

    @Test
    public void testEliminarProfesor() {
        academia.registrarProfesor(profesor1);
        assertTrue(academia.eliminarProfesor("P1"));
        assertFalse(academia.eliminarProfesor("P1"));
    }

    // Test clase Curso

    @Test
    public void testRegistrarCurso() {
        assertTrue(academia.registrarCurso(curso1));
        assertTrue(academia.registrarCurso(curso2));
        assertFalse(academia.registrarCurso(curso1));
        assertEquals(2, academia.listarCursos().size());
    }

    @Test
    public void testActualizarCurso() {
        academia.registrarCurso(curso1);
        Curso actualizado = new Curso("C1", "Piano Mod", Instrumento.PIANO, Nivel.BASICO, 10, profesor1);
        assertTrue(academia.actualizarCurso("C1", actualizado));
        assertEquals("Piano Mod", academia.listarCursos().get(0).getNombreCurso());
    }

    @Test
    public void testEliminarCurso() {
        academia.registrarCurso(curso1);
        assertTrue(academia.eliminarCurso("C1"));
        assertFalse(academia.eliminarCurso("C1"));
    }

    @Test
    public void testCrearCursoMetodo() {
        assertTrue(academia.crearCurso("C3", "Violin Basico", Instrumento.VIOLIN, Nivel.BASICO, 5, profesor1));
        // Datos inválidos
        assertFalse(academia.crearCurso("", "Nombre", Instrumento.VIOLIN, Nivel.BASICO, 5, profesor1));
        assertEquals(1, academia.getListCursos().size());
    }

    // Test clase Aula

    @Test
    public void testRegistrarAula() {
        assertTrue(academia.registrarAula(aula1));
        assertTrue(academia.registrarAula(aula2));
        assertFalse(academia.registrarAula(aula1));
        assertEquals(2, academia.listarAulas().size());
    }

    @Test
    public void testActualizarAula() {
        academia.registrarAula(aula1);
        Aula actualizada = new Aula("A1", "Aula Mod", 15, false);
        assertTrue(academia.actualizarAula("A1", actualizada));
        assertEquals("Aula Mod", academia.listarAulas().get(0).getNombre());
        assertFalse(academia.listarAulas().get(0).getDisponible());
    }

    @Test
    public void testEliminarAula() {
        academia.registrarAula(aula1);
        assertTrue(academia.eliminarAula("A1"));
        assertFalse(academia.eliminarAula("A1"));
    }

    // Test clase AdministradorAcademico

    @Test
    public void testCrearCursoAdministrador() {
        assertTrue(admin.crearCurso(curso1));
        assertFalse(admin.crearCurso(curso1));
        assertEquals(1, admin.getListCursos().size());
    }

    @Test
    public void testAgregarComentarioFormativo() {
        admin.agregarComentarioFormativo(new ComentarioFormativo(1, "Buen progreso", LocalDate.now(), estudiante1, curso1));
        assertEquals(1, admin.getListComentariosFormativos().size());
    }

    // Test clase Clase, ClaseGrupal y ClaseIndividual

    @Test
    public void testClaseGrupalAgregarEstudianteYVerificarCupo() {
        ClaseGrupal claseGrupal = new ClaseGrupal(1, aula1, TipoClase.GRUPAL, profesor1, bloque1, curso1, 2);
        assertTrue(claseGrupal.agregarEstudiante(estudiante1));
        assertTrue(claseGrupal.agregarEstudiante(estudiante2));
        assertFalse(claseGrupal.agregarEstudiante(new Estudiante("E3", "Luis", "Diaz", "luis@uq.edu", "3005555555", Nivel.BASICO, true)));
        assertFalse(claseGrupal.verificarCupo());
    }

    @Test
    public void testClaseIndividualSolicitarClase() {
        ClaseIndividual claseIndividual = new ClaseIndividual(1, aula2, TipoClase.INDIVIDUAL, profesor2, bloque2, curso2, "Objetivo", estudiante2);
        assertTrue(claseIndividual.solicitarClaseIndividual(estudiante2, profesor2, bloque2));
    }

    // Test clase Profesor

    @Test
    public void testAsignarHorarioProfesor() {
        BloqueHorario bloqueNuevo = new BloqueHorario("Miercoles", LocalTime.of(8,0), LocalTime.of(10,0));
        assertTrue(profesor1.asignarHorario(bloqueNuevo));
        assertFalse(profesor1.asignarHorario(new BloqueHorario("Miercoles", LocalTime.of(9,0), LocalTime.of(11,0))));
    }

    @Test
    public void testGenerarComentario() {
        profesor1.getListComentariosFormativos().clear();
        ComentarioFormativo comentario = profesor1.generarComentario(estudiante1, curso1, "Excelente progreso");
        assertEquals("Excelente progreso", comentario.getContenido());
        assertEquals(1, profesor1.getListComentariosFormativos().size());
    }

    @Test
    public void testValorarProgresoProfesor() {
        ReporteProgreso reporte = profesor1.valorarProgreso(estudiante1, curso1, 4.5f, "Muy bien");
        assertTrue(reporte.isAprobado());
        assertEquals(1, estudiante1.getListReportesProgresos().size());
    }

    @Test
    public void testRegistrarAsistenciaProfesor() {
        ClaseGrupal claseGrupal = new ClaseGrupal(1, aula1, TipoClase.GRUPAL, profesor1, bloque1, curso1, 5);
        Asistencia asistencia = new Asistencia(1, estudiante1, claseGrupal, LocalDate.now(), true);
        claseGrupal.agregarEstudiante(estudiante1);
        profesor1.agregarClase(claseGrupal);
        assertTrue(profesor1.registrarAsistencia(asistencia));
    }


    @Test
    public void testRegistrarAsistencia() {

        ClaseGrupal claseGrupal = new ClaseGrupal(1, aula1, TipoClase.GRUPAL, profesor1, bloque1, curso1, 5);
        claseGrupal.agregarEstudiante(estudiante1);

        Asistencia asistencia = new Asistencia(1, estudiante1, claseGrupal, java.time.LocalDate.now(), true);

        assertTrue(claseGrupal.registrarAsistencia(profesor1, asistencia));

        assertEquals(1, profesor1.getListAsistencias().size());

        assertEquals(1, estudiante1.getListAsistencias().size());

        Profesor profesorFalso = new Profesor("P2", "Ana", "Lopez", "ana@uq.edu", "3001111111", "Canto", Instrumento.CANTO, true);
        Asistencia asistenciaInvalida = new Asistencia(2, estudiante1, claseGrupal, java.time.LocalDate.now(), true);
        assertFalse(claseGrupal.registrarAsistencia(profesorFalso, asistenciaInvalida));
    }
}
