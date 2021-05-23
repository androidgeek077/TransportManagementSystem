package app.techsol.Fragments;

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
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import app.techsol.Models.PassModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPassFragment extends Fragment {

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
        View view= inflater.inflate(R.layout.fragment_view_pass, container, false);
        auth=FirebaseAuth.getInstance();
        CustomerReference= FirebaseDatabase.getInstance().getReference().child("Passes").child(auth.getCurrentUser().getUid());
        mCustomerRecycVw=view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
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

                holder.passPriceTV.setText("Balance: 250 rs");
                holder.startDateTV.setText(model.getCurrentdate());
                holder.endDateTV.setText(model.getPassenddate());
                holder.busTypeTV.setText("Bus Type: "+model.getBustype());
                holder.mobileNoTV.setText("Mobile #: 03004626618");
                holder.passStatusTV.setText("Status: Approved");


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