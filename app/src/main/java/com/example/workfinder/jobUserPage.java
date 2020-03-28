package com.example.workfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class jobUserPage extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    private Button addJob;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_user_page);
        addJob = findViewById(R.id.addJob);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String role= (String)dataSnapshot.child("role").getValue();
                final String name= (String)dataSnapshot.child("name").getValue();
                //Toast.makeText(jobUserPage.this, role, Toast.LENGTH_SHORT).show();
                TextView t1=findViewById(R.id.textApply);
                if(role.equals("Company")) {
                    addJob.setVisibility(View.VISIBLE);
                    t1.setText("Tap to Update");
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String key = adapter.getRef(position).getKey();
                            JobsList j = (JobsList) parent.getItemAtPosition(position);
                            String cname= j.getCompany();
                            if(name.equals(cname)) {
                                Intent updtJob = new Intent(jobUserPage.this, updateJob.class);
                                updtJob.putExtra("position", j.getJobTitle());
                                updtJob.putExtra("location", j.getLocation());
                                updtJob.putExtra("duration", j.getDuration());
                                updtJob.putExtra("stipend", j.getStipend());
                                updtJob.putExtra("apply by", j.getApplyDt());
                                updtJob.putExtra("type", j.getType());

                                updtJob.putExtra("key", key);
                                startActivity(updtJob);
                            }
                        }
                    });
                }
                else
                {
                    t1.setText("Tap to Apply");
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            JobsList j = (JobsList) parent.getItemAtPosition(position);
                            Intent updtJob = new Intent(jobUserPage.this, confirmAppPage.class);
                            updtJob.putExtra("position", j.getJobTitle());
                            updtJob.putExtra("location", j.getLocation());
                            updtJob.putExtra("duration", j.getDuration());
                            updtJob.putExtra("stipend", j.getStipend());
                            updtJob.putExtra("apply by", j.getApplyDt());
                            updtJob.putExtra("type", j.getType());
                            updtJob.putExtra("company", j.getCompany());
                            String key = adapter.getRef(position).getKey();
                            updtJob.putExtra("key", key);
                            startActivity(updtJob);
                        }
                    });


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(jobUserPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("Jobs");
        lv =(ListView) findViewById(R.id.listView);
        FirebaseListOptions<JobsList> options = new FirebaseListOptions.Builder<JobsList>()
                .setLayout(R.layout.jobitem).setQuery(query, JobsList.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView  company = v.findViewById(R.id.company);
                TextView  applyDt = v.findViewById(R.id.applyDt);
                TextView  duration = v.findViewById(R.id.duration);
                TextView  jobTitle = v.findViewById(R.id.jobTitle);
                TextView  location = v.findViewById(R.id.location);
                TextView  stipend = v.findViewById(R.id.stipend);
                TextView  jobType = v.findViewById(R.id.jobType);

                JobsList obj = (JobsList) model;
                company.setText(obj.getCompany());
                applyDt.setText("Apply By: "+obj.getApplyDt());
                jobType.setText("Type: "+obj.getType());
                duration.setText("Duration: "+obj.getDuration());
                jobTitle.setText("Position: "+obj.getJobTitle());
                location.setText("Location: "+obj.getLocation());
                stipend.setText("Stipend: "+obj.getStipend());

            }
        };
        lv.setAdapter(adapter);

        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(jobUserPage.this, addJob.class));
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
