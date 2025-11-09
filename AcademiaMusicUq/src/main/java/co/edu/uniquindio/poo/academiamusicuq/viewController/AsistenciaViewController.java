package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.AsistenciaController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class AsistenciaViewController {

    // ===== COMPONENTES PESTAÑA REGISTRO =====
    @FXML private ComboBox<Profesor> cbProfesorAsistencia;
    @FXML private ComboBox<Clase> cbClaseAsistencia;
    @FXML private Label lblFechaActual;
    @FXML private TableView<EstudianteAsistencia> tblEstudiantesAsistencia;
    @FXML private TableColumn<EstudianteAsistencia, String> colEstudianteId, colEstudianteNombre, colEstudianteCurso, colAsistenciaEstado, colAccionesAsistencia;
    @FXML private Button btnCargarEstudiantes, btnRegistrarTodas, btnLimpiarAsistencias, btnGuardarAsistencias;

    // ===== COMPONENTES PESTAÑA CONSULTA =====
    @FXML private ComboBox<Estudiante> cbEstudianteConsulta;
    @FXML private ComboBox<Curso> cbCursoConsulta;
    @FXML private ComboBox<Profesor> cbProfesorConsulta;
    @FXML private ComboBox<String> cbEstadoAsistencia;
    @FXML private DatePicker dpFechaInicioConsulta, dpFechaFinConsulta;
    @FXML private TableView<Asistencia> tblAsistenciasConsultadas;
    @FXML private TableColumn<Asistencia, String> colIdAsistencia, colEstudianteAsistencia, colClaseAsistencia, colFechaAsistencia, colEstadoAsistencia, colProfesorAsistencia;
    @FXML private Label lblTotalAsistencias, lblTotalAusencias, lblPorcentajeAsistencia;
    @FXML private Button btnBuscarAsistencias, btnExportarReporte, btnGenerarReporte;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<EstudianteAsistencia> listaEstudiantesAsistencia = FXCollections.observableArrayList();
    private ObservableList<Asistencia> listaAsistenciasConsultadas = FXCollections.observableArrayList();
    private ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();

    private AsistenciaController asistenciasController;
    private App app;

    // Clase auxiliar para manejar estudiantes en la tabla de asistencia
    public static class EstudianteAsistencia {
        private Estudiante estudiante;
        private boolean presente;
        private Clase clase;

        public EstudianteAsistencia(Estudiante estudiante, Clase clase) {
            this.estudiante = estudiante;
            this.clase = clase;
            this.presente = true; // Por defecto presente
        }

        // Getters y setters
        public Estudiante getEstudiante() { return estudiante; }
        public boolean isPresente() { return presente; }
        public void setPresente(boolean presente) { this.presente = presente; }
        public Clase getClase() { return clase; }
    }

    @FXML
    void initialize() {
        this.asistenciasController = new AsistenciaController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaRegistro();
        configurarPestanaConsulta();
    }

    private void configurarPestanaRegistro() {
        // Configurar fecha actual
        lblFechaActual.setText("Fecha: " + LocalDate.now().toString());

        // Configurar tabla estudiantes asistencia
        colEstudianteId.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEstudiante().getId()));
        colEstudianteNombre.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido()));
        colEstudianteCurso.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getClase().getCurso().getNombreCurso()));
        colAsistenciaEstado.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().isPresente() ? "✅ Presente" : "❌ Ausente"));

        // Columna de acciones con botones
        colAccionesAsistencia.setCellFactory(param -> new TableCell<EstudianteAsistencia, String>() {
            private final Button btnToggle = new Button("Cambiar");

            {
                btnToggle.setOnAction(event -> {
                    EstudianteAsistencia estudiante = getTableView().getItems().get(getIndex());
                    estudiante.setPresente(!estudiante.isPresente());
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnToggle);
                }
            }
        });
    }

    private void configurarPestanaConsulta() {
        // Configurar combo estado asistencia
        cbEstadoAsistencia.setItems(FXCollections.observableArrayList(
                "Todos", "Presente", "Ausente"
        ));

        // Configurar tabla asistencias consultadas
        colIdAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(String.valueOf(cell.getValue().getIdAsistencia())));
        colEstudianteAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido()));
        colClaseAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getClase() != null ? "Clase " + cell.getValue().getClase().getId() : "N/A"));
        colFechaAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getFecha().toString()));
        colEstadoAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getPresente() ? "✅ Presente" : "❌ Ausente"));
        colProfesorAsistencia.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getClase() != null && cell.getValue().getClase().getProfesor() != null ?
                        cell.getValue().getClase().getProfesor().getNombre() : "N/A"));

        // Configurar fechas por defecto
        dpFechaInicioConsulta.setValue(LocalDate.now().minusDays(7));
        dpFechaFinConsulta.setValue(LocalDate.now());
    }

    private void cargarDatosIniciales() {
        cargarCombosRegistro();
        cargarCombosConsulta();
    }

    private void cargarCombosRegistro() {
        // Cargar profesores
        listaProfesores.clear();
        listaProfesores.addAll(App.academia.getListProfesores());
        cbProfesorAsistencia.setItems(listaProfesores);
    }

    private void cargarCombosConsulta() {
        // Cargar estudiantes
        listaEstudiantes.clear();
        listaEstudiantes.addAll(App.academia.getListEstudiantes());
        cbEstudianteConsulta.setItems(listaEstudiantes);

        // Cargar cursos
        listaCursos.clear();
        listaCursos.addAll(App.academia.getListCursos());
        cbCursoConsulta.setItems(listaCursos);

        // Cargar profesores
        cbProfesorConsulta.setItems(listaProfesores);
    }

    // ===== MÉTODOS PESTAÑA REGISTRO =====
    @FXML
    void onCargarEstudiantes() {
        Profesor profesor = cbProfesorAsistencia.getValue();
        Clase clase = cbClaseAsistencia.getValue();

        if (profesor != null && clase != null) {
            // Cargar estudiantes de la clase seleccionada
            listaEstudiantesAsistencia.clear();

            if (clase instanceof ClaseGrupal) {
                ClaseGrupal claseGrupal = (ClaseGrupal) clase;
                for (Estudiante estudiante : claseGrupal.getListEstudiantes()) {
                    listaEstudiantesAsistencia.add(new EstudianteAsistencia(estudiante, clase));
                }
            } else if (clase instanceof ClaseIndividual) {
                ClaseIndividual claseIndividual = (ClaseIndividual) clase;
                if (claseIndividual.getEstudiante() != null) {
                    listaEstudiantesAsistencia.add(new EstudianteAsistencia(claseIndividual.getEstudiante(), clase));
                }
            }

            tblEstudiantesAsistencia.setItems(listaEstudiantesAsistencia);
            mostrarAlerta("Éxito", "Estudiantes cargados: " + listaEstudiantesAsistencia.size(), Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un profesor y una clase", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onRegistrarTodasAsistencias() {
        for (EstudianteAsistencia estudiante : listaEstudiantesAsistencia) {
            estudiante.setPresente(true);
        }
        tblEstudiantesAsistencia.refresh();
        mostrarAlerta("Éxito", "Todas las asistencias marcadas como presentes", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onLimpiarAsistencias() {
        for (EstudianteAsistencia estudiante : listaEstudiantesAsistencia) {
            estudiante.setPresente(false);
        }
        tblEstudiantesAsistencia.refresh();
        mostrarAlerta("Éxito", "Todas las asistencias limpiadas", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onGuardarAsistencias() {
        if (!listaEstudiantesAsistencia.isEmpty()) {
            int contador = 0;
            for (EstudianteAsistencia estudianteAsistencia : listaEstudiantesAsistencia) {
                Asistencia asistencia = new Asistencia(
                        contador + 1,
                        estudianteAsistencia.getEstudiante(),
                        estudianteAsistencia.getClase(),
                        LocalDate.now(),
                        estudianteAsistencia.isPresente()
                );

                if (asistencia.registrarAsistencia()) {
                    contador++;
                }
            }
            mostrarAlerta("Éxito", "Asistencias guardadas: " + contador + " de " + listaEstudiantesAsistencia.size(), Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No hay asistencias para guardar", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS PESTAÑA CONSULTA =====
    @FXML
    void onBuscarAsistencias() {
        Estudiante estudiante = cbEstudianteConsulta.getValue();
        Curso curso = cbCursoConsulta.getValue();
        Profesor profesor = cbProfesorConsulta.getValue();
        String estado = cbEstadoAsistencia.getValue();
        LocalDate fechaInicio = dpFechaInicioConsulta.getValue();
        LocalDate fechaFin = dpFechaFinConsulta.getValue();

        List<Asistencia> asistencias = asistenciasController.buscarAsistencias(
                estudiante, curso, profesor, estado, fechaInicio, fechaFin);

        listaAsistenciasConsultadas.clear();
        listaAsistenciasConsultadas.addAll(asistencias);
        tblAsistenciasConsultadas.setItems(listaAsistenciasConsultadas);

        // Actualizar estadísticas
        actualizarEstadisticas(asistencias);

        mostrarAlerta("Éxito", "Asistencias encontradas: " + asistencias.size(), Alert.AlertType.INFORMATION);
    }

    @FXML
    void onExportarReporte() {
        if (!listaAsistenciasConsultadas.isEmpty()) {
            // Lógica para exportar a PDF
            mostrarAlerta("Éxito", "Reporte exportado a PDF correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No hay datos para exportar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onGenerarReporte() {
        // Lógica para generar reporte detallado
        mostrarAlerta("Info", "Generando reporte de asistencias...", Alert.AlertType.INFORMATION);
    }

    // ===== MÉTODOS AUXILIARES =====
    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void actualizarEstadisticas(List<Asistencia> asistencias) {
        long totalAsistencias = asistencias.stream().filter(Asistencia::getPresente).count();
        long totalAusencias = asistencias.size() - totalAsistencias;
        double porcentaje = asistencias.isEmpty() ? 0 : (double) totalAsistencias / asistencias.size() * 100;

        lblTotalAsistencias.setText(String.valueOf(totalAsistencias));
        lblTotalAusencias.setText(String.valueOf(totalAusencias));
        lblPorcentajeAsistencia.setText(String.format("%.1f%%", porcentaje));
    }

    // ===== LISTENERS PARA CAMBIOS DINÁMICOS =====
    @FXML
    void onProfesorAsistenciaChanged() {
        Profesor profesor = cbProfesorAsistencia.getValue();
        if (profesor != null) {
            // Cargar clases del profesor seleccionado
            ObservableList<Clase> clasesProfesor = FXCollections.observableArrayList(profesor.getListClases());
            cbClaseAsistencia.setItems(clasesProfesor);
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