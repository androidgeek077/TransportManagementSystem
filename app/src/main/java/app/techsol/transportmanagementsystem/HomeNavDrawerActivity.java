package app.techsol.transportmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import app.techsol.Fragments.AddMoneyFragment;
import app.techsol.Fragments.AddPassFragment;
import app.techsol.Fragments.ApplyPassFragment;
import app.techsol.Fragments.BookTicketFragment;
import app.techsol.Fragments.DashboardFragment;
import app.techsol.Fragments.ProfileFragment;
import app.techsol.Fragments.ViewPassFragment;
import app.techsol.Fragments.ViewTicketFragment;


public class HomeNavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int IncomingDataLength = 0;
    TextView textCartItemCount;
    public int mCartItemCount;
    DashboardFragment fragment;
    String cutomer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);
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
            FragmentLoadinManagerWithBackStack(new DashboardFragment());
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), "Logged Out", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return true;
        } else if (id==R.id.action_add_pass){
            FragmentLoadinManagerWithBackStack(new AddPassFragment());
        } else if (id==R.id.action_book_ticket){
            FragmentLoadinManagerWithBackStack(new BookTicketFragment());

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

        } else if (id == R.id.nav_add_money) {
            FragmentLoadinManagerWithBackStack(new AddMoneyFragment());
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

//    void getDahBoardData() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.catagories), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
////                    Toast.makeText(HomeNavDrawerActivity.this, response, Toast.LENGTH_LONG).show();
//                    //getting the data and saving them into separate arrays for using in the recycler view adapter
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray result = new JSONArray(jsonObject.getString("result"));
//                    IncomingDataLength = result.length();
//
////                    Toast.makeText(HomeNavDrawerActivity.this, ""+result, Toast.LENGTH_SHORT).show();
//
//                    CataoryRVData capitalTransactionRvData = new CataoryRVData(IncomingDataLength);
//
//                    for (int i = 0; i < IncomingDataLength; i++) {
//
//                        JSONObject QueryData = result.getJSONObject(i);
//                        capitalTransactionRvData.CatagotyName[i] = QueryData.getString("name");
//                        capitalTransactionRvData.CatagotyId[i] = QueryData.getString("id");
////                        capitalTransactionRvData.ProductName[i] = QueryData2.getString("name");
//                        if (i == IncomingDataLength - 1) {
//                            FragmentLoadinManagerWithBackStack(fragment);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    Toast.makeText(HomeNavDrawerActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(HomeNavDrawerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("api_key", "jau5YO2LoV190mvJ08Lx3tGCQ0B7QdEd");
//                return params;
//            }
//        };
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(HomeNavDrawerActivity.this);
//        requestQueue.add(stringRequest);
//    }

//    void getOrders() {
//        StringRequest stringRequest = new StringRequest(1, "http://gharpochmutton.com/App/UserOrders", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
////                    Toast.makeText(HomeNavDrawerActivity.this, response, Toast.LENGTH_LONG).show();
//                    //getting the data and saving them into separate arrays for using in the recycler view adapter
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray result = new JSONArray(jsonObject.getString("result"));
//                    IncomingDataLength = result.length();
//
////                    Toast.makeText(HomeNavDrawerActivity.this, ""+result, Toast.LENGTH_SHORT).show();
//
//                    OrderHistoryRecyclerVw capitalTransactionRvData = new OrderHistoryRecyclerVw(IncomingDataLength);
//
//                    for (int i = 0; i < IncomingDataLength; i++) {
//                        JSONObject QueryData = result.getJSONObject(i);
//                        capitalTransactionRvData.OrderId[i] = QueryData.getString("id");
//                        capitalTransactionRvData.OrderAmount[i] = QueryData.getString("amount");
//                        capitalTransactionRvData.OrderDate[i] = QueryData.getString("date");
//                    }
//
//                } catch (JSONException e) {
//                    Toast.makeText(HomeNavDrawerActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(HomeNavDrawerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("api_key", "jau5YO2LoV190mvJ08Lx3tGCQ0B7QdEd");
//                params.put("user_id", "2");
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(HomeNavDrawerActivity.this);
//        queue.add(stringRequest);
//    }


}
