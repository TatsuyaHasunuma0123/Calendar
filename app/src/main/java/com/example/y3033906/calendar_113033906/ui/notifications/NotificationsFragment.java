package com.example.y3033906.calendar_113033906.ui.notifications;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.y3033906.calendar_113033906.MainActivity;
import com.example.y3033906.calendar_113033906.MyApplication;
import com.example.y3033906.calendar_113033906.R;
import com.example.y3033906.calendar_113033906.ui.notifications.calendar.CalendarAdapter;
import com.example.y3033906.calendar_113033906.ui.notifications.calendar.DateManager;

import java.util.Arrays;
import java.util.List;

import soup.neumorphism.NeumorphCardView;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private TextView titleText;
    private CalendarAdapter mCalendarAdapter;
    private View beforeView;
    private String[] DAYS;
    private Color background;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        titleText = root.findViewById(R.id.titleText);
        Button prevButton = root.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        Button nextButton = root.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        GridView calendarGridView = root.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(MyApplication.getAppContext());

        DAYS = DateManager.ArrayDays;
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());
        calendarGridView.setOnItemClickListener(this::onItemClick);
        return root;
    }

    public void onItemClick(AdapterView<?>parent, View view, int position, long id){
        System.out.println(DAYS[position]);
        RelativeLayout layout = (RelativeLayout)view;
        System.out.println(layout.getChildCount());
        //view.setBackgroundColor(Color.BLACK);
        if(beforeView != null)
            //beforeView.setBackgroundColor(Color.parseColor("#EDEDED"));
            //layout.getChildAt(0)
            layout.getChildAt(1).setVisibility(View.VISIBLE);
        if(beforeView == view)
            //view.setBackgroundColor(Color.BLACK);
            layout.getChildAt(1).setVisibility(View.GONE);
        beforeView = view;
    }
}
