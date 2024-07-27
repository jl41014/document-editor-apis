package com.example.termsheeteditor.demos.web.document.model;

import javax.persistence.Column;

public class BlockDTO {

    @Column(name = "id")
    public int id;

    @Column(name = "block")
    public String html;

    @Column(name = "type")
    public String type;

    @Column(name = "update_time")
    public String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
