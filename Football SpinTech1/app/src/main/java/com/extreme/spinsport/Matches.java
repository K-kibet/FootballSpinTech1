package com.extreme.spinsport;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.extreme.spinsport.adapters.MatchesAdapter;
import com.extreme.spinsport.data.Match;
import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Matches extends AppCompatActivity {

    FirebaseFirestore db;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    int Pos;


    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Pos = extras.getInt("pos");

        }

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.act_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProgressLoadingJIGB.setupLoading = (setup) ->  {
            setup.srcLottieJson = R.raw.sun; // Tour Source JSON Lottie
            setup.message = "Please Wait!";//  Center Message
            setup.timer = 0;   // Time of live for progress.
            setup.width = 200; // Optional
            setup.hight = 200; // Optional
        };
        ProgressLoadingJIGB.startLoading(this);

        db.collection("Matches").whereEqualTo("Pos",extras.getInt("pos")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ProgressLoadingJIGB.finishLoadingJIGB(Matches.this);
                List<Match> results = task.getResult().toObjects(Match.class);
                ArrayList<Match> completedList = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    completedList.add(results.get(i));
                    adapter = new MatchesAdapter(Matches.this, completedList);
                }


                recyclerView.setAdapter(adapter);
            }
        });

        AppLovinSdk.getInstance( Matches.this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( Matches.this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        FrameLayout nativeAdContainer = findViewById(R.id.my_template1);
        nativeAdLoader = new MaxNativeAdLoader("fc2d4d26a71ea01a",Matches.this);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                Toast.makeText(Matches.this, "Ad loaded", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(Matches.this, maxError.toString(), Toast.LENGTH_SHORT).show();
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

        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.ad_native))
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
    }
}