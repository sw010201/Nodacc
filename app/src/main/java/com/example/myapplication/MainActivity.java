package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;




public class MainActivity extends AppCompatActivity {

    public CalendarView calendarView;
    public Button del_Btn, save_Btn, button;
    public TextView diaryTextView, textView2;
    public EditText contextEditText, fEditText;
    public ImageView imgv;

    //2022-12-11 프래그먼트
    private final int Fragment_Friend1 = 1;
    private final int Fragment_Friend2 = 2;
    private final int Fragment_Friend3 = 3;
    private final int Fragment_Friend4 = 4;
    //2022-12-13 프래그먼트
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    //2022-12-11 툴바
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    //2022-12-06 다이얼로그
    AlertDialog.Builder builder;
    //String[] friends;
    public String[] fr;


    //2022-12-10 알림
    public static final String NOTIFICATION_CHANNEL_ID = "1001";
    private CharSequence channelName = "노티피케이션 채널";
    private String description = "해당 채널에 대한 설명";
    private int importance = NotificationManager.IMPORTANCE_HIGH;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2022-12-11 상단 버튼 만들기
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 왼쪽 상단 버튼 만들기
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //왼쪽 상단 버튼 아이콘 지정


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        //2022-12-10 알림
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
            );
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.drawable.img)
                    .setContentTitle("Nodac")
                    .setContentText("약속1")
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis());

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence channelName = "노티피케이션 채널";
                String description = "해당 채널에 대한 설명";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = new NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        channelName,
                        importance
                );
                channel.setDescription(description);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }


            notificationManager.notify(1234, builder.build());

        });


        getSupportActionBar().setTitle("Nodac");

        save_Btn = (Button) findViewById(R.id.save_Btn);
        del_Btn = (Button) findViewById(R.id.del_Btn);
        diaryTextView = (TextView) findViewById(R.id.diaryTextView);
        contextEditText = (EditText) findViewById(R.id.contextEditText);
        textView2 = (TextView) findViewById(R.id.textView2);
        imgv = (ImageView) findViewById(R.id.imgv);
        fEditText = (EditText) findViewById(R.id.fEditText);

        //2022-12-06 다이얼로그
        fEditText = findViewById(R.id.fEditText);
        fEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                diaryTextView.setVisibility(View.VISIBLE);
                imgv.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                fEditText.setVisibility(View.VISIBLE);
            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        //2022-12-13 프래그먼트 연결
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_friend1, R.id.nav_friend2, R.id.nav_friend3, R.id.nav_friend4)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    //2022-12-11 네브바 상단 버튼 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() { //뒤로가기 했을 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //2022-12-06 다이얼로그
    public void showDialog() {
        fr = getResources().getStringArray(R.array.fr);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("친구");
        builder.setItems(fr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fEditText.append(fr[which]);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //2022-12-13 프래그먼트 연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}