package com.example.y3033906.calendar_113033906.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.y3033906.calendar_113033906.R;
import com.example.y3033906.calendar_113033906.calendar.CalendarAdapter;//hanakuso

public class NotificationsViewModel extends ViewModel {

    private TextView titleText;
    private Button prevButton, nextButton;
    private CalendarAdapter mCalendarAdapter;
    private GridView calendarGridView;
    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");

        prevButton = prevButton.findViewById(R.id.prevButton);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        nextButton = nextButton.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        calendarGridView = calendarGridView.findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(this);
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());
    }

    public LiveData<String> getText() {
        return mText;
    }
}