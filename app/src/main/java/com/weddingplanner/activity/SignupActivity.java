package com.weddingplanner.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.weddingplanner.R;
import com.weddingplanner.base.BaseActivity;
import com.weddingplanner.customdialogs.WeddingRolesDialog;
import com.weddingplanner.interfaces.WeddingRoleListener;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author Ashav
 * on  18/08/2019.
 */
public class SignupActivity extends BaseActivity implements WeddingRoleListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvSignInFB)
    AppCompatTextView tvSignInFB;
    @BindView(R.id.tvSignInGoogle)
    AppCompatTextView tvSignInGoogle;
    @BindView(R.id.etFirstLastName)
    TextInputEditText etFirstLastName;
    @BindView(R.id.etEmailAddress)
    TextInputEditText etEmailAddress;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etChooseRole)
    TextInputEditText etChooseRole;
    @BindView(R.id.etWeddingDate)
    TextInputEditText etWeddingDate;
    @BindView(R.id.etCityTown)
    TextInputEditText etCityTown;
    @BindView(R.id.tvTerms)
    AppCompatTextView tvTerms;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @OnClick({R.id.etChooseRole, R.id.etWeddingDate, R.id.tvTerms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etChooseRole:
                WeddingRolesDialog weddingRolesDialog = new WeddingRolesDialog(this, this);
                weddingRolesDialog.showDialog();
                break;
            case R.id.etWeddingDate:
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, monthOfYear, dayOfMonth)
                        -> etWeddingDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.tvTerms:
                break;
        }
    }

    @Override
    public void onWeddingRoleSelection(String role) {
        etChooseRole.setText(role);
    }
}