package com.example.y3033906.calendar_113033906.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.y3033906.calendar_113033906.R;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Button tweet = root.findViewById(R.id.button2);

        tweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.os.AsyncTask<Void, Void, String> task
                        = new android.os.AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        String word = "もっかいテスト！";
                        Twitter twitter = TwitterFactory.getSingleton();
                        try {
                            twitter4j.Status status = twitter.updateStatus(word);
                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                task.execute();
            }

        });

        Button get = root.findViewById(R.id.button3);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.AsyncTask<Void, Void, String> task
                        = new android.os.AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        Twitter twitter = new TwitterFactory().getInstance();
                        try {
                            ResponseList<twitter4j.Status> userstatus = twitter.getUserTimeline(new Paging(1,100));
                            for(twitter4j.Status status : userstatus)
                                System.out.println(status.getUser().getName()+":"+status.getText());
                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }
                        /*try {
                            User user = twitter.verifyCredentials();
                            twitter4j.Status status = user.getStatus();
                            System.out.println(status.getText());
                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }*/

                        return null;
                    }
                };
                task.execute();
            }
        });


        return root;
    }
}