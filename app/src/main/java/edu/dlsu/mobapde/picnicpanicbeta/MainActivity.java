package edu.dlsu.mobapde.picnicpanicbeta;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    ActivityGame activityGame;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    TextView tvHSLabel;
    TextView tvHS;
    TextView tvCoinAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(this);




        boolean start = dsp.getBoolean("start", false);
        if(!start) {
            SharedPreferences.Editor dspEditor = dsp.edit();
            dspEditor.putBoolean("start", true);
            dspEditor.commit();
            Intent i = new Intent();
            i.setClass(this, StoryActivity.class);
            startActivity(i);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        Log.i("ALARMMMMYA", "" + alarmMgr.getNextAlarmClock());
        Log.i("ALARMMMMY", "" + Calendar.getInstance().getTimeInMillis());

        tvHSLabel = (TextView) findViewById(R.id.tv_hs_label);
        tvHS = (TextView) findViewById(R.id.tv_hs);
        tvCoinAmt = (TextView) findViewById(R.id.tv_coin_amt);
        buttonStart = (Button) findViewById(R.id.button_start);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/unica_one.ttf");
        buttonStart.setTypeface(typeface);

        tvHS.setTypeface(typeface);
        tvHSLabel.setTypeface(typeface);
        tvCoinAmt.setTypeface(typeface);


        tvHS.setText("" + dsp.getInt("highscore", -1));
        tvCoinAmt.setText("" + dsp.getInt("coinTotal", 0));


        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getBaseContext(), ActivityGame.class);

                startActivity(i);

                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        ExitDialog ex = new ExitDialog();
        ex.show(getSupportFragmentManager(), "");
    }
}
