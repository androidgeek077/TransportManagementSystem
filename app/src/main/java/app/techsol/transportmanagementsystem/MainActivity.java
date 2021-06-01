package app.techsol.transportmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText emailET, PasswordET;
    String emailStr, passwordStr;
    Button loginBtn;
    TextView gotoSignupTV;
    ProgressBar mProgressBar;
    FirebaseAuth auth;
    private DatabaseReference UserRef;
    private String userTpye;
    String userId;
    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            getUserTpye();
        }
        UserRef = FirebaseDatabase.getInstance().getReference("Users");

        mProgressBar = findViewById(R.id.mProgressBar);
        emailET = findViewById(R.id.emailET);
        PasswordET = findViewById(R.id.PasswordET);
        loginBtn = findViewById(R.id.loginBtn);
        gotoSignupTV = findViewById(R.id.gotoSignupTV);
        gotoSignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailStr = emailET.getText().toString();
                passwordStr = PasswordET.getText().toString();
                if (emailStr.isEmpty()) {
                    emailET.setError("Enter Email");
                } else if (passwordStr.isEmpty()) {
                    PasswordET.setError("Enter password");
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userId = auth.getCurrentUser().getUid();
                                mProgressBar.setVisibility(View.GONE);
                                getUserTpye();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }

    private String getUserTpye() {
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
//        mLocationList = new ArrayList<>();
//        mLongList = new ArrayList<>();
        UserRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userTpye = dataSnapshot.child("usertype").getValue().toString();
                if (userTpye.equals("user")) {
                    startActivity(new Intent(getBaseContext(), HomeNavDrawerActivity.class));
                    finish();

                } else if (userTpye.equals("admin")){
                    startActivity(new Intent(getBaseContext(), AdminNavDrawerActivity.class));
                    finish();

                } else{
                    startActivity(new Intent(getBaseContext(), ConductorBottomNavActivity.class));
                    finish();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return userTpye;
    }

}