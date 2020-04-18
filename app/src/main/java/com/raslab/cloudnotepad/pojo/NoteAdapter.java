package com.raslab.cloudnotepad.pojo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raslab.cloudnotepad.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements PopupMenu.OnMenuItemClickListener {
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference userIdRef;
    private DatabaseReference noteRef;
    private DatabaseReference noteId;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    private Context context;
    private List<NoteModel> notelist;

    public NoteAdapter(Context context, List<NoteModel> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.titleTV.setText(notelist.get(position).getNoteTitle());
        holder.contentTV.setText(notelist.get(position).getNoteContent());


       firebaseAuth =FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        rootRef= FirebaseDatabase.getInstance().getReference();
        userRef=rootRef.child("Users");
        userIdRef=userRef.child(firebaseUser.getUid());
        userIdRef=userRef.child(firebaseUser.getUid());
        noteRef=userIdRef.child("Notes");
        noteId=noteRef.child(notelist.get(position).getNoteId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            notelist.get(position).getNoteId();
            }
        });



    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }



    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView menutv,titleTV,contentTV;
        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

           // dateTV = itemView.findViewById(R.id.dateItem);
            titleTV = itemView.findViewById(R.id.note_title);
            contentTV = itemView.findViewById(R.id.note_content);

          //Item View PopUp
            menutv=itemView.findViewById(R.id.menu_Row);
            menutv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, v);
                    popup.getMenuInflater().inflate(R.menu.menu_item_raw,popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                          switch (item.getItemId()){
                              case R.id.deleteItem:
                                  noteId.removeValue();
                                  Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                              case R.id.itemEdit:
                                  Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                              default:
                                  return false;
                          }
                        }
                    });
                    popup.show();
                }
            });



        }
    }

    public void updateList(List<NoteModel> notelist){
        this.notelist = notelist;
        notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
       switch (item.getItemId()){
           case R.id.itemEdit:
               Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
           case R.id.deleteItem:
               Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
               default:
                   return false;
       }
    }
}
