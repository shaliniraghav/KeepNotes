package com.shalini.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import java.util.HashMap;
import java.util.Map;
public class createnote extends AppCompatActivity {

    EditText mcreatetitleofnote,mcreatecontentofnote;
    FloatingActionButton msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);


        msavenote=findViewById(R.id.savenote);
        mcreatecontentofnote=findViewById(R.id.createcontentofnote);
        mcreatetitleofnote=findViewById(R.id.createtitleofnote);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        msavenote.setOnClickListener(view -> {
            String title=mcreatetitleofnote.getText().toString();
            String content=mcreatecontentofnote.getText().toString();

            if(title.isEmpty() || content.isEmpty()){
                Toast.makeText(getApplicationContext(),"Both fields are required..",Toast.LENGTH_SHORT).show();
            }else{
                DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                Map<String,Object> note=new HashMap<>();
                note.put("title",title);
                note.put("content",content);

                documentReference.set(note).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(),"Note Created Successfully..",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(createnote.this,NotesActivity.class));
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),"Failed to create note.."+e,Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(createnote.this,notesActivity.class));

                });
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
