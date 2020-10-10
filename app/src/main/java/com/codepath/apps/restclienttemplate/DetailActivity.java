package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {
    TextView tvName;
    LinkifiedTextView tvBody;
    TextView tvScreenName;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail);
        setTitle("");

        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@"+tweet.user.screenName);
        tvBody.setText(tweet.body);
        int radius = 100; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
//        Glide.with(context)
//                .load(tweet.user.imageURL)
//                .transform(new RoundedCornersTransformation(radius, margin))
//                .into(ivProfileImage);

    }
}
