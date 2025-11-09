package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.AdministracionController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.List;

public class AdministracionViewController {

    // ===== COMPONENTES PESTAÑA DATOS ACADEMIA =====
    @FXML private TextField txtNombreAcademia, txtDireccionAcademia, txtTelefonoAcademia;
    @FXML private TextField txtAdminNombre, txtAdminEmail, txtAdminTelefono;
    @FXML private Button btnActualizarAcademia, btnLimpiarAcademia;

    // ===== COMPONENTES PESTAÑA REPORTES =====
    @FXML private ComboBox<String> cbTipoReporte;
    @FXML private DatePicker dpFechaInicio, dpFechaFin;
    @FXML private TableView<ReporteProgreso> tblReportes;
    @FXML private TableColumn<ReporteProgreso, String> colIdReporte, colEstudianteReporte, colCursoReporte, colProfesorReporte, colCalificacionReporte, colFechaReporte;
    @FXML private Button btnGenerarReporte, btnExportarReporte;

    // ===== COMPONENTES PESTAÑA COMENTARIOS FORMATIVOS =====
    @FXML private ComboBox<Estudiante> cbEstudianteComentarios;
    @FXML private TableView<ComentarioFormativo> tblComentarios;
    @FXML private TableColumn<ComentarioFormativo, String> colIdComentario, colEstudianteComentario, colCursoComentario, colContenidoComentario, colFechaComentario;
    @FXML private Button btnAgregarComentario, btnLimpiarComentarios;
    @FXML private TextArea txtNuevoComentario;

    // ===== COMPONENTES PESTAÑA ESTADÍSTICAS =====
    @FXML private PieChart pieChartEstudiantes;
    @FXML private BarChart<String, Number> barChartCursos;
    @FXML private Label lblTotalEstudiantes, lblTotalProfesores, lblTotalCursos, lblTotalMatriculas;
    @FXML private Label lblMatriculasActivas, lblMatriculasFinalizadas, lblCertificadosEmitidos;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<ReporteProgreso> listaReportes = FXCollections.observableArrayList();
    private ObservableList<ComentarioFormativo> listaComentarios = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();

    private AdministracionController administracionController;
    private App app;

    @FXML
    void initialize() {
        this.administracionController = new AdministracionController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
        cargarEstadisticas();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosAcademia();
        configurarPestanaReportes();
        configurarPestanaComentarios();
        configurarPestanaEstadisticas();
    }

    // ===== PESTAÑA 1: DATOS ACADEMIA =====
    private void configurarPestanaDatosAcademia() {
        // Cargar datos actuales de la academia
        cargarDatosAcademia();
    }

