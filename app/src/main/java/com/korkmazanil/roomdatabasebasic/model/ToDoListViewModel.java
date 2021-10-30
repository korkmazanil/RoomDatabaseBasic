package com.korkmazanil.roomdatabasebasic.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.korkmazanil.roomdatabasebasic.data.ToDoListRepository;
import java.util.List;

public class ToDoListViewModel extends AndroidViewModel {

    public static ToDoListRepository repository;
    public final LiveData<List<ToDoList>> allToDo;

    public ToDoListViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoListRepository(application);
        allToDo = repository.getAllData();
    }

    public LiveData<List<ToDoList>> getAllToDo() { return allToDo; }
    public void insert(ToDoList toDoList) { repository.insert(toDoList); }

    public LiveData<ToDoList> get(int id) { return repository.get(id);}
    public void update(ToDoList toDoList) { repository.update(toDoList);}
    public void delete(ToDoList toDoList) { repository.delete(toDoList);}
}
