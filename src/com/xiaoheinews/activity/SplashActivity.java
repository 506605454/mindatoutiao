package com.xiaoheinews.activity;

import com.xiaoheinews.utill.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

 

public class SplashActivity extends Activity {


     private ImageView mImageView;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
		mImageView = (ImageView) findViewById(R.id.splash);
		
		Animation animation =AnimationUtils.loadAnimation(SplashActivity.this, R.anim.alpha_scale_translate);  
		mImageView.setAnimation(animation); 
		animation.setFillAfter(true); 

          
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
        	 
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, Constants.SPLASH_DISPLAY_LENGTH);
		
		
	}
}
