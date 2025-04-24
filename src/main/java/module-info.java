module org.example.java_socket_network_programming {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires io.github.cdimascio.dotenv.java;
    requires annotations;
    requires jakarta.mail;


    exports org.example.java_socket_network_programming.TCP.Controller;
    opens org.example.java_socket_network_programming.TCP.Controller to javafx.fxml;
    exports org.example.java_socket_network_programming.TCP.Client;
    exports org.example.java_socket_network_programming.TCP.Server;
    exports org.example.java_socket_network_programming.TCP.Mail_Server;
    opens org.example.java_socket_network_programming.TCP.Server to javafx.fxml;


}