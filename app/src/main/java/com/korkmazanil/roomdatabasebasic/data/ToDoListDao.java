package com.korkmazanil.roomdatabasebasic.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.korkmazanil.roomdatabasebasic.model.ToDoList;

import java.util.List;

@Dao
public interface ToDoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ToDoList toDoList);

    @Query("DELETE FROM to_do_list_table")
    void deleteAll();

    @Query("SELECT * FROM to_do_list_table ORDER BY title ASC")
    LiveData<List<ToDoList>> getAllToDo();

    @Query("SELECT * FROM to_do_list_table WHERE to_do_list_table.uuid == :uuid")
    LiveData<ToDoList> get(int uuid);

    @Update
    void update(ToDoList toDoList);

    @Delete
    void delete(ToDoList toDoList);

}
