package com.juaracoding.pcmspringboot4.util;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Untuk Generate dari HTML (Thymeleaf) ke PDF
 */
@Component
public class PdfGenerator {
    private String [] strExceptionArr = new String[2];
    private StringBuilder sBuild = new StringBuilder();
    private ServletOutputStream os;


    /**
     *
     * @param html
     * @param prefixFile
     * @param response
     */
    public void htmlToPdf(String html, String prefixFile, HttpServletResponse response) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        ConverterProperties converterProperties = new ConverterProperties();
        DefaultFontProvider defaultFontProvider = new DefaultFontProvider();
        try{
            converterProperties.setFontProvider(defaultFontProvider);
            HtmlConverter.convertToPdf(html,pdfWriter,converterProperties);
            sBuild.setLength(0);
            String fileName = sBuild.append(prefixFile).append("_").
                    append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).
                    append(".pdf").toString();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\""+fileName);
        }
        finally {
            try{
                os = response.getOutputStream();
                byteArrayOutputStream.writeTo(os);
                byteArrayOutputStream.close();
                byteArrayOutputStream.flush();
                os.close();
            }catch (Exception e){
//                LoggingFile.exceptionStringz("PdfGenerator","htmlToPdf", e, OtherConfig.getFlagLogging());
            }
        }
    }
}