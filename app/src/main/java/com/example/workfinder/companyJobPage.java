package com.example.workfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class companyJobPage extends AppCompatActivity {

    FirebaseListAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job_page);

        Query query = FirebaseDatabase.getInstance().getReference().child("Jobs").orderByChild("id").equalTo(FirebaseAuth.getInstance().getUid());
        lv =(ListView) findViewById(R.id.applicantJobList);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updtJob = new Intent(companyJobPage.this, applicantPage.class);
                String key = adapter.getRef(position).getKey();
                updtJob.putExtra("key", key);
                startActivity(updtJob);
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
