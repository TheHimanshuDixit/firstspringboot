package com.himanshu.journalApp.entity;

public class JournalEntry {
    private String idString;
    private String title;
    private String content;
    private String date;

    public JournalEntry() {
    }

    public JournalEntry(String idString, String title, String content, String date) {
        this.idString = idString;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getIdString() {
        return idString;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
