package com.extreme.spinsport.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.extreme.spinsport.League;
import com.extreme.spinsport.R;
import com.extreme.spinsport.databinding.FragmentHomeBinding;
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

public class                HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button live;
    SharedPreferences pref;
    private Context context;
    AdmobInterAds admobInterAds;
    ApplovinInterAd applovinInterAd;

    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;

    @SuppressLint("MissingPermission")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        AppLovinSdk.getInstance( getContext() ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( getContext(), new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        FrameLayout nativeAdContainer = root.findViewById(R.id.my_template1);
        nativeAdLoader = new MaxNativeAdLoader("fc2d4d26a71ea01a",getContext());
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                Toast.makeText(getContext(), "Ad loaded", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(getContext(), maxError.toString(), Toast.LENGTH_SHORT).show();
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



      pref = getActivity().getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        //pref= PreferenceManager.getDefaultSharedPreferences(getContext());


        //Toast.makeText(getActivity(), pref.getString("adsoption",null), Toast.LENGTH_SHORT).show();

        if(pref.getString("adsoption",null).trim().matches("admob")){
            admobInterAds = new AdmobInterAds(this.getActivity());
        }
        else if(pref.getString("adsoption",null).trim().matches("applovin")){
            applovinInterAd = new ApplovinInterAd(this.getActivity());
        }

        AdLoader adLoader = new AdLoader.Builder(getContext(), getString(R.string.ad_native))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = root.findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());



        live = root.findViewById(R.id.live);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplication(), League.class);

                if(pref.getString("adsoption",null).trim().matches("admob")){
                    admobInterAds.MoveNextActivityAdmobAd(i,getActivity());
                }
                else if(pref.getString("adsoption",null).trim().matches("applovin")){
                    applovinInterAd.MoveNextActivityApplovinAd(i,getActivity());
                }

            }
        });

       /* final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}