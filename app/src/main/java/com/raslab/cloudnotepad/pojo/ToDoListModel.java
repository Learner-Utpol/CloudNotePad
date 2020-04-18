package com.raslab.cloudnotepad.pojo;

public class ToDoListModel {


public String itemChk;
public String itemTime;
public String itemDate;
public String itemid;

    public ToDoListModel(String itemChk, String itemTime, String itemDate, String itemid) {
        this.itemChk = itemChk;
        this.itemTime = itemTime;
        this.itemDate = itemDate;
        this.itemid = itemid;
        //this.timesTamp = timesTamp;
    }



    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemChk() {
        return itemChk;
    }

    public void setItemChk(String itemChk) {
        this.itemChk = itemChk;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }




    public ToDoListModel(){


    }
}
