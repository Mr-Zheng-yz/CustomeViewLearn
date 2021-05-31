package com.example.customviewprojdct.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.customviewprojdct.R;
import com.example.customviewprojdct.view.class1.DashboardView;
import com.example.customviewprojdct.view.class1.PieView;
import com.example.customviewprojdct.view.class1.TestView;

public class ClassOneActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ClassOneActivity.class));
    }

    FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_one);
        fl_content = findViewById(R.id.fl_content);
        findViewById(R.id.tvDash).setOnClickListener(this::onClick);
        findViewById(R.id.tvPie).setOnClickListener(this::onClick);
        findViewById(R.id.tvText).setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        fl_content.removeAllViews();
        View targetView = null;
        switch (v.getId()) {
            case R.id.tvText:
                targetView = new TestView(this);
                break;
            case R.id.tvDash:
                targetView = new DashboardView(this);
                break;
            case R.id.tvPie:
                targetView = new PieView(this);
                break;
        }
        if (targetView != null) {
            fl_content.addView(targetView);
        }
    }
}