    // ===== PESTAÑA 2: REPORTES =====
    private void configurarPestanaReportes() {
        // Configurar tabla reportes
        colIdReporte.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdReporte())));
        colEstudianteReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colProfesorReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        colCalificacionReporte.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCalificacion())));
        colFechaReporte.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));

        // Configurar combo tipos de reporte
        cbTipoReporte.setItems(FXCollections.observableArrayList(
                "Todos los reportes",
                "Reportes por estudiante",
                "Reportes por curso",
                "Reportes por profesor",
                "Reportes aprobados",
                "Reportes reprobados"
        ));

        dpFechaInicio.setValue(LocalDate.now().minusMonths(1));
        dpFechaFin.setValue(LocalDate.now());
    }

    // ===== PESTAÑA 3: COMENTARIOS FORMATIVOS =====
    private void configurarPestanaComentarios() {
        // Configurar tabla comentarios
        colIdComentario.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdComentario())));
        colEstudianteComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colContenidoComentario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContenido()));
        colFechaComentario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
    }

    // ===== PESTAÑA 4: ESTADÍSTICAS =====
    private void configurarPestanaEstadisticas() {
        // Los gráficos se configurarán al cargar los datos
    }

    private void cargarDatosIniciales() {
        cargarDatosAcademia();
        cargarCombosReportes();
        cargarCombosComentarios();
    }

    private void cargarDatosAcademia() {
        Academia academia = App.academia;
        if (academia != null) {
            txtNombreAcademia.setText(academia.getNombre());
            txtDireccionAcademia.setText(academia.getDireccion());
            txtTelefonoAcademia.setText(academia.getTelefono());

            AdministradorAcademico admin = academia.getAdministrador();
            if (admin != null) {
                txtAdminNombre.setText(admin.getNombre() + " " + admin.getApellido());
                txtAdminEmail.setText(admin.getEmail());
                txtAdminTelefono.setText(admin.getTelefono());
            }
        }
    }

    private void cargarCombosReportes() {
        // Los combos ya se configuraron en initialize()
    }

    private void cargarCombosComentarios() {
        // Cargar estudiantes para comentarios
        listaEstudiantes.clear();
        listaEstudiantes.addAll(App.academia.getListEstudiantes());
        cbEstudianteComentarios.setItems(listaEstudiantes);
    }

    private void cargarEstadisticas() {
        cargarEstadisticasGenerales();
        cargarGraficos();
    }

    // ===== MÉTODOS PESTAÑA DATOS ACADEMIA =====
    @FXML
    void onActualizarAcademia() {
        if (validarCamposAcademia()) {
            // En una implementación real, aquí se actualizarían los datos de la academia
            mostrarAlerta("Éxito", "Datos de la academia actualizados correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    void onLimpiarAcademia() {
        cargarDatosAcademia(); // Recargar datos originales
    }

    // ===== MÉTODOS PESTAÑA REPORTES =====
    @FXML
    void onGenerarReporte() {
        String tipoReporte = cbTipoReporte.getValue();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();

        if (tipoReporte != null && fechaInicio != null && fechaFin != null) {
            List<ReporteProgreso> reportes = administracionController.generarReporte(
                    App.academia.getListEstudiantes(), tipoReporte, fechaInicio, fechaFin);

            listaReportes.clear();
            listaReportes.addAll(reportes);
            tblReportes.setItems(listaReportes);

            mostrarAlerta("Éxito", "Reporte generado: " + reportes.size() + " registros encontrados", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione tipo de reporte y rango de fechas", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onExportarReporte() {
        if (!listaReportes.isEmpty()) {
            // En una implementación real, aquí se exportaría a PDF o Excel
            mostrarAlerta("Éxito", "Reporte exportado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No hay datos para exportar. Genere un reporte primero.", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS PESTAÑA COMENTARIOS FORMATIVOS =====
    @FXML
    void onAgregarComentario() {
        Estudiante estudiante = cbEstudianteComentarios.getValue();
        String contenido = txtNuevoComentario.getText();

        if (estudiante != null && !contenido.trim().isEmpty()) {
            // En una implementación real, aquí se crearía un comentario formativo
            ComentarioFormativo comentario = new ComentarioFormativo(
                    listaComentarios.size() + 1,
                    contenido,
                    LocalDate.now(),
                    estudiante,
                    null // Curso sería opcional
            );

            if (administracionController.agregarComentarioFormativo(comentario)) {
                listaComentarios.add(comentario);
                tblComentarios.setItems(listaComentarios);
                mostrarAlerta("Éxito", "Comentario formativo agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposComentario();
            } else {
                mostrarAlerta("Error", "Error al agregar el comentario", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante y escriba un comentario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarComentarios() {
        limpiarCamposComentario();
    }

    // ===== MÉTODOS AUXILIARES =====
    private void cargarEstadisticasGenerales() {
        int totalEstudiantes = App.academia.getListEstudiantes().size();
        int totalProfesores = App.academia.getListProfesores().size();
        int totalCursos = App.academia.getListCursos().size();
        int totalMatriculas = administracionController.contarTotalMatriculas();

        lblTotalEstudiantes.setText(String.valueOf(totalEstudiantes));
        lblTotalProfesores.setText(String.valueOf(totalProfesores));
        lblTotalCursos.setText(String.valueOf(totalCursos));
        lblTotalMatriculas.setText(String.valueOf(totalMatriculas));

        int matriculasActivas = administracionController.contarMatriculasActivas();
        int matriculasFinalizadas = administracionController.contarMatriculasFinalizadas();
        int certificadosEmitidos = administracionController.contarCertificadosEmitidos();

        lblMatriculasActivas.setText(String.valueOf(matriculasActivas));
        lblMatriculasFinalizadas.setText(String.valueOf(matriculasFinalizadas));
        lblCertificadosEmitidos.setText(String.valueOf(certificadosEmitidos));
    }

    private void cargarGraficos() {
        cargarPieChartEstudiantes();
        cargarBarChartCursos();
    }

    private void cargarPieChartEstudiantes() {
        pieChartEstudiantes.getData().clear();

        long basicos = App.academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.BASICO)
                .count();
        long intermedios = App.academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.INTERMEDIO)
                .count();
        long avanzados = App.academia.getListEstudiantes().stream()
                .filter(e -> e.getNivel() == Nivel.AVANZADO)
                .count();

        if (basicos > 0) pieChartEstudiantes.getData().add(new PieChart.Data("Básico", basicos));
        if (intermedios > 0) pieChartEstudiantes.getData().add(new PieChart.Data("Intermedio", intermedios));
        if (avanzados > 0) pieChartEstudiantes.getData().add(new PieChart.Data("Avanzado", avanzados));
    }

    private void cargarBarChartCursos() {
        barChartCursos.getData().clear();

        // Crear serie de datos para cursos
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estudiantes por Curso");

        for (Curso curso : App.academia.getListCursos()) {
            int cantidadEstudiantes = curso.getListEstudiantes().size();
            series.getData().add(new XYChart.Data<>(curso.getNombreCurso(), cantidadEstudiantes));
        }

        barChartCursos.getData().add(series);
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposComentario() {
        cbEstudianteComentarios.setValue(null);
        txtNuevoComentario.clear();
    }

    private boolean validarCamposAcademia() {
        if (txtNombreAcademia.getText().isEmpty() || txtDireccionAcademia.getText().isEmpty() ||
                txtTelefonoAcademia.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos de la academia son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Listeners para actualizar comentarios cuando cambia el estudiante
    @FXML
    void onEstudianteComentariosChanged() {
        Estudiante estudiante = cbEstudianteComentarios.getValue();
        if (estudiante != null) {
            List<ComentarioFormativo> comentarios = administracionController.listarComentariosEstudiante(estudiante);
            listaComentarios.clear();
            listaComentarios.addAll(comentarios);
            tblComentarios.setItems(listaComentarios);
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}
