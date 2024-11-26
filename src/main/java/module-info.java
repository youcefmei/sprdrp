module sparadrapxml {
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
    requires jdk.unsupported.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires static lombok;
    requires jakarta.validation;
    requires org.apache.logging.log4j;
    opens com.youcefmei.sparadrap.controller to javafx.fxml;
    opens com.youcefmei.sparadrap.model to javafx.base;
    exports com.youcefmei.sparadrap;
}