package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

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
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public CalendarView calendarView;
    public Button del_Btn,save_Btn, button;
    public TextView diaryTextView,textView2;
    public EditText contextEditText, fEditText;
    public ImageView imgv;

    //2022-12-06 다이얼로그
    AlertDialog.Builder builder;
    //String[] friends;
    public String[] fr;


    //2022-12-10 알림
    public static final String NOTIFICATION_CHANNEL_ID = "1001";
    private CharSequence channelName = "노티피케이션 채널";
    private String description = "해당 채널에 대한 설명";
    private int importance = NotificationManager.IMPORTANCE_HIGH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2022-12-10 알림


        Button button = findViewById(R.id.button);
        button.setOnClickListener(v->{

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
                NotificationCompat.Builder builder=new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.img)
                        .setContentTitle("Nodac")
                        .setContentText("약속1")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis());

                NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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


                notificationManager.notify(1234,builder.build());

        });






        getSupportActionBar().setTitle("Nodac");

        save_Btn = (Button)findViewById(R.id.save_Btn) ;
        del_Btn = (Button)findViewById(R.id.del_Btn) ;
        diaryTextView = (TextView)findViewById(R.id.diaryTextView);
        contextEditText = (EditText) findViewById(R.id.contextEditText);
        textView2 = (TextView)findViewById(R.id.textView2);
        imgv = (ImageView)findViewById(R.id.imgv);
        fEditText = (EditText) findViewById(R.id.fEditText);

        //2022-12-06 다이얼로그
        fEditText=findViewById(R.id.fEditText);
        fEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        calendarView=findViewById(R.id.calendarView);
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
    }


    //2022-12-06 다이얼로그
    public void showDialog(){
        fr=getResources().getStringArray(R.array.fr);
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("친구");
        builder.setItems(fr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fEditText.append(fr [which]);

                        }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }


}