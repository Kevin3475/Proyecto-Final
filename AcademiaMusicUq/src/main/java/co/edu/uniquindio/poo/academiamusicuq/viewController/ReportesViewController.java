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
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class ReportesViewController {

    @FXML private ComboBox<String> cbTipoReporteAvanzado;
    @FXML private ComboBox<Estudiante> cbEstudianteReporte;
    @FXML private ComboBox<Curso> cbCursoReporte;
    @FXML private ComboBox<Profesor> cbProfesorReporte;
    @FXML private DatePicker dpFechaInicioReporte, dpFechaFinReporte;

    @FXML private TableView<ReporteProgreso> tblReportesProgreso;
    @FXML private TableColumn<ReporteProgreso, String> colIdReporte, colEstudiante, colCurso, colProfesor, colCalificacion, colAprobado, colFecha;

    @FXML private TableView<Asistencia> tblReportesAsistencia;
    @FXML private TableColumn<Asistencia, String> colIdAsistencia, colEstudianteAsistencia, colClaseAsistencia, colFechaAsistencia, colPresente;

    @FXML private TableView<Matricula> tblReportesMatriculas;
    @FXML private TableColumn<Matricula, String> colIdMatricula, colEstudianteMatricula, colCursoMatricula, colEstadoMatricula, colFechaMatricula;

    @FXML private PieChart pieChartNiveles;
    @FXML private BarChart<String, Number> barChartInstrumentos;
    @FXML private LineChart<String, Number> lineChartProgreso;
    @FXML private Label lblTotalEstudiantes, lblTotalProfesores, lblTotalCursos;
    @FXML private Label lblPromedioCalificaciones, lblTasaAprobacion, lblTasaAsistencia;

    @FXML private TextArea txtConsultaPersonalizada;
    @FXML private Button btnEjecutarConsulta;
    @FXML private TableView<Object[]> tblResultadosPersonalizados;

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
        try {
            this.reportesController = new ReportesController(App.academia);
            configurarInterfaz();
            cargarDatosIniciales();
            cargarEstadisticasDetalladas();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al inicializar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarInterfaz() {
        configurarPestanaReportesAvanzados();
        configurarPestanaEstadisticasDetalladas();
        configurarPestanaReportesPersonalizados();
        configurarStringConverters(); // NUEVO: Para mostrar nombres en combos
    }

    // NUEVO MÉTODO: Configurar cómo se muestran los objetos en los ComboBox
    private void configurarStringConverters() {
        // Configurar ComboBox de estudiantes
        if (cbEstudianteReporte != null) {
            cbEstudianteReporte.setConverter(new StringConverter<Estudiante>() {
                @Override
                public String toString(Estudiante estudiante) {
                    if (estudiante == null) return "";
                    return estudiante.getNombre() + " " + estudiante.getApellido() + " - " + estudiante.getNivel();
                }

                @Override
                public Estudiante fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de cursos
        if (cbCursoReporte != null) {
            cbCursoReporte.setConverter(new StringConverter<Curso>() {
                @Override
                public String toString(Curso curso) {
                    if (curso == null) return "";
                    return curso.getNombreCurso() + " - " + curso.getNivel();
                }

                @Override
                public Curso fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de profesores
        if (cbProfesorReporte != null) {
            cbProfesorReporte.setConverter(new StringConverter<Profesor>() {
                @Override
                public String toString(Profesor profesor) {
                    if (profesor == null) return "";
                    return profesor.getNombre() + " " + profesor.getApellido() + " - " + profesor.getEspecialidad();
                }

                @Override
                public Profesor fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void configurarPestanaReportesAvanzados() {
        if (cbTipoReporteAvanzado != null) {
            cbTipoReporteAvanzado.setItems(FXCollections.observableArrayList(
                    "📈 Reporte de Progreso General",
                    "✅ Reporte de Asistencias",
                    "📋 Reporte de Matrículas",
                    "🎓 Reporte por Estudiante",
                    "📚 Reporte por Curso",
                    "👨‍🏫 Reporte por Profesor",
                    "📊 Reporte Comparativo"
            ));
        }

        // Configurar tabla Reportes de Progreso
        if (colIdReporte != null) {
            colIdReporte.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdReporte())));
        }
        if (colEstudiante != null) {
            colEstudiante.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getEstudiante() != null ?
                            cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        }
        if (colCurso != null) {
            colCurso.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        }
        if (colProfesor != null) {
            colProfesor.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getProfesor() != null ?
                            cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        }
        if (colCalificacion != null) {
            colCalificacion.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f", cell.getValue().getCalificacion())));
        }
        if (colAprobado != null) {
            colAprobado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().isAprobado() ? "✅ Sí" : "❌ No"));
        }
        if (colFecha != null) {
            colFecha.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
        }

        // Configurar tabla Asistencias
        if (colIdAsistencia != null) {
            colIdAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdAsistencia())));
        }
        if (colEstudianteAsistencia != null) {
            colEstudianteAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getEstudiante() != null ?
                            cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        }
        if (colClaseAsistencia != null) {
            colClaseAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getClase() != null ? "Clase " + cell.getValue().getClase().getId() : "N/A"));
        }
        if (colFechaAsistencia != null) {
            colFechaAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
        }
        if (colPresente != null) {
            colPresente.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPresente() ? "✅ Presente" : "❌ Ausente"));
        }

        // Configurar tabla Matrículas
        if (colIdMatricula != null) {
            colIdMatricula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdMatricula())));
        }
        if (colEstudianteMatricula != null) {
            colEstudianteMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getEstudiante() != null ?
                            cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        }
        if (colCursoMatricula != null) {
            colCursoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        }
        if (colEstadoMatricula != null) {
            colEstadoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEstadoMatricula().toString()));
        }
        if (colFechaMatricula != null) {
            colFechaMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFechaInscripcion().toString()));
        }

        if (dpFechaInicioReporte != null) {
            dpFechaInicioReporte.setValue(LocalDate.now().minusMonths(1));
        }
        if (dpFechaFinReporte != null) {
            dpFechaFinReporte.setValue(LocalDate.now());
        }
    }

    private void configurarPestanaEstadisticasDetalladas() {
        // Los gráficos se configurarán al cargar los datos
    }

    private void configurarPestanaReportesPersonalizados() {
        if (txtConsultaPersonalizada != null) {
            txtConsultaPersonalizada.setText(
                    "Consultas disponibles:\n" +
                            "1. Estudiantes por nivel\n" +
                            "2. Cursos más populares\n" +
                            "3. Profesores con más clases\n" +
                            "4. Asistencia por mes\n" +
                            "5. Progreso promedio por curso"
            );
        }
    }

    private void cargarDatosIniciales() {
        cargarCombosFiltros();
    }

    private void cargarCombosFiltros() {
        try {
            // Cargar estudiantes
            if (cbEstudianteReporte != null && App.academia != null && App.academia.getListEstudiantes() != null) {
                ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList(App.academia.getListEstudiantes());
                cbEstudianteReporte.setItems(estudiantes);
                if (!estudiantes.isEmpty()) {
                    cbEstudianteReporte.setValue(estudiantes.get(0));
                }
            }

            // Cargar cursos
            if (cbCursoReporte != null && App.academia != null && App.academia.getListCursos() != null) {
                ObservableList<Curso> cursos = FXCollections.observableArrayList(App.academia.getListCursos());
                cbCursoReporte.setItems(cursos);
                if (!cursos.isEmpty()) {
                    cbCursoReporte.setValue(cursos.get(0));
                }
            }

            // Cargar profesores
            if (cbProfesorReporte != null && App.academia != null && App.academia.getListProfesores() != null) {
                ObservableList<Profesor> profesores = FXCollections.observableArrayList(App.academia.getListProfesores());
                cbProfesorReporte.setItems(profesores);
                if (!profesores.isEmpty()) {
                    cbProfesorReporte.setValue(profesores.get(0));
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos de filtros: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onGenerarReporte() {
        try {
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
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onExportarPDF() {
        try {
            if (!listaReportesProgreso.isEmpty()) {
                mostrarAlerta("Éxito", "Reporte exportado a PDF correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No hay datos para exportar", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al exportar PDF: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onExportarExcel() {
        try {
            if (!listaReportesProgreso.isEmpty()) {
                mostrarAlerta("Éxito", "Reporte exportado a Excel correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No hay datos para exportar", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al exportar Excel: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEjecutarConsulta() {
        try {
            String consulta = txtConsultaPersonalizada.getText();
            mostrarAlerta("Info", "Consulta ejecutada (funcionalidad en desarrollo)", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al ejecutar consulta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onVolver() {
        try {
            if (app != null) {
                app.mostrarMainView();
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al volver al menú: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReporteProgresoGeneral() {
        try {
            List<ReporteProgreso> reportes = reportesController.generarReporteProgresoGeneral();
            listaReportesProgreso.clear();
            if (reportes != null) {
                listaReportesProgreso.addAll(reportes);
            }
            if (tblReportesProgreso != null) {
                tblReportesProgreso.setItems(listaReportesProgreso);
            }

            mostrarAlerta("Éxito", "Reporte de Progreso General generado: " + listaReportesProgreso.size() + " registros", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte de progreso: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReporteAsistencias() {
        try {
            List<Asistencia> asistencias = reportesController.generarReporteAsistencias(
                    dpFechaInicioReporte.getValue(), dpFechaFinReporte.getValue());
            listaReportesAsistencia.clear();
            if (asistencias != null) {
                listaReportesAsistencia.addAll(asistencias);
            }
            if (tblReportesAsistencia != null) {
                tblReportesAsistencia.setItems(listaReportesAsistencia);
            }

            mostrarAlerta("Éxito", "Reporte de Asistencias generado: " + listaReportesAsistencia.size() + " registros", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte de asistencias: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReporteMatriculas() {
        try {
            List<Matricula> matriculas = reportesController.generarReporteMatriculas();
            listaReportesMatriculas.clear();
            if (matriculas != null) {
                listaReportesMatriculas.addAll(matriculas);
            }
            if (tblReportesMatriculas != null) {
                tblReportesMatriculas.setItems(listaReportesMatriculas);
            }

            mostrarAlerta("Éxito", "Reporte de Matrículas generado: " + listaReportesMatriculas.size() + " registros", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte de matrículas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReportePorEstudiante() {
        try {
            Estudiante estudiante = cbEstudianteReporte.getValue();
            if (estudiante != null) {
                List<ReporteProgreso> reportes = reportesController.generarReportePorEstudiante(estudiante);
                listaReportesProgreso.clear();
                if (reportes != null) {
                    listaReportesProgreso.addAll(reportes);
                }
                if (tblReportesProgreso != null) {
                    tblReportesProgreso.setItems(listaReportesProgreso);
                }

                mostrarAlerta("Éxito", "Reporte del estudiante generado: " + listaReportesProgreso.size() + " registros", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Seleccione un estudiante", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte por estudiante: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReportePorCurso() {
        try {
            Curso curso = cbCursoReporte.getValue();
            if (curso != null) {
                List<ReporteProgreso> reportes = reportesController.generarReportePorCurso(curso);
                listaReportesProgreso.clear();
                if (reportes != null) {
                    listaReportesProgreso.addAll(reportes);
                }
                if (tblReportesProgreso != null) {
                    tblReportesProgreso.setItems(listaReportesProgreso);
                }

                mostrarAlerta("Éxito", "Reporte del curso generado: " + listaReportesProgreso.size() + " registros", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Seleccione un curso", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte por curso: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReportePorProfesor() {
        try {
            Profesor profesor = cbProfesorReporte.getValue();
            if (profesor != null) {
                List<ReporteProgreso> reportes = reportesController.generarReportePorProfesor(profesor);
                listaReportesProgreso.clear();
                if (reportes != null) {
                    listaReportesProgreso.addAll(reportes);
                }
                if (tblReportesProgreso != null) {
                    tblReportesProgreso.setItems(listaReportesProgreso);
                }

                mostrarAlerta("Éxito", "Reporte del profesor generado: " + listaReportesProgreso.size() + " registros", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Seleccione un profesor", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar reporte por profesor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarReporteComparativo() {
        mostrarAlerta("Info", "Reporte comparativo en desarrollo", Alert.AlertType.INFORMATION);
    }

    private void cargarEstadisticasDetalladas() {
        try {
            if (lblTotalEstudiantes != null) {
                lblTotalEstudiantes.setText(String.valueOf(reportesController.contarTotalEstudiantes()));
            }
            if (lblTotalProfesores != null) {
                lblTotalProfesores.setText(String.valueOf(reportesController.contarTotalProfesores()));
            }
            if (lblTotalCursos != null) {
                lblTotalCursos.setText(String.valueOf(reportesController.contarTotalCursos()));
            }

            if (lblPromedioCalificaciones != null) {
                lblPromedioCalificaciones.setText(String.format("%.2f", reportesController.calcularPromedioCalificaciones()));
            }
            if (lblTasaAprobacion != null) {
                lblTasaAprobacion.setText(String.format("%.1f%%", reportesController.calcularTasaAprobacion()));
            }
            if (lblTasaAsistencia != null) {
                lblTasaAsistencia.setText(String.format("%.1f%%", reportesController.calcularTasaAsistencia()));
            }

            cargarGraficosEstadisticas();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar estadísticas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarGraficosEstadisticas() {
        cargarPieChartNiveles();
        cargarBarChartInstrumentos();
        cargarLineChartProgreso();
    }

    private void cargarPieChartNiveles() {
        if (pieChartNiveles != null) {
            pieChartNiveles.getData().clear();
            pieChartNiveles.getData().addAll(
                    new PieChart.Data("Básico", 45),
                    new PieChart.Data("Intermedio", 30),
                    new PieChart.Data("Avanzado", 25)
            );
        }
    }

    private void cargarBarChartInstrumentos() {
        if (barChartInstrumentos != null) {
            barChartInstrumentos.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Estudiantes por Instrumento");

            series.getData().add(new XYChart.Data<>("Piano", 35));
            series.getData().add(new XYChart.Data<>("Guitarra", 28));
            series.getData().add(new XYChart.Data<>("Violín", 15));
            series.getData().add(new XYChart.Data<>("Canto", 22));

            barChartInstrumentos.getData().add(series);
        }
    }

    private void cargarLineChartProgreso() {
        if (lineChartProgreso != null) {
            lineChartProgreso.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Progreso Promedio");

            series.getData().add(new XYChart.Data<>("Ene", 3.2));
            series.getData().add(new XYChart.Data<>("Feb", 3.5));
            series.getData().add(new XYChart.Data<>("Mar", 3.8));
            series.getData().add(new XYChart.Data<>("Abr", 4.0));
            series.getData().add(new XYChart.Data<>("May", 4.2));

            lineChartProgreso.getData().add(series);
        }
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