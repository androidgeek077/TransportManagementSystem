package app.techsol.ConductorFragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.techsol.transportmanagementsystem.R;

public class ScanQRActivity extends AppCompatActivity {

    Calendar myCalendar;
    private long date;
    private SimpleDateFormat sdf;
    private String dateString;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        myCalendar = Calendar.getInstance();
        date = System.currentTimeMillis();
        sdf = new SimpleDateFormat("MM/dd/yy");
        dateString = sdf.format(date);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
    }

    public void ScanButton(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
                snackbar = Snackbar.make(findViewById(android.R.id.content), "Scan not Successful", Snackbar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light ));
                snackbar.show();
            }else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date strDate = null;
                try {
                    strDate = sdf.parse(intentResult.getContents());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (new Date().after(strDate)) {
                    snackbar = Snackbar.make(findViewById(android.R.id.content), "This pass is expired", Snackbar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light ));
                    snackbar.show();
                } else {
                    snackbar = Snackbar.make(findViewById(android.R.id.content), "This pass is Valid", Snackbar.LENGTH_LONG).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    }).setActionTextColor(getResources().getColor(android.R.color.holo_green_dark ));
                    snackbar.show();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}