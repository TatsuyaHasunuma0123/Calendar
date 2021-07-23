package com.example.y3033906.calendar_113033906.ui.notifications.calendar;

import androidx.fragment.app.FragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
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

    public static void getTweetByAttmark(String screenName, CalendarAdapter calendarAdapter, FragmentActivity notificationsFragment) {
        android.os.AsyncTask<Void, Void, String> task
                = new android.os.AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Twitter twitter = TwitterFactory.getSingleton();
                try {
                    user = twitter.showUser(screenName);
                    user.getId();
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

                    //UI変更のため、メインスレッドでの動作を行う。
                    notificationsFragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            calendarAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
    }

    public static void getTweetByHashTag (String hashTag, CalendarAdapter calendarAdapter, FragmentActivity notificationsFragment){
        android.os.AsyncTask<Void, Void, String> task
                = new android.os.AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Twitter twitter = TwitterFactory.getSingleton();
                Query query = new Query("#" + hashTag);
                query.count(100);
                int i=0;
                try {
                    QueryResult result = twitter.search(query);

                    for(twitter4j.Status status : result.getTweets()){
                        tweets[i] = new Tweet(status.getText(), status.getUser().getName(),status.getCreatedAt());
                        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
                        i++;
                    }
                    System.out.println(tweets[0].tweet);
                    //UI変更のため、メインスレッドでの動作を行う。
                    notificationsFragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            calendarAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
    }

    public static String getTweetByCalendarDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/d", Locale.US);
        for(int i = 0; i < 100; i++){
            if(tweets[i] == null)
                return null;
            else if(dateFormat.format(tweets[i].date).equals(strDate))
                return tweets[i].tweet;
        }
        return  null;
    }
}
