package app.techsol.ConductorFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import app.techsol.Fragments.ViewTicketFragment;
import app.techsol.Models.BusModel;
import app.techsol.Models.TicketModel;
import app.techsol.transportmanagementsystem.MapsActivity;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ViewBusesFragment extends Fragment {

    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories=new ArrayList<>();


    int countInt, incrementalCount;
    DatabaseReference CustomerReference;
    FirebaseAuth auth;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    String count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_buses, container, false);
        auth=FirebaseAuth.getInstance();
        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Buses");
        mCustomerRecycVw=view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<BusModel> options = new FirebaseRecyclerOptions.Builder<BusModel>()
                .setQuery(CustomerReference, BusModel.class)
                .build();

        final FirebaseRecyclerAdapter<BusModel, CustomersViewHolder> adapter = new FirebaseRecyclerAdapter<BusModel, CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CustomersViewHolder holder, final int position, @NonNull final BusModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width
                holder.busRouteTV.setText(model.getBusname());
                holder.passPriceTV.setText("Fare: " + model.getBusfare());
                holder.busNoTV.setText("Bus No. " + model.getBusno());
                holder.busTypeTV.setText("Bus Type: " + model.getBustype());
                holder.selectBusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), MapsActivity.class);
                        intent.putExtra("bus_id", model.getBusid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_bus_layout, viewGroup, false);
                CustomersViewHolder customersViewHolder = new CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends RecyclerView.ViewHolder {


        TextView busRouteTV, passPriceTV, busNoTV, busTypeTV;

        Button trackLocationBtn, selectBusBtn;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            busRouteTV = (TextView) itemView.findViewById(R.id.busRouteTV);
            passPriceTV = (TextView) itemView.findViewById(R.id.passPriceTV);
            busNoTV = itemView.findViewById(R.id.busNoTV);
            trackLocationBtn = itemView.findViewById(R.id.trackLocationBtn);
            busTypeTV = itemView.findViewById(R.id.busTypeTV);
            selectBusBtn = itemView.findViewById(R.id.selectBusBtn);
        }
    }
}