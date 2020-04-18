package com.raslab.cloudnotepad;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raslab.cloudnotepad.pojo.ToDoListModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewToDoListFragment extends Fragment {


    private int year, month, dayOfMonth,hour, munite;
    private Button dateButton,timeButton,button, createtextBox;
    Calendar calendar;
    ConstraintLayout constraintLayout;
    EditText editText;


    backTodolist backTodolist;

    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference todoListref;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public NewToDoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_to_do_list, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("To-Do List");

        setHasOptionsMenu(true);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        rootRef= FirebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("Users");
        userIdRef=userRef.child(firebaseUser.getUid());
        todoListref=userIdRef.child("ToDoList");


        constraintLayout = view.findViewById(R.id.contraintTodo);
       // constraintLayout=new ConstraintLayout(getContext());
        createtextBox =view.findViewById(R.id.creatText);

        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        munite=calendar.get(Calendar.MINUTE);
        timeButton=view.findViewById(R.id.editTimePicker);


       //date Picker
        dateButton=view.findViewById(R.id.editDatePicker);
        SimpleDateFormat dateFormat =new SimpleDateFormat("E , dd MMM,YYYY");
        SimpleDateFormat dateFormat1 =new SimpleDateFormat("hh:mm ss a");
        String date =dateFormat.format(calendar.getTime());
        String date1 =dateFormat1.format(calendar.getTime());
        dateButton.setText(date);
        timeButton.setText(date1);
        editText=view.findViewById(R.id.etTaskText);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),listener,year,month,dayOfMonth);
                dialog.show();
            }
        });
//end Of Date Picker

            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),timnePickerlistener,hour,munite,false);
                    timePickerDialog.show();
                }
            });

        createtextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = new EditText(getContext());
            }
        });

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.save_update_button, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backTodolist = (NewToDoListFragment.backTodolist) context;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean status = false;

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newnote_save:

                saveToDo();

                backTodolist.onBackTodoList();
                return  false;
        }

        return true;
    }

    private void saveToDo() {

        String itemChk = editText.getText().toString().trim();
        String itemTime = timeButton.getText().toString().trim();
        String itemDate = dateButton.getText().toString().trim();
        String itemid = todoListref.push().getKey();

        ToDoListModel toDoListModel =new ToDoListModel(itemChk,itemTime,itemDate,itemid);
        todoListref.child(itemid).setValue(toDoListModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                editText.setText("");
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener timnePickerlistener =new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            final Calendar calendar1= Calendar.getInstance();
            calendar1.set(0,0,0,hourOfDay,minute);
            SimpleDateFormat dateFormat1 =new SimpleDateFormat("hh:mm a");
            String date =dateFormat1.format(calendar1.getTime());
            timeButton.setText(date);

        }
    };
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view,  int dayOfMonth,int month, int year) {


            final Calendar calendar1= Calendar.getInstance();
            calendar1.set(dayOfMonth,month,year);
            SimpleDateFormat dateFormat1 =new SimpleDateFormat("E , dd MMM,YYYY");
            String date =dateFormat1.format(calendar1.getTime());
            dateButton.setText(date);

            }

    };

    interface backTodolist{
        void onBackTodoList();
    }
}
