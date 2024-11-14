package com.example.gemm_server.common.util;

import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL;
import static com.example.gemm_server.common.code.error.GeneratorErrorCode.FAILED_TO_GENERATE_PPT_THUMBNAIL;

import com.example.gemm_server.common.enums.MaterialType;
import com.example.gemm_server.dto.common.TypedMaterialFile;
import com.example.gemm_server.exception.GeneratorException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class PoiUtil {

  private static final String PNG = "png";
  private static final String PDF = "pdf";
  private static final String FONT = "BMJUA_otf.otf";

  public static List<String> convertPptToPng(InputStream file, String fileName) {
    try (XMLSlideShow ppt = new XMLSlideShow(file)) {
      List<String> imageFileNames = new ArrayList<>();
      Dimension pgsize = ppt.getPageSize();
      List<XSLFSlide> slides = ppt.getSlides();
      BufferedImage img;

      for (int i = 0; i < slides.size(); i++) {
        img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.white);
        graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
        graphics.setFont(Font.getFont("BMJUA_otf.otf"));

        slides.get(i).draw(graphics);
        graphics.dispose();
        String savedFileName =
            fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf('.')) + i + '.'
                + PNG;
        try (FileOutputStream out = new FileOutputStream(savedFileName)) {
          ImageIO.write(img, PNG, out);
          imageFileNames.add(savedFileName);
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
          (familyName, encoding, size, style, color) -> FontFactory.getFont("BMJUA_otf.otf",
              BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size, style, color));

      String filePath =
          fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf('.') + 1) + PDF;
      OutputStream out = new FileOutputStream(new File(filePath));
      PdfConverter.getInstance().convert(document, out, options);
      return filePath;
    } catch (IOException ex) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    }
  }

  public static String convertPdfToPng(String filePath) {
    String newFilePath = null;
    try (PDDocument document = PDDocument.load(new File(filePath));) {
      BufferedImage bufferedImage = new PDFRenderer(document).renderImage(0);

      newFilePath = filePath.substring(0, filePath.lastIndexOf('.') + 1) + PNG;
      ImageIO.write(bufferedImage, "PNG", new File(newFilePath));
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    } finally {
      new File(filePath).delete();
      return newFilePath;
    }
  }

  public static List<MultipartFile> convertFileToPngs(TypedMaterialFile typedMaterialFile,
      String fileName) {
    MaterialType materialType = typedMaterialFile.getType();
    MultipartFile file = typedMaterialFile.getFile();
    try {
      if (materialType == MaterialType.PPT) {
        return convertPptToPngs(file.getInputStream(), fileName);
      }
      if (materialType == MaterialType.ACTIVITY_SHEET) {
        if (fileName.endsWith(PDF)) {
          return List.of(convertPdfToPng(file.getInputStream(), fileName));
        }
        return List.of(convertDocxToPng(file.getInputStream(), fileName));
      }
      return List.of(typedMaterialFile.getFile());
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PPT_THUMBNAIL, e);
    }
  }

  public static List<MultipartFile> convertPptToPngs(InputStream inputStream, String fileName) {
    try (XMLSlideShow ppt = new XMLSlideShow(inputStream)) {
      Dimension pageSize = ppt.getPageSize();
      List<XSLFSlide> slides = ppt.getSlides();

      return IntStream.range(0, slides.size())
          .mapToObj(index -> {
            String newFileName = FileUtil.getFileNameWithNoExtension(fileName) + index + "." + PNG;
            return convertPptSlideToPng(slides.get(index), pageSize, newFileName);
          }).toList();
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_PPT_THUMBNAIL);
    }
  }

  private static MultipartFile convertPptSlideToPng(XSLFSlide slide,
      Dimension imageSize, String fileName) {
    BufferedImage img = new BufferedImage(imageSize.width, imageSize.height,
        BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = img.createGraphics();

    graphics.setPaint(Color.white);
    graphics.fill(new Rectangle2D.Float(0, 0, imageSize.width, imageSize.height));
    graphics.setFont(Font.getFont(FONT));

    slide.draw(graphics);
    graphics.dispose();
    return convertBufferedImageToMultipartFile(img, fileName);
  }

  public static MultipartFile convertDocxToPng(InputStream stream, String fileName) {
    try {
      XWPFDocument xWPFDocument = new XWPFDocument(stream);
      PdfOptions options = PdfOptions.create();
      options.fontProvider(
          (familyName, encoding, size, style, color) -> FontFactory.getFont(FONT,
              BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size, style, color));

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PdfConverter.getInstance().convert(xWPFDocument, outputStream, options);
      InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
      return convertPdfToPng(inputStream, fileName);
    } catch (IOException ex) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    }
  }

  public static MultipartFile convertPdfToPng(InputStream inputStream, String fileName) {
    try (PDDocument pDDocument = PDDocument.load(inputStream)) {
      BufferedImage bufferedImage = new PDFRenderer(pDDocument).renderImage(0);
      String newFileName = FileUtil.getFileNameWithNoExtension(fileName) + "." + PNG;
      return convertBufferedImageToMultipartFile(bufferedImage, newFileName);

    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL, e);
    }
  }

  private static MultipartFile convertBufferedImageToMultipartFile(BufferedImage bufferedImage,
      String fileName) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ImageIO.write(bufferedImage, PNG, outputStream);
      outputStream.flush();
      return new MockMultipartFile(
          fileName,
          fileName,
          MediaType.IMAGE_PNG_VALUE,
          new ByteArrayInputStream(outputStream.toByteArray())
      );
    } catch (IOException e) {
      throw new GeneratorException(FAILED_TO_GENERATE_ACTIVITY_SHEET_THUMBNAIL);
    }
  }
}
