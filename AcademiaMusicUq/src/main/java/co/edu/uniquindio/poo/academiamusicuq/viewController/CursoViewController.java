package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.CursoController;
import co.edu.uniquindio.poo.academiamusicuq.controller.ClaseController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class CursoViewController {

    // Pestaña Datos Del Curso
    @FXML private TextField txtIdCurso, txtNombreCurso, txtCapacidad;
    @FXML private ComboBox<Instrumento> cbInstrumento;
    @FXML private ComboBox<Nivel> cbNivel;
    @FXML private ComboBox<Profesor> cbProfesor;
    @FXML private TableView<Curso> tblCursos;
    @FXML private TableColumn<Curso, String> colIdCurso, colNombreCurso, colInstrumento, colNivel, colCapacidad, colProfesor;
    @FXML private Button btnAgregarCurso, btnActualizarCurso, btnEliminarCurso, btnLimpiarCurso;

    // Pestaña Configuracion
    @FXML private ComboBox<Curso> cbCursoConfiguracion;
    @FXML private ComboBox<Clase> cbClaseAgregar;
    @FXML private TableView<Clase> tblClasesCurso;
    @FXML private TableColumn<Clase, String> colIdClase, colTipoClase, colAulaClase, colHorarioClase;
    @FXML private Button btnAgregarClaseCurso, btnRemoverClaseCurso;

    // Pestaña Estudiante
    @FXML private ComboBox<Curso> cbCursoEstudiantes;
    @FXML private ComboBox<Estudiante> cbEstudianteVerificar;
    @FXML private TableView<Estudiante> tblEstudiantesInscritos;
    @FXML private TableColumn<Estudiante, String> colIdEstudiante, colNombreEstudiante, colNivelEstudiante, colEstadoNivel;
    @FXML private Button btnVerificarNivel, btnAgregarEstudiante, btnRemoverEstudiante;
    @FXML private Label lblResultadoVerificacion;

    // Botones Generales
    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<Profesor> listaProfesores = FXCollections.observableArrayList();
    private ObservableList<Clase> listaClases = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantesInscritos = FXCollections.observableArrayList();

    private Curso cursoSeleccionado;
    private CursoController cursoController;
    private ClaseController claseController; // NUEVO: Controlador de clases
    private App app;

    @FXML
    void initialize() {
        this.cursoController = new CursoController(App.academia);
        this.claseController = new ClaseController(App.academia); // NUEVO: Inicializar controlador de clases
        configurarInterfaz();
        cargarDatosIniciales();

        // NUEVO: Listener para actualizar datos al cambiar de pestaña
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab.getText().contains("Configuración")) {
                cargarCombosConfiguracion();
            } else if (newTab.getText().contains("Estudiantes")) {
                cargarCombosEstudiantes();
            }
        });
    }

    private void configurarInterfaz() {
        configurarPestanaDatosCurso();
        configurarPestanaConfiguracion();
        configurarPestanaEstudiantes();
        configurarStringConverters();
    }

    // Configurar cómo se muestran los objetos en los ComboBox
    private void configurarStringConverters() {
        // Configurar ComboBox de profesores
        if (cbProfesor != null) {
            cbProfesor.setConverter(new StringConverter<Profesor>() {
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

        // Configurar ComboBox de cursos para configuración
        if (cbCursoConfiguracion != null) {
            cbCursoConfiguracion.setConverter(new StringConverter<Curso>() {
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

        // Configurar ComboBox de cursos para estudiantes
        if (cbCursoEstudiantes != null) {
            cbCursoEstudiantes.setConverter(new StringConverter<Curso>() {
                @Override
                public String toString(Curso curso) {
                    if (curso == null) return "";
                    return curso.getNombreCurso() + " - " + curso.getInstrumento();
                }

                @Override
                public Curso fromString(String string) {
                    return null;
                }
            });
        }

        // Configurar ComboBox de estudiantes
        if (cbEstudianteVerificar != null) {
            cbEstudianteVerificar.setConverter(new StringConverter<Estudiante>() {
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

        // Configurar ComboBox de clases
        if (cbClaseAgregar != null) {
            cbClaseAgregar.setConverter(new StringConverter<Clase>() {
                @Override
                public String toString(Clase clase) {
                    if (clase == null) return "";
                    String tipo = clase.getTipoClase() != null ? clase.getTipoClase().toString() : "N/A";
                    String horario = clase.getHorario() != null ?
                            clase.getHorario().getDia() + " " + clase.getHorario().getHoraInicio() + "-" + clase.getHorario().getHoraFin() : "N/A";
                    return tipo + " - " + horario;
                }

                @Override
                public Clase fromString(String string) {
                    return null;
                }
            });
        }
    }

    // Datos Del Curso
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

    // Datos de Configuracion
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

    // Datos Del Estudiante
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
        if (App.academia != null && App.academia.getListProfesores() != null) {
            listaProfesores.addAll(App.academia.getListProfesores());
            cbProfesor.setItems(listaProfesores);
            if (!listaProfesores.isEmpty()) {
                cbProfesor.setValue(listaProfesores.get(0));
            }
        }

        // NUEVO: Cargar clases disponibles desde el controlador
        listaClases.clear();
        if (claseController != null) {
            List<Clase> clases = claseController.obtenerTodasLasClases();
            if (clases != null) {
                listaClases.addAll(clases);
            }
        }
        cbClaseAgregar.setItems(listaClases);
    }

    private void cargarCombosEstudiantes() {
        // Cargar estudiantes disponibles
        listaEstudiantes.clear();
        if (App.academia != null && App.academia.getListEstudiantes() != null) {
            listaEstudiantes.addAll(App.academia.getListEstudiantes());
            cbEstudianteVerificar.setItems(listaEstudiantes);
            if (!listaEstudiantes.isEmpty()) {
                cbEstudianteVerificar.setValue(listaEstudiantes.get(0));
            }
        }
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

    // Metodos Configuracion
    @FXML
    void onAgregarClaseCurso() {
        if (cbCursoConfiguracion.getValue() != null && cbClaseAgregar.getValue() != null) {
            Curso curso = cbCursoConfiguracion.getValue();
            Clase clase = cbClaseAgregar.getValue();

            if (cursoController.agregarClaseACurso(curso, clase)) {
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
            if (cursoController.removerClaseDeCurso(curso, claseSeleccionada)) {
                actualizarTablaClasesCurso(curso);
                mostrarAlerta("Éxito", "Clase removida del curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover la clase del curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y una clase para remover", Alert.AlertType.WARNING);
        }
    }

    // Metodos Estudiante
    @FXML
    void onVerificarNivel() {
        if (cbCursoEstudiantes.getValue() != null && cbEstudianteVerificar.getValue() != null) {
            Curso curso = cbCursoEstudiantes.getValue();
            Estudiante estudiante = cbEstudianteVerificar.getValue();

            boolean nivelAdecuado = cursoController.verificarNivelEstudiante(curso, estudiante);

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

            if (cursoController.agregarEstudianteACurso(curso, estudiante)) {
                actualizarTablaEstudiantesCurso(curso);
                mostrarAlerta("Éxito", "Estudiante agregado al curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo agregar el estudiante. Verifique el nivel o la capacidad del curso.", Alert.AlertType.ERROR);
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
            if (cursoController.removerEstudianteDeCurso(curso, estudianteSeleccionado)) {
                actualizarTablaEstudiantesCurso(curso);
                mostrarAlerta("Éxito", "Estudiante removido del curso correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo remover el estudiante del curso", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un curso y un estudiante para remover", Alert.AlertType.WARNING);
        }
    }

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