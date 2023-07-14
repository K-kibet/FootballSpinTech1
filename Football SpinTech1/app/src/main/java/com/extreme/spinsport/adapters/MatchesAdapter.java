package com.extreme.spinsport.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.extreme.spinsport.Matches;
import com.extreme.spinsport.R;
import com.extreme.spinsport.Server;
import com.extreme.spinsport.data.Match;
import com.extreme.spinsport.utils.AdmobInterAds;
import com.extreme.spinsport.utils.ApplovinInterAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder> {


   Matches mMatches;

    List<Match> matchlist;
    private SharedPreferences pref;
    private Context context;
    AdmobInterAds admobInterAds;
    ApplovinInterAd applovinInterAd;



    public MatchesAdapter(Context context, ArrayList<Match> results) {
        this.context = context;
        this.matchlist = results;
        //this.mMatches = matches;

        pref = context.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);

        if(pref.getString("adsoption",null).trim().matches("admob")){
            admobInterAds = new AdmobInterAds((Activity) context);
        }
        else if(pref.getString("adsoption",null).trim().matches("applovin")){
            applovinInterAd = new ApplovinInterAd((Activity) context);
        }

    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Team1, Team2, status;
        private ImageView ImageT1, ImageT2;


        public MyViewHolder(View view) {
            super(view);
            Team1 = (TextView) view.findViewById(R.id.team1);
            Team2 = (TextView) view.findViewById(R.id.team2);
            status = (TextView) view.findViewById(R.id.status);
            ImageT1 = (ImageView) view.findViewById(R.id.logo1);
            ImageT2 = (ImageView) view.findViewById(R.id.logo2);



        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matches_element, parent, false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Match match = matchlist.get(position);
        holder.Team1.setText(match.getTeam1());
        holder.Team2.setText(match.getTeam2());
        holder.status.setText(match.getStatus());
        Picasso.get().load(match.getLogo1()).into(holder.ImageT1);
        Picasso.get().load(match.getLogo2()).into(holder.ImageT2);

        if(match.getStatus().matches("Live Now")){
            ObjectAnimator animator = ObjectAnimator.ofInt(holder.status, "backgroundColor", Color.WHITE, Color.RED);

            // duration of one color
            animator.setDuration(500);
            animator.setEvaluator(new ArgbEvaluator());

            // color will be show in reverse manner
            animator.setRepeatCount(Animation.REVERSE);


            // It will be repeated up to infinite time
            animator.setRepeatCount(Animation.INFINITE);
            animator.start();
        }
        else if(match.getStatus().matches("Starts Soon")){
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(view.getContext(), Server.class);
                i.putExtra("link",match.getLink());
                i.putExtra("linke",match.getLinke());

                if(match.getLink().isEmpty() && match.getLinke().isEmpty()) {
                  applovinInterAd.KAlert(view.getContext());
                }
                else{
                    if(pref.getString("adsoption",null).trim().matches("admob")){
                        admobInterAds.MoveNextActivityAdmobAd(i,context);
                    }
                    else if(pref.getString("adsoption",null).trim().matches("applovin")){
                        applovinInterAd.MoveNextActivityApplovinAd(i,context);
                    }
                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return matchlist.size();
    }
}
