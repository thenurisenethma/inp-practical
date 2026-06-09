module com.example.inpexam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.inpexam to javafx.fxml;
    exports com.example.inpexam;
}