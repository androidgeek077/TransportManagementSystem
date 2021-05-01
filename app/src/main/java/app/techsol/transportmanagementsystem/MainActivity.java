package app.techsol.transportmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText emailET, PasswordET;
    String emailStr, passwordStr;
    Button loginBtn;
    TextView gotoSignupTV;
    ProgressBar mProgressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        mProgressBar=findViewById(R.id.mProgressBar);
        emailET=findViewById(R.id.emailET);
        PasswordET=findViewById(R.id.PasswordET);
        loginBtn=findViewById(R.id.loginBtn);
        gotoSignupTV=findViewById(R.id.gotoSignupTV);
        gotoSignupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
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
                                mProgressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getBaseContext(), HomeNavDrawerActivity.class));
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
}