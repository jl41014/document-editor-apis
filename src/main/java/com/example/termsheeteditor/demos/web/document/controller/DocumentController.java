package com.example.termsheeteditor.demos.web.document.controller;


import com.example.termsheeteditor.demos.web.document.model.AddBlockRequest;
import com.example.termsheeteditor.demos.web.document.model.ConbineDocRequest;
import com.example.termsheeteditor.demos.web.document.model.PreviewRequestDTO;
import com.example.termsheeteditor.demos.web.document.model.UpdateBlockRequest;
import com.example.termsheeteditor.demos.web.document.service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping("/document/preview")
    public void preview(@RequestBody PreviewRequestDTO request, HttpServletResponse response) throws IOException {
        try {
            ByteArrayOutputStream pdfStream = documentService.createPreviewPDF(request);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.addHeader("Block-Disposition", "attachment;filename=preview.pdf");
            response.getOutputStream().write(pdfStream.toByteArray());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/document/{docType}/{docName}")
    public void downloadPDF(@PathVariable("docType") String docType, @PathVariable("docName") String docName, HttpServletResponse response) throws IOException {
        try {
            ByteArrayOutputStream pdfStream = documentService.createDocPDF(docType, docName);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.addHeader("Block-Disposition", "attachment;filename="+docName+".pdf");
            response.getOutputStream().write(pdfStream.toByteArray());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/document/blocks")
    @ResponseBody
    public ResponseEntity getAllBlocks() {
        return new ResponseEntity<>(documentService.getAllDocBlocks(), HttpStatus.OK);
    }

    @PutMapping("/document/block/add")
    @ResponseBody
    public ResponseEntity addDocBlock(@RequestBody AddBlockRequest addBlockRequest) {
        int i = documentService.addDocBlock(addBlockRequest);
        if (i > 0) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity("Add Failed!", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/document/block/update")
    @ResponseBody
    public ResponseEntity updateDocBlock(@RequestBody UpdateBlockRequest updateBlockRequest) {
        int i = documentService.updateDocBlock(updateBlockRequest);
        if (i > 0) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity("Update Failed!", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/document/block/conbine")
    @ResponseBody
    public ResponseEntity conbineBlockToDoc(@RequestBody ConbineDocRequest request) {
        int i = documentService.conbineBlocksToDoc(request);
        if (i > 0) {
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity("Conbine Doc Failed!", HttpStatus.NOT_MODIFIED);
        }
    }
}
