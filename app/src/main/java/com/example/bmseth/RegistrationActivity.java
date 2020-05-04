package com.example.bmseth;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =  Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters
            "$");

    private static final Pattern USN_PATTERN = Pattern.compile("^" + "1BM" + "\\d{2}" + "\\p{Upper}{2}" + "\\d{3}");

    private static final Pattern PHONE_PATTERN= Pattern.compile("^" + "\\d{10}");

    Spinner spinner,spin_gender;
    ArrayList<String> branches,genders;

    EditText rname, remail, rpassword, rusn, rcontact, raddress;
    Button submit;

    FirebaseUser user;
    int flag1=1,flag2=1;
    String branch,gender;
    ProgressBar pbar;


    private FirebaseAuth fbauth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fbauth=FirebaseAuth.getInstance();
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Students");

        pbar=(ProgressBar)findViewById(R.id.progressBar2) ;

        spinner=(Spinner)findViewById(R.id.spinner);
        branches=new ArrayList<>();
        branches.add("--Branch--");
        branches.add("CSE");
        branches.add("ISE");
        branches.add("ECE");
        branches.add("EEE");
        branches.add("MECH");
        branches.add("CHEM");
        branches.add("IEM");
        branches.add("EIE");
        branches.add("ME");
        branches.add("BT");
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, branches);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                branch=branches.get(arg2);
                //  Toast.makeText(getApplicationContext(), branches.get(arg2) , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                flag1=0;

            }
        });

        spin_gender=(Spinner)findViewById(R.id.spinner2);
        genders=new ArrayList<>();
        genders.add("--Gender--");
        genders.add("Male");
        genders.add("Female");
        ArrayAdapter<String>adp1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,genders);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_gender.setAdapter(adp1);
        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=genders.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            flag2=0;
            }
        });

        remail=(EditText)findViewById(R.id.et2);
        rpassword=(EditText)findViewById(R.id.et4);
        rname=(EditText)findViewById(R.id.et5);
        rusn=(EditText)findViewById(R.id.et6);
        //rgender=(EditText)findViewById(R.id.et7);
        rcontact=(EditText)findViewById(R.id.et8);
        raddress=(EditText)findViewById(R.id.et9);
        submit=(Button)findViewById(R.id.btn2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pbar.setVisibility(View.VISIBLE);
                if(validate() && validateEmail() && validatePassword() && validateusn() &&validatecontact())
                {

                    final String usr_email=remail.getText().toString().trim();
                    String usr_pswd=rpassword.getText().toString().trim();
                    final String usr_nm=rname.getText().toString().trim();
                    final String usr_usn=rusn.getText().toString().trim();
                    final String usr_phn=rcontact.getText().toString().trim();
                    final String usr_addr=raddress.getText().toString().trim();
                    final String usr_gender=gender;
                    // myRef.push().setValue("Hello World");

                    //Toast.makeText(RegistrationActivity.this, "1", Toast.LENGTH_SHORT).show();

                    fbauth.createUserWithEmailAndPassword(usr_email, usr_pswd).addOnCompleteListener(RegistrationActivity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                user=fbauth.getCurrentUser();
                                Students student=new Students(usr_nm,usr_usn,usr_phn,usr_addr,usr_gender,usr_email,branch);
                                myRef.child(user.getUid()).setValue(student);
                                //StudentRoomDetails srd=new StudentRoomDetails("Rani Kumari","9878987678");
                                //myRef=database.getReference("StudentsRoomDetails");
                                //myRef.child(user.getUid()).setValue(srd);
                                //String id=myRef.getKey();
                                Toast.makeText(RegistrationActivity.this, "Registration successful ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                FirebaseAuthException e=(FirebaseAuthException)task.getException();
                                Toast.makeText(RegistrationActivity.this, "Registration failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                pbar.setVisibility(View.GONE);
                            }


                        }
                    });

                }
            }

        });
        //super.onBackPressed();
    }

    private boolean validateEmail() {
        String emailInput = remail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            remail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            remail.setError("Please enter a valid email address");
            return false;
        } else {
            remail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = rpassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            rpassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            rpassword.setError("Password too weak");
            return false;
        } else {
            rpassword.setError(null);
            return true;
        }
    }

    private boolean validateusn(){
        String val_usn=rusn.getText().toString();
        if(!USN_PATTERN.matcher(val_usn).matches()){
            rusn.setError("USN is incorrect");
            return false;
        }
        else{
            rusn.setError(null);
            return true;
        }
    }
    private boolean validatecontact(){

        String phone=rcontact.getText().toString();
        if(!PHONE_PATTERN.matcher(phone).matches()){
            rcontact.setError("Please enter correct phone number");
            return false;
        }
        else{
            rcontact.setError(null);
            return true;
        }
    }

    private Boolean validate()
    {
        boolean result =false;

        String name1=rname.getText().toString();
        String email1=remail.getText().toString();
        String password1=rpassword.getText().toString();
        String usn1=rusn.getText().toString();
        String contact1=rcontact.getText().toString();
        String address1=raddress.getText().toString();

        if(name1.isEmpty() || usn1.isEmpty() || contact1.isEmpty() || address1.isEmpty()||email1.isEmpty()||password1.isEmpty()||flag1==0||flag2==0) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
           pbar.setVisibility(View.GONE);
        }

        else
            result=true;
       // pbar.setVisibility(View.GONE);
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }
}

