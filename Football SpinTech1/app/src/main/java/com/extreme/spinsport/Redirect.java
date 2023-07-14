package com.extreme.spinsport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Redirect extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageButton btnClose;
    Button btnRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);

        initView();


    }

    private void initView() {
        btnClose = findViewById(R.id.btn_close);
        btnRedirect = findViewById(R.id.btn_redirecte);

        pref = getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        editor = pref.edit();

        btnClose.setOnClickListener(view -> finishAffinity());

        btnRedirect.setOnClickListener(view -> {
            if (pref.getString("redirect",null).matches("")) {
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.redirect_error), Snackbar.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pref.getString("redirect",null))));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}