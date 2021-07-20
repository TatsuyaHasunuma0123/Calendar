package com.example.y3033906.calendar_113033906.ui.notifications.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class TweetModel {
    private static User user;

    public static Tweet[] tweets = new Tweet[100];

    public static class Tweet{
        //ツイートの内容
        public String tweet;
        //ツイートをした人
        public String user;
        //ツイートの日時
        public Date date;

        public static Tweet[] tweets = new Tweet[100];
        Tweet(String tweet, String user,Date date){
            this.tweet = tweet;
            this.user = user;
            this.date = date;
        }
    }

    public static void getTweetById(String screenName) {
        android.os.AsyncTask<Void, Void, String> task
                = new android.os.AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Twitter twitter = TwitterFactory.getSingleton();
                try {
                    user = twitter.verifyCredentials();
                    user = twitter.showUser(screenName);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                twitter = new TwitterFactory().getInstance();
                int i = 0;
                try {
                    ResponseList<twitter4j.Status> userStatus = twitter.getUserTimeline(user.getId(), new Paging(1, 100));
                    for (twitter4j.Status status : userStatus) {
                        tweets[i] = new Tweet(status.getText(), status.getUser().getName(), status.getCreatedAt());
                        i++;
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
    }

    public static String getTweetByCalendarDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
        for(int i = 0; i < 100; i++){
            if(tweets[i] == null)
                return "ツイートが検索されていません";
            else if(dateFormat.format(tweets[i].date).equals(strDate))
                    return tweets[i].tweet;
        }
        return  "ツイートが検索されていません";
    }
}
