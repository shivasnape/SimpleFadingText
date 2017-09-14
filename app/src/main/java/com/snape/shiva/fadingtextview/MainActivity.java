package com.snape.shiva.fadingtextview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Activity activity;
    Context context;
    TextView textView;
    Animation animationFade_In, animationFade_Out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        context = getApplicationContext();

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.window));
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //setting action bar with custom color defined in colors
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.actionbar)));
            actionBar.setTitle("Fading TextView");
        }

        textView = (TextView) findViewById(R.id.textview);

        animationFade_In = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFade_Out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
//        animation.setRepeatMode(Animation.INFINITE);

        //MAKE THE TEXTVIEW BLINKI CONTINOUSLY
        fadeText();

        // ALTERNATE METHOD
//        fadeText2();

    }

    private void fadeText2() {

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);
    }


    private final void fadeText() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in ms
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView.getVisibility() == View.VISIBLE) {
                            textView.setVisibility(View.INVISIBLE);
                            textView.startAnimation(animationFade_Out);

                        } else {
                            textView.setVisibility(View.VISIBLE);
                            textView.startAnimation(animationFade_In);
                        }
                        fadeText();
                    }
                });
            }
        }).start();
    }
}



