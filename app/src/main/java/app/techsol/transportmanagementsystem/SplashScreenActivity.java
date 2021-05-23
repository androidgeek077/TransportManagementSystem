package app.techsol.transportmanagementsystem;

import android.content.Intent;
import android.os.Bundle;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import me.wangyuwei.particleview.ParticleView;


public class SplashScreenActivity extends AppCompatActivity {
    ParticleView particleView;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        particleView=(ParticleView)findViewById(R.id.particleView);
        image=(ImageView)findViewById(R.id.image);
        final Boolean session=getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("session",false);

        YoYo.with(Techniques.BounceIn).duration(8000).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                final float scale = getBaseContext().getResources().getDisplayMetrics().density;
                int pixels = (int) (150 * scale + 0.5f);
                image.requestLayout();
                image.getLayoutParams().width=pixels;
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

                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }

        },8000);


    }
}

