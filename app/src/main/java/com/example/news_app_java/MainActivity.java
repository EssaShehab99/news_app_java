package com.example.news_app_java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomAdapter adapter;
    List<News> newsList = new ArrayList<>();
    RecyclerView recyclerView;
    public static boolean IS_LOGIN = false;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionButton=findViewById(R.id.floatingAction);
        actionButton.setVisibility(IS_LOGIN?View.VISIBLE:View.GONE);
        ImageView menuBTN = findViewById(R.id.menu_btn);
        menuBTN.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, menuBTN);
            popup.getMenuInflater().inflate(IS_LOGIN?R.menu.second_menu:R.menu.main_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.login) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent,1);
                }else   if (item.getItemId() == R.id.logout) {
                    IS_LOGIN=false;
                    actionButton.setVisibility(View.GONE);

                }
                return true;
            });
            popup.show();

        });
        fetchNews(this);

//        newsList.add(new News("1","عين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحرب","عين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحرب عين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحربعين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحرب","https://d5nunyagcicgy.cloudfront.net/external_assets/hero_examples/hair_beach_v391182663/original.jpeg"));
//        newsList.add(new News("2","عين روسيا على «دونباس»موسكو: انتهاء المرحلة الأولى من الحرب","عين","https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png"));
        adapter = new CustomAdapter(this, newsList);
        recyclerView = findViewById(R.id.recycle_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> {
            Intent intent;
            if(IS_LOGIN){
                intent = new Intent(this, NewsManager.class);
                intent.putExtra("id",newsList.get(position).getId());
                intent.putExtra("title",newsList.get(position).getTitle());
                intent.putExtra("details",newsList.get(position).getDetails());
                intent.putExtra("imageUrl",newsList.get(position).getImageUrl());
            }else {
                intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("image", newsList.get(position).getImageUrl());
                intent.putExtra("title", newsList.get(position).getTitle());
                intent.putExtra("details", newsList.get(position).getDetails());
            }
            startActivity(intent);
        });
        actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsManager.class);
            startActivity(intent);

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                actionButton.setVisibility(IS_LOGIN?View.VISIBLE:View.GONE);
            }
        }
    }

    void fetchNews(Context context) {
        FirebaseFirestore   db = FirebaseFirestore.getInstance();
        CollectionReference ref= db.collection("post");

        ref.addSnapshotListener((snapshots, e) -> {

            if (e != null) {
                showToast(R.string.error);
                return;
            }

            try {
                assert snapshots != null;
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            newsList.add(new News().fromMap(dc.getDocument().getData(), dc.getDocument().getId()));
                            break;
                        case MODIFIED:
                            newsList.remove(indexOf(dc.getDocument().getId()));
                            newsList.add(new News().fromMap(dc.getDocument().getData(), dc.getDocument().getId()));
                            break;
                        case REMOVED:
                            newsList.remove(indexOf(dc.getDocument().getId()));
                            break;
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    CustomAdapter myAdapter = new CustomAdapter(context, newsList);
                    recyclerView.setAdapter(myAdapter);
                }
            } catch (Exception exception) {
                showToast(R.string.error);
            }

        });
    }

    int indexOf(String docId) {
        for (int i = 0; i < newsList.size(); i++) {
            if (newsList.get(i).getId().equals(docId))
                return newsList.indexOf(newsList.get(i));
        }
        return 0;
    }
    private void showToast ( int text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}