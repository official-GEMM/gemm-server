package com.example.gemm_server.common.util;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.*;

import com.example.gemm_server.exception.GeneratorException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
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

  private static final String PNG = "png";
  private static final String PDF = "pdf";

  public static List<String> convertPptToPng(InputStream fileInputStream) {
    try (XMLSlideShow ppt = new XMLSlideShow(fileInputStream);) {
      List<String> imageFileNames = new ArrayList<>();
      Dimension pgsize = ppt.getPageSize();
      List<XSLFSlide> slides = ppt.getSlides();
      BufferedImage img = null;

      for (XSLFSlide slide : slides) {
        img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

        slide.draw(graphics);
        String saveFileName = UUIDUtil.getRandomUUID() + '.' + PNG;
        try (FileOutputStream out = new FileOutputStream(saveFileName)) {
          ImageIO.write(img, PNG, out);
          ppt.write(out);
          imageFileNames.add(saveFileName);
        }
      }
      return imageFileNames;
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PPT_THUMBNAIL);
    }
  }

  public static String convertDocxToPdf(InputStream file, String fileName) {
    try {
      XWPFDocument document = new XWPFDocument(file);
      PdfOptions options = PdfOptions.create();
      options.fontProvider(
          (familyName, encoding, size, style, color) -> FontFactory.getFont("NanumGothic.ttf",
              BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size, style, color));

      String filePath = fileName.substring(10, fileName.lastIndexOf('.') + 1) + PDF;
      OutputStream out = new FileOutputStream(new File(filePath));
      PdfConverter.getInstance().convert(document, out, options);
      return filePath;
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  public static String convertPdfToPng(String filePath) {
    try (PDDocument document = PDDocument.load(new File(filePath));) {
      PDFRenderer pdfRenderer = new PDFRenderer(document);
      BufferedImage bufferedImage = pdfRenderer.renderImage(0);

      String newFilePath = UUIDUtil.getRandomUUID() + "0." + PNG;
      ImageIO.write(bufferedImage, "PNG", new File(newFilePath));
      return newFilePath;
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    }
  }
}
