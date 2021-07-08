package com.example.y3033906.calendar_113033906.ui.notifications.calendar;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.y3033906.calendar_113033906.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.VISIBLE;

public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray;
    private final Context mContext;
    private final DateManager mDateManager;
    private final LayoutInflater mLayoutInflater;



    //カスタムセルを拡張したらここでWigetを定義
    public static class ViewHolder {
        public TextView pressedLayerDateText;
        public TextView flatLayerDateText;
        public TextView normalLayerDateText;
        public View pressedNewMorphView;
        public View flatNewMorphView;
    }

    public CalendarAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
                holder = new ViewHolder();
                holder.pressedLayerDateText = convertView.findViewById(R.id.pressedLayerDateText);
                holder.flatLayerDateText    = convertView.findViewById(R.id.flatLayerDateText);
                holder.normalLayerDateText  = convertView.findViewById(R.id.normalLayerDateText);
                holder.pressedNewMorphView  = convertView.findViewById(R.id.pressedNeumorphView);
                holder.flatNewMorphView     = convertView.findViewById(R.id.flatNeumorphView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

        holder.normalLayerDateText.setBackgroundColor(Color.parseColor("#EDEDED"));
        holder.flatNewMorphView.setBackgroundColor(Color.parseColor("#EDEDED"));
        holder.normalLayerDateText.setVisibility(VISIBLE);

        //セルのサイズを指定
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth() / 7 - (int) dp, parent.getWidth() / 7 - (int) dp - (parent.getWidth() / 7 - (int) dp) / 8);
        convertView.setLayoutParams(params);

        if((DateManager.dayOfWeek <= position) && (position < DateManager.dayOfWeek + DateManager.dayOfMonth)) {
            //日付のみ表示させる
            SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.US);
            SimpleDateFormat compareFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            holder.pressedLayerDateText.setText(dateFormat.format(dateArray.get(position)));
            holder.flatLayerDateText.setText(dateFormat.format(dateArray.get(position)));
            holder.normalLayerDateText.setText(dateFormat.format(dateArray.get(position)));



            if (TweetModel.tweets[0] != null) {
                for (int i = 0; i < TweetModel.tweets.length; i++) {
                    if (compareFormat.format(TweetModel.tweets[i].date).compareTo(compareFormat.format(dateArray.get(position))) == 0) {
                        holder.normalLayerDateText.setVisibility(View.GONE);
                        holder.flatNewMorphView.setBackgroundColor(Color.parseColor("#00ACEE"));
                        holder.pressedNewMorphView.setBackgroundColor(Color.parseColor("#267CA7"));
                    }
                }
            }
        }

        else {
            holder.pressedLayerDateText.setText("");
            holder.flatLayerDateText.setText("");
            holder.normalLayerDateText.setText("");
        }

        //日曜日を赤、土曜日を青に
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;

            default:
                colorId = Color.BLACK;
                break;
        }
        holder.normalLayerDateText.setTextColor(colorId);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    //表示月を取得
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MMMM", Locale.US);
        //System.out.println(format.format(DateManager.mCalendar.getTime()));
        return format.format(DateManager.mCalendar.getTime());
    }

    //翌月表示
    public void nextMonth(){
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth(){
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    public static void callTweetById(Integer id){
        TweetModel.getTweetById(id);
    }

    public static String getTweetFromView(View view) {
        View settingView = view.findViewById(R.id.normalLayerDateText);
        TextView txtDate = settingView.findViewById(R.id.normalLayerDateText);
        String strDate = txtDate.getText().toString();
        String tweet = TweetModel.getTweetByCalendarDate(strDate);
        return tweet;
    }



}
