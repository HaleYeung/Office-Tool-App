module vip.sheeptech.pdftool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.pdfbox;

    requires org.kordamp.bootstrapfx.core;

    opens vip.sheeptech.pdftool to javafx.fxml;
    exports vip.sheeptech.pdftool;
}