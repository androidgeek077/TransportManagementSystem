package app.techsol.transportmanagementsystem;

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

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import app.techsol.AdminFragments.AddBusFragment;
import app.techsol.AdminFragments.AddConductorFragment;
import app.techsol.AdminFragments.ManageUsersFragment;
import app.techsol.AdminFragments.PassReportsFragment;
import app.techsol.Fragments.AddMoneyFragment;
import app.techsol.Fragments.AddPassFragment;
import app.techsol.Fragments.BookTicketFragment;
import app.techsol.Fragments.DashboardFragment;
import app.techsol.Fragments.ViewPassFragment;

public class AdminNavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DashboardFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        getAllProducts();getDahBoardData();
////        getOrders();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment = new DashboardFragment();
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
        getMenuInflater().inflate(R.menu.admin_menu_bar, menu);


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
            FragmentLoadinManagerWithBackStack(new DashboardFragment());
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Logged Out", Snackbar.LENGTH_SHORT);
            snackbar.show();
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
        } else if (id == R.id.nav_add_conductor) {
            FragmentLoadinManagerWithBackStack(new AddConductorFragment());
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