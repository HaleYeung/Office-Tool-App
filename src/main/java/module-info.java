module vip.sheeptech.officetool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires cn.hutool.core;
    requires org.kordamp.bootstrapfx.core;

    opens vip.sheeptech.officetool to javafx.fxml;
    exports vip.sheeptech.officetool;
    exports vip.sheeptech.officetool.server;
    opens vip.sheeptech.officetool.server to javafx.fxml;
    exports vip.sheeptech.officetool.controller;
    opens vip.sheeptech.officetool.controller to javafx.fxml;
}