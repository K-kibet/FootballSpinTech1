package com.extreme.spinsport.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.developer.kalert.KAlertDialog;
import com.extreme.spinsport.R;
import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;


public class ApplovinInterAd {
    ProgressDialog delayer;
    Activity activity;
    boolean isFinishAct = false,isback=false;
    public static MaxInterstitialAd mInterstitial2Applovin;
    Class NextClass;
    public ApplovinInterAd(Activity act)
    {
        activity=act;
        mInterstitial2Applovin = new MaxInterstitialAd(activity.getString(R.string.applovin_inter), activity);
//        mInterstitial2Applovin.setListener(this);

        // Load the first ad
        mInterstitial2Applovin.loadAd();
        isFinishAct = false;
    }

    public ApplovinInterAd()
    {

    }


    public void MoveNextActivityApplovinAd(final Intent moveactivity, Context context)
    {
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

                if (mInterstitial2Applovin.isReady())
                {
                    mInterstitial2Applovin.showAd();

                    mInterstitial2Applovin.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(MaxAd ad) {

                        }

                        @Override
                        public void onAdDisplayed(MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(MaxAd ad) {
                            mInterstitial2Applovin.loadAd();
                            context.startActivity(moveactivity);

                        }

                        @Override
                        public void onAdClicked(MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(String adUnitId, MaxError error) {

                        }

                        @Override
                        public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                        }
                    });
                }
                else
                {
//            Intent intent = new Intent(activity, moveactivity);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(moveactivity);

                }
            }
        }, 4000);
//        NextClass = moveactivity;



    }


    public void KAlert(Context context){

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

                if (mInterstitial2Applovin.isReady())
                {
                    mInterstitial2Applovin.showAd();

                    mInterstitial2Applovin.setListener(new MaxAdListener() {
                        @Override
                        public void onAdLoaded(MaxAd ad) {

                        }

                        @Override
                        public void onAdDisplayed(MaxAd ad) {

                        }

                        @Override
                        public void onAdHidden(MaxAd ad) {
                            mInterstitial2Applovin.loadAd();
                            new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops!")
                                    .setContentText("Link will be available in few minutes, check later")
                                    .show();

                        }

                        @Override
                        public void onAdClicked(MaxAd ad) {

                        }

                        @Override
                        public void onAdLoadFailed(String adUnitId, MaxError error) {

                        }

                        @Override
                        public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                        }
                    });
                }
                else
                {
//            Intent intent = new Intent(activity, moveactivity);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Link will be available in few minutes, check later")
                            .show();

                }
            }
        }, 4000);

    }




    public void MoveBackActivityFbAd()
    {
//        delayer=new ProgressDialog(activity);
//        delayer.setMessage("Ad is Loading...");
//        delayer.show();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                delayer.dismiss();
//
//            }
//
//        },50);
        isback=true;
        if (mInterstitial2Applovin.isReady())
        {
            mInterstitial2Applovin.showAd();
        }
        else
        {
            activity.finish();
        }
    }
}
