package com.example.news_app_java;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    List<News> newsList = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;

    public CustomAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String id;
        private final TextView title;
        private final ImageView imageView;
        private final RelativeLayout cardRelativeLayout;
        private String details;
        private final Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title_card_tv);
            imageView = itemView.findViewById(R.id.image_card);
            cardRelativeLayout = itemView.findViewById(R.id.card_relative_layout);
            details = "";
            id = "";
        }

        public void setTitle(String value) {
            title.setText(value);
        }

        public void setImageView(String value) {
            if(value.equals(""))
                cardRelativeLayout.setVisibility(View.GONE);
            else
                Glide.with(context)
                    .load(value)
                    .centerCrop()
                    .into(imageView);
        }

        public void setDetails(String value) {
            details = value;
        }

        public void setId(String value) {
            id = value;
        }

        @Override
        public void onClick(View view) {
            try {
                clickListener.onItemClick(getAdapterPosition(), view);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        return new MyViewHolder(listViewItem);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.setId(news.getId());
        holder.setTitle(news.getTitle());
        holder.setImageView(news.getImageUrl());
        holder.setDetails(news.getDetails());
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }
}
