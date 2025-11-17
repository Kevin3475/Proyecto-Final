package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.AulaController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalTime;
import java.util.List;

public class AulaViewController {

    // Pestaña Datos Aula
    @FXML private TextField txtIdAula, txtNombreAula, txtCapacidadAula;
    @FXML private CheckBox chkDisponible;
    @FXML private TableView<Aula> tblAulas;
    @FXML private TableColumn<Aula, String> colIdAula, colNombreAula, colCapacidadAula, colDisponibleAula;
    @FXML private Button btnAgregarAula, btnActualizarAula, btnEliminarAula, btnLimpiarAula;

    // Pestaña Disponibilidad
    @FXML private ComboBox<Aula> cbAulaDisponibilidad;
    @FXML private ComboBox<String> cbDiaDisponibilidad;
    @FXML private TextField txtHoraInicioDisponibilidad, txtHoraFinDisponibilidad;
    @FXML private TableView<BloqueHorario> tblHorariosAula;
    @FXML private TableColumn<BloqueHorario, String> colDiaHorario, colHoraInicio, colHoraFin;
    @FXML private Button btnVerificarDisponibilidad, btnAgregarHorario, btnRemoverHorario;
    @FXML private Label lblResultadoDisponibilidad;

    // Pestaña Clase
    @FXML private ComboBox<Aula> cbAulaClases;
    @FXML private TableView<Clase> tblClasesAula;
    @FXML private TableColumn<Clase, String> colIdClaseAula, colTipoClaseAula, colCursoClaseAula, colProfesorClaseAula, colHorarioClaseAula;

    // Botones Generales
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
        try {
            this.aulaController = new AulaController(App.academia);
            configurarInterfaz();
            cargarDatosIniciales();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al inicializar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void configurarInterfaz() {
        configurarPestanaDatosAula();
        configurarPestanaDisponibilidad();
        configurarPestanaClases();
        configurarStringConverters();
    }

    // CORREGIDO: Configurar cómo se muestran los objetos en los ComboBox
    private void configurarStringConverters() {
        // Configurar ComboBox de aulas para disponibilidad
        if (cbAulaDisponibilidad != null) {
            cbAulaDisponibilidad.setConverter(new StringConverter<Aula>() {
                @Override
                public String toString(Aula aula) {
                    if (aula == null) return "";
                    return aula.getNombre() + " - Capacidad: " + aula.getCapacidad();
                }

                @Override
                public Aula fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de aulas para clases
        if (cbAulaClases != null) {
            cbAulaClases.setConverter(new StringConverter<Aula>() {
                @Override
                public String toString(Aula aula) {
                    if (aula == null) return "";
                    return aula.getNombre() + " (" + aula.getIdAula() + ")";
                }

                @Override
                public Aula fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void configurarPestanaDatosAula() {
        // Configurar tabla aulas
        if (colIdAula != null) {
            colIdAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIdAula()));
        }
        if (colNombreAula != null) {
            colNombreAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        }
        if (colCapacidadAula != null) {
            colCapacidadAula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCapacidad())));
        }
        if (colDisponibleAula != null) {
            colDisponibleAula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().isDisponible() ? "✅ Disponible" : "❌ Ocupada"));
        }

        if (tblAulas != null) {
            tblAulas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                aulaSeleccionada = newVal;
                mostrarAulaSeleccionada();
            });
        }

