package com.korkmazanil.roomdatabasebasic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.korkmazanil.roomdatabasebasic.R;
import com.korkmazanil.roomdatabasebasic.databinding.ActivityMainBinding;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;
import com.korkmazanil.roomdatabasebasic.model.ToDoListViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ToDoListViewModel toDoListViewModel = new ViewModelProvider(this).get(ToDoListViewModel.class);

        binding.save.setOnClickListener(view -> {

            if (!TextUtils.isEmpty(binding.title.getText()) &&
                !TextUtils.isEmpty(binding.work.getText())){

                String StringTitle = binding.title.getText().toString();
                String StringWork = binding.work.getText().toString();

                ToDoList toDoList = new ToDoList(StringTitle, StringWork);

                toDoListViewModel.insert(toDoList);

                binding.title.setText("");
                binding.work.setText("");
                Toast.makeText(MainActivity.this, getString(R.string.to_do_added), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, getString(R.string.empty_warning), Toast.LENGTH_SHORT).show();
            }
        });

        binding.gotoList.setOnClickListener(view -> {
            startActivity(new Intent(this,ToDoListActivity.class));
        });
    }
}