package com.example.workfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class confirmAppPage extends AppCompatActivity {

    TextView company, applyDt, duration, jobTitle, location, stipend, type;
    Button apply;
    String key;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_app_page);
        applyDt = (TextView)findViewById(R.id.appA);
        duration = (TextView)findViewById(R.id.appD);
        jobTitle = (TextView)findViewById(R.id.appP);
        location = (TextView)findViewById(R.id.appL);
        stipend = (TextView)findViewById(R.id.appS);
        company = (TextView)findViewById(R.id.appC);
        type = (TextView) findViewById(R.id.appT);
        apply = (Button)findViewById(R.id.applyBtn);
        key = getIntent().getStringExtra("key");
        ref= FirebaseDatabase.getInstance().getReference().child("Jobs").child(key);

        applyDt.setText(getIntent().getStringExtra("apply by"));
        duration.setText(getIntent().getStringExtra("duration"));
        jobTitle.setText(getIntent().getStringExtra("position"));
        location.setText(getIntent().getStringExtra("location"));
        stipend.setText(getIntent().getStringExtra("stipend"));
        type.setText(getIntent().getStringExtra("type"));
        company.setText(getIntent().getStringExtra("company"));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                btnApply();
            }
        });
    }

    public  void btnApply(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child(key).setValue("T");
                FirebaseDatabase.getInstance().getReference("Jobs").child(key).child(FirebaseAuth.getInstance().getUid()).setValue("T");
                Toast.makeText(confirmAppPage.this,"Successfully Applied",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(getApplicationContext(), jobUserPage.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
