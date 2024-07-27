package com.example.termsheeteditor.demos.web.document.model;

import javax.persistence.Column;

public class DocumentDTO {

    @Column(name = "type")
    public String type;

    @Column(name = "doc_name")
    public String docName;

    @Column(name = "block_list")
    public String blockList;

    @Column(name = "header")
    public int header;

    @Column(name = "footer")
    public int footer;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getBlockList() {
        return blockList;
    }

    public void setBlockList(String blockList) {
        this.blockList = blockList;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public int getFooter() {
        return footer;
    }

    public void setFooter(int footer) {
        this.footer = footer;
    }
}
