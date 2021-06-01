package app.techsol.transportmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import app.techsol.Fragments.AddPassFragment;
import app.techsol.AdminFragments.DashboardFragment;
import app.techsol.Fragments.ProfileFragment;
import app.techsol.Fragments.ViewPassFragment;
import app.techsol.Fragments.ViewTicketFragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int IncomingDataLength = 0;
    TextView textCartItemCount;
    public int mCartItemCount;
    DashboardFragment fragment;
    String cutomer_id;
    FirebaseAuth auth;
    String userName, userEmail;
    TextView headerUserEmail,headerUserName;
    private String userProfileURl;
    private CircleImageView headerProfileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);
        userName=getIntent().getStringExtra("name");
        userEmail=getIntent().getStringExtra("email");
        userProfileURl=getIntent().getStringExtra("profileurl");

        Toast.makeText(this, userName+","+userEmail+","+userProfileURl, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
//        getAllProducts();getDahBoardData();
////        getOrders();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerUserEmail = headerView.findViewById(R.id.headerUserEmail);
        headerUserName = headerView.findViewById(R.id.headerUserName);
        headerProfileIV = headerView.findViewById(R.id.headerProfileIV);
        Glide.with(getApplicationContext())
                .load( userProfileURl)
                .into(headerProfileIV);

        headerUserEmail.setText(userEmail);
        headerUserName.setText(userName);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment = new DashboardFragment();
        FragmentLoadinManagerWithBackStack(new ProfileFragment());



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
        final MenuItem menuItem = menu.findItem(R.id.loginBtn);


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
            startActivity(new Intent(HomeNavDrawerActivity.this, MainActivity.class));

            return true;

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
            FragmentLoadinManagerWithBackStack(new ProfileFragment());
            // Handle the camera action

        } else if (id == R.id.nav_add_pass) {
            FragmentLoadinManagerWithBackStack(new AddPassFragment());
        } else if (id == R.id.nav_busspass) {
            FragmentLoadinManagerWithBackStack(new ViewPassFragment());
        } else if (id == R.id.nav_tickets) {
            FragmentLoadinManagerWithBackStack(new ViewTicketFragment());
        }else if (id == R.id.nav_profile) {
            FragmentLoadinManagerWithBackStack(new ProfileFragment());
        } else if (id == R.id.nav_signn) {
            startActivity(new Intent(HomeNavDrawerActivity.this, MainActivity.class));
        }else if (id == R.id.nav_signup) {
            startActivity(new Intent(HomeNavDrawerActivity.this, SignupActivity.class));
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



}
