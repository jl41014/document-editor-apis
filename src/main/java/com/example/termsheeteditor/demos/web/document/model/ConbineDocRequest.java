package com.example.termsheeteditor.demos.web.document.model;

import java.util.List;

public class ConbineDocRequest {

    String docName;

    String type;

    int headerId;

    int footerId;

    List contentIds;

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public int getFooterId() {
        return footerId;
    }

    public void setFooterId(int footerId) {
        this.footerId = footerId;
    }

    public List getContentIds() {
        return contentIds;
    }

    public void setContentIds(List contentIds) {
        this.contentIds = contentIds;
    }
}
