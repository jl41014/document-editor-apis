package com.example.termsheeteditor.demos.web.document.model;

public class PreviewRequestDTO {

    String header;
    String footer;
    String[] blocks;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String[] getBlocks() {
        return blocks;
    }

    public void setBlocks(String[] blocks) {
        this.blocks = blocks;
    }
}
