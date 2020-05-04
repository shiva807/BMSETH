package com.example.bmseth;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FragmentDashboard extends Fragment {

   private FirebaseUser user;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mauth;
    private TextView demail,dusn,dgender,dbranch,dcontact,daddress,dname;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false );
        //user=mauth.getCurrentUser();
        dname=(TextView)view.findViewById(R.id.tv20) ;
        demail=(TextView) view.findViewById(R.id.textView13);
        dusn=(TextView) view.findViewById(R.id.textView14);
        //droom=(TextView)view.findViewById(R.id.textView15);
        dgender=(TextView) view.findViewById(R.id.textView16);
        dbranch=(TextView) view.findViewById(R.id.textView17);
        dcontact=(TextView) view.findViewById(R.id.textView18);
        daddress=(TextView) view.findViewById(R.id.textView19);






        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myRef=database.getReference("Students").child(user.getUid());
        demail.setText(user.getEmail());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nm=dataSnapshot.child("name").getValue(String.class);
                dname.setText(nm);
                String usn=dataSnapshot.child("usn").getValue(String.class);
                dusn.setText(usn);
                String gndr=dataSnapshot.child("gender").getValue(String.class);
                dgender.setText(gndr);
                String branch=dataSnapshot.child("branch").getValue(String.class);
                dbranch.setText(branch);
                String phn=dataSnapshot.child("phone").getValue(String.class);
                dcontact.setText(phn);
                String addr=dataSnapshot.child("address").getValue(String.class);
                daddress.setText(addr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

