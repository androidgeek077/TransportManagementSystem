package app.techsol.AdminFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.techsol.Models.BusModel;
import app.techsol.Models.MoneyModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddBusFragment extends Fragment {


    EditText AddBusNoET, RouteFromET, RouteToET, BusPriceET;
    Spinner busTypeSpnr;
    String busNoStr;
    Button AddBusBtn;

    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressBar mProgressBar;
    private String routeToStr, routeFromStr, busTypeStr, busFareStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_bus, container, false);
        ref= FirebaseDatabase.getInstance().getReference("Buses");
        auth=FirebaseAuth.getInstance();
        mProgressBar=view.findViewById(R.id.mProgressBar);
        busTypeSpnr=view.findViewById(R.id.busTypeSpnr);
        AddBusNoET=view.findViewById(R.id.AddBusNoET);
        BusPriceET=view.findViewById(R.id.BusPriceET);
        RouteFromET=view.findViewById(R.id.RouteFromET);
        RouteToET=view.findViewById(R.id.RouteToET);
        AddBusBtn=view.findViewById(R.id.AddBusBtn);
        AddBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddBusNoET.getText().toString().isEmpty()){
                    AddBusNoET.setError("Please Add Bus No First");
                }else if (RouteFromET.getText().toString().isEmpty()){
                    RouteFromET.setError("Please Add Departure First");
                }else if (BusPriceET.getText().toString().isEmpty()){
                    RouteFromET.setError("Please Add Bus Fare First");
                }else if (RouteToET.getText().toString().isEmpty()){
                    RouteToET.setError("Please Add Arrival First");
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    String id=ref.push().getKey();
                    busNoStr=AddBusNoET.getText().toString();
                    routeToStr=RouteToET.getText().toString();
                    routeFromStr=RouteFromET.getText().toString();
                    busFareStr=BusPriceET.getText().toString();
                    busTypeStr = busTypeSpnr.getSelectedItem().toString();
                    BusModel model=new BusModel(id, routeToStr+" To "+routeFromStr, busNoStr, busTypeStr, busFareStr);
                    ref.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Bus Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}