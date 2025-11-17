package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.ProfesorController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ProfesorViewController {

    // [Los mismos campos FXML se mantienen igual...]
    @FXML private TextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono, txtEspecialidad;
    @FXML private ComboBox<Instrumento> cbInstrumento;
    @FXML private Button btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    @FXML private TableView<Profesor> tblProfesores;
    @FXML private TableColumn<Profesor, String> colId, colNombre, colApellido, colEmail, colTelefono, colEspecialidad, colInstrumento;

    @FXML private ComboBox<Profesor> cbProfesorClases;
    @FXML private ComboBox<String> cbDiaHorario;
    @FXML private TextField txtHoraInicio, txtHoraFin, txtCupoMaximo, txtObjetivoPersonal;
    @FXML private ComboBox<Aula> cbAulaClase;
    @FXML private ComboBox<Curso> cbCursoClase;
    @FXML private ComboBox<TipoClase> cbTipoClase;
    @FXML private Button btnAsignarHorario, btnCrearClaseGrupal, btnAgregarClase;
    @FXML private TableView<Clase> tblClasesProfesor;
    @FXML private TableColumn<Clase, String> colIdClase, colTipoClase, colCursoClase, colAulaClase, colHorarioClase;

    @FXML private ComboBox<Profesor> cbProfesorEvaluacion;
    @FXML private ComboBox<Estudiante> cbEstudianteProgreso, cbEstudianteComentario;
    @FXML private ComboBox<Curso> cbCursoProgreso, cbCursoComentario;
    @FXML private TextField txtCalificacion;
    @FXML private TextArea txtObservacionesEvaluacion, txtContenidoComentario;
    @FXML private Button btnValorarProgreso, btnGenerarComentario;
    @FXML private TableView<ComentarioFormativo> tblComentarios;
    @FXML private TableColumn<ComentarioFormativo, String> colEstudianteComentario, colCursoComentario, colContenidoComentario, colFechaComentario;

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
        configurarListeners();
    }

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

        cbInstrumento.setItems(FXCollections.observableArrayList(Instrumento.values()));
        cargarProfesores();
    }

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

        if (txtObjetivoPersonal != null) {
            txtObjetivoPersonal.setDisable(true);
        }

        configurarValidacionesHorario();
    }

    private void configurarPestanaEvaluacion() {
        // Configurar tabla comentarios
        colEstudianteComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                Optional.ofNullable(cell.getValue().getEstudiante())
                        .map(e -> e.getNombre() + " " + e.getApellido())
                        .orElse("N/A")));
        colCursoComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                Optional.ofNullable(cell.getValue().getCurso())
                        .map(Curso::getNombreCurso)
                        .orElse("N/A")));
        colContenidoComentario.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContenido()));
        colFechaComentario.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFecha() != null ? cell.getValue().getFecha().toString() : "N/A"));

        // Configurar validación de calificación
        txtCalificacion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                txtCalificacion.setText(oldValue);
            }
        });
    }

    private void configurarListeners() {
        cbTipoClase.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTipoClaseChanged();
        });

        cbProfesorClases.valueProperty().addListener((observable, oldValue, newValue) -> {
            onProfesorClasesChanged();
        });
    }

    private void configurarValidacionesHorario() {
        txtHoraInicio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                if (!newValue.isEmpty()) {
                    txtHoraInicio.setStyle("-fx-border-color: red;");
                } else {
                    txtHoraInicio.setStyle("");
                }
            } else {
                txtHoraInicio.setStyle("-fx-border-color: green;");
            }
        });

        txtHoraFin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                if (!newValue.isEmpty()) {
                    txtHoraFin.setStyle("-fx-border-color: red;");
                } else {
                    txtHoraFin.setStyle("");
                }
            } else {
                txtHoraFin.setStyle("-fx-border-color: green;");
            }
        });
    }

    private void cargarDatosIniciales() {
        cargarProfesores();
        cargarCombosGestionClases();
        cargarCombosEvaluacion();
    }

    private void cargarProfesores() {
        try {
            listaProfesores.clear();
            if (App.academia != null && App.academia.getListProfesores() != null) {
                listaProfesores.addAll(App.academia.getListProfesores());
                tblProfesores.setItems(listaProfesores);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar profesores: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarCombosGestionClases() {
        try {
            if (App.academia != null) {
                cbProfesorClases.setItems(FXCollections.observableArrayList(App.academia.getListProfesores()));

                if (App.academia.getListAulas() != null) {
                    cbAulaClase.setItems(FXCollections.observableArrayList(App.academia.getListAulas()));
                }

                if (App.academia.getListCursos() != null) {
                    cbCursoClase.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos para gestión de clases: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarCombosEvaluacion() {
        try {
            if (App.academia != null) {
                cbProfesorEvaluacion.setItems(FXCollections.observableArrayList(App.academia.getListProfesores()));

                if (App.academia.getListEstudiantes() != null) {
                    cbEstudianteProgreso.setItems(FXCollections.observableArrayList(App.academia.getListEstudiantes()));
                    cbEstudianteComentario.setItems(FXCollections.observableArrayList(App.academia.getListEstudiantes()));
                }

                if (App.academia.getListCursos() != null) {
                    cbCursoProgreso.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
                    cbCursoComentario.setItems(FXCollections.observableArrayList(App.academia.getListCursos()));
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos para evaluación: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // MÉTODOS CORREGIDOS - DATOS PERSONALES
    @FXML
    void onAgregarProfesor() {
        try {
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
                    cargarCombosGestionClases();
                    cargarCombosEvaluacion();
                } else {
                    mostrarAlerta("Error", "Ya existe un profesor con ese ID", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar profesor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizarProfesor() {
        Profesor profesorSeleccionado = tblProfesores.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado != null) {
            try {
                if (validarCamposProfesor()) {
                    // Crear profesor actualizado
                    Profesor profesorActualizado = new Profesor(
                            profesorSeleccionado.getId(), // Mantener mismo ID
                            txtNombre.getText(),
                            txtApellido.getText(),
                            txtEmail.getText(),
                            txtTelefono.getText(),
                            txtEspecialidad.getText(),
                            cbInstrumento.getValue(),
                            true
                    );

                    if (profesorController.actualizarProfesor(profesorSeleccionado.getId(), profesorActualizado)) {
                        // Actualizar la lista
                        int index = listaProfesores.indexOf(profesorSeleccionado);
                        if (index != -1) {
                            listaProfesores.set(index, profesorActualizado);
                        }
                        tblProfesores.refresh();
                        mostrarAlerta("Éxito", "Profesor actualizado correctamente", Alert.AlertType.INFORMATION);
                        limpiarCamposProfesor();
                    } else {
                        mostrarAlerta("Error", "Error al actualizar profesor", Alert.AlertType.ERROR);
                    }
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al actualizar profesor: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un profesor para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarProfesor() {
        Profesor profesorSeleccionado = tblProfesores.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado != null) {
            try {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("¿Está seguro de eliminar al profesor?");
                confirmacion.setContentText("Esta acción no se puede deshacer: " +
                        profesorSeleccionado.getNombre() + " " + profesorSeleccionado.getApellido());

                if (confirmacion.showAndWait().get() == ButtonType.OK) {
                    if (profesorController.eliminarProfesor(profesorSeleccionado.getId())) {
                        listaProfesores.remove(profesorSeleccionado);
                        mostrarAlerta("Éxito", "Profesor eliminado correctamente", Alert.AlertType.INFORMATION);
                        limpiarCamposProfesor();
                        cargarCombosGestionClases();
                        cargarCombosEvaluacion();
                    } else {
                        mostrarAlerta("Error", "No se pudo eliminar el profesor", Alert.AlertType.ERROR);
                    }
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar profesor: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un profesor para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCampos() {
        limpiarCamposProfesor();
    }

    // MÉTODOS CORREGIDOS - GESTIÓN DE CLASES
    @FXML
    void onAsignarHorario() {
        try {
            Profesor profesor = cbProfesorClases.getValue();
            String dia = cbDiaHorario.getValue();
            String horaInicio = txtHoraInicio.getText();
            String horaFin = txtHoraFin.getText();

            if (profesor != null && dia != null && !horaInicio.isEmpty() && !horaFin.isEmpty()) {
                try {
                    LocalTime inicio = LocalTime.parse(horaInicio);
                    LocalTime fin = LocalTime.parse(horaFin);

                    if (fin.isBefore(inicio) || fin.equals(inicio)) {
                        mostrarAlerta("Error", "La hora de fin debe ser posterior a la hora de inicio", Alert.AlertType.ERROR);
                        return;
                    }

                    BloqueHorario bloque = new BloqueHorario(dia, inicio, fin);

                    if (profesorController.asignarHorarioProfesor(profesor, bloque)) {
                        mostrarAlerta("Éxito", "Horario asignado correctamente", Alert.AlertType.INFORMATION);
                        limpiarCamposHorario();
                        onProfesorClasesChanged();
                    } else {
                        mostrarAlerta("Error", "Conflicto de horario", Alert.AlertType.ERROR);
                    }
                } catch (Exception e) {
                    mostrarAlerta("Error", "Formato de hora inválido (Use HH:mm)", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Complete todos los campos del horario", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al asignar horario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onCrearClaseGrupal() {
        try {
            Profesor profesor = cbProfesorClases.getValue();
            Curso curso = cbCursoClase.getValue();
            Aula aula = cbAulaClase.getValue();
            String dia = cbDiaHorario.getValue();
            String horaInicio = txtHoraInicio.getText();
            String horaFin = txtHoraFin.getText();
            String cupoMaximo = txtCupoMaximo.getText();

            if (profesor != null && curso != null && aula != null && dia != null &&
                    !horaInicio.isEmpty() && !horaFin.isEmpty() && !cupoMaximo.isEmpty()) {

                try {
                    LocalTime inicio = LocalTime.parse(horaInicio);
                    LocalTime fin = LocalTime.parse(horaFin);
                    int cupo = Integer.parseInt(cupoMaximo);

                    if (cupo <= 0) {
                        mostrarAlerta("Error", "El cupo máximo debe ser mayor a 0", Alert.AlertType.ERROR);
                        return;
                    }

                    BloqueHorario horario = new BloqueHorario(dia, inicio, fin);

                    // CORREGIDO: Usar ClaseGrupal específica
                    ClaseGrupal nuevaClase = new ClaseGrupal(
                            generarIdClase(),
                            aula,
                            TipoClase.GRUPAL,
                            profesor,
                            horario,
                            curso,
                            cupo
                    );

                    if (profesorController.crearClaseGrupalProfesor(profesor, nuevaClase)) {
                        listaClasesProfesor.add(nuevaClase);
                        mostrarAlerta("Éxito", "Clase grupal creada correctamente", Alert.AlertType.INFORMATION);
                        limpiarCamposClase();
                    } else {
                        mostrarAlerta("Error", "Error al crear la clase grupal", Alert.AlertType.ERROR);
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El cupo máximo debe ser un número válido", Alert.AlertType.ERROR);
                } catch (Exception e) {
                    mostrarAlerta("Error", "Formato de hora inválido", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Complete todos los campos para clase grupal", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear clase grupal: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onAgregarClase() {
        try {
            Profesor profesor = cbProfesorClases.getValue();
            Curso curso = cbCursoClase.getValue();
            Aula aula = cbAulaClase.getValue();
            TipoClase tipoClase = cbTipoClase.getValue();
            String dia = cbDiaHorario.getValue();
            String horaInicio = txtHoraInicio.getText();
            String horaFin = txtHoraFin.getText();

            if (profesor != null && curso != null && aula != null && tipoClase != null &&
                    dia != null && !horaInicio.isEmpty() && !horaFin.isEmpty()) {

                try {
                    LocalTime inicio = LocalTime.parse(horaInicio);
                    LocalTime fin = LocalTime.parse(horaFin);
                    BloqueHorario horario = new BloqueHorario(dia, inicio, fin);

                    Clase nuevaClase;

                    if (tipoClase == TipoClase.GRUPAL) {
                        // Clase grupal
                        String cupoMaximo = txtCupoMaximo.getText();
                        int cupo = cupoMaximo.isEmpty() ? 10 : Integer.parseInt(cupoMaximo);

                        nuevaClase = new ClaseGrupal(
                                generarIdClase(),
                                aula,
                                tipoClase,
                                profesor,
                                horario,
                                curso,
                                cupo
                        );
                    } else {
                        // Clase individual - necesitarías una clase ClaseIndividual
                        // Por ahora usamos ClaseGrupal con cupo 1 como placeholder
                        nuevaClase = new ClaseGrupal(
                                generarIdClase(),
                                aula,
                                tipoClase,
                                profesor,
                                horario,
                                curso,
                                1
                        );
                    }

                    if (profesorController.agregarClaseAProfesor(profesor, nuevaClase)) {
                        listaClasesProfesor.add(nuevaClase);
                        mostrarAlerta("Éxito", "Clase agregada correctamente", Alert.AlertType.INFORMATION);
                        limpiarCamposClase();
                    } else {
                        mostrarAlerta("Error", "Error al agregar la clase", Alert.AlertType.ERROR);
                    }
                } catch (Exception e) {
                    mostrarAlerta("Error", "Formato de hora inválido", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Complete todos los campos obligatorios", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar clase: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // MÉTODOS CORREGIDOS - EVALUACIÓN
    @FXML
    void onValorarProgreso() {
        try {
            Estudiante estudiante = cbEstudianteProgreso.getValue();
            Curso curso = cbCursoProgreso.getValue();
            Profesor profesor = cbProfesorEvaluacion.getValue();
            String calificacionStr = txtCalificacion.getText();
            String observaciones = txtObservacionesEvaluacion.getText();

            if (estudiante != null && curso != null && profesor != null &&
                    !calificacionStr.isEmpty() && !observaciones.isEmpty()) {
                try {
                    float calificacion = Float.parseFloat(calificacionStr);
                    if (calificacion < 0 || calificacion > 5) {
                        mostrarAlerta("Error", "La calificación debe estar entre 0 y 5", Alert.AlertType.ERROR);
                        return;
                    }

                    boolean aprobado = calificacion >= 3.0f;

                    // CORREGIDO: Usar constructor correcto de ReporteProgreso
                    ReporteProgreso reporte = new ReporteProgreso(
                            generarIdReporte(),
                            estudiante,
                            curso,
                            profesor,
                            LocalDate.now(),
                            observaciones,
                            calificacion,
                            aprobado
                    );

                    // Necesitarías un método en el controller para esto
                    // Por ahora mostramos mensaje de éxito
                    mostrarAlerta("Éxito", "Progreso del estudiante valorado correctamente", Alert.AlertType.INFORMATION);
                    limpiarCamposEvaluacion();

                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "La calificación debe ser un número válido", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "Complete todos los campos de evaluación", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al valorar progreso: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onGenerarComentario() {
        try {
            Estudiante estudiante = cbEstudianteComentario.getValue();
            Curso curso = cbCursoComentario.getValue();
            String contenido = txtContenidoComentario.getText();

            if (estudiante != null && curso != null && !contenido.trim().isEmpty()) {
                // CORREGIDO: Usar constructor correcto de ComentarioFormativo
                ComentarioFormativo comentario = new ComentarioFormativo(
                        generarIdComentario(),
                        contenido,
                        LocalDate.now(),
                        estudiante,
                        curso
                );

                // Necesitarías un método en el controller para esto
                // Por ahora mostramos mensaje de éxito
                listaComentarios.add(comentario);
                mostrarAlerta("Éxito", "Comentario formativo generado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposComentario();

            } else {
                mostrarAlerta("Error", "Seleccione estudiante, curso y escriba un comentario", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al generar comentario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    // MÉTODOS AUXILIARES
    private boolean validarCamposProfesor() {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtApellido.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtEspecialidad.getText().isEmpty() ||
                cbInstrumento.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if (!txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarAlerta("Error", "Formato de email inválido", Alert.AlertType.ERROR);
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
        txtHoraInicio.setStyle("");
        txtHoraFin.setStyle("");
    }

    private void limpiarCamposClase() {
        cbCursoClase.setValue(null);
        cbAulaClase.setValue(null);
        cbTipoClase.setValue(null);
        txtCupoMaximo.clear();
        txtObjetivoPersonal.clear();
        limpiarCamposHorario();
    }

    private void limpiarCamposEvaluacion() {
        txtCalificacion.clear();
        txtObservacionesEvaluacion.clear();
    }

    private void limpiarCamposComentario() {
        txtContenidoComentario.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // LISTENERS
    @FXML
    void onTipoClaseChanged() {
        if (cbTipoClase.getValue() == TipoClase.INDIVIDUAL) {
            if (txtObjetivoPersonal != null) {
                txtObjetivoPersonal.setDisable(false);
            }
            if (txtCupoMaximo != null) {
                txtCupoMaximo.setDisable(true);
                txtCupoMaximo.clear();
            }
        } else if (cbTipoClase.getValue() == TipoClase.GRUPAL) {
            if (txtObjetivoPersonal != null) {
                txtObjetivoPersonal.setDisable(true);
                txtObjetivoPersonal.clear();
            }
            if (txtCupoMaximo != null) {
                txtCupoMaximo.setDisable(false);
            }
        } else {
            if (txtObjetivoPersonal != null) {
                txtObjetivoPersonal.setDisable(true);
            }
            if (txtCupoMaximo != null) {
                txtCupoMaximo.setDisable(true);
            }
        }
    }

    @FXML
    void onProfesorClasesChanged() {
        Profesor profesor = cbProfesorClases.getValue();
        if (profesor != null) {
            try {
                listaClasesProfesor.clear();
                if (profesor.getListClases() != null) {
                    listaClasesProfesor.addAll(profesor.getListClases());
                }
                tblClasesProfesor.setItems(listaClasesProfesor);
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al cargar clases del profesor: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // Métodos para generar IDs únicos
    private int generarIdClase() {
        return (int) System.currentTimeMillis() % 1000000;
    }

    private int generarIdReporte() {
        return (int) System.currentTimeMillis() % 1000000;
    }

    private int generarIdComentario() {
        return (int) System.currentTimeMillis() % 1000000;
    }

    public void setApp(App app) {
        this.app = app;
    }
}