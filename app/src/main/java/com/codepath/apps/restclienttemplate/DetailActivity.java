package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    TextView tvName;
    LinkifiedTextView tvBody;
    TextView tvScreenName;
    ImageView ivProfileImage;
    EditText etReply;
    Button btnTweet;
    String username;
    long tweetId;
    TwitterClient client;
    public static final int MAX_TWEET_LENGTH = 280;
    public static final String TAG = "DetailActivity";

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
        username = "@"+tweet.user.screenName;
        tweetId = tweet.id;
        tvScreenName.setText(username);
        tvBody.setText(tweet.body);
        int radius = 100; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        Glide.with(this)
                .load(tweet.user.imageURL)
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(ivProfileImage);
        client = TwitterApplication.getRestClient(this);

        etReply = findViewById(R.id.etReply);
        btnTweet = findViewById(R.id.btnTweet);

        //set click listener on button - publish once it's tapped
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //make sure the tweet length is not too short (0) or long (280 chars)
                String tweetContent = etReply.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Sorry, you cannot post an empty Tweet!",Toast.LENGTH_LONG).show();
                    return; //don't make api call
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(DetailActivity.this, "Sorry, this Tweet is too long!",Toast.LENGTH_LONG).show();
                    return; //don't make api call
                }
                Toast.makeText(DetailActivity.this, tweetContent,Toast.LENGTH_LONG).show();

                //make api call to twitter to publish the tweet
                client.replyToTweet(tweetContent, tweetId, username, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to reply to tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Replied tweet: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, intent);
                            finish();
                        } catch (JSONException e) {
                            Log.i(TAG, "onFailure to reply to tweet" + e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                    }
                });
            }
        });

    }
}
