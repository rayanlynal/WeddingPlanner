package com.weddingplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.weddingplanner.R;
import com.weddingplanner.base.BaseActivity;


/**
 * author Ashav
 * on  18/08/2019.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(new Intent(SplashActivity.this, LoginActivity.class)), 1000);
    }
}
