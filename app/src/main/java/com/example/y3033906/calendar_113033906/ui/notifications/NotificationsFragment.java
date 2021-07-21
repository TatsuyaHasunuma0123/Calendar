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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.y3033906.calendar_113033906.MainActivity;
import com.example.y3033906.calendar_113033906.MyApplication;
import com.example.y3033906.calendar_113033906.R;
import com.example.y3033906.calendar_113033906.ui.notifications.calendar.CalendarAdapter;

import soup.neumorphism.NeumorphImageButton;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView titleText,tweetText;
    private CalendarAdapter mCalendarAdapter;
    private RelativeLayout beforeLayout;
    private  View root,neumorphView;
    private  ViewGroup p;
    private boolean isNeumorphShow;
    private NeumorphImageButton hashTagButton;
    private int FLAT = 0,PRESSED = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        //NotificationsFragment self = this;
        FragmentActivity self = getActivity();

        //Fragmentを取得
        root = inflater.inflate(R.layout.fragment_notifications, container, false);
        neumorphView = root.findViewById(R.id.neumorphTweetCardView);
        p = (ViewGroup) neumorphView.getParent();
        p.removeView(neumorphView);
        isNeumorphShow = false;

        //タイトルテキスト(ex.「2021.July」の表示部)を取得
        titleText = root.findViewById(R.id.titleText);


        /*---------------------------------EditTextの処理設定--------------------------------------*/
        EditText editText = root.findViewById(R.id.editText);

        //https://www.itcowork.co.jp/blog/?p=864
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    MainActivity.inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
                return false;
            }
        });
        /*----------------------------------------------------------------------------------------*/

        /*----------------------------------------ボタンの処理-------------------------------------*/
        //「検索ボタン」
        ImageButton searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((SpannableStringBuilder)editText.getText() != null) {
                    SpannableStringBuilder sb = (SpannableStringBuilder) editText.getText();
                    String str = sb.toString();
                    mCalendarAdapter.callTweetByScreenName(str,self);
                }
                //キーボードを消す
                MainActivity.inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        //「＠」ボタン
        NeumorphImageButton atButton = root.findViewById(R.id.atMarkButton);
        atButton.setShapeType(PRESSED);
        atButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atButton.setShapeType(PRESSED);
                hashTagButton.setShapeType(FLAT);
                editText.setHint("search by ScreenName...");
            }
        });

        //「＃」ボタン
        hashTagButton = root.findViewById(R.id.hashTagButton);
        hashTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atButton.setShapeType(FLAT);
                hashTagButton.setShapeType(PRESSED);
                editText.setHint("search by ID...");
            }
        });

        //「＜」ボタン
        ImageButton prevButton = root.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        //「＞」ボタン
        ImageButton nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        /*----------------------------------------------------------------------------------------*/

        //カレンダーの表示
        GridView calendarGridView = root.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(MyApplication.getAppContext());
        titleText.setText(mCalendarAdapter.getTitle());
        calendarGridView.setAdapter(mCalendarAdapter);

        calendarGridView.setOnItemClickListener(this::onItemClick);
        return root;
    }

    //カレンダーの日付をタッチした時にレイアウトを変更する
    public void onItemClick(AdapterView<?>parent, View view, int position, long id) {
        RelativeLayout layout = (RelativeLayout) view;
        layout.getChildAt(1).setVisibility(View.GONE);
        if ((beforeLayout != null) && (beforeLayout != layout)) {
            beforeLayout.getChildAt(1).setVisibility(View.VISIBLE);
        }

        if (!isNeumorphShow) {
            p.addView(neumorphView);
            isNeumorphShow = true;
            tweetText = root.findViewById(R.id.tweetTextView);
        }

        String string_TweetText = CalendarAdapter.getTweetFromView(view);

        tweetText.setText(string_TweetText);

        beforeLayout = (RelativeLayout) view;
    }


}
