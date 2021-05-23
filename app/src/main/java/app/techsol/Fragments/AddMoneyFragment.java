package app.techsol.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.techsol.Models.MoneyModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddMoneyFragment extends Fragment {


    EditText AddMoneyET;
    String moneyStr;
    Button AddmoneyBtn;

    FirebaseAuth auth;
    DatabaseReference ref;
    ProgressBar mProgressBar;

    public AddMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_money, container, false);
        ref= FirebaseDatabase.getInstance().getReference("Money");
        auth=FirebaseAuth.getInstance();
        mProgressBar=view.findViewById(R.id.mProgressBar);
        AddMoneyET=view.findViewById(R.id.AddMoneyET);
        AddmoneyBtn=view.findViewById(R.id.AddmoneyBtn);
        AddmoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddMoneyET.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Add Money First", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    String userid=auth.getCurrentUser().getUid();
                    String id=ref.push().getKey();
                    String moneyStr=AddMoneyET.getText().toString();
                    MoneyModel moneyModel=new MoneyModel(id, userid, moneyStr);
                    ref.child(id).setValue(moneyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Money Added Successfully", Toast.LENGTH_SHORT).show();
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