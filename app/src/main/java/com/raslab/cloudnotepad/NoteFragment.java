package com.raslab.cloudnotepad;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raslab.cloudnotepad.pojo.NoteAdapter;
import com.raslab.cloudnotepad.pojo.NoteModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {


        private NewNote newNoteListener;
        private RecyclerView recyclerView;

        NoteModel note = new NoteModel();
        NoteAdapter noteAdapter;
        private DatabaseReference databaseReference2;

        private FirebaseAuth firebaseAuth;
        private FirebaseUser firebaseUser;
        private DatabaseReference rootRef;
        private DatabaseReference userRef;
        private DatabaseReference userIdRef;
        private DatabaseReference noteRef;
        private FirebaseDatabase firebaseDatabase;

    static boolean isInitialized = false;

        GridLayoutManager gridLayoutManager;
    final List<NoteModel> notelist = new ArrayList<>();


    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
            super.onAttach(context);
            newNoteListener= (NewNote) context;

    }



    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notes");
        FloatingActionButton fab = view.findViewById(R.id.fabNote);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNoteListener.onNewNoteFgStart();
            }
        });



        // Offline Data Show
        try{
            if(!isInitialized){
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                isInitialized = true;
            }else {
               // Log.d(TAG,"Already Initialized");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    //End Offline

//adapter Sett With database reference;


        firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        recyclerView=view.findViewById(R.id.recyclerViewitem);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
       // gridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        rootRef=firebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("Users");
        userIdRef=userRef.child(firebaseUser.getUid());
        noteRef=userIdRef.child("Notes");
        noteRef.keepSynced(true);
       // dateRef=userIdRef.child(formattedDate);
        noteAdapter= new NoteAdapter(getContext(),notelist);
        recyclerView.setAdapter(noteAdapter);
        adapter();
        //end data set Adapter;

        return view;
    }

    public void adapter(){


        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot==null){
                    return;
                }else{
                    notelist.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        NoteModel note = snapshot.getValue(NoteModel.class);
                        notelist.add(note);

                    }
                    Toast.makeText(getContext(), "Size of list "+notelist.size(), Toast.LENGTH_SHORT).show();
                    noteAdapter.updateList(notelist);
                    //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    interface NewNote{
         void onNewNoteFgStart();
        }
}
