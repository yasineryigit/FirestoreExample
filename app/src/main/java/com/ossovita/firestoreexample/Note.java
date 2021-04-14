package com.ossovita.firestoreexample;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.Expose;

public class Note {
    private String title, description, documentId;

    public Note() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
