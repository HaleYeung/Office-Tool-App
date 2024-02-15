package vip.sheeptech.officetool.server;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghao
 * @description PDF转换类
 */
public class Pdf2Images {


    /**
     * pdf转换成图片
     *
     * @param pdfPath    pdf文件的路径   例如: D:\\test\\test.pdf (2页)
     * @param targetPath 输出的图片路径        D:\\test\\
     * @return 抽取出来的图片路径数组         Arrays.asList( "D:\\test\\1.jpg","D:\\test\\2.jpg" )
     */
    public static List<String> pdfToManyImage(String pdfPath, String targetPath, int dpiValue, String outputClassValue) {
        File file = new File(pdfPath);
        if (!file.exists()) {
            return null;
        }
        PDDocument doc = null;
        try {
            // 加载pdf文件
            doc = PDDocument.load(file);
            // 读取pdf文件
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            List<String> stringList = new ArrayList<>(pageCount);
            String filePath;
            BufferedImage image;
            for (int i = 0; i < pageCount; i++) {
                // 96/144/198
                // Windows native DPI
                image = renderer.renderImageWithDPI(i, dpiValue);
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                filePath = targetPath + (i + 1) + "." + outputClassValue;
                // 保存图片
                ImageIO.write(image, outputClassValue, new File(filePath));
                stringList.add(filePath);
            }
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

