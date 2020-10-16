package com.codepath.apps.restclienttemplate.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TweetDao {
    //make query on tweet and user models, embedding into the columns
    //the underscore comes from TweetWithUser
    @Query("SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id, User.* " +
            "FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5")
    List<TweetWithUser> recentItems(); //combo of tweets and users

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(Tweet... tweets); //variable number, can get any number of tweets as an array

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertModel(User...users);
}
