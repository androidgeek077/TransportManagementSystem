package app.techsol.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import app.techsol.Models.BusModel;
import app.techsol.Models.TicketModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ViewTicketFragment extends Fragment {


    private ArrayList<String> mCategories = new ArrayList<>();


    int countInt, incrementalCount;
    DatabaseReference BusesReference, myTicketRef;
    FirebaseAuth auth;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    String count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_ticket, container, false);
        auth = FirebaseAuth.getInstance();
        BusesReference = FirebaseDatabase.getInstance().getReference().child("Buses");
        myTicketRef = FirebaseDatabase.getInstance().getReference().child("myTicketRef").child(auth.getCurrentUser().getUid());
        mCustomerRecycVw = view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<BusModel> options = new FirebaseRecyclerOptions.Builder<BusModel>()
                .setQuery(BusesReference, BusModel.class)
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
                holder.bookTicketBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        String id=BusesReference.push().getKey();
                                        DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
                                        String currentDate=dateFormat.format(Calendar.getInstance().getTime());
                                        TicketModel model1=new TicketModel(id, auth.getCurrentUser().getUid(), model.getBusfare(), model.getBusname(), model.getBusno(), "Issued", currentDate);
                                        myTicketRef.child(id).setValue(model1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    Toast.makeText(getContext(), "Your Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();

                    }
                });


//                Glide.with(getActivity().getApplicationContext()).load(model.getImageUrl()).into(holder.postImage);


//                holder.mDelCustomerBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DatabaseReference key= getRef(position);
//                        key.removeValue();
//                    }
//                });


//                holder.mMinusBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        count=holder.mTotalCount.getText().toString();
//                        countInt=Integer.parseInt(count);
//                        incrementalCount=countInt--;
//
//                        holder.mTotalCount.setText(countInt+"");
//                    }
//                });


            }

            @NonNull
            @Override
            public CustomersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bus_layout, viewGroup, false);
                CustomersViewHolder customersViewHolder = new CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends RecyclerView.ViewHolder {


        TextView busRouteTV, passPriceTV, busNoTV, busTypeTV;

        Button trackLocationBtn, bookTicketBtn;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            busRouteTV = (TextView) itemView.findViewById(R.id.busRouteTV);
            passPriceTV = (TextView) itemView.findViewById(R.id.passPriceTV);
            busNoTV = itemView.findViewById(R.id.busNoTV);
            trackLocationBtn = itemView.findViewById(R.id.trackLocationBtn);
            busTypeTV = itemView.findViewById(R.id.busTypeTV);
            bookTicketBtn = itemView.findViewById(R.id.bookTicketBtn);
        }
    }
}