package vip.sheeptech.officetool.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vip.sheeptech.officetool.server.Pdf2Images;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * @author yanghao
 * @description PDF转换控制器
 */
public class PdfTrancodeController {
    @FXML
    private Label resultText;
    @FXML
    private TextField inputFilePath;

    @FXML
    protected void onChangeButtonClick() {
        String resultMsg;
        String pdfPath = inputFilePath.getText();
        try {
            doFileChange(pdfPath);
            resultMsg = "执行成功!";
        } catch (Exception e) {
            resultMsg = "错误:" + e.getMessage();
        }
        resultText.setText(resultMsg);
    }

    private void doFileChange(String pdfPath) {
        Assert.notNull(pdfPath, "路径为空");
        Assert.notEmpty(pdfPath, "路径为空");
        pdfPath = pdfPath.replaceAll("\\\\", "/");
        File pdfFile = new File(pdfPath);
        Assert.isTrue(pdfFile.exists(), "非法路径");
        String parentPath = pdfFile.getParent() + "/";
        List<String> stringList = Pdf2Images.pdfToManyImage(pdfPath, parentPath);
    }
}