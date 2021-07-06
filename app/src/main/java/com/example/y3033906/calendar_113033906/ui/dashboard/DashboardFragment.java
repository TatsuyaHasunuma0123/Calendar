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

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

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
                        String word = "twitter4jを削除";
                        Twitter twitter = TwitterFactory.getSingleton();
                        try {
                            User user = twitter.verifyCredentials();
                            System.out.println(user.getId());
                            System.out.println(user.getName());

                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }
                        //twitter.setOAuthConsumer("5WGptOlaXuQjVWRzBAGxujlPy","o2KjptTGb5q0wSHvuWrcA9ixAdLQbdzwkx3lDTpCRjEFF4CUbU");
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

        Button AOuth = root.findViewById(R.id.button3);
        AOuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ここにAOuth認証
            }
        });


        return root;
    }
}