package com.extreme.spinsport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.extreme.spinsport.utils.AdmobInterAds;
import com.extreme.spinsport.utils.ApplovinInterAd;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;

public class Server extends AppCompatActivity {

    private String link, linke;
    CardView card1, card2;
    ApplovinInterAd applovinInterAd;
    AdmobInterAds admobInterAds;
    private SharedPreferences pref;

    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        pref = getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);


        applovinInterAd = new ApplovinInterAd((Activity) Server.this);


        AppLovinSdk.getInstance( Server.this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( Server.this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        FrameLayout nativeAdContainer = findViewById(R.id.my_template1);
        nativeAdLoader = new MaxNativeAdLoader("fc2d4d26a71ea01a",Server.this);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                Toast.makeText(Server.this, "Ad loaded", Toast.LENGTH_SHORT).show();
                if (nativeAd != null)
                {
                    nativeAdLoader.destroy(nativeAd);

                }


                nativeAd = maxAd;

                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(maxNativeAdView);
            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                super.onNativeAdLoadFailed(s, maxError);

                Toast.makeText(Server.this, maxError.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
            }

            @Override
            public void onNativeAdExpired(MaxAd maxAd) {
                super.onNativeAdExpired(maxAd);
            }
        });

        nativeAdLoader.loadAd();

        MobileAds.initialize(Server.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        AdLoader adLoader = new AdLoader.Builder(Server.this, getString(R.string.ad_native))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link =  extras.getString("link");
            linke = extras.getString("linke");
        }

        card1 = (CardView) findViewById(R.id.card1);
        card2 = (CardView) findViewById(R.id.card2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Server.this, Live.class);
                i.putExtra("link",link);

                if(pref.getString("adsoption",null).trim().matches("admob")){
                    admobInterAds.MoveNextActivityAdmobAd(i,view.getContext());
                }
                else if(pref.getString("adsoption",null).trim().matches("applovin")){
                    applovinInterAd.MoveNextActivityApplovinAd(i,view.getContext());
                }


            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(linke)));
            }
        });

    }
}