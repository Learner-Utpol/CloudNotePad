package com.raslab.cloudnotepad;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raslab.cloudnotepad.pojo.NoteAdapter;
import com.raslab.cloudnotepad.pojo.NoteModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewNoteFragment extends Fragment {

    private NewmNoteFragment newmNoteFragment;
    private EditText editTitle, editContent;
    private FloatingActionButton favVoiceInput;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference noteref;

    private FloatingActionButton favVoice;
    NoteModel note = new NoteModel();
    NoteAdapter noteAdapter;
    RecyclerView recyclerView;


    public static String query = "";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public NewNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        // title bar Name Change //
           // Toolbar actionBarToolBar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("NewNote");
        setHasOptionsMenu(true);
        // end Title bar

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        Toast.makeText(getContext(), "Date"+formattedDate, Toast.LENGTH_SHORT).show();
        editTitle = view.findViewById(R.id.new_note_title);
        editContent=view.findViewById(R.id.new_note_content);


        favVoice = view.findViewById(R.id.fabVoice);

        favVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
        //Database

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        rootRef=FirebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("Users");
        userIdRef=userRef.child(firebaseUser.getUid());
        noteref=userIdRef.child("Notes");



        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.save_update_button, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean status = false;

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newnote_save:

                savenote();
                newmNoteFragment.onNewNoteFragment();

                return  false;
        }

        return true;
    }




    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                     query = result.get(0);

                        editContent.setText(query);

                }
                break;
            }

        }


    }

public void savenote (){

    String noteTitle = editTitle.getText().toString().trim();
    String noteContent = editContent.getText().toString().trim();
    String noteId = userIdRef.push().getKey();
    //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    NoteModel noteModel =new NoteModel( noteTitle, noteContent, noteId);
    noteref.child(noteId).setValue(noteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            editTitle.setText("");
            editContent.setText("");
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    });

}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newmNoteFragment = (NewmNoteFragment) context;
    }

    interface NewmNoteFragment{
        void onNewNoteFragment();

    }
}