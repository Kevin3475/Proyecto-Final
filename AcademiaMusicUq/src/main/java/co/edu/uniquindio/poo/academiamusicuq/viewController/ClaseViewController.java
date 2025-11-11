package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.ClaseController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ClaseViewController {

    // Pestaña Datos de Clase
    @FXML private TextField txtIdClase, txtCupoMaximo, txtObjetivoPersonal;
    @FXML private ComboBox<Aula> cbAulaClase;
    @FXML private ComboBox<TipoClase> cbTipoClase;
    @FXML private ComboBox<Profesor> cbProfesorClase;
    @FXML private ComboBox<Curso> cbCursoClase;
    @FXML private ComboBox<String> cbDiaClase;
    @FXML private TextField txtHoraInicioClase, txtHoraFinClase;
    @FXML private TableView<Clase> tblClases;
    @FXML private TableColumn<Clase, String> colIdClase, colTipoClase, colAulaClase, colProfesorClase, colCursoClase, colHorarioClase;
    @FXML private Button btnAgregarClase, btnActualizarClase, btnEliminarClase, btnLimpiarClase;

    // Pestaña Gestion
    @FXML private ComboBox<ClaseGrupal> cbClaseGrupal;
    @FXML private ComboBox<Estudiante> cbEstudianteAgregar;
    @FXML private TableView<Estudiante> tblEstudiantesClase;
    @FXML private TableColumn<Estudiante, String> colIdEstudianteClase, colNombreEstudianteClase, colNivelEstudianteClase;
    @FXML private Button btnAgregarEstudianteClase, btnRemoverEstudianteClase;
    @FXML private Label lblCupoDisponible;

    // Pestaña Asistencia
    @FXML private ComboBox<Clase> cbClaseAsistencia;
    @FXML private ComboBox<Estudiante> cbEstudianteAsistencia;
    @FXML private DatePicker dpFechaAsistencia;
    @FXML private CheckBox chkPresente;
    @FXML private TableView<Asistencia> tblAsistencias;
    @FXML private TableColumn<Asistencia, String> colIdAsistencia, colEstudianteAsistencia, colFechaAsistencia, colEstadoAsistencia;
    @FXML private Button btnRegistrarAsistencia;

    // Pestaña Evaluacion
    @FXML private ComboBox<Clase> cbClaseEvaluacion;
    @FXML private ComboBox<Estudiante> cbEstudianteEvaluacion;
    @FXML private TextField txtCalificacionEvaluacion;
    @FXML private TextArea txtObservacionesEvaluacion;
    @FXML private TableView<ReporteProgreso> tblEvaluaciones;
    @FXML private TableColumn<ReporteProgreso, String> colIdEvaluacion, colEstudianteEvaluacion, colCalificacionEvaluacion, colAprobadoEvaluacion;
    @FXML private Button btnEvaluarProgreso;


    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Clase> listaClases = FXCollections.observableArrayList();
    private ObservableList<ClaseGrupal> listaClasesGrupales = FXCollections.observableArrayList();
    private ObservableList<Aula> listaAulas = FXCollections.observableArrayList();
    private ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList();
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Asistencia> listaAsistencias = FXCollections.observableArrayList();
    private ObservableList<ReporteProgreso> listaEvaluaciones = FXCollections.observableArrayList();

    private Clase claseSeleccionada;
    private ClaseController claseController;
    private App app;

    @FXML
    void initialize() {
        this.claseController = new ClaseController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosClase();
        configurarPestanaGestionEstudiantes();
        configurarPestanaAsistencias();
        configurarPestanaEvaluacion();
    }

    // Datos Clase
    private void configurarPestanaDatosClase() {

        colIdClase.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colTipoClase.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoClase().toString()));
        colAulaClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getAula() != null ? cell.getValue().getAula().getNombre() : "N/A"));
        colProfesorClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        colCursoClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colHorarioClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getHorario() != null ?
                        cell.getValue().getHorario().getDia() + " " + cell.getValue().getHorario().getHoraInicio() + "-" + cell.getValue().getHorario().getHoraFin() :
                        "N/A"));

        tblClases.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            claseSeleccionada = newVal;
            mostrarClaseSeleccionada();
        });

        // Configurar combo boxes
        cbTipoClase.setItems(FXCollections.observableArrayList(TipoClase.values()));
        cbDiaClase.setItems(FXCollections.observableArrayList(
                "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"
        ));

        // Listener para mostrar/ocultar campos según tipo de clase
        cbTipoClase.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean esGrupal = newVal == TipoClase.GRUPAL;
            txtCupoMaximo.setDisable(!esGrupal);
            txtObjetivoPersonal.setDisable(esGrupal);

            if (esGrupal) {
                txtObjetivoPersonal.clear();
            } else {
                txtCupoMaximo.clear();
            }
        });
    }

    // Datos Estudiantes
    private void configurarPestanaGestionEstudiantes() {

        colIdEstudianteClase.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colNombreEstudianteClase.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getNombre() + " " + cell.getValue().getApellido()));
        colNivelEstudianteClase.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNivel().toString()));
    }

    // Datos Asistencia
    private void configurarPestanaAsistencias() {
        // Configurar tabla asistencias
        colIdAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdAsistencia())));
        colEstudianteAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colFechaAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));
        colEstadoAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getPresente() ? "✅ Presente" : "❌ Ausente"));
    }

    // Datos Evaluacion
    private void configurarPestanaEvaluacion() {
        // Configurar tabla evaluaciones
        colIdEvaluacion.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdReporte())));
        colEstudianteEvaluacion.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCalificacionEvaluacion.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCalificacion())));
        colAprobadoEvaluacion.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isAprobado() ? "✅ Aprobado" : "❌ Reprobado"));
    }

    private void cargarDatosIniciales() {
        cargarClases();
        cargarCombosDatosClase();
        cargarCombosGestionEstudiantes();
        cargarCombosAsistencias();
        cargarCombosEvaluacion();
    }

    private void cargarClases() {
        listaClases.clear();
        listaClases.addAll(claseController.obtenerTodasLasClases());
        tblClases.setItems(listaClases);

        // Actualizar combos que dependen de clases
        cbClaseAsistencia.setItems(listaClases);
        cbClaseEvaluacion.setItems(listaClases);
    }

    private void cargarCombosDatosClase() {
        // Cargar aulas disponibles
        listaAulas.clear();
        listaAulas.addAll(App.academia.getListAulas());
        cbAulaClase.setItems(listaAulas);

        // Cargar profesores disponibles
        listaProfesores.clear();
        listaProfesores.addAll(App.academia.getListProfesores());
        cbProfesorClase.setItems(listaProfesores);

        // Cargar cursos disponibles
        listaCursos.clear();
        listaCursos.addAll(App.academia.getListCursos());
        cbCursoClase.setItems(listaCursos);
    }

    private void cargarCombosGestionEstudiantes() {
        // Cargar estudiantes disponibles
        listaEstudiantes.clear();
        listaEstudiantes.addAll(App.academia.getListEstudiantes());
        cbEstudianteAgregar.setItems(listaEstudiantes);

        // Cargar clases grupales
        listaClasesGrupales.clear();
        listaClasesGrupales.addAll(claseController.obtenerClasesGrupales());
        cbClaseGrupal.setItems(listaClasesGrupales);
    }

    private void cargarCombosAsistencias() {
        cbEstudianteAsistencia.setItems(listaEstudiantes);
        dpFechaAsistencia.setValue(LocalDate.now());
    }

    private void cargarCombosEvaluacion() {
        cbEstudianteEvaluacion.setItems(listaEstudiantes);
    }


    private void mostrarClaseSeleccionada() {
        if (claseSeleccionada != null) {
            txtIdClase.setText(String.valueOf(claseSeleccionada.getId()));
            cbAulaClase.setValue(claseSeleccionada.getAula());
            cbTipoClase.setValue(claseSeleccionada.getTipoClase());
            cbProfesorClase.setValue(claseSeleccionada.getProfesor());
            cbCursoClase.setValue(claseSeleccionada.getCurso());

            if (claseSeleccionada.getHorario() != null) {
                cbDiaClase.setValue(claseSeleccionada.getHorario().getDia());
                txtHoraInicioClase.setText(claseSeleccionada.getHorario().getHoraInicio().toString());
                txtHoraFinClase.setText(claseSeleccionada.getHorario().getHoraFin().toString());
            }

            if (claseSeleccionada instanceof ClaseGrupal) {
                ClaseGrupal claseGrupal = (ClaseGrupal) claseSeleccionada;
                txtCupoMaximo.setText(String.valueOf(claseGrupal.getCupoMaximo()));
            } else if (claseSeleccionada instanceof ClaseIndividual) {
                ClaseIndividual claseIndividual = (ClaseIndividual) claseSeleccionada;
                txtObjetivoPersonal.setText(claseIndividual.getObjetivoPersonal());
            }
        }
    }

    @FXML
    void onAgregarClase() {
        if (validarCamposDatosClase()) {
            try {
                BloqueHorario horario = new BloqueHorario(
                        cbDiaClase.getValue(),
                        LocalTime.parse(txtHoraInicioClase.getText()),
                        LocalTime.parse(txtHoraFinClase.getText())
                );

                Clase clase;
                if (cbTipoClase.getValue() == TipoClase.GRUPAL) {
                    clase = new ClaseGrupal(
                            Integer.parseInt(txtIdClase.getText()),
                            cbAulaClase.getValue(),
                            TipoClase.GRUPAL,
                            cbProfesorClase.getValue(),
                            horario,
                            cbCursoClase.getValue(),
                            Integer.parseInt(txtCupoMaximo.getText())
                    );
                } else {
                    clase = new ClaseIndividual(
                            Integer.parseInt(txtIdClase.getText()),
                            cbAulaClase.getValue(),
                            TipoClase.INDIVIDUAL,
                            cbProfesorClase.getValue(),
                            horario,
                            cbCursoClase.getValue(),
                            txtObjetivoPersonal.getText(),
                            null // Estudiante se asignaría después
                    );
                }

                if (claseController.registrarClase(clase)) {
                    listaClases.add(clase);
                    if (clase instanceof ClaseGrupal) {
                        listaClasesGrupales.add((ClaseGrupal) clase);
                    }
                    limpiarCamposDatosClase();
                    mostrarAlerta("Éxito", "Clase registrada correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al registrar la clase", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Formato de hora inválido. Use formato HH:mm", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarClase() {
        if (claseSeleccionada != null && validarCamposDatosClase()) {

            mostrarAlerta("Info", "Funcionalidad de actualización en desarrollo", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "Seleccione una clase para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarClase() {
        if (claseSeleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta clase?");
            confirmacion.setContentText("Clase ID: " + claseSeleccionada.getId());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (claseController.eliminarClase(claseSeleccionada.getId())) {
                    listaClases.remove(claseSeleccionada);
                    if (claseSeleccionada instanceof ClaseGrupal) {
                        listaClasesGrupales.remove((ClaseGrupal) claseSeleccionada);
                    }
                    limpiarCamposDatosClase();
                    mostrarAlerta("Éxito", "Clase eliminada correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al eliminar la clase", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione una clase para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCamposClase() {
        limpiarCamposDatosClase();
        tblClases.getSelectionModel().clearSelection();
        claseSeleccionada = null;
    }

    // Metodos Gestion Estudiantes
    @FXML
    void onAgregarEstudianteClase() {
        if (cbClaseGrupal.getValue() != null && cbEstudianteAgregar.getValue() != null) {
            ClaseGrupal clase = cbClaseGrupal.getValue();
            Estudiante estudiante = cbEstudianteAgregar.getValue();

            if (clase.agregarEstudiante(estudiante)) {
                actualizarTablaEstudiantesClase(clase);
                actualizarLabelCupo(clase);
                mostrarAlerta("Éxito", "Estudiante agregado a la clase correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo agregar el estudiante. Verifique el cupo o si ya está inscrito", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione una clase grupal y un estudiante", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onRemoverEstudianteClase() {
        Estudiante estudianteSeleccionado = tblEstudiantesClase.getSelectionModel().getSelectedItem();
        ClaseGrupal clase = cbClaseGrupal.getValue();

        if (clase != null && estudianteSeleccionado != null) {
            if (clase.getListEstudiantes().remove(estudianteSeleccionado)) {
                actualizarTablaEstudiantesClase(clase);
                actualizarLabelCupo(clase);
                mostrarAlerta("Éxito", "Estudiante removido de la clase correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover el estudiante", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione una clase y un estudiante para remover", Alert.AlertType.WARNING);
        }
    }

    // Metodos Asistencia
    @FXML
    void onRegistrarAsistencia() {
        if (cbClaseAsistencia.getValue() != null && cbEstudianteAsistencia.getValue() != null &&
                dpFechaAsistencia.getValue() != null) {

            try {
                Clase clase = cbClaseAsistencia.getValue();
                Estudiante estudiante = cbEstudianteAsistencia.getValue();
                boolean presente = chkPresente.isSelected();

                Asistencia asistencia = new Asistencia(
                        listaAsistencias.size() + 1,
                        estudiante,
                        clase,
                        dpFechaAsistencia.getValue(),
                        presente
                );

                if (clase.registrarAsistencia(clase.getProfesor(), asistencia)) {
                    listaAsistencias.add(asistencia);
                    tblAsistencias.setItems(listaAsistencias);
                    mostrarAlerta("Éxito", "Asistencia registrada correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposAsistencia();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar la asistencia", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al registrar la asistencia: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Complete todos los campos de la asistencia", Alert.AlertType.WARNING);
        }
    }

    // Metodos Evaluacion
    @FXML
    void onEvaluarProgreso() {
        if (cbClaseEvaluacion.getValue() != null && cbEstudianteEvaluacion.getValue() != null &&
                !txtCalificacionEvaluacion.getText().isEmpty()) {

            try {
                Clase clase = cbClaseEvaluacion.getValue();
                Estudiante estudiante = cbEstudianteEvaluacion.getValue();
                float calificacion = Float.parseFloat(txtCalificacionEvaluacion.getText());
                String observaciones = txtObservacionesEvaluacion.getText();

                ReporteProgreso reporte = clase.evaluarProgreso(estudiante, calificacion, observaciones);

                if (reporte != null) {
                    listaEvaluaciones.add(reporte);
                    tblEvaluaciones.setItems(listaEvaluaciones);
                    mostrarAlerta("Éxito", "Evaluación registrada correctamente. Calificación: " + calificacion, Alert.AlertType.INFORMATION);
                    limpiarCamposEvaluacion();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar la evaluación", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "La calificación debe ser un número válido", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Complete todos los campos de la evaluación", Alert.AlertType.WARNING);
        }
    }


    private void actualizarTablaEstudiantesClase(ClaseGrupal clase) {
        tblEstudiantesClase.getItems().clear();
        if (clase != null && clase.getListEstudiantes() != null) {
            tblEstudiantesClase.getItems().addAll(clase.getListEstudiantes());
        }
    }

    private void actualizarLabelCupo(ClaseGrupal clase) {
        if (clase != null) {
            int cupoDisponible = clase.getCupoMaximo() - clase.getListEstudiantes().size();
            lblCupoDisponible.setText("Cupo disponible: " + cupoDisponible + "/" + clase.getCupoMaximo());
            lblCupoDisponible.setStyle(cupoDisponible > 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposDatosClase() {
        txtIdClase.clear();
        cbAulaClase.setValue(null);
        cbTipoClase.setValue(null);
        cbProfesorClase.setValue(null);
        cbCursoClase.setValue(null);
        cbDiaClase.setValue(null);
        txtHoraInicioClase.clear();
        txtHoraFinClase.clear();
        txtCupoMaximo.clear();
        txtObjetivoPersonal.clear();
        txtIdClase.requestFocus();
    }

    private void limpiarCamposAsistencia() {
        cbEstudianteAsistencia.setValue(null);
        chkPresente.setSelected(false);
    }

    private void limpiarCamposEvaluacion() {
        cbEstudianteEvaluacion.setValue(null);
        txtCalificacionEvaluacion.clear();
        txtObservacionesEvaluacion.clear();
    }

    private boolean validarCamposDatosClase() {
        if (txtIdClase.getText().isEmpty() || cbAulaClase.getValue() == null ||
                cbTipoClase.getValue() == null || cbProfesorClase.getValue() == null ||
                cbCursoClase.getValue() == null || cbDiaClase.getValue() == null ||
                txtHoraInicioClase.getText().isEmpty() || txtHoraFinClase.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos básicos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if (cbTipoClase.getValue() == TipoClase.GRUPAL && txtCupoMaximo.getText().isEmpty()) {
            mostrarAlerta("Error", "El cupo máximo es obligatorio para clases grupales", Alert.AlertType.ERROR);
            return false;
        }

        if (cbTipoClase.getValue() == TipoClase.INDIVIDUAL && txtObjetivoPersonal.getText().isEmpty()) {
            mostrarAlerta("Error", "El objetivo personal es obligatorio para clases individuales", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Integer.parseInt(txtIdClase.getText());
            if (cbTipoClase.getValue() == TipoClase.GRUPAL) {
                int cupo = Integer.parseInt(txtCupoMaximo.getText());
                if (cupo <= 0) {
                    mostrarAlerta("Error", "El cupo máximo debe ser mayor a 0", Alert.AlertType.ERROR);
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID y cupo deben ser números válidos", Alert.AlertType.ERROR);
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
    void onClaseGrupalChanged() {
        ClaseGrupal clase = cbClaseGrupal.getValue();
        actualizarTablaEstudiantesClase(clase);
        actualizarLabelCupo(clase);
    }

    @FXML
    void onClaseAsistenciaChanged() {
        // Puede cargar asistencias especificas de la clase si es necesario
    }

    @FXML
    void onClaseEvaluacionChanged() {
        // Puede cargar evaluaciones especificas de la clase si es necesario
    }

    public void setApp(App app) {
        this.app = app;
    }
}