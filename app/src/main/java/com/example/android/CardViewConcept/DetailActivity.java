package com.example.android.CardViewConcept;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView topicTitle = findViewById(R.id.titleDetail);
        ImageView topicImage = findViewById(R.id.TopicImagesDetail);

        //Get the drawable
        Drawable drawable = ContextCompat.getDrawable
                (this, getIntent().getIntExtra(Topic.IMAGE_KEY, 0));

        //Create a placeholder gray scrim while the image loads
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.GRAY);

        //Make it the same size as the image
        if (drawable != null) {
            gradientDrawable.setSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }

        //extract the text from the intent extra
        topicTitle.setText(getIntent().getStringExtra(Topic.TITLE_KEY));
        Glide.with(this).load(getIntent().getIntExtra(Topic.IMAGE_KEY, 0))
                .into(topicImage);
        //Glide is a class to load images


    }
}
