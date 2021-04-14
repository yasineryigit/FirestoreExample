package com.ossovita.firestoreexample;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.Expose;

public class Note {
    private String title, description, documentId;
    private int priority;

    public Note() {
        //public no-arg constructor needed
    }

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
