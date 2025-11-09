package co.edu.uniquindio.poo.academiamusicuq.viewController;

import co.edu.uniquindio.poo.academiamusicuq.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginViewController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;

    private App app;

    @FXML
    void initialize() {
        // Enter key para login
        txtPassword.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLogin();
        }
    }

    @FXML
    void onLogin() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese usuario y contraseña");
            return;
        }

        // Validación simple (en producción usaría autenticación real)
        if (usuario.equals("admin") && password.equals("admin123")) {
            app.mostrarMainView();
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos");
            txtPassword.clear();
        }
    }

    @FXML
    void onLimpiar() {
        txtUsuario.clear();
        txtPassword.clear();
        txtUsuario.requestFocus();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}