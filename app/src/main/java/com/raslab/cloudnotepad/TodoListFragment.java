package com.raslab.cloudnotepad;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raslab.cloudnotepad.pojo.ToDoListModel;
import com.raslab.cloudnotepad.pojo.TodoListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodoListFragment extends Fragment {
    private NewTodoList newTodoList;

    private ToDoListModel toDoListModel1 = new ToDoListModel();

    private TodoListAdapter todoListAdapter;
    private RecyclerView recyclerView;



    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference toDolistRef;
    private FirebaseDatabase firebaseDatabase;
    private GridLayoutManager gridLayoutManager;
    final List<ToDoListModel> toDoListModelList = new ArrayList<>();

    public TodoListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    newTodoList = (NewTodoList) context;
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_todo_list, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fabTodoList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTodoList.onNewTodoList();
            }
        });


        recyclerView = view.findViewById(R.id.todoRecycler);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();



        rootRef=firebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("Users");
        userIdRef=userRef.child(firebaseUser.getUid());
        toDolistRef=userIdRef.child("ToDoList");
        toDolistRef.keepSynced(true);

        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        // gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        todoListAdapter = new TodoListAdapter(getContext(),toDoListModelList);
        recyclerView.setAdapter(todoListAdapter);
        setTodoListAdapter();


        return view;
    }

    public void setTodoListAdapter(){

        toDolistRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot==null){
                    return;
                }else {
                    toDoListModelList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ToDoListModel toDoListModel = snapshot.getValue(ToDoListModel.class);
                        toDoListModelList.add(toDoListModel);
                    }
                    todoListAdapter.updateList(toDoListModelList);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    interface NewTodoList{

        void onNewTodoList ();

    }

}
