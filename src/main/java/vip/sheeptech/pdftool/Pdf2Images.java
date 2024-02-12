package vip.sheeptech.pdftool;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pdf2Images {
    /**
     * 经过测试,dpi为96,100,105,120,150,200中,105显示效果较为清晰,体积稳定,dpi越高图片体积越大,一般电脑显示分辨率为96
     */
    public static final float DEFAULT_DPI = 105;

    /**
     * 默认转换的图片格式为jpg
     */
    public static final String DEFAULT_FORMAT = "jpg";

    /**
     * pdf转换成图片
     *
     * @param pdfPath    pdf文件的路径   例如: D:\\test\\test.pdf (2页)
     * @param targetPath 输出的图片路径        D:\\test\\
     * @return 抽取出来的图片路径数组         Arrays.asList( "D:\\test\\1.jpg","D:\\test\\2.jpg" )
     */
    public static List<String> pdfToManyImage(String pdfPath, String targetPath) {
        File file = new File(pdfPath);
        if (!file.exists()) {
            return null;
        }
        try {
            //加载pdf文件
            PDDocument doc = PDDocument.load(file);
            //读取pdf文件
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            List<String> stringList = new ArrayList<>(pageCount);
            String filePath = null;
            BufferedImage image;
            for (int i = 0; i < pageCount; i++) {
                //96/144/198
                // Windows native DPI
                image = renderer.renderImageWithDPI(i, DEFAULT_DPI);
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                filePath = targetPath + (i + 1) + "." + DEFAULT_FORMAT;
                //保存图片
                ImageIO.write(image, DEFAULT_FORMAT, new File(filePath));
                stringList.add(filePath);
            }
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String pdfPath = "E:/test/杨浩Java简历.pdf";
        String targetPath = "E:/test/";
        Pdf2Images.pdfToManyImage(pdfPath, targetPath);
    }
}

