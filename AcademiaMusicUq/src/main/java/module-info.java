module co.edu.uniquindio.academiamusicuq {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.academiamusicuq to javafx.fxml;
    exports co.edu.uniquindio.poo.academiamusicuq;

    exports co.edu.uniquindio.poo.academiamusicuq.viewController;
    opens co.edu.uniquindio.poo.academiamusicuq.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.academiamusicuq.controller;
    opens co.edu.uniquindio.poo.academiamusicuq.controller to javafx.fxml;

    exports co.edu.uniquindio.poo.academiamusicuq.model;
    opens co.edu.uniquindio.poo.academiamusicuq.model to javafx.fxml;
}