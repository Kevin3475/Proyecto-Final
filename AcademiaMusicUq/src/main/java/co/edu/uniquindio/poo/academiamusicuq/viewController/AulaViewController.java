package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.AulaController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalTime;
import java.util.List;

public class AulaViewController {

    // ===== COMPONENTES PESTAÑA DATOS DEL AULA =====
    @FXML private TextField txtIdAula, txtNombreAula, txtCapacidadAula;
    @FXML private CheckBox chkDisponible;
    @FXML private TableView<Aula> tblAulas;
    @FXML private TableColumn<Aula, String> colIdAula, colNombreAula, colCapacidadAula, colDisponibleAula;
    @FXML private Button btnAgregarAula, btnActualizarAula, btnEliminarAula, btnLimpiarAula;

    // ===== COMPONENTES PESTAÑA DISPONIBILIDAD =====
    @FXML private ComboBox<Aula> cbAulaDisponibilidad;
    @FXML private ComboBox<String> cbDiaDisponibilidad;
    @FXML private TextField txtHoraInicioDisponibilidad, txtHoraFinDisponibilidad;
    @FXML private TableView<BloqueHorario> tblHorariosAula;
    @FXML private TableColumn<BloqueHorario, String> colDiaHorario, colHoraInicio, colHoraFin;
    @FXML private Button btnVerificarDisponibilidad, btnAgregarHorario, btnRemoverHorario;
    @FXML private Label lblResultadoDisponibilidad;

    // ===== COMPONENTES PESTAÑA CLASES =====
    @FXML private ComboBox<Aula> cbAulaClases;
    @FXML private TableView<Clase> tblClasesAula;
    @FXML private TableColumn<Clase, String> colIdClaseAula, colTipoClaseAula, colCursoClaseAula, colProfesorClaseAula, colHorarioClaseAula;

    // ===== COMPONENTES GENERALES =====
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Aula> listaAulas = FXCollections.observableArrayList();
    private ObservableList<BloqueHorario> listaHorarios = FXCollections.observableArrayList();
    private ObservableList<Clase> listaClases = FXCollections.observableArrayList();

    private Aula aulaSeleccionada;
    private AulaController aulaController;
    private App app;

