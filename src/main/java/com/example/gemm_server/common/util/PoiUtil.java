package com.example.gemm_server.common.util;

import java.util.ArrayList;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class PoiUtil {

  public static List<String> pptToImages(MultipartFile pptFile) {
    List<String> imageFileNames = new ArrayList<>();
    try {
      XMLSlideShow ppt = new XMLSlideShow(pptFile.getInputStream());

      Dimension pgsize = ppt.getPageSize();
      List<XSLFSlide> slides = ppt.getSlides();
      BufferedImage img = null;

      for (XSLFSlide slide : slides) {
        img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

        slide.draw(graphics);
        String saveFileName = UUIDUtil.getRandomUUID() + ".png";
        FileOutputStream out = new FileOutputStream(saveFileName);
        javax.imageio.ImageIO.write(img, "png", out);
        ppt.write(out);
        imageFileNames.add(saveFileName);

        out.close();
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return imageFileNames;
  }
}
