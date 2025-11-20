package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.EstudianteController;
import co.edu.uniquindio.poo.academiamusicuq.controller.ClaseController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class EstudianteViewController {

    // Datos Personales
    @FXML private TextField txtId, txtNombre, txtApellido, txtEmail, txtTelefono;
    @FXML private ComboBox<Nivel> cbNivel;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, String> colId, colNombre, colApellido, colEmail, colTelefono, colNivel;
    @FXML private Button btnAgregar, btnActualizar, btnEliminar, btnLimpiar;

    // Gestion Academia
    @FXML private ComboBox<Estudiante> cbEstudianteAcademico, cbEstudianteClases, cbEstudianteHorario;
    @FXML private ComboBox<Curso> cbCursosInscribir;
    @FXML private ComboBox<ClaseGrupal> cbClasesGrupales;
    @FXML private ComboBox<ClaseIndividual> cbClasesIndividuales;
    @FXML private TextArea txtHorario;
    @FXML private Button btnInscribirCurso, btnInscribirClaseGrupal, btnAgregarClaseIndividual, btnConsultarHorario;

    // Pestaña Progreso
    @FXML private ComboBox<Estudiante> cbEstudianteReporte;
    @FXML private TableView<ReporteProgreso> tblReportes;
    @FXML private TableView<Asistencia> tblAsistencias;
    @FXML private TableColumn<ReporteProgreso, String> colCursoReporte, colProfesorReporte, colObservaciones, colCalificacion, colAprobado;
    @FXML private TableColumn<Asistencia, String> colClaseAsistencia, colFechaAsistencia, colPresente;
    @FXML private Button btnGenerarReporte, btnCargarAsistencias;

    // Boton General
    @FXML private Button btnVolver;
    // TabPane General
    @FXML private TabPane tabPane;

    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<ClaseGrupal> listaClasesGrupales = FXCollections.observableArrayList();
    private ObservableList<ClaseIndividual> listaClasesIndividuales = FXCollections.observableArrayList();
    private ObservableList<ReporteProgreso> listaReportes = FXCollections.observableArrayList();
    private ObservableList<Asistencia> listaAsistencias = FXCollections.observableArrayList();

    private Estudiante estudianteSeleccionado;
    private EstudianteController estudianteController;
    private ClaseController claseController;
    private App app;

    @FXML
    void initialize() {
        this.estudianteController = new EstudianteController(App.academia);
        this.claseController = new ClaseController(App.academia);
        configurarInterfaz();
        cargarDatosIniciales();

        // Listener para actualizar datos al cambiar de pestaña
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab.getText().contains("Gestión Académica")) {
                cargarCombosGestionAcademica();
            }
        });
    }

    private void configurarInterfaz() {
        configurarPestanaDatosPersonales();
        configurarPestanaGestionAcademica();
        configurarPestanaProgreso();
        configurarStringConverters();
    }

    // Configurar cómo se muestran los objetos en los ComboBox
    private void configurarStringConverters() {
        // Configurar ComboBox de estudiantes para gestión académica
        if (cbEstudianteAcademico != null) {
            cbEstudianteAcademico.setConverter(new StringConverter<Estudiante>() {
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

        // Configurar ComboBox de estudiantes para clases
        if (cbEstudianteClases != null) {
            cbEstudianteClases.setConverter(new StringConverter<Estudiante>() {
                @Override
                public String toString(Estudiante estudiante) {
                    if (estudiante == null) return "";
                    return estudiante.getNombre() + " " + estudiante.getApellido();
                }

                @Override
                public Estudiante fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de estudiantes para horario
        if (cbEstudianteHorario != null) {
            cbEstudianteHorario.setConverter(new StringConverter<Estudiante>() {
                @Override
                public String toString(Estudiante estudiante) {
                    if (estudiante == null) return "";
                    return estudiante.getNombre() + " " + estudiante.getApellido();
                }

                @Override
                public Estudiante fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de estudiantes para reportes
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
        if (cbCursosInscribir != null) {
            cbCursosInscribir.setConverter(new StringConverter<Curso>() {
                @Override
                public String toString(Curso curso) {
                    if (curso == null) return "";
                    return curso.getNombreCurso() + " - " + curso.getInstrumento() + " (" + curso.getNivel() + ")";
                }

                @Override
                public Curso fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de clases grupales
        if (cbClasesGrupales != null) {
            cbClasesGrupales.setConverter(new StringConverter<ClaseGrupal>() {
                @Override
                public String toString(ClaseGrupal clase) {
                    if (clase == null) return "";
                    String horario = clase.getHorario() != null ?
                            clase.getHorario().getDia() + " " + clase.getHorario().getHoraInicio() + "-" + clase.getHorario().getHoraFin() : "N/A";
                    String profesor = clase.getProfesor() != null ?
                            clase.getProfesor().getNombre() + " " + clase.getProfesor().getApellido() : "N/A";
                    return "Grupal - " + horario + " - " + profesor;
                }

                @Override
                public ClaseGrupal fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de clases individuales
        if (cbClasesIndividuales != null) {
            cbClasesIndividuales.setConverter(new StringConverter<ClaseIndividual>() {
                @Override
                public String toString(ClaseIndividual clase) {
                    if (clase == null) return "";
                    String horario = clase.getHorario() != null ?
                            clase.getHorario().getDia() + " " + clase.getHorario().getHoraInicio() + "-" + clase.getHorario().getHoraFin() : "N/A";
                    String profesor = clase.getProfesor() != null ?
                            clase.getProfesor().getNombre() + " " + clase.getProfesor().getApellido() : "N/A";
                    return "Individual - " + horario + " - " + profesor;
                }

                @Override
                public ClaseIndividual fromString(String string) {
                    return null;
                }
            });
        }
    }

    // Datos Personales
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

    // Datos Gestion Academia
    private void configurarPestanaGestionAcademica() {
        // Cargar combos de estudiantes
        cbEstudianteAcademico.setItems(listaEstudiantes);
        cbEstudianteClases.setItems(listaEstudiantes);
        cbEstudianteHorario.setItems(listaEstudiantes);

        // Cargar combos con datos de la academia
        cargarCombosGestionAcademica();
    }

    // Datos Progreso
    private void configurarPestanaProgreso() {
        // Configurar tabla reportes
        colCursoReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colProfesorReporte.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getProfesor() != null ?
                        cell.getValue().getProfesor().getNombre() + " " + cell.getValue().getProfesor().getApellido() : "N/A"));
        colObservaciones.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getObservaciones()));
        colCalificacion.setCellValueFactory(cell -> new SimpleStringProperty(
                String.format("%.1f", cell.getValue().getCalificacion())));
        colAprobado.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isAprobado() ? "✅ SÍ" : "❌ NO"));

        // Configurar tabla asistencias
        colClaseAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getClase() != null ?
                        "Clase " + cell.getValue().getClase().getId() + " - " +
                                (cell.getValue().getClase().getTipoClase() != null ? cell.getValue().getClase().getTipoClase().toString() : "N/A") : "N/A"));
        colFechaAsistencia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFecha().toString()));
        colPresente.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getPresente() ? "✅ PRESENTE" : "❌ AUSENTE"));

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
        if (App.academia != null && App.academia.getListCursos() != null) {
            listaCursos.addAll(App.academia.getListCursos());
            cbCursosInscribir.setItems(listaCursos);
            if (!listaCursos.isEmpty()) {
                cbCursosInscribir.setValue(listaCursos.get(0));
            }
        }

        // Cargar clases grupales disponibles desde el controlador
        listaClasesGrupales.clear();
        if (claseController != null) {
            List<ClaseGrupal> clasesGrupales = claseController.obtenerClasesGrupales();
            if (clasesGrupales != null) {
                listaClasesGrupales.addAll(clasesGrupales);
            }
        }
        cbClasesGrupales.setItems(listaClasesGrupales);

        // Cargar clases individuales disponibles desde el controlador
        listaClasesIndividuales.clear();
        if (claseController != null) {
            List<ClaseIndividual> clasesIndividuales = claseController.obtenerClasesIndividuales();
            if (clasesIndividuales != null) {
                listaClasesIndividuales.addAll(clasesIndividuales);
            }
        }
        cbClasesIndividuales.setItems(listaClasesIndividuales);
    }

    // Metodos Datos Personales
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

    // Metodos Gestion Academia
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

    // Metodos Progreso
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