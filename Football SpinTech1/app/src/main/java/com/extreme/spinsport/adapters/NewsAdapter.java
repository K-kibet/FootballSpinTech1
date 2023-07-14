package com.extreme.spinsport.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.extreme.spinsport.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private ArrayList<String> mImagelinkList = new ArrayList<>();

    private Context mContext;
    private int lastPosition = -1;

    public NewsAdapter(Context context, ArrayList<String> mImagelinkList) {

        this.mContext = context;
        this.mImagelinkList = mImagelinkList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_element, parent, false);





        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Picasso.get().load(mImagelinkList.get(position)).into(holder.image);

        Toast.makeText(mContext, mImagelinkList.get(1), Toast.LENGTH_SHORT).show();

    }



    @Override
    public int getItemCount() {
        return mImagelinkList.size();
    }
}
