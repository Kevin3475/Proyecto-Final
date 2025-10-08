module co.edu.uniquindio.poo.projectmusic {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.projectmusic to javafx.fxml;
    exports co.edu.uniquindio.poo.projectmusic;
}