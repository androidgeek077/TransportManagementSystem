package app.techsol.Fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.WindowManager;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;

import app.techsol.Models.PassModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ViewPassFragment extends Fragment {

    private ViewFlipper simpleViewFlipper;
    private ArrayList<String> mCategories=new ArrayList<>();
    private Dialog dialog;


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

                holder.passHolderNameTV.setText(model.getUserid());

                holder.passPriceTV.setText("Rs."+model.getpassprice());
                holder.startDateTV.setText(model.getCurrentdate());
                holder.endDateTV.setText(model.getPassenddate());
                holder.busTypeTV.setText("Bus Type: "+model.getBustype());
                holder.mobileNoTV.setText("Booking Date:"+model.getCurrentdate());
                holder.passStatusTV.setText("Status: "+model.getpassstatus());
                holder.generateQRBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(model.getPassenddate());
                    }
                });



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

        Button generateQRBtn;

        public CustomersViewHolder(@NonNull View itemView) {
            super(itemView);

            passHolderNameTV = (TextView) itemView.findViewById(R.id.passHolderNameTV);
            passPriceTV = (TextView) itemView.findViewById(R.id.passPriceTV);
            startDateTV=itemView.findViewById(R.id.startDateTV);
            endDateTV=itemView.findViewById(R.id.endDateTV);
            busTypeTV=itemView.findViewById(R.id.busTypeTV);
            mobileNoTV=itemView.findViewById(R.id.mobileNoTV);
            passStatusTV=itemView.findViewById(R.id.passStatusTV);
            generateQRBtn=itemView.findViewById(R.id.generateQRBtn);


        }
    }

    private void openDialog(String passExpiry) {
        dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_box_layout);
        dialog.setTitle("QR Code");
        dialog.getWindow().getAttributes().windowAnimations = R.style.Theme_AppCompat_DayNight_Dialog_Alert;
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        dialog.setCancelable(true);
        ImageView sendRequestIV = dialog.findViewById(R.id.QRCodeGenerated);
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(passExpiry, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            sendRequestIV.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        dialog.show();
    }

}