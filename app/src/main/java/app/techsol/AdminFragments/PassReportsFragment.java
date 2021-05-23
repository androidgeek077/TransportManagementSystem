package app.techsol.AdminFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.techsol.Fragments.ViewPassFragment;
import app.techsol.Models.PassModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PassReportsFragment extends Fragment {

    ProgressBar mProgressBar;
    DatabaseReference CustomerReference;
    FirebaseAuth auth;
    //    CustomerProfileAdapter mProductAdapter;
    RecyclerView mCustomerRecycVw;
    String count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_users, container, false);
        auth = FirebaseAuth.getInstance();
        String userid = getArguments().getString("userid");
        CustomerReference = FirebaseDatabase.getInstance().getReference().child("Passes").child(userid);
        mCustomerRecycVw = view.findViewById(R.id.main_recycler_vw);
        mProgressBar = view.findViewById(R.id.mProgressBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
//
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<PassModel> options=new FirebaseRecyclerOptions.Builder<PassModel>()
                .setQuery(CustomerReference, PassModel.class)
                .build();

        final FirebaseRecyclerAdapter<PassModel, CustomersViewHolder> adapter=new FirebaseRecyclerAdapter<PassModel, CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CustomersViewHolder holder, final int position, @NonNull final PassModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.passHolderNameTV.setText("Muhammad Ali");

                holder.passPriceTV.setText("Balance: 250/-");
                holder.startDateTV.setText(model.getCurrentdate());
                holder.endDateTV.setText(model.getPassenddate());
                holder.busTypeTV.setText("Bus Type: "+model.getBustype());
                holder.mobileNoTV.setText("Mobile #: 03004626618");
                holder.passStatusTV.setText("Status: "+model.getpassstatus());
                holder.passStatusTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Update Status")
                                .setMessage("Are you sure you want to approve this Pass?")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        CustomerReference.child(model.getPassid()).child("passstatus").setValue("Approved");
                                        // Continue with delete operation
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
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

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pass_card_layout, viewGroup,false);
                CustomersViewHolder customersViewHolder=new CustomersViewHolder(view);
                mProgressBar.setVisibility(View.GONE);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends  RecyclerView.ViewHolder{


        TextView passHolderNameTV, passPriceTV, startDateTV, endDateTV, busTypeTV, mobileNoTV, passStatusTV;
        TextView postDescription;
        LinearLayout mItemCountLin;
        Button btnOrderNow;
        CardView cardView;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            passHolderNameTV = (TextView) itemView.findViewById(R.id.passHolderNameTV);
            passPriceTV = (TextView) itemView.findViewById(R.id.passPriceTV);
            startDateTV=itemView.findViewById(R.id.startDateTV);
            endDateTV=itemView.findViewById(R.id.endDateTV);
            busTypeTV=itemView.findViewById(R.id.busTypeTV);
            mobileNoTV=itemView.findViewById(R.id.mobileNoTV);
            passStatusTV=itemView.findViewById(R.id.passStatusTV);


        }
    }


}