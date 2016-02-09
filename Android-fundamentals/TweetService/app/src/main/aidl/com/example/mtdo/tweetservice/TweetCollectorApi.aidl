// TweetCollectorApi.aidl
package com.example.mtdo.tweetservice;

import com.example.mtdo.tweetservice.TweetSearchResult;
import com.example.mtdo.tweetservice.TweetCollectorListener;

// Declare any non-default types here with import statements

interface TweetCollectorApi {
    TweetSearchResult getLatestSearchResult();
    void addListener(TweetCollectorListener listener);
    void removeListener(TweetCollectorListener listener);
}
