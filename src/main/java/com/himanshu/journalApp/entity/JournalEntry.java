package com.himanshu.journalApp.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "journal")
@Data
public class JournalEntry {
    @Id
    private ObjectId idString;
    @NonNull
    private String title;
    private String content;
    private Date date;

    // public JournalEntry() {
    // }

    // public JournalEntry(ObjectId idString, String title, String content, Date
    // date) {
    // this.idString = idString;
    // this.title = title;
    // this.content = content;
    // this.date = date;
    // }

    // public ObjectId getIdString() {
    // return idString;
    // }

    // public String getTitle() {
    // return title;
    // }

    // public String getContent() {
    // return content;
    // }

    // public Date getDate() {
    // return date;
    // }

    // public void setIdString(ObjectId idString) {
    // this.idString = idString;
    // }

    // public void setTitle(String title) {
    // this.title = title;
    // }

    // public void setContent(String content) {
    // this.content = content;
    // }

    // public void setDate(Date date) {
    // this.date = date;
    // }
}
