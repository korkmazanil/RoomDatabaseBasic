package com.korkmazanil.roomdatabasebasic.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.korkmazanil.roomdatabasebasic.R;
import com.korkmazanil.roomdatabasebasic.adapters.ToDoListAdapter;
import com.korkmazanil.roomdatabasebasic.databinding.ActivityToDoListBinding;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;
import com.korkmazanil.roomdatabasebasic.model.ToDoListViewModel;
import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity  {

    private ToDoListViewModel toDoListViewModel;
    private ToDoListAdapter adapter;
    private ActivityToDoListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToDoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toDoListViewModel = new ViewModelProvider.AndroidViewModelFactory(ToDoListActivity.this
        .getApplication())
        .create(ToDoListViewModel.class);

        toDoListViewModel.getAllToDo().observe(this,toDoLists -> {

            binding.rv.setLayoutManager(new LinearLayoutManager(ToDoListActivity.this));
            binding.rv.setHasFixedSize(true);

            adapter = new ToDoListAdapter((ArrayList<ToDoList>) toDoLists,ToDoListActivity.this);
            binding.rv.setAdapter(adapter);

            swipeToEdit(toDoLists);
        });

        swipeToDelete();
    }

    private void swipeToEdit(List<ToDoList> toDoLists) {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper
                .SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(ToDoListActivity.this,ToDoUpdateActivity.class);
                intent.putExtra("todolist", toDoLists.get(position));
                startActivity(intent);
                finish();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rv);
    }

    private void swipeToDelete() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper
                .SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                ToDoList item = adapter.getToDoLists().get(position);

                AlertDialog alertDialog = new AlertDialog.Builder(ToDoListActivity.this).create();
                alertDialog.setIcon(R.drawable.ic_delete_black);
                alertDialog.setTitle(R.string.delete);
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage(getString(R.string.delete_data));

                adapter.removeItem(position);

                alertDialog.setButton(AlertDialog
                        .BUTTON_POSITIVE,getResources().getString(R.string.yes),(dialogInterface, i) -> {
                    toDoListViewModel.delete(item);
                });

                alertDialog.setButton(AlertDialog
                        .BUTTON_NEGATIVE,getResources().getString(R.string.no),(dialogInterface, i) -> {
                    adapter.restoreItem(item,position);
                    binding.rv.scrollToPosition(position);
                });

                alertDialog.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.rv);
    }
}
