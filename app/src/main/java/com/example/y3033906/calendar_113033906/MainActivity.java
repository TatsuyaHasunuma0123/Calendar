package com.example.y3033906.calendar_113033906;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import twitter4j.auth.RequestToken;

public class MainActivity extends AppCompatActivity {

    public static InputMethodManager inputMethodManager;
    static final String CALLBACK_URL = "callback://";
    static final String CONSUMER_ID = "5WGptOlaXuQjVWRzBAGxujlPy";
    static final String CONSUMER_SECRET = "o2KjptTGb5q0wSHvuWrcA9ixAdLQbdzwkx3lDTpCRjEFF4CUbU";
    private RequestToken _reqToken = null;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        context = getApplicationContext();
    }
}