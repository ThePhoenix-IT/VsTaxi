package com.thephoenixit.vstaxi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText input_email;
    private Button btnResetPass;
    private TextView btnBack;
    private RelativeLayout activity_forgot;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();
        input_email = (EditText)findViewById(R.id.forgot_email);
        btnResetPass = (Button)findViewById(R.id.forgot_btn_reset);
        btnBack = (TextView)findViewById(R.id.forgot_btn_back);
        activity_forgot = (RelativeLayout)findViewById(R.id.activity_forgot_password);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(input_email);
            }
        });

    }
    private void resetPassword(EditText input_email) {
        String email = input_email.getText().toString();
        email = email.replaceAll(" ", "");
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this, "Reset password link sent to your email address ", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                            ForgotPassword.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "Failed to send reset password link", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
