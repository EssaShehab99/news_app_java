package com.example.news_app_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewsManager extends AppCompatActivity {
    ProgressBar progressBAR;
    int SELECT_PICTURE = 200;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView imageManager;
    Uri filePath;
    FirebaseFirestore db;
    EditText titleTextView, detailsTextView;
    StorageReference storageRef;
    String id;
    String imageUrl;
    boolean isEdit = false;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_manager);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();
        progressBAR = findViewById(R.id.progress_bar);
        titleTextView = findViewById(R.id.title_et);
        detailsTextView = findViewById(R.id.details_et);
        imageManager = findViewById(R.id.image_manager);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && (!bundle.isEmpty())) {
            isEdit = true;
            news = new News(bundle.getString("id"), bundle.getString("title"), bundle.getString("details"), bundle.getString("imageUrl"));
            id = news.getId();
            titleTextView.setText(news.getTitle());
            detailsTextView.setText(news.getDetails());
            if (!news.getImageUrl().equals("")) {
                imageManager.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(bundle.getString("imageUrl"))
                        .centerCrop()
                        .into(imageManager);
            }
        }
        findViewById(R.id.delete_btn).setOnClickListener(v -> {
            deletePost();
        });
        Button postBTN = findViewById(R.id.post_btn);
        postBTN.setText(isEdit ? "تعديل" : "نشر");
        postBTN.setOnClickListener(v -> {
            if (validate()) {
                uploadFile(filePath);
            }
        });
        findViewById(R.id.back_btn).setOnClickListener(v -> finish());
        findViewById(R.id.image_btn).setOnClickListener(v -> {
            imageChooser();
        });
        progressBAR.setVisibility(View.GONE);
    }


    void uploadFile(Uri imageLocaleUrl) {
        progressBAR.setVisibility(View.VISIBLE);
        try {
            if (imageLocaleUrl == null) {
                if (isEdit) {
                    updatePost(news.getImageUrl());
                } else {
                    setPost(null);
                }
            } else {
                StorageReference ref
                        = storageReference
                        .child(UUID.randomUUID().toString());
                ref.putFile(imageLocaleUrl)
                        .addOnSuccessListener(
                                taskSnapshot -> {
                                    Task<Uri> result = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                                    result.addOnSuccessListener(uri -> {
                                        String downloadLink = uri.toString();
                                        if (isEdit) {
                                            updatePost(downloadLink);
                                        } else {
                                            setPost(downloadLink);
                                        }

                                    });
                                })

                        .addOnFailureListener(e -> {
                            showMessage("لم يتم النشر");

                        })
                        .addOnProgressListener(
                                taskSnapshot -> {
                                });
            }
        } catch (Exception e) {
            showMessage("لم يتم النشر");
        }
    }

    void deletePost(){

        db.collection("post").document(id).delete().addOnSuccessListener(command -> {
            showMessage("حذف ناجح");
        });
    }
    void updatePost(String urlFile) {
        try {
            db.collection("post").document(id)
                    .update(new News(null, titleTextView.getText().toString(), detailsTextView.getText().toString(), urlFile).toMap())
                    .addOnSuccessListener(documentReference -> {
                        showMessage("تم التعديل بنجاح");
                    })
                    .addOnFailureListener(e ->
                            {
                                showMessage("لم يتم التعديل");
                            }
                    );
        } catch (Exception e) {
            showMessage("لم يتم التعديل");
        }
    }

    void setPost(String urlFile) {
        try {
            db.collection("post")
                    .add(new News(null, titleTextView.getText().toString(), detailsTextView.getText().toString(), urlFile).toMap())
                    .addOnSuccessListener(documentReference -> {
                        showMessage("تم النشر بنجاح");
                    })
                    .addOnFailureListener(e ->
                            {
                                showMessage("لم يتم النشر");
                            }
                    );
        } catch (Exception e) {
            showMessage("لم يتم النشر");
        }
    }

    void showMessage(String text) {
        progressBAR.setVisibility(View.GONE);
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    imageManager.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath));
                    imageManager.setVisibility(View.VISIBLE);

                } catch (Exception ignored) {
                }
            }
        }
    }

    private Boolean validate() {

        if (TextUtils.isEmpty(titleTextView.getText())) {
            showToast(R.string.invalid_data);
            return false;
        } else if (TextUtils.isEmpty(detailsTextView.getText())) {
            showToast(R.string.invalid_data);
            return false;
        }
        return true;
    }

    private void showToast(int text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}