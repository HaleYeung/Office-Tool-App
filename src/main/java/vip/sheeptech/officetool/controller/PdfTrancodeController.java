package vip.sheeptech.officetool.controller;

import cn.hutool.core.lang.Assert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vip.sheeptech.officetool.MainApplication;
import vip.sheeptech.officetool.server.Pdf2Images;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author yanghao
 * @description PDF转换控制器
 */
public class PdfTrancodeController implements Initializable {
    @FXML
    private VBox rootVbox;
    @FXML
    private TextField inputFilePath;
    @FXML
    private ChoiceBox<Integer> outputDpi;
    @FXML
    private ChoiceBox<String> outputClass;

    @FXML
    protected void onChangeButtonClick(){
        String pdfPath = inputFilePath.getText();
        try {
            doFileChange(pdfPath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("结果");
            alert.setHeaderText(null);
            alert.setContentText("执行成功!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void doFileChange(String pdfPath) {
        Assert.notNull(pdfPath, "路径为空");
        Assert.notEmpty(pdfPath, "路径为空");
        Integer outputDpiValue = outputDpi.getValue();
        Assert.notNull(outputDpiValue, "DPI为空");
        String outputClassValue = outputClass.getValue();
        Assert.notNull(outputClassValue, "输出格式为空");
        pdfPath = pdfPath.replaceAll("\\\\", "/");
        File pdfFile = new File(pdfPath);
        Assert.isTrue(pdfFile.exists(), "非法路径");
        String parentPath = pdfFile.getParent() + "/";
        List<String> stringList = Pdf2Images.pdfToManyImage(pdfPath, parentPath, outputDpiValue, outputClassValue);
    }

    @FXML
    protected void openFileChoice() {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(MainApplication.class.getResource("pdf-transcode-view.fxml"));
            root = loader.load();
            rootVbox.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //将一个新的fxml文件（界面B）加入到界面A的根布局上。
        Stage stage = (Stage) rootVbox.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
        PdfTrancodeController pdfTrancodeController = loader.getController();
        pdfTrancodeController.inputFilePath.setText(file.getAbsolutePath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outputDpi.setValue(105);
        outputClass.setValue("jpg");
    }
}