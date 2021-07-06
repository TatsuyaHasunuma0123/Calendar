package com.example.y3033906.calendar_113033906.Twitter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.y3033906.calendar_113033906.R;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtils {

    //SharedPrerence用のキー
    private static final String TOKEN = "762998251079421954-gTmxdKJYtNo88SUOuwIl6zoYooo9Wq7";
    private static final String TOKEN_SECRET = "NiemzxsEa9BiQrGnK1AVU4ivsSvkgGeNdYKIMwBgsTzkA";
    private static final String PREF_NAME = "twitter_access_token";


    //Twitterインスタンスの生成
    public static Twitter getTwitterInstance (Context context){

        //string.xmlで記述した設定の呼び出し
        String consumerKey = context.getString(R.string.twitter_consumer_key);
        String consumerSecret = context.getString(R.string.twitter_consumer_secret);

        //Twitterオブジェクトのインスタンス
        Twitter twitter = new TwitterFactory().getInstance();
        //twitter.setOAuthConsumer(consumerKey, consumerSecret);

        //トークンの設定
        if(hasAccessToken(context)){
            twitter.setOAuthAccessToken(loadAccessToken(context));
        }

        return twitter;
    }

    //トークンの格納
    public static void storeAccessToken(Context context, AccessToken accessToken) {

        //トークンの設定
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, accessToken.getToken());
        editor.putString(TOKEN_SECRET, accessToken.getTokenSecret());

        //トークンの保存
        editor.commit();
    }

    //トークンの読み込み
    public static AccessToken loadAccessToken(Context context) {

        //preferenceからトークンの呼び出し
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        String tokenSecret = preferences.getString(TOKEN_SECRET, null);
        if(token != null && tokenSecret != null){
            return new AccessToken(token, tokenSecret);
        }
        else{
            return null;
        }
    }

    //トークンの有無判定
    public static boolean hasAccessToken(Context context) {
        return  loadAccessToken(context) != null;
    }
}
