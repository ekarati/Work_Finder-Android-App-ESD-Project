package com.example.workfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class appliedPage extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_page);


        //final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("applied");

        /*Query query = FirebaseDatabase.getInstance().getReference().child("Jobs");
        lv =(ListView) findViewById(R.id.appliedList);
        FirebaseListOptions<JobsList> options = new FirebaseListOptions.Builder<JobsList>()
                .setLayout(R.layout.jobitem).setQuery(query, JobsList.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {

                final String key = adapter.getRef(position).getKey();

                final View v2=v;
                final Object model2=model;

                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(key)) {
                            TextView company = v2.findViewById(R.id.company);
                            TextView applyDt = v2.findViewById(R.id.applyDt);
                            TextView duration = v2.findViewById(R.id.duration);
                            TextView jobTitle = v2.findViewById(R.id.jobTitle);
                            TextView location = v2.findViewById(R.id.location);
                            TextView stipend = v2.findViewById(R.id.stipend);
                            TextView jobType = v2.findViewById(R.id.jobType);

                            JobsList obj = (JobsList) model2;
                            company.setText(obj.getCompany());
                            applyDt.setText("Apply By: " + obj.getApplyDt());
                            jobType.setText("Type: " + obj.getType());
                            duration.setText("Duration: " + obj.getDuration());
                            jobTitle.setText("Position: " + obj.getJobTitle());
                            location.setText("Location: " + obj.getLocation());
                            stipend.setText("Stipend: " + obj.getStipend());

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(appliedPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        lv.setAdapter(adapter);*/

        Query query = FirebaseDatabase.getInstance().getReference().child("Jobs").orderByChild(FirebaseAuth.getInstance().getUid()).equalTo("T");
        lv =(ListView) findViewById(R.id.appliedJobList);
        FirebaseListOptions<JobsList> options = new FirebaseListOptions.Builder<JobsList>()
                .setLayout(R.layout.jobitem).setQuery(query, JobsList.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView company = v.findViewById(R.id.company);
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

        /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Jobs");
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("applied");
        final List<JobsList> jobList = new ArrayList<>();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                jobList.clear();
                for (final DataSnapshot postSnapshot: snapshot.getChildren()) {
                    final String key = postSnapshot.getKey();

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(key)) {
                                JobsList jb = postSnapshot.getValue(JobsList.class);
                                jobList.add(jb);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(appliedPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(appliedPage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseListOptions<JobsList> options = new FirebaseListOptions.Builder<JobsList>()
                .setLayout(R.layout.jobitem).setSnapshotArray(jobList).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {


            }
        };
        lv.setAdapter(adapter);*/

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
