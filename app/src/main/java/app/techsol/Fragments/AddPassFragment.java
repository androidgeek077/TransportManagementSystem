package app.techsol.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import app.techsol.Models.PassModel;
import app.techsol.transportmanagementsystem.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddPassFragment extends Fragment {
    EditText StartDateET, endDateET, currentDate, descriptionET;
    String startDateStr, endDateStr, currentDateStr, busTypeStr, descriptionStr, userIdStr, keyid;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener startDatePicker, endDatePicker;
    private Spinner busTypeSpnr;
    private Button AddPassBtn;

    ProgressBar mProgressBar;
    FirebaseAuth auth;
    DatabaseReference ref;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_pass, container, false);
        myCalendar = Calendar.getInstance();
        auth=FirebaseAuth.getInstance();

        AddPassBtn=view.findViewById(R.id.AddPassBtn);
        AddPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterWidget(view);
                getStrings();
                ref= FirebaseDatabase.getInstance().getReference("Passes").child(userIdStr);
                if (startDateStr.isEmpty()){
                    StartDateET.setError("Select Start Date");
                }else if (endDateStr.isEmpty()){
                    endDateET.setError("Select End Date");
                }else if (descriptionStr.isEmpty()){
                    descriptionET.setError("Enter Description");
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    keyid=ref.push().getKey();
                    PassModel passModel=new PassModel(keyid, userIdStr, currentDateStr,startDateStr, endDateStr, busTypeStr, descriptionStr,"");
                    ref.child(keyid).setValue(passModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Pass Generated Successfuly", Toast.LENGTH_SHORT).show();
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

        StartDateET = view.findViewById(R.id.startDate);
        endDateET = view.findViewById(R.id.endDate);
        currentDate = view.findViewById(R.id.currentDate);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        String dateString = sdf.format(date);
        currentDate.setText(dateString);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        StartDateET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), startDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                endDateET.setText(sdf.format(myCalendar.getTime()));
            }

        };

        endDateET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), endDatePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        StartDateET.setText(sdf.format(myCalendar.getTime()));
    }

    void RegisterWidget(View view){
        StartDateET=view.findViewById(R.id.startDate);
        endDateET=view.findViewById(R.id.endDate);
        currentDate=view.findViewById(R.id.currentDate);
        descriptionET=view.findViewById(R.id.descriptionET);
        busTypeSpnr=view.findViewById(R.id.busTypeSpnr);
        mProgressBar=view.findViewById(R.id.mProgressBar);
    }
    void getStrings(){
        startDateStr=StartDateET.getText().toString();
        endDateStr=endDateET.getText().toString();
        currentDateStr=currentDate.getText().toString();
        descriptionStr=descriptionET.getText().toString();
        busTypeStr = busTypeSpnr.getSelectedItem().toString();
        userIdStr=auth.getCurrentUser().getUid();
    }


}