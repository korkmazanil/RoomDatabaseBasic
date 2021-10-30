package com.korkmazanil.roomdatabasebasic.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "to_do_list_table")
public class ToDoList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uuid")
    private long uuid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "work")
    private String work;

    public ToDoList(@NonNull String title,@NonNull String work) {
        this.title = title;
        this.work = work;
    }

    @Ignore
    public ToDoList() {

    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public long getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getWork() {
        return work;
    }

}
