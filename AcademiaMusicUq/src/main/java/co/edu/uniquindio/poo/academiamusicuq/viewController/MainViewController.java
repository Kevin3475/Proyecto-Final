package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainViewController {

    @FXML private Label lblFechaHora;
    @FXML private Label lblTotalEstudiantes, lblTotalProfesores, lblTotalCursos, lblTotalAulas;
    @FXML private Label lblTotalClases, lblTotalMatriculas, lblAsistenciasHoy, lblReportes;

    private App app;

    @FXML
    void initialize() {
        actualizarFechaHora();
        cargarEstadisticas();
    }

    private void actualizarFechaHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy - HH:mm");
        lblFechaHora.setText(LocalDateTime.now().format(formatter));
    }

    private void cargarEstadisticas() {
        // Cargar estadísticas reales desde la academia
        if (App.academia != null) {
            lblTotalEstudiantes.setText(String.valueOf(App.academia.getListEstudiantes().size()));
            lblTotalProfesores.setText(String.valueOf(App.academia.getListProfesores().size()));
            lblTotalCursos.setText(String.valueOf(App.academia.getListCursos().size()));
            lblTotalAulas.setText(String.valueOf(App.academia.getListAulas().size()));

            // Placeholders para las demás estadísticas
            lblTotalClases.setText("5"); // Ejemplo
            lblTotalMatriculas.setText("12"); // Ejemplo
            lblAsistenciasHoy.setText("8"); // Ejemplo
            lblReportes.setText("3"); // Ejemplo
        }
    }

    // ===== MÉTODOS DE NAVEGACIÓN =====

    @FXML
    void onGestionEstudiantes() {
        app.mostrarGestionEstudiantes();
    }

    @FXML
    void onGestionProfesores() {
        app.mostrarGestionProfesores();
    }

    @FXML
    void onGestionCursos() {
        app.mostrarGestionCursos();
    }

    @FXML
    void onGestionAulas() {
        app.mostrarGestionAulas();
    }

    @FXML
    void onGestionClases() {
        app.mostrarGestionClases();
    }

    @FXML
    void onGestionMatriculas() {
        app.mostrarGestionMatriculas();
    }

    @FXML
    void onGestionAsistencias() {
        app.mostrarGestionAsistencias();
    }

    @FXML
    void onModuloReportes() {
        app.mostrarReportes();
    }

    @FXML
    void onGestionAdministracion() {
        app.mostrarGestionAdministracion();
    }

    @FXML
    void onCerrarSesion() {
        app.mostrarLogin();
    }

    public void setApp(App app) {
        this.app = app;
    }
}