package com.raslab.cloudnotepad.pojo;

public class NoteModel {

    public  String noteTitle;
    public  String noteContent;
    public  String noteTime;
    public  String noteId;

    public NoteModel(){

    }
    public NoteModel(String noteTitle, String noteContent, String noteId) {

        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteTime = noteTime;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteTime() {
        return noteTime;
    }



}
