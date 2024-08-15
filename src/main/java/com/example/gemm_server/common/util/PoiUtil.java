package com.example.gemm_server.common.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class PoiUtil {

  public static List<String> convertPptToPng(InputStream fileInputStream) {
    List<String> imageFileNames = new ArrayList<>();
    try {
      XMLSlideShow ppt = new XMLSlideShow(fileInputStream);

      Dimension pgsize = ppt.getPageSize();
      List<XSLFSlide> slides = ppt.getSlides();
      BufferedImage img = null;

      for (XSLFSlide slide : slides) {
        img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

        slide.draw(graphics);
        String saveFileName = "temp/pptx/thumbnail/" + UUIDUtil.getRandomUUID() + ".png";
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

  public static String convertDocxToPdf(InputStream file, String fileName) {
    try {
      XWPFDocument docx = new XWPFDocument(file);
      PdfOptions options = PdfOptions.create();

      String filePath =
          "temp/docx/pdf/" + fileName.substring(10, fileName.lastIndexOf('.')) + ".pdf";
      FileOutputStream out = new FileOutputStream(new File(filePath));
      PdfConverter.getInstance().convert(docx, out, options);
      return filePath;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String convertPdfToPng(String filePath) {
    try {
      PDDocument document = PDDocument.load(new File(filePath));
      PDFRenderer pdfRenderer = new PDFRenderer(document);
      BufferedImage bufferedImage = pdfRenderer.renderImage(0);

      String newFilePath =
          "temp/docx/thumbnail/" + filePath.substring(14, filePath.lastIndexOf('.')) + "0.png";
      ImageIO.write(bufferedImage, "PNG", new File(newFilePath));
      return newFilePath;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
