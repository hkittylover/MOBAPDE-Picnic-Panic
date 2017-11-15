package edu.dlsu.mobapde.picnicpanicbeta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    Button buttonNewGame;
    Button buttonHome;
    TextView tvScore, tvHighscore, tvGameOver, tvHighscoreText, tvScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int score = getIntent().getExtras().getInt("score");
        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int highscore = dsp.getInt("highscore", -1);
        if (score > highscore || highscore == -1) {
            highscore = score;
            SharedPreferences.Editor dspEditor = dsp.edit();
            dspEditor.putInt("highscore", highscore);
            dspEditor.commit();
        }

        tvHighscore = (TextView) findViewById(R.id.tv_highscore);
        tvHighscore.setText("" + highscore);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvScore.setText("" + score);
        tvHighscoreText = (TextView) findViewById(R.id.tv_highscore_text);
        tvScoreText = (TextView) findViewById(R.id.tv_score_text);
        tvGameOver = (TextView) findViewById(R.id.tv_game_over);

        Typeface title = Typeface.createFromAsset(getAssets(),
                "fonts/dancing_script.ttf");
        tvGameOver.setTypeface(title);

        buttonNewGame = (Button) findViewById(R.id.button_new_game);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO go to game
                Intent i = new Intent();
                i.setClass(getBaseContext(), ActivityGame.class);

                startActivity(i);
                finish();
            }
        });
        buttonHome = (Button) findViewById(R.id.button_home);

        buttonHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO go to game
                Intent i = new Intent();
                i.setClass(getBaseContext(), MainActivity.class);

                startActivity(i);
                finish();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/unica_one.ttf");
        buttonHome.setTypeface(typeface);
        buttonNewGame.setTypeface(typeface);
        tvScore.setTypeface(typeface);
        tvHighscore.setTypeface(typeface);
        tvScoreText.setTypeface(typeface);
        tvHighscoreText.setTypeface(typeface);
    }
}
