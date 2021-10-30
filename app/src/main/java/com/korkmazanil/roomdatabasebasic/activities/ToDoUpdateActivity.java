package com.korkmazanil.roomdatabasebasic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.korkmazanil.roomdatabasebasic.R;
import com.korkmazanil.roomdatabasebasic.databinding.ActivityToDoUpdateBinding;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;
import com.korkmazanil.roomdatabasebasic.model.ToDoListViewModel;

public class ToDoUpdateActivity extends AppCompatActivity {

    private ActivityToDoUpdateBinding binding;
    private ToDoList toDoList;
    private ToDoListViewModel toDoListViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToDoUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toDoList = new ToDoList();

        Intent intent = getIntent();
        if (intent != null){

            toDoList = (ToDoList) intent.getSerializableExtra("todolist");

            binding.updateTitle.setText(toDoList.getTitle());
            binding.updateWork.setText(toDoList.getWork());
        }

        toDoListViewModel = new ViewModelProvider.AndroidViewModelFactory(ToDoUpdateActivity.this
                .getApplication())
                .create(ToDoListViewModel.class);

        binding.update.setOnClickListener(view -> {
            if (! TextUtils.isEmpty(binding.updateTitle.getText()) &&
                    ! TextUtils.isEmpty(binding.updateWork.getText())){

                String title = binding.updateTitle.getText().toString().trim();
                String work = binding.updateWork.getText().toString().trim();

                toDoList.setTitle(title);
                toDoList.setWork(work);

                toDoListViewModel.update(toDoList);

                Toast.makeText(ToDoUpdateActivity.this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ToDoUpdateActivity.this,ToDoListActivity.class));
                finish();

            }else {
                Toast.makeText(ToDoUpdateActivity.this, getString(R.string.empty_warning), Toast.LENGTH_SHORT).show();
            }
        });


        binding.back.setOnClickListener(view -> {
            startActivity(new Intent(ToDoUpdateActivity.this,ToDoListActivity.class));
            finish();
        });
    }
}