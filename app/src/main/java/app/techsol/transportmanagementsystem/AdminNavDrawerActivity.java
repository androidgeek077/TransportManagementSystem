package app.techsol.transportmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.techsol.AdminFragments.AddBusFragment;
import app.techsol.AdminFragments.ManageUsersFragment;
import app.techsol.AdminFragments.PassReportsFragment;
import app.techsol.AdminFragments.DashboardFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminNavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DashboardFragment fragment;
    private FirebaseAuth auth;
    String userName, userEmail;
    private DatabaseReference UserRef;
    TextView headerUserEmail,headerUserName;
    private String userProfileURl;
    private CircleImageView headerProfileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav_drawer);
        auth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        gerUserInfo();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment = new DashboardFragment();
        FragmentLoadinManagerWithBackStack(fragment);
        View headerView = navigationView.getHeaderView(0);
        headerUserEmail = headerView.findViewById(R.id.headerUserEmail);
        headerUserName = headerView.findViewById(R.id.headerUserName);
        headerProfileIV = headerView.findViewById(R.id.headerProfileIV);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav_drawer, menu);


//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HomeNavDrawerActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
//            }
//        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(AdminNavDrawerActivity.this, MainActivity.class));

            return true;
        } else if (id==R.id.nav_pass_report){
            FragmentLoadinManagerWithBackStack(new PassReportsFragment());
        } else if (id==R.id.nav_add_bus){
            FragmentLoadinManagerWithBackStack(new AddBusFragment());

        }
//        else if (id == R.id.optCart) {
//            if (manager.userIsLogged()) {
//                startActivity(new Intent(getBaseContext(), CartActivity.class));
//            } else {
//                Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Please Login First", Snackbar.LENGTH_INDEFINITE);
//                snackbar.setAction("Login", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        FragmentLoadinManagerWithBackStack(new SigninFragment());
//                    }
//                });
//                snackbar.show();
//            }
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            FragmentLoadinManagerWithBackStack(new DashboardFragment());
            // Handle the camera action

        } else if (id==R.id.nav_pass_report){
            FragmentLoadinManagerWithBackStack(new ManageUsersFragment());
        } else if (id==R.id.nav_add_bus){
            FragmentLoadinManagerWithBackStack(new AddBusFragment());
        } else if (id == R.id.nav_signin) {
            startActivity( new Intent(getApplicationContext(), MainActivity.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();


    }

    private void gerUserInfo() {

        UserRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userProfileURl = dataSnapshot.child("profilepicurl").getValue().toString();
                userEmail = dataSnapshot.child("useremail").getValue().toString();
                userName = dataSnapshot.child("username").getValue().toString();
                Glide.with(getApplicationContext())
                        .load( userProfileURl)
                        .into(headerProfileIV);

                headerUserEmail.setText(userEmail);
                headerUserName.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}