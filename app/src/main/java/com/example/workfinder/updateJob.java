package com.example.workfinder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class updateJob extends AppCompatActivity {

    EditText applyDt, duration, jobTitle, location, stipend, type;
    DatabaseReference ref;
    Button updtBtn, deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job);

        applyDt= (EditText)findViewById(R.id.editDate);
        duration= (EditText)findViewById(R.id.editDuration);
        jobTitle= (EditText)findViewById(R.id.editTitle);
        location= (EditText)findViewById(R.id.editLocation);
        stipend= (EditText)findViewById(R.id.editStipend);
        type= (EditText)findViewById(R.id.editType);
        //String key = getIntent().getExtras().get("key").toString();
        String key = getIntent().getStringExtra("key");
        //Toast.makeText(updateJob.this,key,Toast.LENGTH_LONG).show();
        ref= FirebaseDatabase.getInstance().getReference().child("Jobs").child(key);

        updtBtn = (Button)findViewById(R.id.editItem);
        deleteBtn = (Button)findViewById(R.id.deleteItem);

        applyDt.setText(getIntent().getStringExtra("apply by"));
        duration.setText(getIntent().getStringExtra("duration"));
        jobTitle.setText(getIntent().getStringExtra("position"));
        location.setText(getIntent().getStringExtra("location"));
        stipend.setText(getIntent().getStringExtra("stipend"));
        type.setText(getIntent().getStringExtra("type"));

        updtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnUpdate();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnDelete();
            }
        });


    }

    public  void btnUpdate(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("jobTitle").setValue(jobTitle.getText().toString());
                dataSnapshot.getRef().child("location").setValue(location.getText().toString());
                dataSnapshot.getRef().child("duration").setValue(duration.getText().toString());
                dataSnapshot.getRef().child("stipend").setValue(stipend.getText().toString());
                dataSnapshot.getRef().child("applyDt").setValue(applyDt.getText().toString());
                dataSnapshot.getRef().child("type").setValue(type.getText().toString());
                Toast.makeText(updateJob.this,"Data Updated",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void btnDelete(){
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(updateJob.this,"Deletion Successful",Toast.LENGTH_LONG).show();
                    updateJob.this.finish();;
                }
                else {
                    Toast.makeText(updateJob.this,"Deletion Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
