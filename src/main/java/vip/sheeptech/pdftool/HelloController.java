package vip.sheeptech.pdftool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label resultText;

    @FXML
    protected void onChangeButtonClick() {
        resultText.setText("执行成功!");
        System.out.println("点击。。。");
        String pdfPath = "/Users/yanghao/Downloads/杨浩Java简历.pdf";
        String targetPath = "/Users/yanghao/Downloads/";
        Pdf2Images.pdfToManyImage(pdfPath, targetPath);
    }
}