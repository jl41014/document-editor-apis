package com.example.termsheeteditor.demos.web.document.service;

import com.example.termsheeteditor.demos.web.document.DAO.DocBlockDAO;
import com.example.termsheeteditor.demos.web.document.model.*;
import com.example.termsheeteditor.demos.web.document.util.PDFGenerationUtil;
import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentService {

    @Autowired
    DocBlockDAO docBlockDAO;

    public ByteArrayOutputStream createPreviewPDF(PreviewRequestDTO request) throws TemplateException, DocumentException, IOException {
        HashMap data = new HashMap();
        data.put("header", request.getHeader());
        data.put("footer", request.getFooter());
        data.put("contents", request.getBlocks());
        data.put("fileName", "preview");
        return PDFGenerationUtil.generatePDF(data, "Preview");
    }

    public ByteArrayOutputStream createDocPDF(String docType, String docName) throws TemplateException, DocumentException, IOException {
        HashMap data = docBlockDAO.getDocDefinition(docType, docName);
        data.put("fileName", docName);
        return PDFGenerationUtil.generatePDF(data, docName);
    }

    public int conbineBlocksToDoc(ConbineDocRequest request) {
        return docBlockDAO.conbineBlocksToDoc(request);
    }

    public List<BlockDTO> getAllDocBlocks() {
        return docBlockDAO.getAllDocBlocks();
    }

    public int addDocBlock(AddBlockRequest addBlock) {
        return docBlockDAO.addDocBlock(addBlock);
    }

    public int updateDocBlock(UpdateBlockRequest updateBlock) {
        return docBlockDAO.updateDocBlock(updateBlock);
    }
}
