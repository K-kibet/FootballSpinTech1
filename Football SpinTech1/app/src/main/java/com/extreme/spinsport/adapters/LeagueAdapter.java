package com.extreme.spinsport.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.extreme.spinsport.League;
import com.extreme.spinsport.Matches;
import com.extreme.spinsport.R;
import com.extreme.spinsport.data.Lig;
import com.extreme.spinsport.utils.AdmobInterAds;
import com.extreme.spinsport.utils.ApplovinInterAd;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.MyViewHolder> {


    League mLeague;

    List<Lig> leaguelist;
    AdmobInterAds admobInterAds;
    ApplovinInterAd applovinInterAd;
    private SharedPreferences pref;
    private Context context;



    public LeagueAdapter(Context context, List<Lig> results) {
        this.context = context;
        this.leaguelist = results;
        //this.mLeague = league;

        pref = context.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);

        if(pref.getString("adsoption",null).trim().matches("admob")){
            admobInterAds = new AdmobInterAds((Activity) context);
        }
        else if(pref.getString("adsoption",null).trim().matches("applovin")){
            applovinInterAd = new ApplovinInterAd((Activity) context);
        }

    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView League;
        private ImageView liglogo;


        public MyViewHolder(View view) {
            super(view);
            League = (TextView) view.findViewById(R.id.lig);
            liglogo = (ImageView) view.findViewById(R.id.liglogo);



        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.league_layout, parent, false);


        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Lig lig = leaguelist.get(position);
        holder.League.setText(lig.getLeague());
        Picasso.get().load(lig.getLogo()).into(holder.liglogo);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent i = new Intent(view.getContext(), Matches.class);
                i.putExtra("pos",lig.getPos());

                if(pref.getString("adsoption",null).trim().matches("admob")){
                    admobInterAds.MoveNextActivityAdmobAd(i,context);
                }
                else if(pref.getString("adsoption",null).trim().matches("applovin")){
                    applovinInterAd.MoveNextActivityApplovinAd(i,context);
                }

                //view.getContext().startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return leaguelist.size();
    }
}
