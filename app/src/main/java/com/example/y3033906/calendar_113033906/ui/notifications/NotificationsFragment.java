package com.example.y3033906.calendar_113033906.ui.notifications;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.y3033906.calendar_113033906.MainActivity;
import com.example.y3033906.calendar_113033906.MyApplication;
import com.example.y3033906.calendar_113033906.R;
import com.example.y3033906.calendar_113033906.ui.notifications.calendar.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView titleText;
    private CalendarAdapter mCalendarAdapter;
    private RelativeLayout beforeLayout;

    public static Tweet[] tweets = new Tweet[100];
    public class Tweet{
        //ツイートの内容
        public String tweet;
        //ツイートをした人
        public String user;
        //ツイートの日時
        public Date date;
        Tweet(String tweet, String user,Date date){
            this.tweet = tweet;
            this.user = user;
            this.date = date;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        titleText = root.findViewById(R.id.titleText);

        EditText editText = root.findViewById(R.id.editText);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    MainActivity.inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    SpannableStringBuilder sb = (SpannableStringBuilder)editText.getText();
                    String str = sb.toString();
                    Integer id = Integer.valueOf(str);
                    android.os.AsyncTask<Void, Void, String> task
                            = new android.os.AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            Twitter twitter = new TwitterFactory().getInstance();
                            int i = 0;
                            try {
                                ResponseList<twitter4j.Status> userstatus = twitter.getUserTimeline(id,new Paging(1,100));
                                for(twitter4j.Status status : userstatus) {
                                    Date date =status.getCreatedAt();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                                    tweets[i] = new Tweet(status.getText(),status.getUser().getName(),status.getCreatedAt());

                                    i += 1;

                                }

                            } catch (TwitterException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    };
                    task.execute();
                    return true;
                }
                return false;
            }
        });

        ImageButton prevButton = root.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        ImageButton nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        GridView calendarGridView = root.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(MyApplication.getAppContext());

        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());
        calendarGridView.setOnItemClickListener(this::onItemClick);
        return root;
    }

    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
        RelativeLayout layout = (RelativeLayout) view;
        layout.getChildAt(1).setVisibility(View.GONE);
        if((beforeLayout != null) && (beforeLayout != layout)) {
            beforeLayout.getChildAt(1).setVisibility(View.VISIBLE);
        }
        beforeLayout = (RelativeLayout) view;
    }
}
