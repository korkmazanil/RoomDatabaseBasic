package com.korkmazanil.roomdatabasebasic.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.korkmazanil.roomdatabasebasic.R;
import com.korkmazanil.roomdatabasebasic.model.ToDoList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListCardView> {

    private List<ToDoList> toDoLists;

    private Context mContext;

    public ToDoListAdapter(List<ToDoList> toDoLists, Context mContext) {
        this.toDoLists = toDoLists;
        this.mContext = mContext;
    }

    public static class ToDoListCardView extends RecyclerView.ViewHolder {

        TextView title, work;

        public ToDoListCardView(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            work = itemView.findViewById(R.id.workTextView);
        }
    }

    @NonNull
    @Override
    public ToDoListCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewHolder = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.todolist_row,parent,false);

        return new ToDoListAdapter.ToDoListCardView(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListCardView holder, int position) {

        ToDoList model = toDoLists.get(position);

        if (model != null){
            holder.title.setText(model.getTitle());
            holder.work.setText(model.getWork());
        }

    }

    @Override
    public int getItemCount() {
        return toDoLists.size();
    }


    public void removeItem(int position){
        toDoLists.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ToDoList item, int position){
        toDoLists.add(position,item);
        notifyItemInserted(position);
    }

    public List<ToDoList> getToDoLists(){
        return toDoLists;
    }
}
