package com.raslab.cloudnotepad.pojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.raslab.cloudnotepad.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {
        private Context context;
        private List<ToDoListModel>toDoListModelList;

    public TodoListAdapter(Context context, List<ToDoListModel> toDoListModelList) {
        this.context = context;
        this.toDoListModelList = toDoListModelList;
    }

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_raw,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
            holder.checkBox.setText(toDoListModelList.get(position).getItemChk());
            holder.dateTv.setText(toDoListModelList.get(position).getItemDate());
            holder.timeTv.setText(toDoListModelList.get(position).getItemTime());
    }
    public void updateList(List<ToDoListModel> toDoListModels){
        this.toDoListModelList = toDoListModels;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return toDoListModelList.size();
    }

    public class TodoListViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox;
        private TextView timeTv,dateTv;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox =itemView.findViewById(R.id.cb_item);
            timeTv =itemView.findViewById(R.id.itemTime);
            dateTv =itemView.findViewById(R.id.itemDate);

        }
    }
}
