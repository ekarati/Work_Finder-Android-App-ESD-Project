package com.example.workfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class applicantPage extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_page);
        key = getIntent().getStringExtra("key");

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild(key).equalTo("T");
        lv =(ListView) findViewById(R.id.applicantList);
        FirebaseListOptions<Users> options = new FirebaseListOptions.Builder<Users>()
                .setLayout(R.layout.useritem).setQuery(query, Users.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView name = v.findViewById(R.id.userN);
                TextView  email = v.findViewById(R.id.userE);

                Users obj = (Users) model;
                name.setText(obj.getUserName());
                email.setText("Email: "+obj.getUserEmail());
            }
        };
        lv.setAdapter(adapter);
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