        // Configurar combo días
        if (cbDiaDisponibilidad != null) {
            cbDiaDisponibilidad.setItems(FXCollections.observableArrayList(
                    "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO"
            ));
        }
    }

    private void configurarPestanaDisponibilidad() {
        // Configurar tabla horarios
        if (colDiaHorario != null) {
            colDiaHorario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDia()));
        }
        if (colHoraInicio != null) {
            colHoraInicio.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getHoraInicio().toString()));
        }
        if (colHoraFin != null) {
            colHoraFin.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getHoraFin().toString()));
        }
    }

    private void configurarPestanaClases() {
        // Configurar tabla clases del aula
        if (colIdClaseAula != null) {
            colIdClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        }
        if (colTipoClaseAula != null) {
            colTipoClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipoClase().toString()));
        }
        if (colCursoClaseAula != null) {
            colCursoClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        }
        if (colProfesorClaseAula != null) {
            colProfesorClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getProfesor() != null ?
                            cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        }
        if (colHorarioClaseAula != null) {
            colHorarioClaseAula.setCellValueFactory(cell -> new SimpleStringProperty(
                    cell.getValue().getHorario() != null ?
                            cell.getValue().getHorario().getDia() + " " + cell.getValue().getHorario().getHoraInicio() + "-" + cell.getValue().getHorario().getHoraFin() :
                            "N/A"));
        }
    }

    private void cargarDatosIniciales() {
        cargarAulas();
    }

    private void cargarAulas() {
        try {
            listaAulas.clear();
            if (aulaController != null) {
                List<Aula> aulas = aulaController.obtenerAulas();
                if (aulas != null) {
                    listaAulas.addAll(aulas);
                }
            }

            if (tblAulas != null) {
                tblAulas.setItems(listaAulas);
            }

            // Actualizar combos que dependen de aulas
            if (cbAulaDisponibilidad != null) {
                cbAulaDisponibilidad.setItems(listaAulas);
                if (!listaAulas.isEmpty()) {
                    cbAulaDisponibilidad.setValue(listaAulas.get(0));
                }
            }

            if (cbAulaClases != null) {
                cbAulaClases.setItems(listaAulas);
                if (!listaAulas.isEmpty()) {
                    cbAulaClases.setValue(listaAulas.get(0));
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar aulas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAulaSeleccionada() {
        if (aulaSeleccionada != null) {
            if (txtIdAula != null) txtIdAula.setText(aulaSeleccionada.getIdAula());
            if (txtNombreAula != null) txtNombreAula.setText(aulaSeleccionada.getNombre());
            if (txtCapacidadAula != null) txtCapacidadAula.setText(String.valueOf(aulaSeleccionada.getCapacidad()));
            if (chkDisponible != null) chkDisponible.setSelected(aulaSeleccionada.isDisponible());
        }
    }

    @FXML
    void onAgregarAula() {
        try {
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
                    cargarAulas(); // Actualizar combos
                } else {
                    mostrarAlerta("Error", "Ya existe un aula con esta ID", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar aula: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizarAula() {
        try {
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
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar aula: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminarAula() {
        try {
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
                        cargarAulas(); // Actualizar combos
                    } else {
                        mostrarAlerta("Error", "Error al eliminar el aula", Alert.AlertType.ERROR);
                    }
                }
            } else {
                mostrarAlerta("Error", "Seleccione un aula para eliminar", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar aula: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onLimpiarCamposAula() {
        limpiarCamposDatosAula();
        if (tblAulas != null) {
            tblAulas.getSelectionModel().clearSelection();
        }
        aulaSeleccionada = null;
    }

    @FXML
    void onVerificarDisponibilidad() {
        try {
            if (cbAulaDisponibilidad.getValue() != null && cbDiaDisponibilidad.getValue() != null &&
                    !txtHoraInicioDisponibilidad.getText().isEmpty() && !txtHoraFinDisponibilidad.getText().isEmpty()) {

                Aula aula = cbAulaDisponibilidad.getValue();
                BloqueHorario nuevoBloque = new BloqueHorario(
                        cbDiaDisponibilidad.getValue(),
                        LocalTime.parse(txtHoraInicioDisponibilidad.getText()),
                        LocalTime.parse(txtHoraFinDisponibilidad.getText())
                );

                boolean disponible = aula.estaDisponible(nuevoBloque);

                if (lblResultadoDisponibilidad != null) {
                    if (disponible) {
                        lblResultadoDisponibilidad.setText("✅ El aula está disponible en ese horario");
                        lblResultadoDisponibilidad.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        lblResultadoDisponibilidad.setText("❌ El aula NO está disponible en ese horario");
                        lblResultadoDisponibilidad.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }
                }
            } else {
                mostrarAlerta("Error", "Complete todos los campos para verificar disponibilidad", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Formato de hora inválido. Use formato HH:mm", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onAgregarHorario() {
        try {
            if (cbAulaDisponibilidad.getValue() != null && cbDiaDisponibilidad.getValue() != null &&
                    !txtHoraInicioDisponibilidad.getText().isEmpty() && !txtHoraFinDisponibilidad.getText().isEmpty()) {

                Aula aula = cbAulaDisponibilidad.getValue();
                BloqueHorario nuevoHorario = new BloqueHorario(
                        cbDiaDisponibilidad.getValue(),
                        LocalTime.parse(txtHoraInicioDisponibilidad.getText()),
                        LocalTime.parse(txtHoraFinDisponibilidad.getText())
                );

                if (!aula.estaDisponible(nuevoHorario)) {
                    mostrarAlerta("Error", "El aula no está disponible en ese horario", Alert.AlertType.ERROR);
                    return;
                }

                aula.getListHorarios().add(nuevoHorario);
                actualizarTablaHorariosAula(aula);
                mostrarAlerta("Éxito", "Horario agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposHorario();

            } else {
                mostrarAlerta("Error", "Complete todos los campos del horario", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Formato de hora inválido. Use formato HH:mm", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onRemoverHorario() {
        try {
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
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al remover horario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarTablaHorariosAula(Aula aula) {
        if (tblHorariosAula != null) {
            tblHorariosAula.getItems().clear();
            if (aula != null && aula.getListHorarios() != null) {
                tblHorariosAula.getItems().addAll(aula.getListHorarios());
            }
        }
    }

    private void actualizarTablaClasesAula(Aula aula) {
        if (tblClasesAula != null) {
            tblClasesAula.getItems().clear();
            if (aula != null && aula.getListClases() != null) {
                tblClasesAula.getItems().addAll(aula.getListClases());
            }
        }
    }

    @FXML
    void onVolver() {
        if (app != null) {
            app.mostrarMainView();
        }
    }

    private void limpiarCamposDatosAula() {
        if (txtIdAula != null) txtIdAula.clear();
        if (txtNombreAula != null) txtNombreAula.clear();
        if (txtCapacidadAula != null) txtCapacidadAula.clear();
        if (chkDisponible != null) chkDisponible.setSelected(true);
        if (txtIdAula != null) txtIdAula.requestFocus();
    }

    private void limpiarCamposHorario() {
        if (cbDiaDisponibilidad != null) cbDiaDisponibilidad.setValue(null);
        if (txtHoraInicioDisponibilidad != null) txtHoraInicioDisponibilidad.clear();
        if (txtHoraFinDisponibilidad != null) txtHoraFinDisponibilidad.clear();
        if (lblResultadoDisponibilidad != null) lblResultadoDisponibilidad.setText("");
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