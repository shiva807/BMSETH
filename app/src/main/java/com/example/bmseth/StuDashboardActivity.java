package com.example.bmseth;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

import static com.example.bmseth.R.string.open;

public class StuDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentRoomDetails.onFragmentbtnSelected,warden_details.onFragmenttxtSelected,warden_details.onFragmentemailSelected{

    DrawerLayout drl;
    ActionBarDrawerToggle abdt;
    Toolbar toolbar;
    NavigationView nv;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    /*
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mauth;
    TextView demail,dusn,dgender,dbranch,dcontact,daddress,dname;*/
    private FirebaseAuth mauth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_dashboard);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drl=findViewById(R.id.drawer);
        nv=findViewById(R.id.navigationView);
        nv.setNavigationItemSelectedListener(this);

        abdt=new ActionBarDrawerToggle(this, drl, toolbar, R.string.open , R.string.close );
        drl.addDrawerListener(abdt);
        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();

        /*
        dname=(TextView)findViewById(R.id.tv20) ;
        demail=(TextView) findViewById(R.id.textView13);
        dusn=(TextView) findViewById(R.id.textView14);
        dgender=(TextView) findViewById(R.id.textView16);
        dbranch=(TextView) findViewById(R.id.textView17);
        dcontact=(TextView) findViewById(R.id.textView18);
        daddress=(TextView) findViewById(R.id.textView19);
        myRef=database.getReference("Students").child(user.getUid());
        demail.setText(user.getEmail());*/
        mauth=FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();

        //load default fragment
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new FragmentDashboard());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drl.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.dashboard)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FragmentDashboard());
            fragmentTransaction.commit();

        }

        if(item.getItemId() == R.id.edit)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FragmentEditDetails());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.room)
        {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FragmentRoomDetails());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.warden)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new warden_details());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.about)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new FragmentAbout());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.logout)
        {
            logoutHandler();

        }
        return true;
    }

    @Override
    public void onButtonSelected() {
        startActivity(new Intent(StuDashboardActivity.this, BookRoomActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    @Override
    public void onContactSelected(String phn)
    {
        Intent i=new Intent(Intent.ACTION_DIAL);
        //int phn1= Integer.parseInt(phn);
        String phn1="tel:"+phn;
        i.setData(Uri.parse(phn1));
        startActivity(i);

    }

    @Override
    public void onEmailSelected(String email)
    {
            Intent intent=new Intent(Intent.ACTION_SEND);
            String recipients[]={email};

            intent.putExtra(Intent.EXTRA_EMAIL,recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Hostel Leave Application");
            intent.setType("message/rfc822");
            startActivity(intent.createChooser(intent,"Choose"));
    }
    public void logoutHandler(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(StuDashboardActivity.this);
        alertDialog2.setTitle("Log Out?");
        alertDialog2.setMessage("Are you sure you want to log out of the application?");
        alertDialog2.setIcon(R.drawable.ic_dialog_icon);
        alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mauth.signOut();
                startActivity(new Intent(StuDashboardActivity.this,LoginActivity.class));

            }
        });
        alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog2.show();
    }

    public void backButtonHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StuDashboardActivity.this);

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

