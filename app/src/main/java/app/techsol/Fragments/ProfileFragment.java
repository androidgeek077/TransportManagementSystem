package app.techsol.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.techsol.Models.TicketModel;
import app.techsol.transportmanagementsystem.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TextView userBalanceTV,userPhoneTV, userNameTV;
    private String Username, UserPhone, UserEmail, usercnic;
    ArrayList mLocationList, mLongList;
    DatabaseReference TicketsRef;
    RecyclerView mCustomerRecycVw;

    FirebaseAuth auth;

    DatabaseReference UserRef;
    DatabaseReference databaseReference;



    private ImageView UserImage;
    private String userBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");

        UserRef= FirebaseDatabase.getInstance().getReference().child("Users");

        userNameTV=view.findViewById(R.id.userNameTV);
        userBalanceTV=view.findViewById(R.id.userBalanceTV);
        userPhoneTV=view.findViewById(R.id.userPhoneTV);
        getStudentInfo();

        auth=FirebaseAuth.getInstance();
        TicketsRef= FirebaseDatabase.getInstance().getReference().child("myTicketRef").child(auth.getCurrentUser().getUid());
        mCustomerRecycVw=view.findViewById(R.id.main_recycler_vw);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mCustomerRecycVw.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<TicketModel> options=new FirebaseRecyclerOptions.Builder<TicketModel>()
                .setQuery(TicketsRef, TicketModel.class)
                .build();

        final FirebaseRecyclerAdapter<TicketModel, CustomersViewHolder> adapter=new FirebaseRecyclerAdapter<TicketModel, CustomersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CustomersViewHolder holder, final int position, @NonNull final TicketModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.busRouteTV.setText(model.getRoute());

                holder.ticketPriceTV.setText("Rs. "+model.getTicketfare());
                holder.busNoTV.setText("Bus #: "+model.getBusNo());
                holder.busTypeTV.setText("Bus Type: Daewoo");
//                holder.busTimeTV.setText(model);
//                holder.ticketExpiryTV.setText("Status: Approved");


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

                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ticket_layout, viewGroup,false);
                CustomersViewHolder customersViewHolder=new CustomersViewHolder(view);
                return customersViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();

    }


    public static class CustomersViewHolder extends  RecyclerView.ViewHolder {


        TextView busRouteTV, ticketPriceTV, busNoTV, endDateTV, busTypeTV, busTimeTV, ticketExpiryTV;
        

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            busRouteTV = (TextView) itemView.findViewById(R.id.busRouteTV);
            ticketPriceTV = (TextView) itemView.findViewById(R.id.ticketPriceTV);
            busNoTV = itemView.findViewById(R.id.busNoTV);
            busTypeTV = itemView.findViewById(R.id.busTypeTV);
            busTimeTV = itemView.findViewById(R.id.busTimeTV);
            ticketExpiryTV = itemView.findViewById(R.id.ticketExpiryTV);


        }
    }

    private void getStudentInfo() {
//        mLocationList = new ArrayList<>();
//        mLongList = new ArrayList<>();
        UserRef.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Username = dataSnapshot.child("username").getValue().toString();
                UserPhone = dataSnapshot.child("usermobileno").getValue().toString();
                userBalance = dataSnapshot.child("userbalance").getValue().toString();

                userNameTV.setText(Username);
                userBalanceTV.setText("Rs. "+userBalance);
                userPhoneTV.setText(UserPhone);
//                Glide.with(getContext())
//                        .load( UserImgUrl)
//                        .into(mProfilePic);
//                Glide.with(getContext())
//                        .load( UserImgUrl)
//                        .into(mBgPic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    public void FragmentLoadinManagerNoBackStack(Fragment fragment) {
//
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//
//    }
}