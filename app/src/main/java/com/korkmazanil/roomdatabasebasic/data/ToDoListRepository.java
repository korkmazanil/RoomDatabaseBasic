package com.korkmazanil.roomdatabasebasic.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;
import com.korkmazanil.roomdatabasebasic.util.ToDoListRoomDatabase;

import java.util.List;

public class ToDoListRepository {

    private ToDoListDao toDoListDao;
    private LiveData<List<ToDoList>> allToDo;

    public ToDoListRepository(Application application){

        ToDoListRoomDatabase db = ToDoListRoomDatabase
                .getDatabase(application);

        toDoListDao = db.toDoListDao();

        allToDo = toDoListDao.getAllToDo();
    }

    public LiveData<List<ToDoList>> getAllData() { return allToDo; }

    public void insert(ToDoList toDoList){
        ToDoListRoomDatabase
                .databaseWriteExecutor
                .execute(() -> toDoListDao.insert(toDoList));
    }

    public LiveData<ToDoList> get(int id){
        return toDoListDao.get(id);
    }

    public void update(ToDoList toDoList){
        ToDoListRoomDatabase
                .databaseWriteExecutor
                .execute(()-> toDoListDao.update(toDoList));
    }

    public void delete(ToDoList toDoList){
        ToDoListRoomDatabase
                .databaseWriteExecutor
                .execute(()-> toDoListDao.delete(toDoList));
    }
}
