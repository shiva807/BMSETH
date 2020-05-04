package com.example.bmseth;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;
import java.util.zip.Inflater;

public class FragmentEditDetails extends Fragment {
    private FirebaseAuth mauth;
    DatabaseReference mref;
    private FirebaseUser user;
    private EditText name,branch,usn,contact,address;

    private static final Pattern PHONE_PATTERN= Pattern.compile("^" + "\\d{10}");
    private static final Pattern USN_PATTERN = Pattern.compile("^" + "1BM" + "\\d{2}" + "\\p{Upper}{2}" + "\\d{3}");


    int c=0,flag1=1,flag2=1;
    Button save;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_edit_details, container, false );
        final EditText name=(EditText)view.findViewById(R.id.editText16);
        final EditText usn=(EditText)view.findViewById(R.id.editText17) ;
        final EditText branch=(EditText)view.findViewById(R.id.editText18);
        final EditText contact=(EditText)view.findViewById(R.id.editText19);
        final EditText address=(EditText)view.findViewById(R.id.editText20);
        Button save=(Button)view.findViewById(R.id.button4);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(validateusn()&&validatecontact())
                //{
                    String name1=name.getText().toString();
                    String branch1=branch.getText().toString();
                    String address1=address.getText().toString();
                    String usn1=usn.getText().toString();
                    String contact1=contact.getText().toString();
                    if(name1.isEmpty()&&branch1.isEmpty()&&address1.isEmpty()&&usn1.isEmpty()&&contact1.isEmpty())
                {
                    flag1=flag2=0;
                }
                if(!usn1.isEmpty())
                {
                    if(!USN_PATTERN.matcher(usn1).matches())
                    {
                        usn.setError("Enter Valid USN");
                        flag1=0;
                    }
                    else
                    {
                        usn.setError(null);
                        c++;
                        flag1=1;

                    }

                }

                if(!contact1.isEmpty())
                {
                    if(!PHONE_PATTERN.matcher(contact1).matches())
                    {
                        contact.setError("Enter Valid Phone number");
                        flag2=0;
                    }
                    else
                    {
                        contact.setError(null);
                        c++;
                        flag2=1;
                    }

                }
                if(flag1==1&&flag2==1) {
                    if (!name1.isEmpty()) {
                        mref = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid()).child("name");
                        mref.setValue(name1);
                        c++;
                    }
                    if (!branch1.isEmpty()) {
                        mref = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid()).child("branch");
                        mref.setValue(branch1);
                        c++;
                    }
                    if (!address1.isEmpty()) {
                        mref = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid()).child("address");
                        mref.setValue(address1);
                        c++;
                    }
                    if (!usn1.isEmpty()) {
                        mref = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid()).child("usn");
                        mref.setValue(usn1);
                    }
                    if (!contact1.isEmpty()) {
                        mref = FirebaseDatabase.getInstance().getReference("Students").child(user.getUid()).child("phone");
                        mref.setValue(contact1);
                    }

                    Toast.makeText(getContext(), " Updation Successful !! Field(s) Updated: " + c, Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment FragmentDashboard = new FragmentDashboard();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, FragmentDashboard).addToBackStack(null).commit();
                    //savebuttonhandler();
                    //}
                }
                if(flag1==0&&flag2==0)
                {
                    Toast.makeText(getContext(), "No Updation Occcured!", Toast.LENGTH_LONG).show();

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment FragmentDashboard = new FragmentDashboard();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, FragmentDashboard).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }


  /*private Boolean validateusn()
    {
        String val_sun=usn.getText().toString();
        if(!val_sun.isEmpty())
        {
            if(!USN_PATTERN.matcher(val_sun).matches())
            {
                usn.setError("Enter Valid USN");
                return false;
            }
            else
            {
                usn.setError(null);
                c++;
                return  true;

            }

        }
        return true;
    }
    private Boolean validatecontact()
    {
        String val_phn=contact.getText().toString();
        if(!val_phn.isEmpty())
        {
            if(PHONE_PATTERN.matcher(val_phn).matches())
            {
                contact.setError("Enter Valid Phone number");
                return false;
            }
            else
            {
                contact.setError(null);
                c++;
                return true;
            }

        }
        return true;
    }
*/

}
