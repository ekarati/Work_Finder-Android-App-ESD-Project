package com.example.workfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addJob extends AppCompatActivity {

    EditText applyDt, duration, jobTitle, location, stipend, type;
    String apply, company, dur, jobT, loc, sti, ty;
    Button insert;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        applyDt = (EditText)findViewById(R.id.addDate);
        duration = (EditText)findViewById(R.id.addDuration);
        jobTitle = (EditText)findViewById(R.id.addTitle);
        location = (EditText)findViewById(R.id.addLocation);
        stipend = (EditText)findViewById(R.id.addStipend);
        insert = (Button)findViewById(R.id.addItem);
        type = (EditText) findViewById(R.id.addType);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users userProfile = dataSnapshot.getValue(Users.class);
                company = userProfile.getUserName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addJob.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                insertJob();
            }
        });
    }

    private void insertJob()
    {
        apply = applyDt.getText().toString().trim();
        dur = duration.getText().toString().trim();
        jobT = jobTitle.getText().toString().trim();
        loc = location.getText().toString().trim();
        sti = stipend.getText().toString().trim();
        ty = type.getText().toString().trim();
        int rand = (int)(Math.random() * 1000000);
        final String jobID= Integer.toString(rand);

        if(TextUtils.isEmpty(jobT)){
            Toast.makeText(this,"Please enter Position",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(ty)){
            Toast.makeText(this,"Please enter type",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(loc)){
            Toast.makeText(this,"Please enter location",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(dur)){
            Toast.makeText(this,"Please enter duration",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(sti)){
            Toast.makeText(this,"Please enter stipend",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(apply)){
            Toast.makeText(this,"Please enter apply by date",Toast.LENGTH_LONG).show();
            return;
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users userProfile = dataSnapshot.getValue(Users.class);
                company = userProfile.getUserName();
                JobsList job = new JobsList(apply, company, dur, jobT, loc, sti, ty, FirebaseAuth.getInstance().getUid());
                FirebaseDatabase.getInstance().getReference("Jobs")
                        .child(jobID)
                        .setValue(job);
                Toast.makeText(addJob.this,"Successfully Added",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(addJob.this, jobUserPage.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addJob.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
