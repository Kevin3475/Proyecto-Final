package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import co.edu.uniquindio.poo.academiamusicuq.controller.MatriculaController;
import co.edu.uniquindio.poo.academiamusicuq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class MatriculaViewController {

    @FXML private TextField txtIdMatricula;
    @FXML private ComboBox<Estudiante> cbEstudianteMatricula;
    @FXML private ComboBox<Curso> cbCursoMatricula;
    @FXML private DatePicker dpFechaInscripcion;
    @FXML private ComboBox<EstadoMatricula> cbEstadoMatricula;
    @FXML private TableView<Matricula> tblMatriculas;
    @FXML private TableColumn<Matricula, String> colIdMatricula, colEstudianteMatricula, colCursoMatricula, colFechaMatricula, colEstadoMatricula;
    @FXML private Button btnAgregarMatricula, btnActualizarMatricula, btnEliminarMatricula, btnLimpiarMatricula;

    @FXML private ComboBox<Matricula> cbMatriculaCertificado;
    @FXML private TableView<Matricula> tblMatriculasCertificados;
    @FXML private TableColumn<Matricula, String> colIdMatriculaCert, colEstudianteCert, colCursoCert, colEstadoCert, colCertificadoCert;
    @FXML private Button btnEmitirCertificado, btnVerCertificado;
    @FXML private Label lblInfoCertificado;

    @FXML private Button btnVolver;
    @FXML private TabPane tabPane;

    private ObservableList<Matricula> listaMatriculas = FXCollections.observableArrayList();
    private ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<EstadoMatricula> listaEstados = FXCollections.observableArrayList();

    private Matricula matriculaSeleccionada;
    private MatriculaController matriculaController;
    private App app;

    @FXML
    void initialize() {
        this.matriculaController = new MatriculaController(App.academia);
        configurarCombos(); // Nueva línea agregada
        configurarInterfaz();
        cargarDatosIniciales();
    }

    private void configurarCombos() {
        // Configurar ComboBox de Estudiantes
        cbEstudianteMatricula.setCellFactory(param -> new ListCell<Estudiante>() {
            @Override
            protected void updateItem(Estudiante estudiante, boolean empty) {
                super.updateItem(estudiante, empty);
                if (empty || estudiante == null) {
                    setText(null);
                } else {
                    setText(estudiante.getNombre() + " " + estudiante.getApellido() + " (" + estudiante.getId() + ")");
                }
            }
        });

        cbEstudianteMatricula.setButtonCell(new ListCell<Estudiante>() {
            @Override
            protected void updateItem(Estudiante estudiante, boolean empty) {
                super.updateItem(estudiante, empty);
                if (empty || estudiante == null) {
                    setText(null);
                } else {
                    setText(estudiante.getNombre() + " " + estudiante.getApellido() + " (" + estudiante.getId() + ")");
                }
            }
        });

        // Configurar ComboBox de Cursos
        cbCursoMatricula.setCellFactory(param -> new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (empty || curso == null) {
                    setText(null);
                } else {
                    setText(curso.getNombreCurso() + " - " + curso.getInstrumento());
                }
            }
        });

        cbCursoMatricula.setButtonCell(new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (empty || curso == null) {
                    setText(null);
                } else {
                    setText(curso.getNombreCurso() + " - " + curso.getInstrumento());
                }
            }
        });

        // Configurar ComboBox de Matrículas para Certificados
        cbMatriculaCertificado.setCellFactory(param -> new ListCell<Matricula>() {
            @Override
            protected void updateItem(Matricula matricula, boolean empty) {
                super.updateItem(matricula, empty);
                if (empty || matricula == null) {
                    setText(null);
                } else {
                    setText("Matrícula " + matricula.getIdMatricula() + " - " +
                            matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido());
                }
            }
        });

        cbMatriculaCertificado.setButtonCell(new ListCell<Matricula>() {
            @Override
            protected void updateItem(Matricula matricula, boolean empty) {
                super.updateItem(matricula, empty);
                if (empty || matricula == null) {
                    setText(null);
                } else {
                    setText("Matrícula " + matricula.getIdMatricula() + " - " +
                            matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido());
                }
            }
        });
    }

    private void configurarInterfaz() {
        configurarPestanaGestionMatriculas();
        configurarPestanaCertificados();
    }

    // Datos Gestion Matricula
    private void configurarPestanaGestionMatriculas() {
        // Configurar tabla matriculas
        colIdMatricula.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdMatricula())));
        colEstudianteMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colFechaMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFechaInscripcion().toString()));
        colEstadoMatricula.setCellValueFactory(cell -> new SimpleStringProperty(getEstadoMatriculaTexto(cell.getValue().getEstadoMatricula())));

        tblMatriculas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            matriculaSeleccionada = newVal;
            mostrarMatriculaSeleccionada();
        });

        // Configurar combo boxes
        cbEstadoMatricula.setItems(FXCollections.observableArrayList(EstadoMatricula.values()));
        dpFechaInscripcion.setValue(LocalDate.now());
    }

    // Datos Certificados
    private void configurarPestanaCertificados() {
        // Configurar tabla matrículas para certificados
        colIdMatriculaCert.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getIdMatricula())));
        colEstudianteCert.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstudiante() != null ?
                        cell.getValue().getEstudiante().getNombre() + " " + cell.getValue().getEstudiante().getApellido() : "N/A"));
        colCursoCert.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getCurso() != null ? cell.getValue().getCurso().getNombreCurso() : "N/A"));
        colEstadoCert.setCellValueFactory(cell -> new SimpleStringProperty(getEstadoMatriculaTexto(cell.getValue().getEstadoMatricula())));
        colCertificadoCert.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().isCertificadoEmitido() ? "✅ Emitido" : "❌ No emitido"));
    }

    private String getEstadoMatriculaTexto(EstadoMatricula estado) {
        switch (estado) {
            case ACTIVA: return "✅ Activa";
            case FINALIZADA: return "🏁 Finalizada";
            case CANCELADA: return "❌ Cancelada";
            default: return "N/A";
        }
    }

    private void cargarDatosIniciales() {
        cargarMatriculas();
        cargarCombosGestionMatriculas();
        cargarCombosCertificados();
    }

    private void cargarMatriculas() {
        listaMatriculas.clear();
        listaMatriculas.addAll(matriculaController.obtenerTodasLasMatriculas());
        tblMatriculas.setItems(listaMatriculas);

        // Actualizar combos que dependen de matrículas
        cbMatriculaCertificado.setItems(listaMatriculas);
        tblMatriculasCertificados.setItems(listaMatriculas);
    }

    private void cargarCombosGestionMatriculas() {
        // Cargar estudiantes disponibles
        listaEstudiantes.clear();
        listaEstudiantes.addAll(App.academia.getListEstudiantes());
        cbEstudianteMatricula.setItems(listaEstudiantes);

        // Cargar cursos disponibles
        listaCursos.clear();
        listaCursos.addAll(App.academia.getListCursos());
        cbCursoMatricula.setItems(listaCursos);
    }

    private void cargarCombosCertificados() {
        // Los combos ya se configuraron en initialize()
    }

    // Metodos Gestion Matriculas
    private void mostrarMatriculaSeleccionada() {
        if (matriculaSeleccionada != null) {
            txtIdMatricula.setText(String.valueOf(matriculaSeleccionada.getIdMatricula()));
            cbEstudianteMatricula.setValue(matriculaSeleccionada.getEstudiante());
            cbCursoMatricula.setValue(matriculaSeleccionada.getCurso());
            dpFechaInscripcion.setValue(matriculaSeleccionada.getFechaInscripcion());
            cbEstadoMatricula.setValue(matriculaSeleccionada.getEstadoMatricula());
        }
    }

    @FXML
    void onAgregarMatricula() {
        if (validarCamposMatricula()) {
            Matricula matricula = new Matricula(
                    Integer.parseInt(txtIdMatricula.getText()),
                    cbEstudianteMatricula.getValue(),
                    cbCursoMatricula.getValue(),
                    dpFechaInscripcion.getValue(),
                    cbEstadoMatricula.getValue(),
                    false // certificadoEmitido inicialmente false
            );

            if (matriculaController.registrarMatricula(matricula)) {
                listaMatriculas.add(matricula);
                limpiarCamposMatricula();
                mostrarAlerta("Éxito", "Matrícula registrada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Ya existe una matrícula con este ID", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizarMatricula() {
        if (matriculaSeleccionada != null && validarCamposMatricula()) {
            Matricula actualizada = new Matricula(
                    Integer.parseInt(txtIdMatricula.getText()),
                    cbEstudianteMatricula.getValue(),
                    cbCursoMatricula.getValue(),
                    dpFechaInscripcion.getValue(),
                    cbEstadoMatricula.getValue(),
                    matriculaSeleccionada.isCertificadoEmitido()
            );

            if (matriculaController.actualizarMatricula(matriculaSeleccionada.getIdMatricula(), actualizada)) {
                cargarMatriculas();
                limpiarCamposMatricula();
                mostrarAlerta("Éxito", "Matrícula actualizada correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Error al actualizar la matrícula", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione una matrícula para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminarMatricula() {
        if (matriculaSeleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta matrícula?");
            confirmacion.setContentText("Matrícula ID: " + matriculaSeleccionada.getIdMatricula());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (matriculaController.eliminarMatricula(matriculaSeleccionada.getIdMatricula())) {
                    listaMatriculas.remove(matriculaSeleccionada);
                    limpiarCamposMatricula();
                    mostrarAlerta("Éxito", "Matrícula eliminada correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "Error al eliminar la matrícula", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione una matrícula para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiarCamposMatricula() {
        limpiarCamposMatricula();
        tblMatriculas.getSelectionModel().clearSelection();
        matriculaSeleccionada = null;
    }

    // Metodos Certificados
    @FXML
    void onEmitirCertificado() {
        Matricula matricula = cbMatriculaCertificado.getValue();

        if (matricula != null) {
            if (matricula.puedeEmitirCertificado()) {
                if (matriculaController.emitirCertificado(matricula.getIdMatricula())) {
                    actualizarInfoCertificado(matricula);
                    cargarMatriculas(); // Recargar para actualizar estado
                    mostrarAlerta("Éxito", "Certificado emitido correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo emitir el certificado", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error",
                        "No se puede emitir certificado. La matrícula debe estar FINALIZADA y no tener certificado previo",
                        Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione una matrícula para emitir certificado", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onVerCertificado() {
        Matricula matricula = tblMatriculasCertificados.getSelectionModel().getSelectedItem();

        if (matricula != null) {
            if (matricula.isCertificadoEmitido()) {
                mostrarAlerta("Certificado",
                        "CERTIFICADO DE APROBACIÓN\n\n" +
                                "Estudiante: " + matricula.getEstudiante().getNombre() + " " + matricula.getEstudiante().getApellido() + "\n" +
                                "Curso: " + matricula.getCurso().getNombreCurso() + "\n" +
                                "Instrumento: " + matricula.getCurso().getInstrumento() + "\n" +
                                "Nivel: " + matricula.getCurso().getNivel() + "\n" +
                                "Fecha de finalización: " + matricula.getFechaInscripcion().plusMonths(3) + "\n\n" +
                                "ACADEMIA DE MÚSICA UQ",
                        Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Esta matrícula no tiene certificado emitido", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "Seleccione una matrícula para ver el certificado", Alert.AlertType.WARNING);
        }
    }

    private void actualizarInfoCertificado(Matricula matricula) {
        if (matricula != null) {
            StringBuilder info = new StringBuilder();
            info.append("Estudiante: ").append(matricula.getEstudiante().getNombre()).append(" ").append(matricula.getEstudiante().getApellido()).append("\n");
            info.append("Curso: ").append(matricula.getCurso().getNombreCurso()).append("\n");
            info.append("Estado: ").append(getEstadoMatriculaTexto(matricula.getEstadoMatricula())).append("\n");
            info.append("Certificado: ").append(matricula.isCertificadoEmitido() ? "✅ EMITIDO" : "❌ NO EMITIDO");

            if (matricula.puedeEmitirCertificado()) {
                info.append("\n\n✅ PUEDE EMITIR CERTIFICADO");
            } else {
                info.append("\n\n❌ NO PUEDE EMITIR CERTIFICADO");
                if (matricula.getEstadoMatricula() != EstadoMatricula.FINALIZADA) {
                    info.append("\n- La matrícula debe estar FINALIZADA");
                }
                if (matricula.isCertificadoEmitido()) {
                    info.append("\n- Ya tiene certificado emitido");
                }
            }

            lblInfoCertificado.setText(info.toString());
        }
    }

    @FXML
    void onVolver() {
        app.mostrarMainView();
    }

    private void limpiarCamposMatricula() {
        txtIdMatricula.clear();
        cbEstudianteMatricula.setValue(null);
        cbCursoMatricula.setValue(null);
        dpFechaInscripcion.setValue(LocalDate.now());
        cbEstadoMatricula.setValue(EstadoMatricula.ACTIVA);
        txtIdMatricula.requestFocus();
    }

    private boolean validarCamposMatricula() {
        if (txtIdMatricula.getText().isEmpty() || cbEstudianteMatricula.getValue() == null ||
                cbCursoMatricula.getValue() == null || dpFechaInscripcion.getValue() == null ||
                cbEstadoMatricula.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Integer.parseInt(txtIdMatricula.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID debe ser un número válido", Alert.AlertType.ERROR);
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

    // Listeners para actualizar información cuando cambian los combos
    @FXML
    void onMatriculaCertificadoChanged() {
        Matricula matricula = cbMatriculaCertificado.getValue();
        actualizarInfoCertificado(matricula);
    }

    public void setApp(App app) {
        this.app = app;
    }
}