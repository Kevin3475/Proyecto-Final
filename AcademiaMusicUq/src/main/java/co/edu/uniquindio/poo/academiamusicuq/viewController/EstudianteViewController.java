package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.EstudianteController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class EstudianteViewController {

    // ===== COMPONENTES PESTAÑA DATOS PERSONALES =====
    @FXML private TextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    @FXML private ComboBox<Nivel> cbNivel;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, String> colId, colNombre, colApellido, colEmail, colTelefono, colNivel;
    @FXML private Button btnAgregar, btnActualizar, btnEliminar, btnLimpiar;

    // ===== COMPONENTES PESTAÑA GESTIÓN ACADÉMICA =====
    @FXML private ComboBox<Estudiante> cbEstudianteAcademico, cbEstudianteClases, cbEstudianteHorario;
    @FXML private ComboBox<Curso> cbCursosInscribir;
    @FXML private ComboBox<ClaseGrupal> cbClasesGrupales;
    @FXML private ComboBox<ClaseIndividual> cbClasesIndividuales;
    @FXML private TextArea txtHorario;
    @FXML private Button btnInscribirCurso, btnInscribirClaseGrupal, btnAgregarClaseIndividual, btnConsultarHorario;

    // ===== COMPONENTES PESTAÑA PROGRESO =====
    @FXML private ComboBox<Estudiante> cbEstudianteReporte;
    @FXML private TableView<ReporteProgreso> tblReportes;
    @FXML private TableView<Asistencia> tblAsistencias;
    @FXML private TableColumn<ReporteProgreso, String> colCursoReporte, colProfesorReporte, colObservaciones, colCalificacion, colAprobado;
    @FXML private TableColumn<Asistencia, String> colClaseAsistencia, colFechaAsistencia, colPresente;
    @FXML private Button btnGenerarReporte, btnCargarAsistencias;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<ClaseGrupal> listaClasesGrupales = FXCollections.observableArrayList();
    private ObservableList<ClaseIndividual> listaClasesIndividuales = FXCollections.observableArrayList();
    private ObservableList<ReporteProgreso> listaReportes = FXCollections.observableArrayList();
    private ObservableList<Asistencia> listaAsistencias = FXCollections.observableArrayList();

    private Estudiante estudianteSeleccionado;
    private EstudianteController estudianteController;
    private App app;

    @FXML
    void initialize() {
        this.estudianteController = new EstudianteController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosPersonales();
        configurarPestanaGestionAcademica();
        configurarPestanaProgreso();
    }

    // ===== PESTAÑA 1: DATOS PERSONALES =====
    private void configurarPestanaDatosPersonales() {
        // Configurar tabla estudiantes
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colApellido.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getApellido()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));
        colNivel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNivel().toString()));

        tblEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            estudianteSeleccionado = newVal;
            mostrarEstudianteSeleccionado();
        });

        // Configurar combo box nivel
        cbNivel.setItems(FXCollections.observableArrayList(Nivel.values()));
    }

    // ===== PESTAÑA 2: GESTIÓN ACADÉMICA =====
    private void configurarPestanaGestionAcademica() {
        // Cargar combos de estudiantes
        cbEstudianteAcademico.setItems(listaEstudiantes);
        cbEstudianteClases.setItems(listaEstudiantes);
        cbEstudianteHorario.setItems(listaEstudiantes);

        // Cargar combos con datos de la academia
        cargarCombosGestionAcademica();
    }

    // ===== PESTAÑA 3: PROGRESO =====
    private void configurarPestanaProgreso() {
        // Configurar tabla reportes
        colCursoReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colProfesorReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ? cell.getValue().getProfesor().getNombre() : "N/A"));
        colObservaciones.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getObservaciones()));
        colCalificacion.setCellValueFactory(cell -> new SimpleStringProperty(
                String.valueOf(cell.getValue().getCalificacion())));
        colAprobado.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isAprobado() ? "SÍ" : "NO"));

        // Configurar tabla asistencias
        colClaseAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getClase() != null ? "Clase " + cell.getValue().getClase().getId() : "N/A"));
        colFechaAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFecha().toString()));
        colPresente.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getPresente() ? "PRESENTE" : "AUSENTE"));

        // Configurar combo estudiante para reportes
        cbEstudianteReporte.setItems(listaEstudiantes);
    }

    private void cargarDatosIniciales() {
        cargarEstudiantes();
        cargarCombosGestionAcademica();
    }

    private void cargarEstudiantes() {
        listaEstudiantes.clear();
        listaEstudiantes.addAll(estudianteController.obtenerEstudiantes());
        tblEstudiantes.setItems(listaEstudiantes);
    }

    private void cargarCombosGestionAcademica() {
        // Cargar cursos disponibles
        listaCursos.clear();
        listaCursos.addAll(App.academia.getListCursos());
        cbCursosInscribir.setItems(listaCursos);

        // Cargar clases grupales disponibles
        listaClasesGrupales.clear();
        // Esto se implementará cuando tengamos la gestión de clases
        cbClasesGrupales.setItems(listaClasesGrupales);

        // Cargar clases individuales disponibles
        listaClasesIndividuales.clear();
        // Esto se implementará cuando tengamos la gestión de clases
        cbClasesIndividuales.setItems(listaClasesIndividuales);
    }

    // ===== MÉTODOS PESTAÑA DATOS PERSONALES =====
    private void mostrarEstudianteSeleccionado() {
        if (estudianteSeleccionado != null) {
            txtId.setText(estudianteSeleccionado.getId());
            txtNombre.setText(estudianteSeleccionado.getNombre());
            txtApellido.setText(estudianteSeleccionado.getApellido());
            txtEmail.setText(estudianteSeleccionado.getEmail());
            txtTelefono.setText(estudianteSeleccionado.getTelefono());
            cbNivel.setValue(estudianteSeleccionado.getNivel());
        }
    }

    @FXML
    void onAgregarEstudiante() {
        if (validarCamposDatosPersonales()) {
            Estudiante estudiante = new Estudiante(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    cbNivel.getValue(),
                    true
            );

            if (estudianteController.registrarEstudiante(estudiante)) {
                listaEstudiantes.add(estudiante);
                limpiarCamposDatosPersonales();
                mostrarAlerta("Éxito", "Estudiante registrado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Ya existe un estudiante con esta ID", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarEstudiante() {
        if (estudianteSeleccionado != null && validarCamposDatosPersonales()) {
            Estudiante actualizado = new Estudiante(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    cbNivel.getValue(),
                    true
            );

            if (estudianteController.actualizarEstudiante(estudianteSeleccionado.getId(), actualizado)) {
                cargarEstudiantes();
                limpiarCamposDatosPersonales();
                mostrarAlerta("Éxito", "Estudiante actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Error al actualizar el estudiante", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarEstudiante() {
        if (estudianteSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este estudiante?");
            confirmacion.setContentText("Estudiante: " + estudianteSeleccionado.getNombre() + " " + estudianteSeleccionado.getApellido());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (estudianteController.eliminarEstudiante(estudianteSeleccionado.getId())) {
                    listaEstudiantes.remove(estudianteSeleccionado);
                    limpiarCamposDatosPersonales();
                    mostrarAlerta("Éxito", "Estudiante eliminado correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al eliminar el estudiante", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCampos() {
        limpiarCamposDatosPersonales();
        tblEstudiantes.getSelectionModel().clearSelection();
        estudianteSeleccionado = null;
    }

    // ===== MÉTODOS PESTAÑA GESTIÓN ACADÉMICA =====
    @FXML
    void onInscribirCurso() {
        if (cbEstudianteAcademico.getValue() != null && cbCursosInscribir.getValue() != null) {
            Estudiante estudiante = cbEstudianteAcademico.getValue();
            Curso curso = cbCursosInscribir.getValue();

            if (estudianteController.inscribirEstudianteEnCurso(estudiante, curso)) {
                mostrarAlerta("Éxito", "Estudiante inscrito en el curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo inscribir al estudiante en el curso. Verifique que el estudiante tenga el nivel requerido.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante y un curso", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onInscribirClaseGrupal() {
        if (cbEstudianteClases.getValue() != null && cbClasesGrupales.getValue() != null) {
            Estudiante estudiante = cbEstudianteClases.getValue();
            ClaseGrupal clase = cbClasesGrupales.getValue();

            if (estudianteController.inscribirEnClaseGrupal(estudiante, clase)) {
                mostrarAlerta("Éxito", "Estudiante inscrito en clase grupal correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo inscribir al estudiante en la clase grupal. Verifique que haya cupo disponible.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante y una clase grupal", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onAgregarClaseIndividual() {
        if (cbEstudianteClases.getValue() != null && cbClasesIndividuales.getValue() != null) {
            Estudiante estudiante = cbEstudianteClases.getValue();
            ClaseIndividual clase = cbClasesIndividuales.getValue();

            if (estudianteController.agregarClaseIndividual(estudiante, clase)) {
                mostrarAlerta("Éxito", "Clase individual agregada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo agregar la clase individual", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante y una clase individual", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onConsultarHorario() {
        if (cbEstudianteHorario.getValue() != null) {
            Estudiante estudiante = cbEstudianteHorario.getValue();
            String horario = estudianteController.consultarHorarioEstudiante(estudiante);
            txtHorario.setText(horario);
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante para consultar su horario", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS PESTAÑA PROGRESO =====
    @FXML
    void onGenerarReporte() {
        if (cbEstudianteReporte.getValue() != null) {
            ReporteProgreso reporte = cbEstudianteReporte.getValue().generarReporteProgreso();
            listaReportes.add(reporte);
            tblReportes.setItems(listaReportes);
            mostrarAlerta("Éxito", "Reporte generado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante para generar reporte", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onCargarAsistencias() {
        if (cbEstudianteReporte.getValue() != null) {
            // Cargar asistencias del estudiante seleccionado
            listaAsistencias.clear();
            listaAsistencias.addAll(cbEstudianteReporte.getValue().getListAsistencias());
            tblAsistencias.setItems(listaAsistencias);
            mostrarAlerta("Éxito", "Asistencias cargadas correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione un estudiante para cargar asistencias", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS GENERALES =====
    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposDatosPersonales() {
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtTelefono.clear();
        cbNivel.setValue(null);
        txtId.requestFocus();
    }

    private boolean validarCamposDatosPersonales() {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || cbNivel.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if (!txtEmail.getText().contains("@")) {
            mostrarAlerta("Error", "Ingrese un email válido", Alert.AlertType.ERROR);
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

    public void setApp(App app) {
        this.app = app;
    }
}