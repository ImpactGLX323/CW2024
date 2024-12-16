module com.example.demo {
    // Requires javafx.controls to provide JavaFX UI components like buttons, labels, etc.
    requires javafx.controls;

    // Requires javafx.fxml to support FXML loading for UI layout and controller binding.
    requires javafx.fxml;

    // Transitive requirement of javafx.graphics to provide access to Stage, Scene, etc. for graphical UI elements.
    requires transitive javafx.graphics; // For access to Stage, Scene, etc.

    // Transitive requirement of javafx.media for media-related functionality such as audio/video support.
    requires transitive javafx.media;

    // Open the com.example.demo package for reflection-based FXML loading.
    // This allows FXML files to reference classes in this package.
    opens com.example.demo to javafx.fxml;

    // Open the com.example.demo.controller package for reflection-based FXML loading.
    // This allows FXML files to reference controllers in this package.
    opens com.example.demo.controller to javafx.fxml;

    // Export the com.example.demo package so that other modules can access its public classes.
    exports com.example.demo;

    // Export the com.example.demo.controller package for external access to the controllers.
    exports com.example.demo.controller;
}
