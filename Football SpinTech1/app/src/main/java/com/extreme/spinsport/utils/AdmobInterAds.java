package com.extreme.spinsport.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.extreme.spinsport.R;
import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdmobInterAds {

    Activity activity;
    static InterstitialAd mInterstitialAd;
    public static int adCounter = 1;
    public static int adDisplayCounter = 7;
    boolean isFinishAct = false,isback=false;


    public AdmobInterAds(Activity act)
    {
        activity=act;

        MobileAds.initialize(act, initializationStatus -> {
LoadAdmobInter(act);
        });


    }

    public void LoadAdmobInter(Activity act){

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(act,act.getString(R.string.interstitial_ad), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });

    }

    public void MoveNextActivityAdmobAd(final Intent moveactivity, Context context)
    {
//        NextClass = moveactivity;
        isback=false;

        ProgressLoadingJIGB.setupLoading = (setup) ->  {
            setup.srcLottieJson = R.raw.sun; // Tour Source JSON Lottie
            setup.message = "Please Wait!";//  Center Message
            setup.timer = 0;   // Time of live for progress.
            setup.width = 200; // Optional
            setup.hight = 200; // Optional
        };
        ProgressLoadingJIGB.startLoading(context);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressLoadingJIGB.finishLoadingJIGB(context);

                if (mInterstitialAd != null) {
                   mInterstitialAd.show((Activity) context);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
//                    Log.d("TAG", "The ad was dismissed.");
                            LoadAdmobInter((Activity) context);
                            context.startActivity(moveactivity);

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
//                     Called when fullscreen content failed to show.
//                    Log.d("TAG", "The ad failed to show.");
                        }


                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
                            mInterstitialAd = null;
//                    Log.d("TAG", "The ad was shown.");
                        }
                    });
                } else {

                    //startGame();
                }

            }
        }, 4000);





    }
}
