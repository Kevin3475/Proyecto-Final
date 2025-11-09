package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.CursoController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class CursoViewController {

    // ===== COMPONENTES PESTAÑA DATOS DEL CURSO =====
    @FXML private TextField txtIdCurso, txtNombreCurso, txtCapacidad;
    @FXML private ComboBox<Instrumento> cbInstrumento;
    @FXML private ComboBox<Nivel> cbNivel;
    @FXML private ComboBox<Profesor> cbProfesor;
    @FXML private TableView<Curso> tblCursos;
    @FXML private TableColumn<Curso, String> colIdCurso, colNombreCurso, colInstrumento, colNivel, colCapacidad, colProfesor;
    @FXML private Button btnAgregarCurso, btnActualizarCurso, btnEliminarCurso, btnLimpiarCurso;

    // ===== COMPONENTES PESTAÑA CONFIGURACIÓN =====
    @FXML private ComboBox<Curso> cbCursoConfiguracion;
    @FXML private ComboBox<Clase> cbClaseAgregar;
    @FXML private TableView<Clase> tblClasesCurso;
    @FXML private TableColumn<Clase, String> colIdClase, colTipoClase, colAulaClase, colHorarioClase;
    @FXML private Button btnAgregarClaseCurso, btnRemoverClaseCurso;

    // ===== COMPONENTES PESTAÑA ESTUDIANTES =====
    @FXML private ComboBox<Curso> cbCursoEstudiantes;
    @FXML private ComboBox<Estudiante> cbEstudianteVerificar;
    @FXML private TableView<Estudiante> tblEstudiantesInscritos;
    @FXML private TableColumn<Estudiante, String> colIdEstudiante, colNombreEstudiante, colNivelEstudiante, colEstadoNivel;
    @FXML private Button btnVerificarNivel, btnAgregarEstudiante, btnRemoverEstudiante;
    @FXML private Label lblResultadoVerificacion;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList();
    private ObservableList<Clase> listaClases = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantesInscritos = FXCollections.observableArrayList();

    private Curso cursoSeleccionado;
    private CursoController cursoController;
    private App app;

    @FXML
    void initialize() {
        this.cursoController = new CursoController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosCurso();
        configurarPestanaConfiguracion();
        configurarPestanaEstudiantes();
    }

    // ===== PESTAÑA 1: DATOS DEL CURSO =====
    private void configurarPestanaDatosCurso() {
        // Configurar tabla cursos
        colIdCurso.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIdCurso()));
        colNombreCurso.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombreCurso()));
        colInstrumento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getInstrumento().toString()));
        colNivel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNivel().toString()));
        colCapacidad.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCapacidad())));
        colProfesor.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "Sin asignar"));

        tblCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            cursoSeleccionado = newVal;
            mostrarCursoSeleccionado();
        });

        // Configurar combo boxes
        cbInstrumento.setItems(FXCollections.observableArrayList(Instrumento.values()));
        cbNivel.setItems(FXCollections.observableArrayList(Nivel.values()));
    }

    // ===== PESTAÑA 2: CONFIGURACIÓN =====
    private void configurarPestanaConfiguracion() {
        // Configurar tabla clases del curso
        colIdClase.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colTipoClase.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoClase().toString()));
        colAulaClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getAula() != null ? cell.getValue().getAula().getNombre() : "N/A"));
        colHorarioClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getHorario() != null ?
                        cell.getValue().getHorario().getDia() + " " + cell.getValue().getHorario().getHoraInicio() + "-" + cell.getValue().getHorario().getHoraFin() :
                        "N/A"));
    }

    // ===== PESTAÑA 3: ESTUDIANTES =====
    private void configurarPestanaEstudiantes() {
        // Configurar tabla estudiantes inscritos
        colIdEstudiante.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colNombreEstudiante.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getNombre() + " " + cell.getValue().getApellido()));
        colNivelEstudiante.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNivel().toString()));
        colEstadoNivel.setCellValueFactory(cell -> {
            if (cbCursoEstudiantes.getValue() != null) {
                boolean nivelAdecuado = cbCursoEstudiantes.getValue().verificarNivelEstudiante(cell.getValue());
                return new SimpleStringProperty(nivelAdecuado ? "✅ Adecuado" : "❌ Inadecuado");
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void cargarDatosIniciales() {
        cargarCursos();
        cargarCombosConfiguracion();
        cargarCombosEstudiantes();
    }

    private void cargarCursos() {
        listaCursos.clear();
        listaCursos.addAll(cursoController.obtenerCursos());
        tblCursos.setItems(listaCursos);

        // Actualizar combos que dependen de cursos
        cbCursoConfiguracion.setItems(listaCursos);
        cbCursoEstudiantes.setItems(listaCursos);
    }

    private void cargarCombosConfiguracion() {
        // Cargar profesores disponibles
        listaProfesores.clear();
        listaProfesores.addAll(App.academia.getListProfesores());
        cbProfesor.setItems(listaProfesores);

        // Cargar clases disponibles
        listaClases.clear();
        // Aquí deberías cargar las clases disponibles de la academia
    }

    private void cargarCombosEstudiantes() {
        // Cargar estudiantes disponibles
        listaEstudiantes.clear();
        listaEstudiantes.addAll(App.academia.getListEstudiantes());
        cbEstudianteVerificar.setItems(listaEstudiantes);
    }

    // ===== MÉTODOS PESTAÑA DATOS DEL CURSO =====
    private void mostrarCursoSeleccionado() {
        if (cursoSeleccionado != null) {
            txtIdCurso.setText(cursoSeleccionado.getIdCurso());
            txtNombreCurso.setText(cursoSeleccionado.getNombreCurso());
            cbInstrumento.setValue(cursoSeleccionado.getInstrumento());
            cbNivel.setValue(cursoSeleccionado.getNivel());
            txtCapacidad.setText(String.valueOf(cursoSeleccionado.getCapacidad()));
            cbProfesor.setValue(cursoSeleccionado.getProfesor());
        }
    }

    @FXML
    void onAgregarCurso() {
        if (validarCamposDatosCurso()) {
            Curso curso = new Curso(
                    txtIdCurso.getText(),
                    txtNombreCurso.getText(),
                    cbInstrumento.getValue(),
                    cbNivel.getValue(),
                    Integer.parseInt(txtCapacidad.getText()),
                    cbProfesor.getValue()
            );

            if (cursoController.registrarCurso(curso)) {
                listaCursos.add(curso);
                limpiarCamposDatosCurso();
                mostrarAlerta("Éxito", "Curso registrado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Ya existe un curso con esta ID o nombre", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarCurso() {
        if (cursoSeleccionado != null && validarCamposDatosCurso()) {
            Curso actualizado = new Curso(
                    txtIdCurso.getText(),
                    txtNombreCurso.getText(),
                    cbInstrumento.getValue(),
                    cbNivel.getValue(),
                    Integer.parseInt(txtCapacidad.getText()),
                    cbProfesor.getValue()
            );

            if (cursoController.actualizarCurso(cursoSeleccionado.getIdCurso(), actualizado)) {
                cargarCursos();
                limpiarCamposDatosCurso();
                mostrarAlerta("Éxito", "Curso actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Error al actualizar el curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarCurso() {
        if (cursoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este curso?");
            confirmacion.setContentText("Curso: " + cursoSeleccionado.getNombreCurso());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (cursoController.eliminarCurso(cursoSeleccionado.getIdCurso())) {
                    listaCursos.remove(cursoSeleccionado);
                    limpiarCamposDatosCurso();
                    mostrarAlerta("Éxito", "Curso eliminado correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al eliminar el curso", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCamposCurso() {
        limpiarCamposDatosCurso();
        tblCursos.getSelectionModel().clearSelection();
        cursoSeleccionado = null;
    }

    // ===== MÉTODOS PESTAÑA CONFIGURACIÓN =====
    @FXML
    void onAgregarClaseCurso() {
        if (cbCursoConfiguracion.getValue() != null && cbClaseAgregar.getValue() != null) {
            Curso curso = cbCursoConfiguracion.getValue();
            Clase clase = cbClaseAgregar.getValue();

            if (curso.agregarClase(clase)) {
                actualizarTablaClasesCurso(curso);
                mostrarAlerta("Éxito", "Clase agregada al curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo agregar la clase al curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y una clase", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onRemoverClaseCurso() {
        Clase claseSeleccionada = tblClasesCurso.getSelectionModel().getSelectedItem();
        Curso curso = cbCursoConfiguracion.getValue();

        if (curso != null && claseSeleccionada != null) {
            if (curso.getListClases().remove(claseSeleccionada)) {
                actualizarTablaClasesCurso(curso);
                mostrarAlerta("Éxito", "Clase removida del curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover la clase del curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y una clase para remover", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS PESTAÑA ESTUDIANTES =====
    @FXML
    void onVerificarNivel() {
        if (cbCursoEstudiantes.getValue() != null && cbEstudianteVerificar.getValue() != null) {
            Curso curso = cbCursoEstudiantes.getValue();
            Estudiante estudiante = cbEstudianteVerificar.getValue();

            boolean nivelAdecuado = curso.verificarNivelEstudiante(estudiante);

            if (nivelAdecuado) {
                lblResultadoVerificacion.setText("✅ El estudiante tiene el nivel adecuado para el curso");
                lblResultadoVerificacion.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lblResultadoVerificacion.setText("❌ El estudiante NO tiene el nivel adecuado para el curso");
                lblResultadoVerificacion.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y un estudiante", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onAgregarEstudiante() {
        if (cbCursoEstudiantes.getValue() != null && cbEstudianteVerificar.getValue() != null) {
            Curso curso = cbCursoEstudiantes.getValue();
            Estudiante estudiante = cbEstudianteVerificar.getValue();

            // Verificar nivel primero
            if (!curso.verificarNivelEstudiante(estudiante)) {
                mostrarAlerta("Error", "El estudiante no tiene el nivel adecuado para este curso", Alert.AlertType.ERROR);
                return;
            }

            // Verificar capacidad
            if (curso.getListEstudiantes().size() >= curso.getCapacidad()) {
                mostrarAlerta("Error", "El curso ha alcanzado su capacidad máxima", Alert.AlertType.ERROR);
                return;
            }

            // Agregar estudiante al curso
            if (curso.getListEstudiantes().add(estudiante)) {
                actualizarTablaEstudiantesCurso(curso);
                mostrarAlerta("Éxito", "Estudiante agregado al curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "El estudiante ya está en el curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y un estudiante", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onRemoverEstudiante() {
        Estudiante estudianteSeleccionado = tblEstudiantesInscritos.getSelectionModel().getSelectedItem();
        Curso curso = cbCursoEstudiantes.getValue();

        if (curso != null && estudianteSeleccionado != null) {
            if (curso.getListEstudiantes().remove(estudianteSeleccionado)) {
                actualizarTablaEstudiantesCurso(curso);
                mostrarAlerta("Éxito", "Estudiante removido del curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover el estudiante del curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y un estudiante para remover", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS AUXILIARES =====
    private void actualizarTablaClasesCurso(Curso curso) {
        tblClasesCurso.getItems().clear();
        if (curso != null && curso.getListClases() != null) {
            tblClasesCurso.getItems().addAll(curso.getListClases());
        }
    }

    private void actualizarTablaEstudiantesCurso(Curso curso) {
        tblEstudiantesInscritos.getItems().clear();
        if (curso != null && curso.getListEstudiantes() != null) {
            tblEstudiantesInscritos.getItems().addAll(curso.getListEstudiantes());
        }
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposDatosCurso() {
        txtIdCurso.clear();
        txtNombreCurso.clear();
        cbInstrumento.setValue(null);
        cbNivel.setValue(null);
        txtCapacidad.clear();
        cbProfesor.setValue(null);
        txtIdCurso.requestFocus();
    }

    private boolean validarCamposDatosCurso() {
        if (txtIdCurso.getText().isEmpty() || txtNombreCurso.getText().isEmpty() ||
                cbInstrumento.getValue() == null || cbNivel.getValue() == null ||
                txtCapacidad.getText().isEmpty() || cbProfesor.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            if (capacidad <= 0) {
                mostrarAlerta("Error", "La capacidad debe ser mayor a 0", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La capacidad debe ser un número válido", Alert.AlertType.ERROR);
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

    // Listeners para actualizar tablas cuando cambian los combos
    @FXML
    void onCursoConfiguracionChanged() {
        Curso curso = cbCursoConfiguracion.getValue();
        actualizarTablaClasesCurso(curso);
    }

    @FXML
    void onCursoEstudiantesChanged() {
        Curso curso = cbCursoEstudiantes.getValue();
        actualizarTablaEstudiantesCurso(curso);
    }

    public void setApp(App app) {
        this.app = app;
    }
}