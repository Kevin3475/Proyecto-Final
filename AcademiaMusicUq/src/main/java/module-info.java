module co.edu.uniquindio.poo.academiamusicuq {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.academiamusicuq to javafx.fxml;
    exports co.edu.uniquindio.poo.academiamusicuq;
}