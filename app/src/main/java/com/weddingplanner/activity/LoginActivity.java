package com.weddingplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.weddingplanner.R;
import com.weddingplanner.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * author Ashav
 * on  18/08/2019.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etEmailAddress)
    TextInputEditText etEmailAddress;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvSignUpFree, R.id.tvSignInFB, R.id.tvSignInGoogle, R.id.btnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSignUpFree:
                startActivity(new Intent(this, SignupActivity.class));
                break;
            case R.id.tvSignInFB:
                break;
            case R.id.tvSignInGoogle:
                break;
            case R.id.btnLogin:
                break;
        }
    }
}
