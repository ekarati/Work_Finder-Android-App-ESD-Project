package com.example.workfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dref;

    private TextView userEmail, userName;
    Button appliedBtn, applicantBtn, viewB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appliedBtn = findViewById(R.id.appliedBtn);
        applicantBtn = findViewById(R.id.applicantBtn);
        viewB=findViewById(R.id.viewJIBtn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Made by Erina Karati :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);


        ImageView homewall = (ImageView) findViewById(R.id.imageWall);
        int imageResource= getResources().getIdentifier("@drawable/homewall", null, this.getPackageName());
        homewall.setImageResource(imageResource);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());

        View hView =  navigationView.getHeaderView(0);
        userEmail = (TextView)hView.findViewById(R.id.nav_email);
        userName = (TextView)hView.findViewById(R.id.nav_name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users userProfile = dataSnapshot.getValue(Users.class);
                userName.setText(userProfile.getUserName());
                userEmail.setText(userProfile.getUserEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        appliedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Home.this, appliedPage.class));
            }
        });
        applicantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Home.this, companyJobPage.class));
            }
        });
        viewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Home.this, jobUserPage.class));
            }
        });
        //Toast.makeText(this,userN,Toast.LENGTH_LONG).show();

        //View hView =  navigationView.getHeaderView(0);
        //userEmail = (TextView)hView.findViewById(R.id.nav_email);
        //userEmail.setText(firebaseAuth.getEmail());
        //userEmail.setText(userType);
        //userName = (TextView)hView.findViewById(R.id.nav_name);
        //userName.setText(userN);
        dref = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid()).child("role");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String role= (String)dataSnapshot.getValue();
                //Toast.makeText(jobUserPage.this, role, Toast.LENGTH_SHORT).show();
                if(role.equals("Company")) {
                    applicantBtn.setVisibility(View.VISIBLE);
                }
                else
                {
                    appliedBtn.setVisibility(View.VISIBLE);
                }

                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_profile) {
                            startActivity(new Intent(Home.this, Profile.class));
                        } else if (id == R.id.nav_logout) {
                            Toast.makeText(Home.this,"Logging Out...",Toast.LENGTH_LONG).show();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(Home.this, MainActivity.class));

                        } else if (id == R.id.nav_intern) {
                            if(role.equals("Company")) {
                                startActivity(new Intent(Home.this, companyJobPage.class));
                            }
                            else
                            {
                                startActivity(new Intent(Home.this, appliedPage.class));
                            }
                        } else if (id == R.id.nav_job) {
                            startActivity(new Intent(Home.this, jobUserPage.class));

                        } else if (id == R.id.nav_more) {
                            startActivity(new Intent(Home.this, aboutPage.class));

                        }
                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, Profile.class));
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"Logging Out...",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));

        } else if (id == R.id.nav_intern) {


        } else if (id == R.id.nav_job) {
            startActivity(new Intent(this, jobUserPage.class));

        } else if (id == R.id.nav_more) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}
