package com.example.y3033906.calendar_113033906.ui.notifications;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
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

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView titleText,tweetText;
    private CalendarAdapter mCalendarAdapter;
    private RelativeLayout beforeLayout;
    private Integer id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        //Fragmentを取得
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        //タイトルテキスト(ex.「2021.July」の表示部)を取得
        titleText = root.findViewById(R.id.titleText);
        tweetText = root.findViewById(R.id.tweetTextView);


        /*---------------------------------EditTextの処理設定--------------------------------------*/
        EditText editText = root.findViewById(R.id.editText);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    MainActivity.inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    SpannableStringBuilder sb = (SpannableStringBuilder)editText.getText();
                    String str = sb.toString();
                    id = Integer.valueOf(str);
                    //CalendarAdapter.callTweetById(id);
                }
                return false;
            }
        });
        /*----------------------------------------------------------------------------------------*/

        /*----------------------------------------ボタンの処理-------------------------------------*/
        //「検索ボタン」
        Button searchButton = root.findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == null)
                    System.out.println("空にだ");
                CalendarAdapter.callTweetById(id);
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
    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
        RelativeLayout layout = (RelativeLayout) view;
        layout.getChildAt(1).setVisibility(View.GONE);
        if((beforeLayout != null) && (beforeLayout != layout)) {
            beforeLayout.getChildAt(1).setVisibility(View.VISIBLE);
        }

        String string_TweetText = CalendarAdapter.getTweetFromView(view);
        tweetText.setText(string_TweetText);
        beforeLayout = (RelativeLayout) view;
    }
}
