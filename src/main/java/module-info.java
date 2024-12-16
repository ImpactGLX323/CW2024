module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics; // For access to Stage, Scene, etc.
    requires transitive javafx.media;


    // Open for reflection-based FXML loading
    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;

    // Export main and controller packages for external access
    exports com.example.demo;
    exports com.example.demo.controller;
}
