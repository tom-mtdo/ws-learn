package com.example.mtdo.tweetservice;

/**
 * Created by thangdo on 6/12/2015.
 * just simulate tweet search for now
 */
public final class TweetSearcher {

    private static final String TAG = TweetSearcher.class.getSimpleName();

    public TweetSearchResult search(){
        TweetSearchResult result = new TweetSearchResult();
        Tweet tweet;

        String author = null;
        String text = null;
        String url = null;

        for (int i = 1; i <= 3; i++){

            author  = "author " + i;
            text    = "text " + i;
            url     = "url " + i;

            tweet   = new Tweet(author, text, url);
            result.addTweet(tweet);
        }

        return result;
    }
}
