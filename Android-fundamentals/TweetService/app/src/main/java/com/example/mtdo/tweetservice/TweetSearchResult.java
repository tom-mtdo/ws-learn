package com.example.mtdo.tweetservice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangdo on 6/12/2015.
 */
public class TweetSearchResult implements Parcelable {

    private List<Tweet> tweets;

    public TweetSearchResult() {
        tweets = new ArrayList<Tweet>();
    }

    @SuppressWarnings("unchecked")
    private TweetSearchResult(Parcel source){
        tweets = source.readArrayList(Tweet.class.getClassLoader());
    }

    public static final Creator<TweetSearchResult> CREATOR = new Creator<TweetSearchResult>() {
        @Override
        public TweetSearchResult createFromParcel(Parcel source) {
            return new TweetSearchResult(source);
        }

        @Override
        public TweetSearchResult[] newArray(int size) {
            return new TweetSearchResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tweets);
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public List<Tweet> getTweets() {
        return tweets;
    }
}
