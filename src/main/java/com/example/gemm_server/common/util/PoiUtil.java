package com.example.gemm_server.common.util;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class PoiUtil {
    public static List<String> pptToImages(String filePath) {
        File file = new File("pptToImage.pptx");
        try {
            XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
            Dimension pgsize = ppt.getPageSize();
            List<XSLFSlide> slide = ppt.getSlides();
            BufferedImage img = null;
            for (int i = 0; i < slide.size(); i++) {
                img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                //clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                //render
                slide.get(i).draw(graphics);
                FileOutputStream out = new FileOutputStream("ppt_image.png");
                javax.imageio.ImageIO.write(img, "png", out);
                ppt.write(out);

                System.out.println("Image successfully created");
                out.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
