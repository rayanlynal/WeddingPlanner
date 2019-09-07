package com.weddingplanner.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.weddingplanner.R;
import com.weddingplanner.interfaces.WeddingRoleListener;
import com.weddingplanner.utils.AppUtils;

public class WeddingRolesDialog {

    private RadioButton rbBride, rbGroom, rbOthers, rbGuest;
    private MaterialButton btnDone;
    private RadioGroup rgRoles;
    private Context context;
    private Dialog dialog;
    private WeddingRoleListener weddingRoleListener;

    public WeddingRolesDialog(Context context, WeddingRoleListener weddingRoleListener) {
        this.context = context;
        this.weddingRoleListener = weddingRoleListener;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_roles);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout((int) (AppUtils.getScreenWidth((Activity) context) * 0.88), LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        btnDone = dialog.findViewById(R.id.btnDone);
        rgRoles = dialog.findViewById(R.id.rgRoles);

        rbBride = dialog.findViewById(R.id.rbBride);
        rbGroom = dialog.findViewById(R.id.rbGroom);
        rbOthers = dialog.findViewById(R.id.rbOthers);
        rbGuest = dialog.findViewById(R.id.rbGuest);

        btnDone.setOnClickListener(view -> {
            int selectedId = rgRoles.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton radioButton = dialog.findViewById(selectedId);
                weddingRoleListener.onWeddingRoleSelection(radioButton.getText().toString());
                hideDialog();
            } else {
                AppUtils.showToast(context, context.getString(R.string.err_please_select_wedding_role));
            }
        });
    }

    public void showDialog() {
        dialog.show();
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
            dialog.dismiss();
        }
    }
}