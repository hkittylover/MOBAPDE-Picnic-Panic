package edu.dlsu.mobapde.picnicpanicbeta;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    ActivityGame activityGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.button_start);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/unica_one.ttf");
        buttonStart.setTypeface(typeface);

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
}
