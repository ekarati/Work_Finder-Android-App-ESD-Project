package com.example.workfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText userPassword, userEmail, userName;
    private String name, email, role;
    private Button regBtn;
    private TextView textViewLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        userPassword = (EditText)findViewById(R.id.password);
        userEmail = (EditText)findViewById(R.id.email);
        userName = (EditText)findViewById(R.id.userName);
        regBtn = (Button)findViewById(R.id.registerBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        textViewLogin  = (TextView) findViewById(R.id.textViewLogin);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                registerUser();
            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                startActivity(new Intent(registerPage.this, MainActivity.class));
            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        role = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), role, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void registerUser()
    {
        email = userEmail.getText().toString().trim();
        name = userName.getText().toString().trim();
        final String password  = userPassword.getText().toString().trim();

        //checking if email, name, passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful()){
                    Users user = new Users(name, email, role);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user);
                    Toast.makeText(registerPage.this,"Successfully registered",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }else{
                    Toast.makeText(registerPage.this,"Registration Error",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
