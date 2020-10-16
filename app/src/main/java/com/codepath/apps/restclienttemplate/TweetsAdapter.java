package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    //pass context and list of tweets
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tweetView = LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        return new ViewHolder(tweetView);
    }

    //bind value based on the position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinkifiedTextView tvBody;
        public TextView tvScreenName;
        public TextView tvCreatedAt;
        public ImageView ivProfileImage;
        public RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            container = itemView.findViewById(R.id.item_tweet);
        }

        public void bind(final Tweet tweet) {
            tvName.setText(tweet.user.name);
            tvScreenName.setText("@"+tweet.user.screenName);
            tvBody.setText(tweet.body);
            tvCreatedAt.setText(tweet.getRelativeTimeAgo());
            int radius = 100; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(tweet.user.imageURL)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivProfileImage);
            container.setOnClickListener(new View.OnClickListener(){
                //2. Navigate to a new activity on tap
                @Override
                public void onClick(View v) {
                    Log.i("Adapter", "on click!");
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    Pair<View, String> p1 = Pair.create((View)ivProfileImage, "profileImage");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)context, p1);
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }
}