    @FXML
    void initialize() {
        this.aulaController = new AulaController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarInterfaz() {
        configurarPestanaDatosAula();
        configurarPestanaDisponibilidad();
        configurarPestanaClases();
    }

    // ===== PESTAÑA 1: DATOS DEL AULA =====
    private void configurarPestanaDatosAula() {
        // Configurar tabla aulas
        colIdAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIdAula()));
        colNombreAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCapacidadAula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCapacidad())));
        colDisponibleAula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isDisponible() ? "✅ Disponible" : "❌ Ocupada"));

        tblAulas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            aulaSeleccionada = newVal;
            mostrarAulaSeleccionada();
        });

        // Configurar combo días
        cbDiaDisponibilidad.setItems(FXCollections.observableArrayList(
                "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"
        ));
    }

    // ===== PESTAÑA 2: DISPONIBILIDAD =====
    private void configurarPestanaDisponibilidad() {
        // Configurar tabla horarios
        colDiaHorario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDia()));
        colHoraInicio.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getHoraInicio().toString()));
        colHoraFin.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getHoraFin().toString()));
    }

    // ===== PESTAÑA 3: CLASES =====
    private void configurarPestanaClases() {
        // Configurar tabla clases del aula
        colIdClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colTipoClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoClase().toString()));
        colCursoClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colProfesorClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        colHorarioClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getHorario() != null ?
                        cell.getValue().getHorario().getDia() + " " + cell.getValue().getHorario().getHoraInicio() + "-" + cell.getValue().getHorario().getHoraFin() :
                        "N/A"));
    }

    private void cargarDatosIniciales() {
        cargarAulas();
        cargarCombosDisponibilidad();
    }

    private void cargarAulas() {
        listaAulas.clear();
        listaAulas.addAll(aulaController.obtenerAulas());
        tblAulas.setItems(listaAulas);

        // Actualizar combos que dependen de aulas
        cbAulaDisponibilidad.setItems(listaAulas);
        cbAulaClases.setItems(listaAulas);
    }

    private void cargarCombosDisponibilidad() {
        // Los combos ya se configuraron en initialize()
    }

    // ===== MÉTODOS PESTAÑA DATOS DEL AULA =====
    private void mostrarAulaSeleccionada() {
        if (aulaSeleccionada != null) {
            txtIdAula.setText(aulaSeleccionada.getIdAula());
            txtNombreAula.setText(aulaSeleccionada.getNombre());
            txtCapacidadAula.setText(String.valueOf(aulaSeleccionada.getCapacidad()));
            chkDisponible.setSelected(aulaSeleccionada.isDisponible());
        }
    }

    @FXML
    void onAgregarAula() {
        if (validarCamposDatosAula()) {
            Aula aula = new Aula(
                    txtIdAula.getText(),
                    txtNombreAula.getText(),
                    Integer.parseInt(txtCapacidadAula.getText()),
                    chkDisponible.isSelected()
            );

            if (aulaController.registrarAula(aula)) {
                listaAulas.add(aula);
                limpiarCamposDatosAula();
                mostrarAlerta("Éxito", "Aula registrada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Ya existe un aula con esta ID", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarAula() {
        if (aulaSeleccionada != null && validarCamposDatosAula()) {
            Aula actualizada = new Aula(
                    txtIdAula.getText(),
                    txtNombreAula.getText(),
                    Integer.parseInt(txtCapacidadAula.getText()),
                    chkDisponible.isSelected()
            );

            if (aulaController.actualizarAula(aulaSeleccionada.getIdAula(), actualizada)) {
                cargarAulas();
                limpiarCamposDatosAula();
                mostrarAlerta("Éxito", "Aula actualizada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Error al actualizar el aula", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un aula para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarAula() {
        if (aulaSeleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este aula?");
            confirmacion.setContentText("Aula: " + aulaSeleccionada.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (aulaController.eliminarAula(aulaSeleccionada.getIdAula())) {
                    listaAulas.remove(aulaSeleccionada);
                    limpiarCamposDatosAula();
                    mostrarAlerta("Éxito", "Aula eliminada correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al eliminar el aula", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione un aula para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCamposAula() {
        limpiarCamposDatosAula();
        tblAulas.getSelectionModel().clearSelection();
        aulaSeleccionada = null;
    }

    // ===== MÉTODOS PESTAÑA DISPONIBILIDAD =====
    @FXML
    void onVerificarDisponibilidad() {
        if (cbAulaDisponibilidad.getValue() != null && cbDiaDisponibilidad.getValue() != null &&
                !txtHoraInicioDisponibilidad.getText().isEmpty() && !txtHoraFinDisponibilidad.getText().isEmpty()) {

            try {
                Aula aula = cbAulaDisponibilidad.getValue();
                BloqueHorario nuevoBloque = new BloqueHorario(
                        cbDiaDisponibilidad.getValue(),
                        LocalTime.parse(txtHoraInicioDisponibilidad.getText()),
                        LocalTime.parse(txtHoraFinDisponibilidad.getText())
                );

                boolean disponible = aula.estaDisponible(nuevoBloque);

                if (disponible) {
                    lblResultadoDisponibilidad.setText("✅ El aula está disponible en ese horario");
                    lblResultadoDisponibilidad.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                } else {
                    lblResultadoDisponibilidad.setText("❌ El aula NO está disponible en ese horario");
                    lblResultadoDisponibilidad.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Formato de hora inválido. Use formato HH:mm", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Complete todos los campos para verificar disponibilidad", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onAgregarHorario() {
        if (cbAulaDisponibilidad.getValue() != null && cbDiaDisponibilidad.getValue() != null &&
                !txtHoraInicioDisponibilidad.getText().isEmpty() && !txtHoraFinDisponibilidad.getText().isEmpty()) {

            try {
                Aula aula = cbAulaDisponibilidad.getValue();
                BloqueHorario nuevoHorario = new BloqueHorario(
                        cbDiaDisponibilidad.getValue(),
                        LocalTime.parse(txtHoraInicioDisponibilidad.getText()),
                        LocalTime.parse(txtHoraFinDisponibilidad.getText())
                );

                // Verificar disponibilidad primero
                if (!aula.estaDisponible(nuevoHorario)) {
                    mostrarAlerta("Error", "El aula no está disponible en ese horario", Alert.AlertType.ERROR);
                    return;
                }

                // Agregar horario al aula
                aula.getListHorarios().add(nuevoHorario);
                actualizarTablaHorariosAula(aula);
                mostrarAlerta("Éxito", "Horario agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposHorario();

            } catch (Exception e) {
                mostrarAlerta("Error", "Formato de hora inválido. Use formato HH:mm", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Complete todos los campos del horario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onRemoverHorario() {
        BloqueHorario horarioSeleccionado = tblHorariosAula.getSelectionModel().getSelectedItem();
        Aula aula = cbAulaDisponibilidad.getValue();

        if (aula != null && horarioSeleccionado != null) {
            if (aula.getListHorarios().remove(horarioSeleccionado)) {
                actualizarTablaHorariosAula(aula);
                mostrarAlerta("Éxito", "Horario removido correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover el horario", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un aula y un horario para remover", Alert.AlertType.WARNING);
        }
    }

    // ===== MÉTODOS AUXILIARES =====
    private void actualizarTablaHorariosAula(Aula aula) {
        tblHorariosAula.getItems().clear();
        if (aula != null && aula.getListHorarios() != null) {
            tblHorariosAula.getItems().addAll(aula.getListHorarios());
        }
    }

    private void actualizarTablaClasesAula(Aula aula) {
        tblClasesAula.getItems().clear();
        if (aula != null && aula.getListClases() != null) {
            tblClasesAula.getItems().addAll(aula.getListClases());
        }
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposDatosAula() {
        txtIdAula.clear();
        txtNombreAula.clear();
        txtCapacidadAula.clear();
        chkDisponible.setSelected(true);
        txtIdAula.requestFocus();
    }

    private void limpiarCamposHorario() {
        cbDiaDisponibilidad.setValue(null);
        txtHoraInicioDisponibilidad.clear();
        txtHoraFinDisponibilidad.clear();
        lblResultadoDisponibilidad.setText("");
    }

    private boolean validarCamposDatosAula() {
        if (txtIdAula.getText().isEmpty() || txtNombreAula.getText().isEmpty() ||
                txtCapacidadAula.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            int capacidad = Integer.parseInt(txtCapacidadAula.getText());
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
    void onAulaDisponibilidadChanged() {
        Aula aula = cbAulaDisponibilidad.getValue();
        actualizarTablaHorariosAula(aula);
    }

    @FXML
    void onAulaClasesChanged() {
        Aula aula = cbAulaClases.getValue();
        actualizarTablaClasesAula(aula);
    }

    public void setApp(App app) {
        this.app = app;
    }
}