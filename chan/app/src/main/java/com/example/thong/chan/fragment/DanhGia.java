package com.example.thong.chan.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DanhGia extends Fragment{
    // Insert your Application Title
    private final static String TITLE = "Ngay Bây Giờ";

    // Insert your Application Package Name
    private final static String PACKAGE_NAME = "com.androidbegin.rateustutorial";

    // Day until the Rate Us Dialog Prompt(Default 2 Days)
    private final static int DAYS_UNTIL_PROMPT = 0;

    // App launches until Rate Us Dialog Prompt(Default 5 Launches)
    private final static int LAUNCHES_UNTIL_PROMPT = 2;

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("rateus", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch
                    + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext,
                                      final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);
        // Set Dialog Title
        dialog.setTitle("Đánh Giá " + TITLE);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(mContext);
        tv.setText("Nếu Bạn Thấy Ứng Dụng Chất Lượng, " + TITLE
                + ", \n" +
                "hãy cho chúng tôi một số ngôi sao và nhận xét");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);

        // First Button
        Button b1 = new Button(mContext);
        b1.setText("Đánh Giá " + TITLE);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("market://details?id=" + PACKAGE_NAME)));
                dialog.dismiss();
            }
        });
        ll.addView(b1);

        // Second Button
        Button b2 = new Button(mContext);
        b2.setText("Nhắc Lại Sau");
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);

        // Third Button
        Button b3 = new Button(mContext);
        b3.setText("Không Bao Giờ");
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);

        dialog.setContentView(ll);

        // Show Dialog
        dialog.show();
    }
}
