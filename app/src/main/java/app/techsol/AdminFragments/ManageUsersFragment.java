package app.techsol.AdminFragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.techsol.Models.UserModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ManageUsersFragment extends Fragment {

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
        CustomerReference = FirebaseDatabase.getInstance().getReference().child("Users");
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
        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(CustomerReference, UserModel.class)
                .build();

        final FirebaseRecyclerAdapter<UserModel, UserViewHolder> adapter = new FirebaseRecyclerAdapter<UserModel, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final UserViewHolder holder, final int position, @NonNull final UserModel model) {


                DisplayMetrics displaymetrics = new DisplayMetrics();
                (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width

                holder.userNameTV.setText(model.getUsername());

                holder.userGenderTV.setText(model.getUsergender());
                holder.userAddressTV.setText(model.getUseraddress());
                holder.userCnicTV.setText(model.getUsercnic());
                holder.mobileNoTV.setText(model.getUsermobileno());
                holder.userEmailTV.setText(model.getUseremail());
                holder.openPassesIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userid", model.getUid());
                        PassReportsFragment fragment=new PassReportsFragment();
                        fragment.setArguments(bundle);
                        FragmentLoadinManagerWithBackStack(fragment);
                    }
                });


            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_layout, viewGroup, false);
                UserViewHolder UserViewHolder = new UserViewHolder(view);
                mProgressBar.setVisibility(View.GONE);

                return UserViewHolder;
            }
        };

        mCustomerRecycVw.setAdapter(adapter);
        adapter.startListening();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {


        TextView userNameTV, userGenderTV, userAddressTV, userCnicTV, mobileNoTV, userEmailTV;
        ImageView openPassesIV;
        LinearLayout mItemCountLin;
        Button btnOrderNow;
        CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTV = (TextView) itemView.findViewById(R.id.userNameTV);
            userGenderTV = itemView.findViewById(R.id.userGenderTV);
            userAddressTV = itemView.findViewById(R.id.userAddressTV);
            userCnicTV = itemView.findViewById(R.id.userCnicTV);
            mobileNoTV = itemView.findViewById(R.id.mobileNoTV);
            userEmailTV = itemView.findViewById(R.id.userEmailTV);
            openPassesIV = itemView.findViewById(R.id.openPassesIV);


        }
    }

    public void FragmentLoadinManagerWithBackStack(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();


    }
}