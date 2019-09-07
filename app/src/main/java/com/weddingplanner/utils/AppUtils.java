package com.weddingplanner.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Patterns;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.weddingplanner.base.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author ashav on 20/08/19.
 */

public final class AppUtils {

    public static final int NULL_INT = -1;
    public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";

    public static boolean hasInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void showToast(Context activity, String message) {
        Toast t = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        t.show();
    }

    public static int getPerPage() {
        return 5;
    }

    public static void showSnackbar(Activity activity, String message) {
        Snackbar s = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        /*View snackbarView = s.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(ResourcesCompat.getFont(activity, R.font.font_bold));
        textView.setTextColor(ContextCompat.getColor(activity, android.R.color.white));*/
        s.show();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
                        : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static boolean isEmptyText(TextView textView) {
        return TextUtils.isEmpty(getText(textView));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.findViewById(android.R.id.content).getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getScreenWidth(Activity activity) {
        Point p = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        return p.x;
    }

    public static int getScreenHeight(Activity activity) {
        Point p = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        return p.y;
    }

    public static int getHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    /*public static Dialog getProgressDialog(Activity activity) {
        Dialog progressDialog = new Dialog(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null, false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(view);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(activity.getResources().getDrawable(android.R.color.transparent));
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        return progressDialog;
    }*/

    public static AlertDialog createAlertDialog(Activity activity, String message,
                                                String positiveText, String negativeText,
                                                DialogInterface.OnClickListener mDialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setPositiveButton(positiveText, mDialogClick)
                .setNegativeButton(negativeText, mDialogClick)
                .setMessage(message)
                .setCancelable(true);
        return builder.create();
    }

    public static AlertDialog createAlertDialogException(Activity activity, String title, String message,
                                                         String positiveText, String negativeText,
                                                         DialogInterface.OnClickListener mDialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setPositiveButton(positiveText, mDialogClick)
                .setNegativeButton(negativeText, mDialogClick)
                .setMessage(message)
                .setCancelable(true);
        return builder.create();
    }

    public static AlertDialog createAlertDialog(Activity activity, String message,
                                                String positiveText,
                                                DialogInterface.OnClickListener mDialogClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setPositiveButton(positiveText, mDialogClick)
                .setMessage(message)
                .setCancelable(false);
        return builder.create();
    }

    public static String getUniqueToken(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static Animator getCircularAnimator(View view) {
        if (Build.VERSION.SDK_INT >= 21 && view.isAttachedToWindow()) {
            int cx = view.getWidth();
            int cy = view.getHeight();
            float finalRadius = (float) Math.hypot(cx, cy);
            return ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        }
        return null;
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public static String getFormatedDate(Long mills, String format) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        calendar.setTimeInMillis(mills);
        return formatter.format(calendar.getTime());
    }

    public static void startFromRightToLeft(Activity activity) {
        //activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        //activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void finishFromLeftToRight(Activity activity) {
        //activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        //activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    // Conversion bytes data to string
    public static String getHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * Shows the soft keyboard
     */
    public static void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    /**
     * IT WILL OPEN APP DETAIL SCREEN
     **/
    public static void appSettingsIntent(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        mContext.startActivity(intent);
    }

    /**
     * get date timestamp from date format
     */
    public static long getTimeStampFromDate(String strDate, String strDateFormat) {
        try {
            DateFormat formatter = new SimpleDateFormat(strDateFormat);
            Date date = formatter.parse(strDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * return date object from format
     */
    public static Date getDateObjFromFormat(String strDate, String strDateFormat) {
        try {
            DateFormat formatter = new SimpleDateFormat(strDateFormat);
            return formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setLocale(String language, Context context) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    /**
     * calculate the day difference between two dates
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * formats date from one format to another
     *
     * @param oldFormat
     * @param newFormat
     * @param dateString
     * @return
     */
    public static String convertDateFormatUtc(String oldFormat, String newFormat, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat(newFormat);
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormat(String oldFormat, String newFormat, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        try {
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat(newFormat);
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * used for logout from application
     */
    public static void logout(final BaseApplication application) {
        /*SPreferenceManager.getInstance(application).clearSession();
        Intent intent = new Intent(application, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
        new Handler(application.getMainLooper())
                .post(() -> Log.d("AppUtils", "logout: "));*/
    }

    public static void gotoPlaystore(Activity activity) {
        final String appPackageName = activity.getPackageName(); // package name of the app
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static String getVersionName(Activity activity) {
        /*try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return activity.getString(R.string.tv_version) + " " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }*/
        return "";
    }

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     *
     * @param requestingLocationUpdates The location updates state.
     */
    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     *
     * @param location The {@link Location}.
     */
    public static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    public static String getLocationTitle(Context context) {
        return "location_updated" + DateFormat.getDateTimeInstance().format(new Date());
    }

    public static boolean isOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}