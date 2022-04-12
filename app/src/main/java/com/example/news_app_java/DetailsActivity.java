package com.example.news_app_java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        ImageView imageView =findViewById(R.id.image_details);
        findViewById(R.id.back_btn).setOnClickListener(v -> finish());
        if(intent.getStringExtra("image").equals(""))
            imageView.setVisibility(View.GONE);
        else
        Glide.with(this)
                .load(intent.getStringExtra("image"))
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
        TextView titleTextView=findViewById(R.id.title_details_tv);
        TextView detailsTextView=findViewById(R.id.detail_tv);
        titleTextView.setText(intent.getStringExtra("title"));
        detailsTextView.setText(intent.getStringExtra("details"));

    }
}