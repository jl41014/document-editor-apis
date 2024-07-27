package com.example.termsheeteditor.demos.web.document.util;

import com.lowagie.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;

public class PDFGenerationUtil {

    public static ByteArrayOutputStream generatePDF(HashMap data, String fileName) throws IOException, DocumentException, TemplateException {
        System.out.println("generate PDF in");
        String resourcePath = PDFGenerationUtil.class.getResource("/").getPath() + "static";
        System.out.println(resourcePath);
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File(resourcePath));
        config.setEncoding(Locale.CHINA, "UTF-8");
        Template template = config.getTemplate("template.ftl");
        template.setEncoding("UTF-8");


        ITextRenderer render = new ITextRenderer();
        render.getFontResolver().addFont(resourcePath + "/msjh.ttc", "Chinese", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, null);
        render.getFontResolver().addFont(resourcePath + "/arial.ttf", "arial", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, null);
        System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");

        StringWriter writer = new StringWriter();
        template.process(data, writer);
        String htmlTemplate = writer.toString();
        System.out.println(htmlTemplate);
        writer.close();
        render.setDocumentFromString(htmlTemplate);
        render.getSharedContext().setBaseURL("file:///" + resourcePath + "/");
        render.layout();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        render.createPDF(bout);
        render.finishPDF();

        FileOutputStream file = new FileOutputStream(resourcePath + "/" + fileName + ".pdf");
        file.write(bout.toByteArray());
        file.close();
        System.out.println("generate PDF out");
        return bout;
    }
}
