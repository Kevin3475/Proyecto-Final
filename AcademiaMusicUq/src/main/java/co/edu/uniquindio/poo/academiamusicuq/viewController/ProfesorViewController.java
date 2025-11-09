package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.ProfesorController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;
import java.util.List;

public class ProfesorViewController {

    // ===== COMPONENTES PESTAÑA DATOS PERSONALES =====
    @FXML private TextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono, txtEspecialidad;
    @FXML private ComboBox<Instrumento> cbInstrumento;
    @FXML private Button btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    @FXML private TableView<Profesor> tblProfesores;
    @FXML private TableColumn<Profesor, String> colId, colNombre, colApellido, colEmail, colTelefono, colEspecialidad, colInstrumento;

    // ===== COMPONENTES PESTAÑA GESTIÓN DE CLASES =====
    @FXML private ComboBox<Profesor> cbProfesorClases;
    @FXML private ComboBox<String> cbDiaHorario;
    @FXML private TextField txtHoraInicio, txtHoraFin, txtCupoMaximo, txtObjetivoPersonal; // ✅ CORREGIDO
    @FXML private ComboBox<Aula> cbAulaClase;
    @FXML private ComboBox<Curso> cbCursoClase;
    @FXML private ComboBox<TipoClase> cbTipoClase;
    @FXML private Button btnAsignarHorario, btnCrearClaseGrupal, btnAgregarClase;
    @FXML private TableView<Clase> tblClasesProfesor;
    @FXML private TableColumn<Clase, String> colIdClase, colTipoClase, colCursoClase, colAulaClase, colHorarioClase;

    // ===== COMPONENTES PESTAÑA EVALUACIÓN =====
    @FXML private ComboBox<Profesor> cbProfesorEvaluacion;
    @FXML private ComboBox<Estudiante> cbEstudianteProgreso, cbEstudianteComentario;
    @FXML private ComboBox<Curso> cbCursoProgreso, cbCursoComentario;
    @FXML private TextField txtCalificacion;
    @FXML private TextArea txtObservacionesEvaluacion, txtContenidoComentario;
    @FXML private Button btnValorarProgreso, btnGenerarComentario;
    @FXML private TableView<ComentarioFormativo> tblComentarios;
    @FXML private TableColumn<ComentarioFormativo, String> colEstudianteComentario, colCursoComentario, colContenidoComentario, colFechaComentario;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList();
    private ObservableList<Clase> listaClasesProfesor = FXCollections.observableArrayList();
    private ObservableList<ComentarioFormativo> listaComentarios = FXCollections.observableArrayList();

    private ProfesorController profesorController;
    private App app;

    @FXML
    void initialize() {
        this.profesorController = new ProfesorController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosPersonales();
        configurarPestanaGestionClases();
        configurarPestanaEvaluacion();
    }

