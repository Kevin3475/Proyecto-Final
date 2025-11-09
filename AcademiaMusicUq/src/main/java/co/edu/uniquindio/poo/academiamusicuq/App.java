package co.edu.uniquindio.poo.academiamusicuq;

import co.edu.uniquindio.poo.academiamusicuq.model.*;
import co.edu.uniquindio.poo.academiamusicuq.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage;
    public static Academia academia = inicializarAcademia();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Academia de Música UQ - Sistema de Gestión");
        this.primaryStage.setMaximized(true);
        mostrarLogin();
    }

    public void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/co/edu/uniquindio/poo/academiamusicuq/login.fxml"));
            AnchorPane rootLayout = loader.load();

            LoginViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando login.fxml: " + e.getMessage());
        }
    }

    public void mostrarMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/co/edu/uniquindio/poo/academiamusicuq/main.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR MainViewController
            MainViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando main.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionEstudiantes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/estudiante.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR EstudianteViewController
            EstudianteViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando estudiante.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionProfesores() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/profesor.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR ProfesorViewController
            ProfesorViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando profesor.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionCursos() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/curso.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR CursoViewController
            CursoViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando curso.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionAulas() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/aula.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR AulaViewController
            AulaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando aula.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionClases() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/clase.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR ClaseViewController
            ClaseViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando clase.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionMatriculas() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/matricula.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR MatriculaViewController
            MatriculaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando matricula.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionAdministracion() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/administracion.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR AdministracionViewController
            AdministracionViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando administracion.fxml: " + e.getMessage());
        }
    }

    public void mostrarGestionAsistencias() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/asistencia.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR AsistenciasViewController
            AsistenciaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando asistencia.fxml: " + e.getMessage());
        }
    }

    public void mostrarReportes() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/reportes.fxml"));
            AnchorPane rootLayout = loader.load();

            // CONECTAR ReportesViewController
            ReportesViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("/co/edu/uniquindio/poo/academiamusicuq/styles.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error cargando reportes.fxml: " + e.getMessage());
        }
    }

    private static Academia inicializarAcademia() {
        AdministradorAcademico admin = new AdministradorAcademico(
                "ADM001", "Carlos", "Administrador", "admin@academia.edu", "123456789", true
        );

        Academia academia = new Academia("Academia de Música UQ", "Calle 123", "6011234567", admin);

        // ===== DATOS DE EJEMPLO - PROFESORES =====
        Profesor profesor1 = new Profesor("PROF001", "Ana", "García", "ana@academia.edu", "3001112233",
                "Piano Clásico", Instrumento.PIANO, true);
        Profesor profesor2 = new Profesor("PROF002", "Luis", "Martínez", "luis@academia.edu", "3002223344",
                "Guitarra Flamenca", Instrumento.GUITARRA, true);
        Profesor profesor3 = new Profesor("PROF003", "Elena", "Rodríguez", "elena@academia.edu", "3003334455",
                "Violín Clásico", Instrumento.VIOLIN, true);
        Profesor profesor4 = new Profesor("PROF004", "Carlos", "Hernández", "carlos@academia.edu", "3004445566",
                "Canto Lírico", Instrumento.CANTO, true);

        // ===== DATOS DE EJEMPLO - ESTUDIANTES =====
        Estudiante estudiante1 = new Estudiante("EST001", "Juan", "Pérez", "juan@estudiante.edu",
                "3004445566", Nivel.BASICO, true);
        Estudiante estudiante2 = new Estudiante("EST002", "María", "López", "maria@estudiante.edu",
                "3005556677", Nivel.INTERMEDIO, true);
        Estudiante estudiante3 = new Estudiante("EST003", "Carlos", "Gómez", "carlos@estudiante.edu",
                "3006667788", Nivel.AVANZADO, true);
        Estudiante estudiante4 = new Estudiante("EST004", "Ana", "Rodríguez", "ana@estudiante.edu",
                "3007778899", Nivel.BASICO, true);
        Estudiante estudiante5 = new Estudiante("EST005", "Pedro", "Martínez", "pedro@estudiante.edu",
                "3008889900", Nivel.INTERMEDIO, true);

        // ===== DATOS DE EJEMPLO - CURSOS =====
        Curso curso1 = new Curso("CUR001", "Piano Básico", Instrumento.PIANO, Nivel.BASICO, 10, profesor1);
        Curso curso2 = new Curso("CUR002", "Guitarra Intermedia", Instrumento.GUITARRA, Nivel.INTERMEDIO, 8, profesor2);
        Curso curso3 = new Curso("CUR003", "Violín Avanzado", Instrumento.VIOLIN, Nivel.AVANZADO, 6, profesor3);
        Curso curso4 = new Curso("CUR004", "Canto para Principiantes", Instrumento.CANTO, Nivel.BASICO, 12, profesor4);

        // ===== DATOS DE EJEMPLO - AULAS =====
        Aula aula1 = new Aula("AUL001", "Sala de Piano", 15, true);
        Aula aula2 = new Aula("AUL002", "Sala de Cuerdas", 12, true);
        Aula aula3 = new Aula("AUL003", "Sala de Canto", 20, true);
        Aula aula4 = new Aula("AUL004", "Sala Polivalente", 25, true);

        // ===== DATOS DE EJEMPLO - CLASES =====
        BloqueHorario horario1 = new BloqueHorario("LUNES", java.time.LocalTime.of(9, 0), java.time.LocalTime.of(10, 0));
        BloqueHorario horario2 = new BloqueHorario("MIÉRCOLES", java.time.LocalTime.of(14, 0), java.time.LocalTime.of(15, 30));

        ClaseGrupal clase1 = new ClaseGrupal(1, aula1, TipoClase.GRUPAL, profesor1, horario1, curso1, 10);
        ClaseIndividual clase2 = new ClaseIndividual(2, aula2, TipoClase.INDIVIDUAL, profesor2, horario2, curso2,
                "Mejorar técnica de digitación", estudiante1);

        // ===== REGISTRAR EN ACADEMIA =====

        // Registrar profesores
        academia.registrarProfesor(profesor1);
        academia.registrarProfesor(profesor2);
        academia.registrarProfesor(profesor3);
        academia.registrarProfesor(profesor4);

        // Registrar estudiantes
        academia.registrarEstudiante(estudiante1);
        academia.registrarEstudiante(estudiante2);
        academia.registrarEstudiante(estudiante3);
        academia.registrarEstudiante(estudiante4);
        academia.registrarEstudiante(estudiante5);

        // Registrar cursos
        academia.registrarCurso(curso1);
        academia.registrarCurso(curso2);
        academia.registrarCurso(curso3);
        academia.registrarCurso(curso4);

        // Registrar aulas
        academia.registrarAula(aula1);
        academia.registrarAula(aula2);
        academia.registrarAula(aula3);
        academia.registrarAula(aula4);

        // Agregar clases a cursos
        curso1.agregarClase(clase1);
        curso2.agregarClase(clase2);

        // Agregar estudiantes a cursos
        curso1.getListEstudiantes().add(estudiante1);
        curso1.getListEstudiantes().add(estudiante2);
        curso2.getListEstudiantes().add(estudiante3);

        return academia;
    }

    public static void main(String[] args) {
        launch();
    }
}