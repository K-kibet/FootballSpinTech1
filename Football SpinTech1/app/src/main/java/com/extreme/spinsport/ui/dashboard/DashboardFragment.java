package com.extreme.spinsport.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.extreme.spinsport.R;
import com.extreme.spinsport.adapters.NewsAdapter;
import com.extreme.spinsport.databinding.FragmentDashboardBinding;
import com.forms.sti.progresslitieigb.ProgressLoadingJIGB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<String> mImagelinkList = new ArrayList<>();
    private ArrayList<String> mDesLinkList = new ArrayList<>();
    RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = (RecyclerView) root.findViewById(R.id.act_recyclerview);
        //new Description().execute();

        //final TextView textView = binding.textDashboard;
       // dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressLoadingJIGB.setupLoading = (setup) ->  {
                setup.srcLottieJson = R.raw.sun; // Tour Source JSON Lottie
                setup.message = "Please Wait!";//  Center Message
                setup.timer = 0;   // Time of live for progress.
                setup.width = 200; // Optional
                setup.hight = 200; // Optional
            };
            ProgressLoadingJIGB.startLoading(getContext());
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                String url = "https://www.skysports.com/football/news";

                Document mBlogDocument = null;
                mBlogDocument = Jsoup.connect(url).get();

                Elements mElementDataSize = mBlogDocument.select("div[class=news-list__item news-list__item--show-thumb-bp30]");

                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++) {

                    Elements mElementImageLink = mBlogDocument.select("div[class=news-list__body]");

                    int mLinkSize = mElementImageLink.size();

                    for (int x = 0; x < mLinkSize; x++) {

                        Elements mImageLink = mElementImageLink.select("h4[class=news-list__headline");

                       /* for (Element image : mImageLink) {
                            String ImageLink = image.attr("data-src");
                            mImagelinkList.add(ImageLink);
                        }*/

                        String TestText = mImageLink.text();
                        mImagelinkList.add(TestText);



                    }


                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            NewsAdapter mDataAdapter = new NewsAdapter(getContext(), mImagelinkList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);

            ProgressLoadingJIGB.finishLoadingJIGB(getContext());




            // button.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //   public void onClick(View view) {
            //      Intent i = new Intent(this,DetailActivity.class);
            //      i.putExtra("betlink", mBetLink);
            //      startActivity(i);

            //  }
            // });


        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}