    // ===== PESTAÑA 1: DATOS PERSONALES =====
    private void configurarPestanaDatosPersonales() {
        // Configurar tabla profesores
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colApellido.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getApellido()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));
        colEspecialidad.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEspecialidad()));
        colInstrumento.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getInstrumento() != null ? cell.getValue().getInstrumento().toString() : "N/A"));

        // Configurar combo instrumentos
        cbInstrumento.setItems(FXCollections.observableArrayList(Instrumento.values()));

        // Cargar datos
        cargarProfesores();
    }

    // ===== PESTAÑA 2: GESTIÓN DE CLASES =====
    private void configurarPestanaGestionClases() {
        // Configurar combo días
        cbDiaHorario.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        ));

        // Configurar combo tipos de clase
        cbTipoClase.setItems(FXCollections.observableArrayList(TipoClase.values()));

        // Configurar tabla clases
        colIdClase.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colTipoClase.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoClase().toString()));
        colCursoClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colAulaClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getAula() != null ? cell.getValue().getAula().getNombre() : "N/A"));
        colHorarioClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getHorario() != null ?
                        cell.getValue().getHorario().getDia() + " " +
                                cell.getValue().getHorario().getHoraInicio() + "-" +
                                cell.getValue().getHorario().getHoraFin() : "N/A"));

        // ✅ INICIALIZAR txtObjetivoPersonal para evitar NullPointerException
        if (txtObjetivoPersonal != null) {
            txtObjetivoPersonal.setDisable(true); // Inicialmente deshabilitado
        }
    }

    // ===== PESTAÑA 3: EVALUACIÓN =====
    private void configurarPestanaEvaluacion() {
        // Configurar tabla comentarios
        colEstudianteComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colContenidoComentario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContenido()));
        colFechaComentario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
    }

    private void cargarDatosIniciales() {
        cargarProfesores();
        cargarCombosGestionClases();
        cargarCombosEvaluacion();
    }

    private void cargarProfesores() {
        listaProfesores.clear();
        listaProfesores.addAll(App.academia.getListProfesores());
        tblProfesores.setItems(listaProfesores);
    }

    private void cargarCombosGestionClases() {
        // Cargar profesores para selección
        cbProfesorClases.setItems(FXCollections.observableArrayList(App.academia.getListProfesores()));

        // Cargar aulas
        cbAulaClase.setItems(FXCollections.observableArrayList(App.academia.getListAulas()));

        // Cargar cursos
        cbCursoClase.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
    }

    private void cargarCombosEvaluacion() {
        // Cargar profesores para evaluación
        cbProfesorEvaluacion.setItems(FXCollections.observableArrayList(App.academia.getListProfesores()));

        // Cargar estudiantes
        cbEstudianteProgreso.setItems(FXCollections.observableArrayList(App.academia.getListEstudiantes()));
        cbEstudianteComentario.setItems(FXCollections.observableArrayList(App.academia.getListEstudiantes()));

        // Cargar cursos
        cbCursoProgreso.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
        cbCursoComentario.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
    }

    // ===== MÉTODOS PESTAÑA DATOS PERSONALES =====
    @FXML
    void onAgregarProfesor() {
        if (validarCamposProfesor()) {
            Profesor nuevoProfesor = new Profesor(
                    txtId.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    txtEspecialidad.getText(),
                    cbInstrumento.getValue(),
                    true
            );

            if (profesorController.registrarProfesor(nuevoProfesor)) {
                listaProfesores.add(nuevoProfesor);
                mostrarAlerta("Éxito", "Profesor agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposProfesor();
            } else {
                mostrarAlerta("Error", "Ya existe un profesor con ese ID", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarProfesor() {
        // Implementar lógica de actualización
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onEliminarProfesor() {
        // Implementar lógica de eliminación
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onLimpiarCampos() {
        limpiarCamposProfesor();
    }

    // ===== MÉTODOS PESTAÑA GESTIÓN DE CLASES =====
    @FXML
    void onAsignarHorario() {
        Profesor profesor = cbProfesorClases.getValue();
        String dia = cbDiaHorario.getValue();
        String horaInicio = txtHoraInicio.getText();
        String horaFin = txtHoraFin.getText();

        if (profesor != null && dia != null && !horaInicio.isEmpty() && !horaFin.isEmpty()) {
            try {
                LocalTime inicio = LocalTime.parse(horaInicio);
                LocalTime fin = LocalTime.parse(horaFin);
                BloqueHorario bloque = new BloqueHorario(dia, inicio, fin);

                if (profesor.asignarHorario(bloque)) {
                    mostrarAlerta("Éxito", "Horario asignado correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposHorario();
                } else {
                    mostrarAlerta("Error", "Conflicto de horario", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Formato de hora inválido (Use HH:mm)", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onCrearClaseGrupal() {
        // Implementar lógica de creación de clase grupal
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onAgregarClase() {
        // Implementar lógica de agregar clase
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    // ===== MÉTODOS PESTAÑA EVALUACIÓN =====
    @FXML
    void onValorarProgreso() {
        // Implementar lógica de valoración de progreso
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    @FXML
    void onGenerarComentario() {
        // Implementar lógica de generación de comentarios
        mostrarAlerta("Info", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }

    // ===== MÉTODOS AUXILIARES =====
    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private boolean validarCamposProfesor() {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtEspecialidad.getText().isEmpty() ||
                cbInstrumento.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarCamposProfesor() {
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtEspecialidad.clear();
        cbInstrumento.setValue(null);
    }

    private void limpiarCamposHorario() {
        cbDiaHorario.setValue(null);
        txtHoraInicio.clear();
        txtHoraFin.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ===== LISTENERS PARA CAMBIOS DINÁMICOS =====
    @FXML
    void onTipoClaseChanged() {
        // Habilitar/deshabilitar campos según el tipo de clase
        if (cbTipoClase.getValue() == TipoClase.INDIVIDUAL) {
            txtObjetivoPersonal.setDisable(false);
            txtCupoMaximo.setDisable(true);
            txtCupoMaximo.clear();
        } else if (cbTipoClase.getValue() == TipoClase.GRUPAL) {
            txtObjetivoPersonal.setDisable(true);
            txtObjetivoPersonal.clear();
            txtCupoMaximo.setDisable(false);
        } else {
            txtObjetivoPersonal.setDisable(true);
            txtCupoMaximo.setDisable(true);
        }
    }

    @FXML
    void onProfesorClasesChanged() {
        // Cargar clases del profesor seleccionado
        Profesor profesor = cbProfesorClases.getValue();
        if (profesor != null) {
            listaClasesProfesor.clear();
            listaClasesProfesor.addAll(profesor.getListClases());
            tblClasesProfesor.setItems(listaClasesProfesor);
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}