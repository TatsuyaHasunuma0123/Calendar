
 package com.example.y3033906.calendar_113033906.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.y3033906.calendar_113033906.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView textView,textView2;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.textView);
        textView.setBackgroundColor(Color.BLACK);
        textView2 = root.findViewById(R.id.textView2);
        textView2.setBackgroundColor(Color.BLACK);
        button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    httpRequest("https://api.tronalddump.io/random/quote");
                } catch (Exception e) {
                    Log.e("Hoge", e.getMessage());
                }
            }
        });

        return root;
    }

    public void httpRequest(String url) throws IOException{

        //OkHttpClinet生成
        OkHttpClient client = new OkHttpClient();

        //request生成
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "*/*")
                .build();

        //非同期リクエスト
        client.newCall(request)
                .enqueue(new Callback() {

                    //エラーのとき
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("Hoge",e.getMessage());
                    }

                    //正常のとき
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        //response取り出し
                        final String jsonStr = response.body().string();
                        Log.d("Hoge","jsonStr=" + jsonStr);

                        //JSON処理
                        try{
                            //jsonパース
                            JSONObject json = new JSONObject(jsonStr);
                            final String value = json.getString("value");
                            translate(value);

                            //親スレッドUI更新
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(value);
                                }
                            });

                        }catch(Exception e){
                            Log.e("Hoge",e.getMessage());
                        }
                    }
                });
    }

    private void translate(String value){
        System.out.println(value);
        //OkHttpClinet生成
        OkHttpClient client = new OkHttpClient();
        String url ="https://script.google.com/macros/s/AKfycbzuDmbOrcwJBrUtvz8bQ13rRR4BZgYdjPy3850B089425l5AZ3D-yV1LiVarQYrkmp5Zw/exec";
        //String url = "https://824a39e2b0c5.ngrok.io/api/test";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        //set Request parameters
        Map<String, String> params = new HashMap<>();
        params.put("text", value);
        params.put("source", "en");
        params.put("target", "ja");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            params.forEach(urlBuilder::addQueryParameter);
        }

        //request生成
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Accept","*/*")
                .build();

        //非同期リクエスト
        client.newCall(request)
                .enqueue(new Callback() {

                    //エラーのとき
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("Hoge",e.getMessage());
                    }

                    //正常のとき
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        //response取り出し
                        final String jsonStr = response.body().string();
                        Log.d("Hoge","jsonStr=" + jsonStr);

                        //JSON処理
                        try{
                            //jsonパース
                            //JSONObject json = new JSONObject(jsonStr);
                            //final String value = json.getString("value");


                            //親スレッドUI更新
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println(jsonStr);
                                    textView2.setText(jsonStr);
                                }
                            });

                        }catch(Exception e){
                            Log.e("Hoge",e.getMessage());
                        }
                    }
                });
    }
}


