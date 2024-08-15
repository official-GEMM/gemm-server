package com.example.gemm_server.common.util;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.*;

import com.example.gemm_server.common.code.error.GeneratorErrorCode;
import com.example.gemm_server.common.constant.FilePath;
import com.example.gemm_server.exception.GeneratorException;
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
  private static final String TEMP_PDF_PATH = "temp/docx/pdf/";
  private static final String TEMP_DOCX_THUMBNAIL_PATH = "temp/docx/thumbnail/";

  public static List<String> convertPptToPng(InputStream fileInputStream) {
    try {
      List<String> imageFileNames = new ArrayList<>();
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
        String saveFileName =
            FilePath.TEMP_PPT_THUMBNAIL_PATH + UUIDUtil.getRandomUUID() + +'.' + PNG;
        FileOutputStream out = new FileOutputStream(saveFileName);
        javax.imageio.ImageIO.write(img, PNG, out);
        ppt.write(out);
        imageFileNames.add(saveFileName);
        out.close();
      }
      return imageFileNames;
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PPT_THUMBNAIL);
    }
  }

  public static String convertDocxToPdf(InputStream file, String fileName) {
    try {
      XWPFDocument docx = new XWPFDocument(file);
      PdfOptions options = PdfOptions.create();

      String filePath =
          TEMP_PDF_PATH + fileName.substring(10, fileName.lastIndexOf('.') + 1) + PDF;
      FileOutputStream out = new FileOutputStream(new File(filePath));
      PdfConverter.getInstance().convert(docx, out, options);
      return filePath;
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_CONVERT_DOCX_TO_PDF);
    }
  }

  public static String convertPdfToPng(String filePath) {
    try {
      PDDocument document = PDDocument.load(new File(filePath));
      PDFRenderer pdfRenderer = new PDFRenderer(document);
      BufferedImage bufferedImage = pdfRenderer.renderImage(0);

      String newFilePath =
          TEMP_DOCX_THUMBNAIL_PATH + filePath.substring(14, filePath.lastIndexOf('.')) + "0." + PNG;
      ImageIO.write(bufferedImage, "PNG", new File(newFilePath));
      return newFilePath;
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    }
  }
}
