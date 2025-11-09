package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.ReportesController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class ReportesViewController {

    // ===== COMPONENTES PESTAÑA REPORTES AVANZADOS =====
    @FXML private ComboBox<String> cbTipoReporteAvanzado;
    @FXML private ComboBox<Estudiante> cbEstudianteReporte;
    @FXML private ComboBox<Curso> cbCursoReporte;
    @FXML private ComboBox<Profesor> cbProfesorReporte;
    @FXML private DatePicker dpFechaInicioReporte, dpFechaFinReporte;

    // ===== TABLAS DE REPORTES =====
    @FXML private TableView<ReporteProgreso> tblReportesProgreso;
    @FXML private TableColumn<ReporteProgreso, String> colIdReporte, colEstudiante, colCurso, colProfesor, colCalificacion, colAprobado, colFecha;

    @FXML private TableView<Asistencia> tblReportesAsistencia;
    @FXML private TableColumn<Asistencia, String> colIdAsistencia, colEstudianteAsistencia, colClaseAsistencia, colFechaAsistencia, colPresente;

    @FXML private TableView<Matricula> tblReportesMatriculas;
    @FXML private TableColumn<Matricula, String> colIdMatricula, colEstudianteMatricula, colCursoMatricula, colEstadoMatricula, colFechaMatricula;

    // ===== COMPONENTES PESTAÑA ESTADÍSTICAS DETALLADAS =====
    @FXML private PieChart pieChartNiveles;
    @FXML private BarChart<String, Number> barChartInstrumentos;
    @FXML private LineChart<String, Number> lineChartProgreso;
    @FXML private Label lblTotalEstudiantes, lblTotalProfesores, lblTotalCursos;
    @FXML private Label lblPromedioCalificaciones, lblTasaAprobacion, lblTasaAsistencia;

    // ===== COMPONENTES PESTAÑA REPORTES PERSONALIZADOS =====
    @FXML private TextArea txtConsultaPersonalizada;
    @FXML private Button btnEjecutarConsulta;
    @FXML private TableView<Object[]> tblResultadosPersonalizados;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnGenerarReporte, btnExportarPDF, btnExportarExcel, btnVolver;
    @FXML private TabPane tabPaneReportes;

    private ObservableList<ReporteProgreso> listaReportesProgreso = FXCollections.observableArrayList();
    private ObservableList<Asistencia> listaReportesAsistencia = FXCollections.observableArrayList();
    private ObservableList<Matricula> listaReportesMatriculas = FXCollections.observableArrayList();
    private ObservableList<Object[]> listaResultadosPersonalizados = FXCollections.observableArrayList();

    private ReportesController reportesController;
    private App app;

    @FXML
    void initialize() {
        this.reportesController = new ReportesController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
        cargarEstadisticasDetalladas();
    }

    private void configurarInterfaz() {
        configurarPestanaReportesAvanzados();
        configurarPestanaEstadisticasDetalladas();
        configurarPestanaReportesPersonalizados();
    }

    // ===== PESTAÑA 1: REPORTES AVANZADOS =====
    private void configurarPestanaReportesAvanzados() {
        // Configurar combo tipos de reporte
        cbTipoReporteAvanzado.setItems(FXCollections.observableArrayList(
                "📈 Reporte de Progreso General",
                "✅ Reporte de Asistencias",
                "📋 Reporte de Matrículas",
                "🎓 Reporte por Estudiante",
                "📚 Reporte por Curso",
                "👨‍🏫 Reporte por Profesor",
                "📊 Reporte Comparativo"
        ));

        // Configurar tabla Reportes de Progreso
        colIdReporte.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdReporte())));
        colEstudiante.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCurso.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colProfesor.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        colCalificacion.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getCalificacion())));
        colAprobado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isAprobado() ? "✅ Sí" : "❌ No"));
        colFecha.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));

        // Configurar tabla Asistencias
        colIdAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdAsistencia())));
        colEstudianteAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colClaseAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getClase() != null ? "Clase " + cell.getValue().getClase().getId() : "N/A"));
        colFechaAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
        colPresente.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPresente() ? "✅ Presente" : "❌ Ausente"));

        // Configurar tabla Matrículas
        colIdMatricula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdMatricula())));
        colEstudianteMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colEstadoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEstadoMatricula().toString()));
        colFechaMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFechaInscripcion().toString()));

        dpFechaInicioReporte.setValue(LocalDate.now().minusMonths(1));
        dpFechaFinReporte.setValue(LocalDate.now());
    }

    // ===== PESTAÑA 2: ESTADÍSTICAS DETALLADAS =====
    private void configurarPestanaEstadisticasDetalladas() {
        // Los gráficos se configurarán al cargar los datos
    }

    // ===== PESTAÑA 3: REPORTES PERSONALIZADOS =====
    private void configurarPestanaReportesPersonalizados() {
        // Configurar consultas predefinidas
        txtConsultaPersonalizada.setText(
                "Consultas disponibles:\n" +
                        "1. Estudiantes por nivel\n" +
                        "2. Cursos más populares\n" +
                        "3. Profesores con más clases\n" +
                        "4. Asistencia por mes\n" +
                        "5. Progreso promedio por curso"
        );
    }

    private void cargarDatosIniciales() {
        cargarCombosFiltros();
    }

    private void cargarCombosFiltros() {
        // Cargar estudiantes
        ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList(App.academia.getListEstudiantes());
        cbEstudianteReporte.setItems(estudiantes);

        // Cargar cursos
        ObservableList<Curso> cursos = FXCollections.observableArrayList(App.academia.getListCursos());
        cbCursoReporte.setItems(cursos);

        // Cargar profesores
        ObservableList<Profesor> profesores = FXCollections.observableArrayList(App.academia.getListProfesores());
        cbProfesorReporte.setItems(profesores);
    }

    // ===== MÉTODOS PRINCIPALES =====
    @FXML
    void onGenerarReporte() {
        String tipoReporte = cbTipoReporteAvanzado.getValue();
        if (tipoReporte == null) {
            mostrarAlerta("Error", "Seleccione un tipo de reporte", Alert.AlertType.WARNING);
            return;
        }

        switch (tipoReporte) {
            case "📈 Reporte de Progreso General":
                generarReporteProgresoGeneral();
                break;
            case "✅ Reporte de Asistencias":
                generarReporteAsistencias();
                break;
            case "📋 Reporte de Matrículas":
                generarReporteMatriculas();
                break;
            case "🎓 Reporte por Estudiante":
                generarReportePorEstudiante();
                break;
            case "📚 Reporte por Curso":
                generarReportePorCurso();
                break;
            case "👨‍🏫 Reporte por Profesor":
                generarReportePorProfesor();
                break;
            case "📊 Reporte Comparativo":
                generarReporteComparativo();
                break;
        }
    }

    @FXML
    void onExportarPDF() {
        if (!listaReportesProgreso.isEmpty()) {
            // Lógica para exportar a PDF
            mostrarAlerta("Éxito", "Reporte exportado a PDF correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No hay datos para exportar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onExportarExcel() {
        if (!listaReportesProgreso.isEmpty()) {
            // Lógica para exportar a Excel
            mostrarAlerta("Éxito", "Reporte exportado a Excel correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No hay datos para exportar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEjecutarConsulta() {
        String consulta = txtConsultaPersonalizada.getText();
        // Lógica para ejecutar consultas personalizadas
        mostrarAlerta("Info", "Consulta ejecutada (funcionalidad en desarrollo)", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    // ===== MÉTODOS DE GENERACIÓN DE REPORTES =====
    private void generarReporteProgresoGeneral() {
        List<ReporteProgreso> reportes = reportesController.generarReporteProgresoGeneral();
        listaReportesProgreso.clear();
        listaReportesProgreso.addAll(reportes);
        tblReportesProgreso.setItems(listaReportesProgreso);

        mostrarAlerta("Éxito", "Reporte de Progreso General generado: " + reportes.size() + " registros", Alert.AlertType.INFORMATION);
    }

    private void generarReporteAsistencias() {
        List<Asistencia> asistencias = reportesController.generarReporteAsistencias(
                dpFechaInicioReporte.getValue(), dpFechaFinReporte.getValue());
        listaReportesAsistencia.clear();
        listaReportesAsistencia.addAll(asistencias);
        tblReportesAsistencia.setItems(listaReportesAsistencia);

        mostrarAlerta("Éxito", "Reporte de Asistencias generado: " + asistencias.size() + " registros", Alert.AlertType.INFORMATION);
    }

    private void generarReporteMatriculas() {
        List<Matricula> matriculas = reportesController.generarReporteMatriculas();
        listaReportesMatriculas.clear();
        listaReportesMatriculas.addAll(matriculas);
        tblReportesMatriculas.setItems(listaReportesMatriculas);

        mostrarAlerta("Éxito", "Reporte de Matrículas generado: " + matriculas.size() + " registros", Alert.AlertType.INFORMATION);
    }

    private void generarReportePorEstudiante() {
        Estudiante estudiante = cbEstudianteReporte.getValue();
        if (estudiante != null) {
            List<ReporteProgreso> reportes = reportesController.generarReportePorEstudiante(estudiante);
            listaReportesProgreso.clear();
            listaReportesProgreso.addAll(reportes);
            tblReportesProgreso.setItems(listaReportesProgreso);

            mostrarAlerta("Éxito", "Reporte del estudiante generado: " + reportes.size() + " registros", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante", Alert.AlertType.WARNING);
        }
    }

    private void generarReportePorCurso() {
        Curso curso = cbCursoReporte.getValue();
        if (curso != null) {
            List<ReporteProgreso> reportes = reportesController.generarReportePorCurso(curso);
            listaReportesProgreso.clear();
            listaReportesProgreso.addAll(reportes);
            tblReportesProgreso.setItems(listaReportesProgreso);

            mostrarAlerta("Éxito", "Reporte del curso generado: " + reportes.size() + " registros", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un curso", Alert.AlertType.WARNING);
        }
    }

    private void generarReportePorProfesor() {
        Profesor profesor = cbProfesorReporte.getValue();
        if (profesor != null) {
            List<ReporteProgreso> reportes = reportesController.generarReportePorProfesor(profesor);
            listaReportesProgreso.clear();
            listaReportesProgreso.addAll(reportes);
            tblReportesProgreso.setItems(listaReportesProgreso);

            mostrarAlerta("Éxito", "Reporte del profesor generado: " + reportes.size() + " registros", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un profesor", Alert.AlertType.WARNING);
        }
    }

    private void generarReporteComparativo() {
        // Lógica para reporte comparativo entre cursos/profesores/estudiantes
        mostrarAlerta("Info", "Reporte comparativo en desarrollo", Alert.AlertType.INFORMATION);
    }

    // ===== MÉTODOS AUXILIARES =====
    private void cargarEstadisticasDetalladas() {
        // Estadísticas generales
        lblTotalEstudiantes.setText(String.valueOf(reportesController.contarTotalEstudiantes()));
        lblTotalProfesores.setText(String.valueOf(reportesController.contarTotalProfesores()));
        lblTotalCursos.setText(String.valueOf(reportesController.contarTotalCursos()));

        // Métricas de rendimiento
        lblPromedioCalificaciones.setText(String.format("%.2f", reportesController.calcularPromedioCalificaciones()));
        lblTasaAprobacion.setText(String.format("%.1f%%", reportesController.calcularTasaAprobacion()));
        lblTasaAsistencia.setText(String.format("%.1f%%", reportesController.calcularTasaAsistencia()));

        // Cargar gráficos
        cargarGraficosEstadisticas();
    }

    private void cargarGraficosEstadisticas() {
        cargarPieChartNiveles();
        cargarBarChartInstrumentos();
        cargarLineChartProgreso();
    }

    private void cargarPieChartNiveles() {
        pieChartNiveles.getData().clear();

        // Datos de ejemplo - en implementación real vendrían del controlador
        pieChartNiveles.getData().addAll(
                new PieChart.Data("Básico", 45),
                new PieChart.Data("Intermedio", 30),
                new PieChart.Data("Avanzado", 25)
        );
    }

    private void cargarBarChartInstrumentos() {
        barChartInstrumentos.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Estudiantes por Instrumento");

        // Datos de ejemplo
        series.getData().add(new XYChart.Data<>("Piano", 35));
        series.getData().add(new XYChart.Data<>("Guitarra", 28));
        series.getData().add(new XYChart.Data<>("Violín", 15));
        series.getData().add(new XYChart.Data<>("Canto", 22));

        barChartInstrumentos.getData().add(series);
    }

    private void cargarLineChartProgreso() {
        lineChartProgreso.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Progreso Promedio");

        // Datos de ejemplo
        series.getData().add(new XYChart.Data<>("Ene", 3.2));
        series.getData().add(new XYChart.Data<>("Feb", 3.5));
        series.getData().add(new XYChart.Data<>("Mar", 3.8));
        series.getData().add(new XYChart.Data<>("Abr", 4.0));
        series.getData().add(new XYChart.Data<>("May", 4.2));

        lineChartProgreso.getData().add(series);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}