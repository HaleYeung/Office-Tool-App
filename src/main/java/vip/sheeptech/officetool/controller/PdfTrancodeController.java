package vip.sheeptech.officetool.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vip.sheeptech.officetool.MainApplication;
import vip.sheeptech.officetool.server.Pdf2Images;
import vip.sheeptech.officetool.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private TextArea runLog;
    private String outputPath;
    @FXML
    private TextField outputDirPath;

    @FXML
    protected void onChangeButtonClick(){
        runLog.clear();
        String pdfPath = inputFilePath.getText();
        String outputDirPath = this.outputDirPath.getText();
        try {
            List<String> outputFilePathList = doFileChange(pdfPath, outputDirPath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("结果");
            alert.setHeaderText(null);
            alert.setContentText("执行成功!");
            alert.showAndWait();
            runLog.setText("输出文件:\n");
            for (String outputFilePath : outputFilePathList) {
                runLog.appendText(outputFilePath + "\n");
            }
            SystemUtil.openFileLocation(outputPath);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            runLog.setText("错误:" + e.getMessage());
        }
    }

    /**
     * @param pdfPath PDF文件路径
     * @return 输出文件地址集合
     */
    private List<String> doFileChange(String pdfPath, String outputDir) {
        Assert.notNull(pdfPath, "路径为空");
        Assert.notEmpty(pdfPath, "路径为空");
        Integer outputDpiValue = outputDpi.getValue();
        Assert.notNull(outputDpiValue, "DPI为空");
        String outputClassValue = outputClass.getValue();
        Assert.notNull(outputClassValue, "输出格式为空");
        pdfPath = pdfPath.replaceAll("\\\\", "/");
        File pdfFile = new File(pdfPath);
        Assert.isTrue(pdfFile.exists(), "非法文件路径");
        Assert.isTrue(pdfPath.toLowerCase().endsWith("pdf"), "文件不为PDF");
        String fileName = FileNameUtil.mainName(pdfFile);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowTime = new Date();
        String nowTimeString = dateFormat.format(nowTime);
        // 输出地址为空，自动生成在同级目录
        if (StrUtil.isEmptyIfStr(outputDir)) {
            String parentPath = pdfFile.getParent() + "/";
            parentPath = parentPath.replaceAll("\\\\", "/");
            outputPath = parentPath + fileName + "-" + nowTimeString + "/";
        } else {
            outputDir = outputDir.replaceAll("\\\\", "/");
            if (!outputDir.endsWith("/")) {
                outputDir = outputDir + "/";
            }
            File outputDirFile = new File(outputDir);
            Assert.isTrue(outputDirFile.isDirectory(), "输出目录不存在");
            outputPath = outputDir + fileName + "-" + nowTimeString + "/";
        }
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            boolean doneMkDir = outputFile.mkdirs();
            Assert.isTrue(doneMkDir, "创建输出目录失败");
        }
        return Pdf2Images.pdfToManyImage(pdfPath, outputPath, outputDpiValue, outputClassValue);
    }



    @FXML
    protected void openFileChoice() {
        String inputFilePath = this.inputFilePath.getText();
        Integer outputDpi = this.outputDpi.getValue();
        String outputClass = this.outputClass.getValue();
        String runLog = this.runLog.getText();
        String outputDirPath = this.outputDirPath.getText();
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        PdfTrancodeController pdfTrancodeController;
        try {
            loader.setLocation(MainApplication.class.getResource("pdf-transcode-view.fxml"));
            root = loader.load();
            rootVbox.getChildren().setAll(root);
            Insets padding = new Insets(0, 0, 0, 0);
            rootVbox.setPadding(padding);
            pdfTrancodeController = loader.getController();
            pdfTrancodeController.outputDpi.setValue(outputDpi);
            pdfTrancodeController.outputClass.setValue(outputClass);
            pdfTrancodeController.outputDirPath.setText(outputDirPath);
            pdfTrancodeController.runLog.clear();
            pdfTrancodeController.runLog.setText(runLog);
            pdfTrancodeController.inputFilePath.setText(inputFilePath);
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
        if (null != file) {
            pdfTrancodeController.inputFilePath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    protected void openOutputDirChoice() {
        String inputFilePath = this.inputFilePath.getText();
        Integer outputDpi = this.outputDpi.getValue();
        String outputClass = this.outputClass.getValue();
        String runLog = this.runLog.getText();
        String outputDirPath = this.outputDirPath.getText();
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        PdfTrancodeController pdfTrancodeController;
        try {
            loader.setLocation(MainApplication.class.getResource("pdf-transcode-view.fxml"));
            root = loader.load();
            rootVbox.getChildren().setAll(root);
            Insets padding = new Insets(0, 0, 0, 0);
            rootVbox.setPadding(padding);
            pdfTrancodeController = loader.getController();
            pdfTrancodeController.outputDpi.setValue(outputDpi);
            pdfTrancodeController.outputClass.setValue(outputClass);
            pdfTrancodeController.outputDirPath.setText(outputDirPath);
            pdfTrancodeController.runLog.clear();
            pdfTrancodeController.runLog.setText(runLog);
            pdfTrancodeController.inputFilePath.setText(inputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //将一个新的fxml文件（界面B）加入到界面A的根布局上。
        Stage stage = (Stage) rootVbox.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(stage);
        if (null != dir) {
            pdfTrancodeController.outputDirPath.setText(dir.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outputDpi.setValue(105);
        outputClass.setValue("jpg");
        // 自动换行
        runLog.setWrapText(false);
    }
}