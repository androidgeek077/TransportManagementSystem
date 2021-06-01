package app.techsol.transportmanagementsystem;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.wangyuwei.particleview.ParticleView;


public class SplashScreenActivity extends AppCompatActivity {
    ParticleView particleView;
    ImageView image;
    FirebaseAuth auth;
    private DatabaseReference UserRef;
    private String userTpye="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        particleView = (ParticleView) findViewById(R.id.particleView);
        image = (ImageView) findViewById(R.id.image);
        if (auth.getCurrentUser() != null) {
            getUserTpye();
        }
        final Boolean session = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("session", false);

        YoYo.with(Techniques.BounceIn).duration(8000).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                final float scale = getBaseContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (150 * scale + 0.5f);
                image.requestLayout();
                image.getLayoutParams().width = pixels;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).playOn(image);
        particleView.startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    if (userTpye.equals("user")) {
                        startActivity(new Intent(getBaseContext(), HomeNavDrawerActivity.class));
                    } else if (userTpye.equals("admin")) {
                        startActivity(new Intent(getBaseContext(), AdminNavDrawerActivity.class));
                    } else if (userTpye.equals("conductor")) {
                        startActivity(new Intent(getBaseContext(), ConductorBottomNavActivity.class));
                    } else if (userTpye.isEmpty()){
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

                }
            }

        }, 8000);


    }

    private String getUserTpye() {
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
//        mLocationList = new ArrayList<>();
//        mLongList = new ArrayList<>();
        UserRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userTpye = dataSnapshot.child("usertype").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return userTpye;
    }

}

