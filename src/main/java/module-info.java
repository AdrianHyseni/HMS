module com.hms.hmsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.sql;


    opens com.hms.hmsfx to javafx.fxml;
    exports com.hms.hmsfx;
    exports com.hms.hmsfx.data;
    opens com.hms.hmsfx.data to javafx.fxml;
    exports com.hms.hmsfx.Controller;
    opens com.hms.hmsfx.Controller to javafx.fxml;


}