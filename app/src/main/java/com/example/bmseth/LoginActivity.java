package com.example.bmseth;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity  {

    EditText email;
    EditText pass;
    Button login;
    ProgressBar Pbar;
    FirebaseAuth mAuth;

    //FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText3);
        login=(Button)findViewById(R.id.button);
        Pbar=(ProgressBar)findViewById(R.id.progressBar) ;

        mAuth = FirebaseAuth.getInstance();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pbar.setVisibility(View.VISIBLE);
                startSignin();

            }
        });

        TextView register=(TextView)findViewById(R.id.textView);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class) );
                finishAffinity();

            }
        });

    }



    private void startSignin(){

        String em=email.getText().toString();
        String pa=pass.getText().toString();

        if(TextUtils.isEmpty(em) || TextUtils.isEmpty(pa)){
            Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_LONG).show();
            Pbar.setVisibility(View.GONE);
        }
        else{

            mAuth.signInWithEmailAndPassword(em, pa).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Logging you in!", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(LoginActivity.this, StuDashboardActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        FirebaseAuthException e=(FirebaseAuthException)task.getException();
                        Toast.makeText(LoginActivity.this, "Sign In Failed !" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Pbar.setVisibility(View.GONE);
                    }
                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Leave Application?");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to leave the application?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.ic_dialog_icon);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }

        });
        // Showing Alert Message
        alertDialog.show();
    }

}
