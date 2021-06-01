package app.techsol.AdminFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.techsol.Models.UserModel;
import app.techsol.transportmanagementsystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    PieChart pieChart;
    BarChart barChart;
    private DatabaseReference UserRef;
    private FirebaseAuth auth;
    List<BarEntry> entryList;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        pieChart = view.findViewById(R.id.piChartOverview);
        barChart = view.findViewById(R.id.barChartOverview);
        setUpPieChart(100, 200);
        setUpBarChart();
        return view;
    }

    private void setUpPieChart(int approved, int nonApproved) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(getContext().getResources().getColor(R.color.colorPrimaryDark));

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);


        List<PieEntry> pieEntriesWin = new ArrayList<>();
        // for approved
        pieEntriesWin.add(new PieEntry(approved));
        // for loses
        pieEntriesWin.add(new PieEntry(nonApproved));

        PieDataSet dataSetWin = new PieDataSet(pieEntriesWin, "Approved Passes/NonApproved Passes");


        int[] colors = new int[]{getContext().getResources().getColor(R.color.colorWin), getContext().getResources().getColor(R.color.colorLose)};

        //dataSetWin.setColors(new int[]{Color.GREEN,Color.RED});
        dataSetWin.setColors(colors);
        PieData data = new PieData(dataSetWin);


        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
    }

    private void setUpBarChart() {
        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);

        entryList = new ArrayList<>();
        getCurrentBalance();

//        entryList.add(new BarEntry(1, 1, "Toto"));
//        entryList.add(new BarEntry(2, 4, "Tata"));
//        //entryList.add(new BarEntry(3,7,getContext().getResources().getDrawable(R.drawable.ic_groups)));
//        entryList.add(new BarEntry(3, 7, "Tetu"));
//        entryList.add(new BarEntry(4, 2, "Taty"));
//        entryList.add(new BarEntry(5, 4, "Tutu"));
//        entryList.add(new BarEntry(6, 1, "Tete"));
//        entryList.add(new BarEntry(7, 4, "Tktk"));


    }

    private void getCurrentBalance() {

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int x=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    x=x+1;
                    UserModel model = postSnapshot.getValue(UserModel.class);
                    entryList.add(new BarEntry(x, Integer.parseInt(model.getBalance()), model.getUsername()));
                }
                BarDataSet dataSet = new BarDataSet(entryList, "User Balance");
                // dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                BarData data = new BarData(dataSet);


                barChart.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
