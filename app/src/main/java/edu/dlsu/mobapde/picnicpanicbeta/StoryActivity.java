package edu.dlsu.mobapde.picnicpanicbeta;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {
    int cnt = 0;
    TextView tvName, tvStory;
    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvStory = (TextView) findViewById(R.id.tv_story);
        buttonNext = (Button) findViewById(R.id.button_next);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/unica_one.ttf");
        tvName.setTypeface(typeface);
        tvStory.setTypeface(typeface);
        tvStory.setText("\"Oh no! Somebody blew up the amusement park!\"");
        buttonNext.setTypeface(typeface);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnt == 0) {
                    tvStory.setText("\"We need to save all the food so we don’t waste any!\"");
                    buttonNext.setText("START");
                    cnt++;
                }
                else {
                    Intent i = new Intent();
                    i.setClass(v.getContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
