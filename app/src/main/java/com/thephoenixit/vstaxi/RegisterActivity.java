package com.thephoenixit.vstaxi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity {
    Button btnSignup;
    TextView btnLogin,btnForgotPass;
    EditText input_email,input_pass, input_pass2;
    RelativeLayout activity_sign_up;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        btnSignup = (Button)findViewById(R.id.signup_btn_register);
        btnLogin = (TextView)findViewById(R.id.signup_btn_login);
        btnForgotPass = (TextView)findViewById(R.id.signup_btn_forgot_pass);
        input_email = (EditText)findViewById(R.id.signup_email);
        input_pass = (EditText)findViewById(R.id.signup_password);
        input_pass2 = (EditText)findViewById(R.id.signup_password2);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        activity_sign_up = (RelativeLayout)findViewById(R.id.activity_sign_up);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(input_email, input_pass, input_pass2);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                RegisterActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, ForgotPassword.class);
                startActivity(i);
                RegisterActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    private void registerUser(EditText editTextEmail, EditText editTextPassword, EditText editTextPassword2) {
        String email = editTextEmail.getText().toString().trim();
        String password =  editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password2.isEmpty()) {
            editTextPassword2.setError("Password is required");
            editTextPassword2.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }
        if (password2.length() < 6) {
            editTextPassword2.setError("Minimum lenght of password should be 6");
            editTextPassword2.requestFocus();
            return;
        }
        if (!password.equals(password2)){
            editTextPassword2.setError("Passwords are not the same");
            editTextPassword2.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    Toast.makeText(RegisterActivity.this, "Sign in successfully", Toast.LENGTH_LONG).show();
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
